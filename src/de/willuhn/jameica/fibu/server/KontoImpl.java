/**********************************************************************
 *
 * Copyright (c) 2004 Olaf Willuhn
 * All rights reserved.
 * 
 * This software is copyrighted work licensed under the terms of the
 * Jameica License.  Please consult the file "LICENSE" for details. 
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.server;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import de.willuhn.datasource.BeanUtil;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.ResultSetExtractor;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.rmi.Anfangsbestand;
import de.willuhn.jameica.fibu.rmi.Anlagevermoegen;
import de.willuhn.jameica.fibu.rmi.Buchung;
import de.willuhn.jameica.fibu.rmi.Buchungstemplate;
import de.willuhn.jameica.fibu.rmi.DBService;
import de.willuhn.jameica.fibu.rmi.Geschaeftsjahr;
import de.willuhn.jameica.fibu.rmi.HilfsBuchung;
import de.willuhn.jameica.fibu.rmi.Kontenrahmen;
import de.willuhn.jameica.fibu.rmi.Konto;
import de.willuhn.jameica.fibu.rmi.Kontoart;
import de.willuhn.jameica.fibu.rmi.Kontotyp;
import de.willuhn.jameica.fibu.rmi.Steuer;
import de.willuhn.jameica.fibu.rmi.Transfer;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.util.DateUtil;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * @author willuhn
 */
public class KontoImpl extends AbstractUserObjectImpl implements Konto
{
  private I18N i18n = null;
  
