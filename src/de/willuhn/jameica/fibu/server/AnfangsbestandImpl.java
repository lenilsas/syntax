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

import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.rmi.Anfangsbestand;
import de.willuhn.jameica.fibu.rmi.Geschaeftsjahr;
import de.willuhn.jameica.fibu.rmi.Konto;
import de.willuhn.jameica.fibu.rmi.Kontoart;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Implementierung des Anfangsbestandes eines Kontos.
 */
public class AnfangsbestandImpl extends AbstractDBObject implements
    Anfangsbestand
{

  /**
   * @throws java.rmi.RemoteException
   */
  public AnfangsbestandImpl() throws RemoteException
  {
    super();
  }

  /**
   * @see de.willuhn.datasource.rmi.Changeable#delete()
   */
  public void delete() throws RemoteException, ApplicationException
  {
    Konto k = getKonto();
    super.delete();
    if (k != null)
      SaldenCache.remove(k.getKontonummer());
  }
  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getTableName()
   */
  protected String getTableName()
  {
    return "konto_ab";
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#insertCheck()
   */
  protected void insertCheck() throws ApplicationException
  {
    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();

    updateCheck();
    try
    {
      Konto k = getKonto();
      if (k.getAnfangsbestand(getGeschaeftsjahr()) != null)
        throw new ApplicationException(i18n.tr("F�r das Konto {0} existiert bereits ein Anfangsbestand",k.getKontonummer()));
    }
    catch (RemoteException e)
    {
      Logger.error("error while checking anfangsbestand",e);
      throw new ApplicationException(i18n.tr("Fehler beim Pr�fen des Anfangsbestandes"));
    }
    super.insertCheck();
  }
  
  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#updateCheck()
   */
  protected void updateCheck() throws ApplicationException
  {
    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
    
    try
    {
      Geschaeftsjahr jahr = getGeschaeftsjahr();

      if (jahr == null || jahr.isNewObject())
        throw new ApplicationException(i18n.tr("Bitte w�hlen Sie ein Gesch�ftsjahr aus"));

      if (jahr.isClosed())
        throw new ApplicationException(i18n.tr("Gesch�ftsjahr ist bereits geschlossen"));

      Konto k = getKonto();
      
      if (k == null || k.isNewObject())
        throw new ApplicationException(i18n.tr("Bitte w�hlen Sie ein Konto aus"));

      int ka = k.getKontoArt().getKontoArt();
      if (ka != Kontoart.KONTOART_ANLAGE && ka != Kontoart.KONTOART_GELD)
        throw new ApplicationException(i18n.tr("Nur Anlage- und Geldkonten d�rfen einen Anfangsbestand haben"));

      // BUGZILLA 1153 - Bei Geldkonten tolerieren wir negative Anfangsbestaende
      if (ka == Kontoart.KONTOART_ANLAGE && this.getBetrag() < 0d)
        throw new ApplicationException(i18n.tr("Bei Anlagekonten muss der Anfangsbestand gr��er als 0 sein"));
    }
    catch (RemoteException e)
    {
      Logger.error("error while checking anfangsbestand",e);
      throw new ApplicationException(i18n.tr("Fehler beim Pr�fen des Anfangsbestandes"));
    }
    super.updateCheck();
  }

  /**
   * @see de.willuhn.datasource.GenericObject#getPrimaryAttribute()
   */
  public String getPrimaryAttribute() throws RemoteException
  {
    return "konto_id";
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Anfangsbestand#getGeschaeftsjahr()
   */
  public Geschaeftsjahr getGeschaeftsjahr() throws RemoteException
  {
    return (Geschaeftsjahr) getAttribute("geschaeftsjahr_id");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Anfangsbestand#setGeschaeftsjahr(de.willuhn.jameica.fibu.rmi.Geschaeftsjahr)
   */
  public void setGeschaeftsjahr(Geschaeftsjahr jahr) throws RemoteException
  {
    setAttribute("geschaeftsjahr_id",jahr);
  }

  /**
   * @see de.willuhn.datasource.rmi.Changeable#store()
   */
  public void store() throws RemoteException, ApplicationException
  {
    super.store();
    SaldenCache.remove(getKonto().getKontonummer());
  }
  /**
   * @see de.willuhn.jameica.fibu.rmi.Anfangsbestand#getKonto()
   */
  public Konto getKonto() throws RemoteException
  {
    return (Konto) getAttribute("konto_id");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Anfangsbestand#setKonto(de.willuhn.jameica.fibu.rmi.Konto)
   */
  public void setKonto(Konto k) throws RemoteException
  {
    setAttribute("konto_id",k);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Anfangsbestand#getBetrag()
   */
  public double getBetrag() throws RemoteException
  {
    Number d = (Number) getAttribute("betrag");
    return d == null ? 0.0d : d.doubleValue();
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Anfangsbestand#setBetrag(double)
   */
  public void setBetrag(double betrag) throws RemoteException
  {
    setAttribute("betrag",new Double(betrag));
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getForeignObject(java.lang.String)
   */
  protected Class getForeignObject(String arg0) throws RemoteException
  {
    if ("geschaeftsjahr_id".equals(arg0))
      return Geschaeftsjahr.class;
    if ("konto_id".equals(arg0))
      return Konto.class;
    return super.getForeignObject(arg0);
  }
}


/*********************************************************************
 * $Log: AnfangsbestandImpl.java,v $
 * Revision 1.18  2011/12/08 22:12:41  willuhn
 * @N BUGZILLA 1153
 *
 * Revision 1.17  2009-07-03 10:52:19  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.15  2007/02/27 18:40:14  willuhn
 * @B fehlender updateCheck Aufruf
 *
 * Revision 1.14  2007/02/27 18:30:23  willuhn
 * @R removed debug output
 *
 * Revision 1.13  2007/02/27 18:28:33  willuhn
 * @B fehlender updateCheck Aufruf
 *
 * Revision 1.12  2007/02/27 18:17:32  willuhn
 * @B Anfangsbestaende nur von Anlage- und Geldkonten erzeugen
 *
 * Revision 1.11  2005/10/18 23:28:55  willuhn
 * @N client/server tauglichkeit
 *
 * Revision 1.10  2005/09/26 23:51:59  willuhn
 * *** empty log message ***
 *
 * Revision 1.9  2005/09/01 23:07:17  willuhn
 * @B bugfixing
 *
 * Revision 1.8  2005/08/30 22:51:31  willuhn
 * @B bugfixing
 *
 * Revision 1.7  2005/08/29 17:46:14  willuhn
 * @N Jahresabschluss
 *
 * Revision 1.6  2005/08/29 14:54:28  willuhn
 * @B bugfixing
 *
 * Revision 1.5  2005/08/29 12:17:29  willuhn
 * @N Geschaeftsjahr
 *
 * Revision 1.4  2005/08/28 01:08:03  willuhn
 * @N buchungsjournal
 *
 * Revision 1.3  2005/08/22 23:13:26  willuhn
 * *** empty log message ***
 *
 * Revision 1.2  2005/08/22 21:44:08  willuhn
 * @N Anfangsbestaende
 *
 * Revision 1.1  2005/08/22 16:37:22  willuhn
 * @N Anfangsbestaende
 *
 **********************************************************************/