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
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.rmi.Abschreibung;
import de.willuhn.jameica.fibu.rmi.AbschreibungsBuchung;
import de.willuhn.jameica.fibu.rmi.Anlagevermoegen;
import de.willuhn.jameica.fibu.rmi.DBService;
import de.willuhn.jameica.fibu.rmi.Geschaeftsjahr;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Implementierung einer einzelnen Abschreibung.
 */
public class AbschreibungImpl extends AbstractDBObject implements Abschreibung
{

  private transient I18N i18n = null;
  
  /**
   * @throws java.rmi.RemoteException
   */
  public AbschreibungImpl() throws RemoteException
  {
    super();
    this.i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getTableName()
   */
  protected String getTableName()
  {
    return "abschreibung";
  }

  /**
   * @see de.willuhn.datasource.GenericObject#getPrimaryAttribute()
   */
  public String getPrimaryAttribute() throws RemoteException
  {
    return "buchung_id";
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Abschreibung#getAnlagevermoegen()
   */
  public Anlagevermoegen getAnlagevermoegen() throws RemoteException
  {
    return (Anlagevermoegen) getAttribute("av_id");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Abschreibung#getBuchung()
   */
  public AbschreibungsBuchung getBuchung() throws RemoteException
  {
    return (AbschreibungsBuchung) getAttribute("buchung_id");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Abschreibung#setAnlagevermoegen(de.willuhn.jameica.fibu.rmi.Anlagevermoegen)
   */
  public void setAnlagevermoegen(Anlagevermoegen av) throws RemoteException
  {
    setAttribute("av_id",av);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Abschreibung#setBuchung(de.willuhn.jameica.fibu.rmi.AbschreibungsBuchung)
   */
  public void setBuchung(AbschreibungsBuchung b) throws RemoteException
  {
    setAttribute("buchung_id",b);
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getForeignObject(java.lang.String)
   */
  protected Class getForeignObject(String arg0) throws RemoteException
  {
    if ("av_id".equals(arg0))
      return Anlagevermoegen.class;
    if ("buchung_id".equals(arg0))
      return AbschreibungsBuchung.class;
    
    return super.getForeignObject(arg0);
  }
  
  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#insertCheck()
   */
  protected void insertCheck() throws ApplicationException
  {
    if (Settings.inUpdate())
      return;
      
    try
    {
      Geschaeftsjahr jahr = ((DBService)getService()).getActiveGeschaeftsjahr();
      if (jahr.isClosed())
        throw new ApplicationException(i18n.tr("Gesch�ftsjahr ist bereits geschlossen"));

      if (getAnlagevermoegen() == null)
        throw new ApplicationException(i18n.tr("Kein Anlage-Gegenstand zugeordnet"));
      if (getBuchung() == null)
        throw new ApplicationException(i18n.tr("Keine Abschreibungs-Buchung zugeordnet"));
    }
    catch (RemoteException e)
    {
      Logger.error("error while checking abschreibung",e);
      throw new ApplicationException(i18n.tr("Fehler beim Pr�fen der Abschreibung"));
    }
    super.insertCheck();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#updateCheck()
   */
  protected void updateCheck() throws ApplicationException
  {
    insertCheck();
  }
  
  /**
   * @see de.willuhn.datasource.rmi.Changeable#delete()
   */
  public void delete() throws RemoteException, ApplicationException
  {
    // Wir muessen die Buchung mit loeschen
    try
    {
      transactionBegin();

      // Erst die Abschreibung loeschen, weil sonst das Loeschen
      // der Abschreibungsbuchung fehlschlaegt
      super.delete();

      Logger.info("L�sche zugeh�rige Abschreibungsbuchung");
      AbschreibungsBuchung b = getBuchung();
      if (b != null)
        b.delete();

      transactionCommit();
    }
    catch (ApplicationException e)
    {
      try
      {
        transactionRollback();
      }
      catch (Throwable tr)
      {
        Logger.error("unable to rollback transaction",tr);
      }
      throw e;
    }
    catch (RemoteException e2)
    {
      try
      {
        transactionRollback();
      }
      catch (Throwable tr)
      {
        Logger.error("unable to rollback transaction",tr);
      }
      throw e2;
    }
    catch (Throwable t)
    {
      try
      {
        transactionRollback();
      }
      catch (Throwable tr)
      {
        Logger.error("unable to rollback transaction",tr);
      }
      Logger.error("unable to delete abschreibung",t);
      throw new ApplicationException(i18n.tr("Fehler beim L�schen der Abschreibung"));
    }
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Abschreibung#isSonderabschreibung()
   */
  public boolean isSonderabschreibung() throws RemoteException
  {
    Integer i = (Integer) getAttribute("sonderabschreibung");
    return i != null && i.intValue() == 1;
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Abschreibung#setSonderabschreibung(boolean)
   */
  public void setSonderabschreibung(boolean b) throws RemoteException
  {
    setAttribute("sonderabschreibung",b ? Integer.valueOf(1) : null);
  }
}
