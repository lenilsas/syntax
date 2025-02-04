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
import de.willuhn.jameica.fibu.rmi.Mandant;
import de.willuhn.jameica.fibu.rmi.UserObject;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * @author willuhn
 */
public abstract class AbstractUserObjectImpl extends AbstractDBObject implements UserObject
{
  private transient I18N i18n = null;
  
  /**
   * Erzeugt ein neues User-Objekt.
   * @throws RemoteException
   */
  public AbstractUserObjectImpl() throws RemoteException
  {
    super();
    this.i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#deleteCheck()
   */
  protected void deleteCheck() throws ApplicationException
  {
    try
    {
      if (!canChange())
        throw new ApplicationException("Datensatz geh�rt zum System-Kontenrahmen und darf daher nicht gel�scht werden.");
    }
    catch (RemoteException e)
    {
      Logger.error("unable to check user object",e);
      throw new ApplicationException(i18n.tr("Fehler beim L�schen des Datensatzes"));
    }
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#insertCheck()
   */
  protected void insertCheck() throws ApplicationException
  {
    try {
      if (!canChange())
        throw new ApplicationException("Datensatz geh�rt zum System-Kontenrahmen und darf daher nicht ge�ndert werden.");
    }
    catch (RemoteException e)
    {
      throw new ApplicationException(i18n.tr("Fehler bei der �berpr�fung des Datensatzes"),e);
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
   * @see de.willuhn.jameica.fibu.rmi.UserObject#isUserObject()
   */
  public boolean isUserObject() throws RemoteException
  {
    return getMandant() != null;
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.UserObject#canChange()
   */
  public boolean canChange() throws RemoteException
  {
    return Settings.getSystemDataWritable() || isUserObject() || Settings.inUpdate();
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.UserObject#getMandant()
   */
  public Mandant getMandant() throws RemoteException
  {
    Integer i = (Integer) super.getAttribute("mandant_id");
    if (i == null)
      return null;
   
    Cache cache = Cache.get(Mandant.class,true);
    return (Mandant) cache.get(i);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.UserObject#setMandant(de.willuhn.jameica.fibu.rmi.Mandant)
   */
  public void setMandant(Mandant mandant) throws RemoteException
  {
    if (!this.isNewObject() && !this.canChange())
      throw new RemoteException("Datensatz geh�rt zum initialen Datenbestand und darf daher nicht ge�ndert werden.");
    setAttribute("mandant_id",mandant == null || mandant.getID() == null ? null : Integer.valueOf(mandant.getID()));
  }
  
  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getAttribute(java.lang.String)
   */
  @Override
  public Object getAttribute(String arg0) throws RemoteException
  {
    if ("mandant".equals(arg0))
      return this.getMandant();
    
    return super.getAttribute(arg0);
  }
}
