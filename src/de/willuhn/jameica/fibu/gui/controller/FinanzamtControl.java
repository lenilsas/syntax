/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/controller/FinanzamtControl.java,v $
 * $Revision: 1.2 $
 * $Date: 2003/11/27 00:21:05 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.controller;

import java.rmi.RemoteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import de.willuhn.jameica.Application;
import de.willuhn.jameica.ApplicationException;
import de.willuhn.jameica.GUI;
import de.willuhn.jameica.I18N;
import de.willuhn.jameica.fibu.objects.Finanzamt;
import de.willuhn.jameica.rmi.DBObject;
import de.willuhn.jameica.views.parts.Controller;

/**
 * Diese Klasse behandelt alle Button-Drueckungen(sic!) ;) des
 * Dialogs "Finanzamt".
 * @author willuhn
 */
public class FinanzamtControl extends Controller
{

  /**
   * Erzeugt einen neuen Controller der fuer dieses Finanzamt zustaendig ist.
   * @param object die Buchung.
   */
  public FinanzamtControl(DBObject object)
  {
    super(object);
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleDelete(java.lang.String)
   */
  public void handleDelete(String id)
  {
    try {
      this.object = Application.getDefaultDatabase().createObject(Finanzamt.class,id);
      handleDelete();
    }
    catch (RemoteException e)
    {
      // Objekt kann nicht geladen werden. Dann muessen wir es auch nicht loeschen.
      Application.getLog().error("no valid finanzamt found");
      GUI.setActionText(I18N.tr("Finanzamt wurde nicht gefunden."));
    }
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleDelete()
   */
  public void handleDelete()
  {
    Finanzamt fa = (Finanzamt) getObject();

    try {
      if (fa.isNewObject())
      {
        GUI.setActionText(I18N.tr("Finanzamt wurde noch nicht gespeichert und muss daher nicht gel�scht werden."));
        return; // wenn's ein neues Objekt ist, gibt's nichts zu loeschen. ;)
      }

    }
    catch (RemoteException e)
    {
      // Also wenn wir nicht mal ne Verbindung zum Business-Objekt haben, ist sowieso was faul ;)
      Application.getLog().error("no valid finanzamt found");
    }

    MessageBox box = new MessageBox(GUI.shell,SWT.ICON_WARNING | SWT.YES | SWT.NO);
    box.setText(I18N.tr("Finanzamt wirklich l�schen?"));
    box.setMessage(I18N.tr("Wollen Sie die Daten dieses Finanzamtes wirklich l�schen?"));
    if (box.open() == SWT.YES)
    {
      // ok, wir loeschen das Objekt
      try {
        fa.delete();
        GUI.setActionText(I18N.tr("Daten des Finanzamtes gel�scht."));
      }
      catch (ApplicationException e1)
      {
        MessageBox box2 = new MessageBox(GUI.shell,SWT.ICON_WARNING | SWT.OK);
        box2.setText(I18N.tr("Fehler"));
        box2.setMessage(e1.getLocalizedMessage());
        box2.open();
        return;
      }
      catch (RemoteException e)
      {
        GUI.setActionText(I18N.tr("Fehler beim L�schen der Daten des Finanzamtes."));
        Application.getLog().error("unable to delete finanzamt");
      }
    }
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleCancel()
   */
  public void handleCancel()
  {
    GUI.startView("de.willuhn.jameica.fibu.views.FinanzamtListe",null);
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleStore()
   */
  public void handleStore()
  {
    Finanzamt fa = (Finanzamt) getObject();

    try {

      fa.setName(getField("name").getValue());
      fa.setStrasse(getField("strasse").getValue());
      fa.setPLZ(getField("plz").getValue());
      fa.setPostfach(getField("postfach").getValue());
      fa.setOrt(getField("ort").getValue());

      
      // und jetzt speichern wir.
      fa.store();
      GUI.setActionText(I18N.tr("Daten des Finanzamtes gespeichert."));
    }
    catch (ApplicationException e1)
    {
      GUI.setActionText(e1.getLocalizedMessage());
    }
    catch (RemoteException e)
    {
      if (Application.DEBUG)
        e.printStackTrace();
      GUI.setActionText("Fehler beim Speichern der Daten des Finanzamtes.");
      Application.getLog().error("unable to store finanzamt");
    }
    
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleChooseFromList(java.lang.String)
   */
  public void handleLoad(String id)
  {
    try {
      Finanzamt fa = (Finanzamt) Application.getDefaultDatabase().createObject(Finanzamt.class,id);
      GUI.startView("de.willuhn.jameica.fibu.views.FinanzamtNeu",fa);
    }
    catch (RemoteException e)
    {
      Application.getLog().error("unable to load finanzamt with id " + id);
      GUI.setActionText(I18N.tr("Daten des zu ladenden Finanzamtes wurde nicht gefunden."));
    }
        
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleCreate()
   */
  public void handleCreate()
  {
    GUI.startView("de.willuhn.jameica.fibu.views.FinanzamtNeu",null);
  }

}

/*********************************************************************
 * $Log: FinanzamtControl.java,v $
 * Revision 1.2  2003/11/27 00:21:05  willuhn
 * @N Checks via insertCheck(), deleteCheck() updateCheck() in Business-Logik verlagert
 *
 * Revision 1.1  2003/11/25 00:22:17  willuhn
 * @N added Finanzamt
 *
 **********************************************************************/