  /**
   * Erzeugt ein neues Konto.
   * @throws RemoteException
   */
  public KontoImpl() throws RemoteException
  {
    super();
    this.i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getTableName()
   */
  protected String getTableName()
  {
    return "konto";
  }
  
  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getKontonummer()
   */
  public String getKontonummer() throws RemoteException
  {
    return (String) getAttribute("kontonummer");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getKontenrahmen()
   */
  public Kontenrahmen getKontenrahmen() throws RemoteException
  {
    return (Kontenrahmen) getAttribute("kontenrahmen_id");
  }

  /**
   * @see de.willuhn.datasource.GenericObject#getPrimaryAttribute()
   */
  public String getPrimaryAttribute() throws RemoteException
  {
    return "kontonummer";
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getSaldo(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr)
   */
  public double getSaldo(Geschaeftsjahr jahr) throws RemoteException
  {
    // das ist ein neues Konto. Da gibts noch keinen Umsatz.
    if (getID() == null || getID().length() == 0)
      return 0;

    Double d = SaldenCache.get(jahr,this.getKontonummer());
    if (d != null)
      return d.doubleValue();

    double saldo = 0.0d;
    Anfangsbestand a = getAnfangsbestand(jahr);
    if (a != null)
      saldo = a.getBetrag();

    saldo += getUmsatz(jahr);
    SaldenCache.put(jahr,this.getKontonummer(),new Double(saldo));
    return saldo;
  }
  
  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getSaldo(java.util.Date)
   */
  @Override
  public double getSaldo(Date date) throws RemoteException
  {
    // das ist ein neues Konto. Von daher wissen wir den Saldo natuerlich noch nicht ;)
    if (getID() == null || getID().length() == 0 || date == null)
      return 0;

    Geschaeftsjahr jahr = findGeschaeftsjahr(date);
    if (jahr == null)
    {
      Logger.warn("no geschaeftsjahr found for date " + date);
      return 0.0d;
    }

    double saldo = 0.0d;
    Anfangsbestand a = getAnfangsbestand(jahr);
    if (a != null)
      saldo = a.getBetrag();

    saldo += getUmsatz(jahr,null,date);
    return saldo;
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getUmsatzAfter(java.util.Date)
   */
  public double getUmsatzAfter(Date date) throws RemoteException
  {
    // das ist ein neues Konto. Von daher wissen wir den Saldo natuerlich noch nicht ;)
    if (getID() == null || getID().length() == 0 || date == null)
      return 0;

    Geschaeftsjahr jahr = findGeschaeftsjahr(date);
    if (jahr == null)
    {
      Logger.warn("no geschaeftsjahr found for date " + date);
      return 0.0d;
    }

    // Wir setzen noch die Uhrzeit auf das Ende des Tages, um sicherzustellen,
    // dass die Buchungen des Tages dabei sind
    return getUmsatz(getHauptBuchungen(jahr,jahr.getBeginn(),DateUtil.endOfDay(date)),
                     getHilfsBuchungen(jahr,jahr.getBeginn(),DateUtil.endOfDay(date)));
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getUmsatzBefore(java.util.Date)
   */
  public double getUmsatzBefore(Date date) throws RemoteException
  {
    // Da der aktuelle Tag nicht mitzaehlen soll, ziehen wir einen ab
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE,-1);
    
    // Wir pruefen noch, ob wir mit dem neuen Datum aus dem Geschaeftsjahr fliegen
    // wuerden:
    Date dayBefore = cal.getTime();
    Geschaeftsjahr j = findGeschaeftsjahr(date);
    if (!j.check(dayBefore))
      return 0.0d; // Jepp, also liefern wir 0.0, denn wir sind ganz am Anfang des Geschaeftsjahres angekommen
    return getUmsatzAfter(dayBefore);
  }
  
  /**
   * Sucht das zum Datum gehoerende Geschaeftsjahr beim aktuellen Mandanten.
   * @param date das Datum.
   * @return das Geschaeftsjahr oder null.
   * @throws RemoteException
   */
  private Geschaeftsjahr findGeschaeftsjahr(Date date) throws RemoteException
  {
    // Wir ermitteln das Geschaeftsjahr, in dem sich das Datum befindet
    Geschaeftsjahr jahr = Settings.getActiveGeschaeftsjahr();
    if (jahr.check(date))
      return jahr;
    
    // Ist nicht das aktuielle, dann muessen wir es suchen
    DBIterator list = jahr.getMandant().getGeschaeftsjahre();
    while (list.hasNext())
    {
      jahr = (Geschaeftsjahr) list.next();
      if (jahr.check(date))
        return jahr;
    }
    return null;
  }
  
  /**
   * Liefert den Umsatz aus der Liste der uebergebenen Buchungen.
   * @param buchungen Liste der Buchungen.
   * @param hilfsbuchungen Liste der Hilfsbuchungen.
   * @return Umsatz.
   * @throws RemoteException
   */
  private double getUmsatz(DBIterator buchungen, DBIterator hilfsbuchungen) throws RemoteException
  {
    // das ist ein neues Konto. Von daher wissen wir den Saldo natuerlich noch nicht ;)
    if (getID() == null || getID().length() == 0)
      return 0;

    double soll = 0.0d;
    double haben = 0.0d;

    int kontoArt = getKontoArt().getKontoArt();
    Kontotyp typ = getKontoTyp();
    
    // Erst die Hauptbuchungen
    while (buchungen.hasNext())
    {
      Transfer t = (Transfer) buchungen.next();
      if (t.getSollKonto().equals(this))
        soll += t.getBetrag();
      else
        haben += t.getBetrag();
    }

    // Und jetzt noch die Hilfs-Buchungen
    while (hilfsbuchungen.hasNext())
    {
      Transfer t = (Transfer) hilfsbuchungen.next();
      if (t.getSollKonto().equals(this))
        soll += t.getBetrag();
      else
        haben += t.getBetrag();
    }
    
    // Siehe auch BetriebsergebnisImpl#getEinnahmen
    if (kontoArt == Kontoart.KONTOART_ERLOES || (kontoArt == Kontoart.KONTOART_STEUER && typ != null && typ.getKontoTyp() == Kontotyp.KONTOTYP_EINNAHME))
      return haben - soll;
    return soll - haben;
  }
  
  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getUmsatz(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr)
   */
  public double getUmsatz(Geschaeftsjahr jahr) throws RemoteException
  {
    return getUmsatz(getHauptBuchungen(jahr), getHilfsBuchungen(jahr));
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getUmsatz(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr, java.util.Date, java.util.Date)
   */
  public double getUmsatz(Geschaeftsjahr jahr, Date von, Date bis)
      throws RemoteException
  {
    return getUmsatz(getHauptBuchungen(jahr, von, bis),
        getHilfsBuchungen(jahr, von, bis));
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getName()
   */
  public String getName() throws RemoteException
  {
    return (String) getAttribute("name");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getKontoArt()
   */
  public Kontoart getKontoArt() throws RemoteException
  {
    Integer i = (Integer) super.getAttribute("kontoart_id");
    if (i == null)
      return null;
   
    Cache cache = Cache.get(Kontoart.class,true);
    return (Kontoart) cache.get(i);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getSteuer()
   */
  public Steuer getSteuer() throws RemoteException
  {
    Integer i = (Integer) super.getAttribute("steuer_id");
    if (i == null)
      return null;
   
    Cache cache = Cache.get(Steuer.class,true);
    return (Steuer) cache.get(i);
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#delete()
   */
  @Override
  public void delete() throws RemoteException, ApplicationException
  {
    super.delete();
    Cache.clear(Konto.class);
  }
  
  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#store()
   */
  @Override
  public void store() throws RemoteException, ApplicationException
  {
    super.store();
    Cache.clear(Konto.class);
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#deleteCheck()
   */
  protected void deleteCheck() throws ApplicationException
  {
    super.deleteCheck();
    try
    {
      DBIterator list = getService().createList(Steuer.class);
      list.addFilter("steuerkonto_id = " + this.getID());
      if (list.hasNext())
        throw new ApplicationException(i18n.tr("Das Konto ist als Sammelkonto einem Steuersatz zugeordnet."));

      list = getService().createList(Anfangsbestand.class);
      list.addFilter("konto_id = " + this.getID());
      if (list.hasNext())
        throw new ApplicationException(i18n.tr("Das Konto besitzt bereits einen Anfangsbestand. L�schen Sie zuerst diesen."));

      list = getService().createList(Buchung.class);
      list.addFilter("habenkonto_id = " + this.getID() + " OR sollkonto_id = " + this.getID());
      if (list.hasNext())
        throw new ApplicationException(i18n.tr("Es existieren bereits Buchungen auf diesem Konto."));

      list = getService().createList(Buchungstemplate.class);
      list.addFilter("habenkonto_id = " + this.getID() + " OR sollkonto_id = " + this.getID());
      if (list.hasNext())
        throw new ApplicationException(i18n.tr("Es existieren bereits Buchungsvorlagen auf diesem Konto."));

      list = getService().createList(Anlagevermoegen.class);
      list.addFilter("konto_id = " + this.getID());
      
      if (list.hasNext())
        throw new ApplicationException(i18n.tr("Das Konto ist einem Anlage-Gegenstand zugeordnet."));
    }
    catch (RemoteException e)
    {
      Logger.error("unable to check konto",e);
      throw new ApplicationException(i18n.tr("Fehler beim L�schen des Kontos"));
    }
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#insertCheck()
   */
  protected void insertCheck() throws ApplicationException
  {
    super.insertCheck();
    try {
      Kontenrahmen kr = getKontenrahmen();
      if (kr == null)
        throw new ApplicationException(i18n.tr("Bitte w�hlen Sie einen Kontenrahmen aus."));

      String name = (String) getAttribute("name");
      if (name == null || "".equals(name))
        throw new ApplicationException(i18n.tr("Bitte geben Sie einen Namen f�r das Konto ein."));
      
      String kontonummer = (String) getAttribute("kontonummer");
      if (kontonummer == null || "".equals(kontonummer))
        throw new ApplicationException(i18n.tr("Bitte geben Sie eine Kontonummer ein."));
      
      Kontoart ka = (Kontoart) getAttribute("kontoart_id");
      if (ka == null)
        throw new ApplicationException(i18n.tr("Bitte w�hlen Sie eine Kontoart aus."));

      // Jetzt muessen wir noch pruefen, ob die Kontonummer schon bei einem anderen
      // Konto vergeben ist
      Konto other = kr.findByKontonummer(kontonummer);
      if (other != null && !other.equals(this))
        throw new ApplicationException(i18n.tr("Ein Konto mit dieser Kontonummer existiert bereits in diesem Kontenrahmen."));
    }
    catch (RemoteException e)
    {
      throw new ApplicationException(i18n.tr("Fehler bei der �berpr�fung der Pflichtfelder"),e);
    }
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#setKontonummer(java.lang.String)
   */
  public void setKontonummer(String kontonummer) throws RemoteException
  {
    setAttribute("kontonummer",kontonummer);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#setKontenrahmen(de.willuhn.jameica.fibu.rmi.Kontenrahmen)
   */
  public void setKontenrahmen(Kontenrahmen k) throws RemoteException
  {
    setAttribute("kontenrahmen_id",k);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#setName(java.lang.String)
   */
  public void setName(String name) throws RemoteException
  {
    setAttribute("name",name);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#setKontoArt(de.willuhn.jameica.fibu.rmi.Kontoart)
   */
  public void setKontoArt(Kontoart art) throws RemoteException
  {
    setAttribute("kontoart_id",art == null || art.getID() == null ? null : new Integer(art.getID()));
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#setSteuer(de.willuhn.jameica.fibu.rmi.Steuer)
   */
  public void setSteuer(Steuer steuer) throws RemoteException
  {
    setAttribute("steuer_id",steuer == null || steuer.getID() == null ? null : new Integer(steuer.getID()));
  }

  /**
   * Ueberschrieben, um ein synthetisches Attribut "saldo" zu erzeugen
   * @see de.willuhn.datasource.GenericObject#getAttribute(java.lang.String)
   */
  public Object getAttribute(String arg0) throws RemoteException
  {
    if ("saldo".equals(arg0))
    {
      Geschaeftsjahr jahr = ((DBService)getService()).getActiveGeschaeftsjahr();
      if (jahr == null)
        return new Double(0.0d);
      
      // Checken, ob das ueberhaupt der selbe Kontenrahmen ist
      if (!BeanUtil.equals(this.getKontenrahmen(),jahr.getKontenrahmen()))
        return new Double(0.0d);
      
      return new Double(getSaldo(jahr));
    }
    return super.getAttribute(arg0);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getHauptBuchungen(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr)
   */
  public DBIterator getHauptBuchungen(Geschaeftsjahr jahr) throws RemoteException
  {
    return getBuchungen(jahr, null, null, Buchung.class);
  }
  
  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getHilfsBuchungen(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr)
   */
  public DBIterator getHilfsBuchungen(Geschaeftsjahr jahr) throws RemoteException
  {
    return getBuchungen(jahr, null, null, HilfsBuchung.class);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getHauptBuchungen(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr, java.util.Date, java.util.Date)
   */
  public DBIterator getHauptBuchungen(Geschaeftsjahr jahr, Date von, Date bis) throws RemoteException
  {
    return getBuchungen(jahr, von, bis, Buchung.class);
  }
  
  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getHilfsBuchungen(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr, java.util.Date, java.util.Date)
   */
  public DBIterator getHilfsBuchungen(Geschaeftsjahr jahr, Date von, Date bis) throws RemoteException
  {
    return getBuchungen(jahr, von, bis, HilfsBuchung.class);
  }

  /**
   * Interne Hilfs-Funktion zum Laden der entsprechenden Buchungen.
   * @param jahr das Jahr.
   * @param von Start-Datum.
   * @param bis End-Datum.
   * @param type Art der Buchungen.
   * @return Liste der Buchungen.
   * @throws RemoteException
   */
  private DBIterator getBuchungen(Geschaeftsjahr jahr, Date von, Date bis, Class type) throws RemoteException
  {
    
    if (von != null && !jahr.check(von))
      throw new RemoteException(i18n.tr("Das Start-Datum {0} befindet sich ausserhalb des angegebenen Gesch�ftsjahres", Settings.DATEFORMAT.format(von)));

    if (bis != null && !jahr.check(bis))
      throw new RemoteException(i18n.tr("Das End-Datum {0} befindet sich ausserhalb des angegebenen Gesch�ftsjahres", Settings.DATEFORMAT.format(bis)));

    Date start = null;
    if (von != null)
      start = DateUtil.startOfDay(von);

    Date end = null;
    if (bis != null)
      end = DateUtil.endOfDay(bis);
    
    DBService db = ((DBService)getService());

    DBIterator list = getService().createList(type);
    list.addFilter("(sollkonto_id = " + this.getID() + " OR habenkonto_id = " + this.getID() + ")");
    list.addFilter("geschaeftsjahr_id = " + jahr.getID());
    if (start != null)
      list.addFilter(db.getSQLTimestamp("datum") + " >= " + start.getTime());
    if (end != null)
      list.addFilter(db.getSQLTimestamp("datum") + " <=" + end.getTime());
    list.setOrder("order by datum,belegnummer");

    return list;
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getAnfangsbestand(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr)
   */
  public Anfangsbestand getAnfangsbestand(Geschaeftsjahr jahr) throws RemoteException
  {
    DBIterator ab = jahr.getAnfangsbestaende();
    ab.addFilter("konto_id = " + this.getID());
    if (!ab.hasNext())
      return null;
    return (Anfangsbestand) ab.next();
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getKontoTyp()
   */
  public Kontotyp getKontoTyp() throws RemoteException
  {
    Integer i = (Integer) super.getAttribute("kontotyp_id");
    if (i == null)
      return null;
   
    Cache cache = Cache.get(Kontotyp.class,true);
    return (Kontotyp) cache.get(i);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#setKontoTyp(de.willuhn.jameica.fibu.rmi.Kontotyp)
   */
  public void setKontoTyp(Kontotyp typ) throws RemoteException
  {
    setAttribute("kontotyp_id",typ == null || typ.getID() == null ? null : new Integer(typ.getID()));
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Konto#getNumBuchungen(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr, java.util.Date, java.util.Date)
   */
  public int getNumBuchungen(Geschaeftsjahr jahr, Date von, Date bis) throws RemoteException
  {
    if (this.isNewObject())
      return 0;
    
    if (von != null && !jahr.check(von))
      throw new RemoteException(i18n.tr("Das Start-Datum {0} befindet sich ausserhalb des angegebenen Gesch�ftsjahres", Settings.DATEFORMAT.format(von)));

    if (bis != null && !jahr.check(bis))
      throw new RemoteException(i18n.tr("Das End-Datum {0} befindet sich ausserhalb des angegebenen Gesch�ftsjahres", Settings.DATEFORMAT.format(bis)));

    Date start = null;
    if (von != null)
      start = DateUtil.startOfDay(von);

    Date end = null;
    if (bis != null)
      end = DateUtil.endOfDay(bis);

    String sql = "select count(id) from buchung where geschaeftsjahr_id = ? and (sollkonto_id = ? or habenkonto_id = ?)";

    DBService db = (DBService) this.getService();

    if (start != null) sql += " and " + db.getSQLTimestamp("datum") + " >= " + start.getTime();
    if (end != null)   sql += " and " + db.getSQLTimestamp("datum") + " <=" + end.getTime();

    ResultSetExtractor rs = new ResultSetExtractor()
    {
      public Object extract(ResultSet rs) throws RemoteException, SQLException
      {
        if (!rs.next())
          return new Integer(0);
        return new Integer(rs.getInt(1));
      }
    };

    Integer id = new Integer(this.getID());
    Integer i = (Integer) db.execute(sql, new Object[] {new Integer(jahr.getID()),id,id},rs);
    return i == null ? 0 : i.intValue();
  }
}

/*********************************************************************
 * $Log: KontoImpl.java,v $
 * Revision 1.59  2011/07/04 13:05:18  willuhn
 * @N Delete-Check bei Buchungsvorlagen
 *
 * Revision 1.58  2011-05-12 09:10:31  willuhn
 * @R Back-Buttons entfernt
 * @C GUI-Cleanup
 *
 * Revision 1.57  2011-03-25 10:14:10  willuhn
 * @N Loeschen von Mandanten und Beruecksichtigen der zugeordneten Konten und Kontenrahmen
 * @C BUGZILLA 958
 *
 * Revision 1.56  2011-03-10 16:10:50  willuhn
 * @B Auswertung Kontoauszug erlaubt die Auswahl eines Zeitraumes innerhalb des Jahres - das muss in getNumBuchungen() auch beachtet werden
 *
 * Revision 1.55  2011-03-10 16:01:26  willuhn
 * @B Das Geschaeftsjahr wurde bei der Ermittlung der Anzahl der Buchungen gar nicht beruecksichtigt
 *
 * Revision 1.54  2010-06-01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 * Revision 1.53  2009/07/03 10:52:19  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.51.2.1  2009/06/23 11:04:49  willuhn
 * @N auch nach Belegnummer sortieren
 *
 * Revision 1.51  2007/11/05 01:05:43  willuhn
 * @C refactoring
 *
 * Revision 1.50  2007/02/27 15:46:17  willuhn
 * @N Anzeige des vorherigen Kontostandes im Kontoauszug
 *
 * Revision 1.49  2006/09/05 20:57:51  willuhn
 * @B wrong import
 *
 * Revision 1.48  2006/09/05 20:57:27  willuhn
 * @ResultsetIterator merged into datasource lib
 *
 * Revision 1.47  2006/05/30 23:22:55  willuhn
 * @C Redsign beim Laden der Buchungen. Jahresabschluss nun korrekt
 *
 * Revision 1.46  2006/05/29 23:05:07  willuhn
 * *** empty log message ***
 *
 * Revision 1.45  2006/05/29 17:30:26  willuhn
 * @N a lot of debugging
 *
 * Revision 1.44  2006/05/08 22:44:18  willuhn
 * @N Debugging
 *
 * Revision 1.43  2006/03/27 20:26:53  willuhn
 * *** empty log message ***
 *
 * Revision 1.42  2006/03/17 16:23:28  willuhn
 * *** empty log message ***
 *
 * Revision 1.41  2006/01/09 01:17:12  willuhn
 * *** empty log message ***
 *
 * Revision 1.40  2006/01/06 00:05:51  willuhn
 * @N MySQL Support
 *
 * Revision 1.39  2006/01/02 15:18:29  willuhn
 * @N Buchungs-Vorlagen
 *
 * Revision 1.38  2006/01/02 01:54:07  willuhn
 * @N Benutzerdefinierte Konten
 *
 * Revision 1.37  2005/10/18 23:28:55  willuhn
 * @N client/server tauglichkeit
 *
 * Revision 1.36  2005/10/18 09:25:31  willuhn
 * *** empty log message ***
 *
 * Revision 1.35  2005/10/06 16:00:37  willuhn
 * @B bug 135
 *
 * Revision 1.34  2005/10/04 23:36:13  willuhn
 * *** empty log message ***
 *
 * Revision 1.33  2005/10/03 14:22:11  willuhn
 * *** empty log message ***
 *
 * Revision 1.32  2005/09/27 17:41:27  willuhn
 * *** empty log message ***
 *
 * Revision 1.31  2005/09/26 23:51:59  willuhn
 * *** empty log message ***
 *
 * Revision 1.30  2005/09/02 17:35:07  willuhn
 * @N Kontotyp
 * @N Betriebsergebnis
 *
 * Revision 1.29  2005/09/01 21:08:41  willuhn
 * *** empty log message ***
 *
 * Revision 1.28  2005/08/30 23:15:32  willuhn
 * *** empty log message ***
 *
 * Revision 1.27  2005/08/30 22:51:31  willuhn
 * @B bugfixing
 *
 * Revision 1.26  2005/08/29 12:17:29  willuhn
 * @N Geschaeftsjahr
 *
 * Revision 1.25  2005/08/25 23:00:02  willuhn
 * *** empty log message ***
 *
 * Revision 1.24  2005/08/22 23:13:26  willuhn
 * *** empty log message ***
 *
 * Revision 1.23  2005/08/22 21:44:09  willuhn
 * @N Anfangsbestaende
 *
 * Revision 1.22  2005/08/22 16:37:22  willuhn
 * @N Anfangsbestaende
 *
 * Revision 1.2  2005/08/15 23:38:27  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2005/08/12 00:10:59  willuhn
 * @B bugfixing
 *
 * Revision 1.20  2005/08/10 17:48:02  willuhn
 * @C refactoring
 *
 * Revision 1.19  2005/08/08 21:35:46  willuhn
 * @N massive refactoring
 *
 * Revision 1.18  2004/02/09 13:05:13  willuhn
 * @C misc
 *
 * Revision 1.17  2004/01/29 01:05:09  willuhn
 * *** empty log message ***
 *
 * Revision 1.16  2004/01/29 00:19:53  willuhn
 * *** empty log message ***
 *
 * Revision 1.15  2004/01/29 00:13:19  willuhn
 * *** empty log message ***
 *
 * Revision 1.14  2004/01/27 22:47:30  willuhn
 * *** empty log message ***
 *
 * Revision 1.13  2004/01/25 19:44:03  willuhn
 * *** empty log message ***
 *
 * Revision 1.12  2004/01/03 18:07:22  willuhn
 * @N Exception logging
 *
 * Revision 1.11  2003/12/19 19:45:06  willuhn
 * *** empty log message ***
 *
 * Revision 1.10  2003/12/12 21:11:26  willuhn
 * @N ObjectMetaCache
 *
 * Revision 1.9  2003/12/11 21:00:34  willuhn
 * @C refactoring
 *
 * Revision 1.8  2003/12/05 17:11:58  willuhn
 * @N added GeldKonto, Kontoart
 *
 * Revision 1.7  2003/12/01 20:29:00  willuhn
 * @B filter in DBIteratorImpl
 * @N InputFelder generalisiert
 *
 * Revision 1.6  2003/11/30 16:23:11  willuhn
 * *** empty log message ***
 *
 * Revision 1.5  2003/11/27 00:21:05  willuhn
 * @N Checks via insertCheck(), deleteCheck() updateCheck() in Business-Logik verlagert
 *
 * Revision 1.4  2003/11/24 16:26:15  willuhn
 * @N AbstractDBObject is now able to resolve foreign keys
 *
 * Revision 1.3  2003/11/24 15:18:21  willuhn
 * *** empty log message ***
 *
 * Revision 1.2  2003/11/22 20:43:07  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2003/11/20 03:48:44  willuhn
 * @N first dialogues
 *
 **********************************************************************/