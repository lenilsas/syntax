/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/controller/BuchungControl.java,v $
 * $Revision: 1.15 $
 * $Date: 2004/01/27 00:09:10 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.gui.controller;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.DBObject;
import de.willuhn.jameica.Application;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.gui.views.BuchungListe;
import de.willuhn.jameica.fibu.gui.views.BuchungNeu;
import de.willuhn.jameica.fibu.rmi.Buchung;
import de.willuhn.jameica.fibu.rmi.GeldKonto;
import de.willuhn.jameica.fibu.rmi.Konto;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Diese Klasse behandelt alle Button-Drueckungen(sic!) ;) des
 * Dialogs "Neue Buchung".
 * @author willuhn
 */
public class BuchungControl extends Controller
{

  /**
   * Erzeugt einen neuen Controller der fuer diese Buchung zustaendig ist.
   * @param object die Buchung.
   */
  public BuchungControl(DBObject object)
  {
    super(object);
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleDelete(java.lang.String)
   */
  public void handleDelete(String id)
  {
    try {
      this.object = Settings.getDatabase().createObject(Buchung.class,id);
      handleDelete();
    }
    catch (RemoteException e)
    {
      // Objekt kann nicht geladen werden. Dann muessen wir es auch nicht loeschen.
      Application.getLog().error("no valid buchung found");
      GUI.setActionText(I18N.tr("Buchung wurde nicht gefunden."));
    }
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleDelete()
   */
  public void handleDelete()
  {
    Buchung buchung = (Buchung) getObject();
    int beleg = 0;

    try {
      beleg = buchung.getBelegnummer();
      if (buchung.isNewObject())
      {
        GUI.setActionText(I18N.tr("Buchung wurde noch nicht gespeichert und muss daher nicht gel�scht werden."));
        return; // wenn's ein neues Objekt ist, gibt's nichts zu loeschen. ;)
      }
    }
    catch (RemoteException e)
    {
      // Also wenn wir nicht mal ne Verbindung zum Business-Objekt haben, ist sowieso was faul ;)
      Application.getLog().error("no valid buchung found");
    }

    MessageBox box = new MessageBox(GUI.getShell(),SWT.ICON_WARNING | SWT.YES | SWT.NO);
    box.setText(I18N.tr("Buchung wirklich stornieren?"));
    box.setMessage(I18N.tr("Wollen Sie diese Buchung wirklich stornieren?"));
    if (box.open() == SWT.YES)
    {
      // ok, wir loeschen das Objekt und wechseln zurueck zur Buchungsliste
      try {
        buchung.delete();
        GUI.setActionText(I18N.tr("Buchung Nr. " + beleg + " storniert."));
      }
      catch (ApplicationException e1)
      {
        GUI.setActionText(e1.getLocalizedMessage());
      }
      catch (RemoteException e)
      {
        GUI.setActionText(I18N.tr("Fehler beim Stornieren der Buchung."));
        Application.getLog().error("unable to delete buchung");
      }
    }
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleCancel()
   */
  public void handleCancel()
  {
    GUI.startView(BuchungListe.class.getName(),null);
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleStore()
   */
  public void handleStore()
  {
    Buchung buchung = (Buchung) getObject();

    try {

      //////////////////////////////////////////////////////////////////////////
      // Belegnummer checken
      try {
        buchung.setBelegnummer(Integer.parseInt(getField("belegnummer").getValue()));
      }
      catch (NumberFormatException e)
      {
        GUI.setActionText(I18N.tr("Belegnummer ung�ltig."));
        return;
      }
      //
      //////////////////////////////////////////////////////////////////////////
      
      //////////////////////////////////////////////////////////////////////////
      // Betrag checken
      try {
        buchung.setBetrag(Fibu.DECIMALFORMAT.parse(getField("betrag").getValue()).doubleValue());
      }
      catch (NumberFormatException e)
      {
        GUI.setActionText(I18N.tr("Betrag ung�ltig."));
        return;
      }
      catch (ParseException e)
      {
        GUI.setActionText(I18N.tr("Betrag ung�ltig."));
        return;
      }
      //
      //////////////////////////////////////////////////////////////////////////
      

      //////////////////////////////////////////////////////////////////////////
      // Steuer checken
      try {
        buchung.setSteuer(Fibu.DECIMALFORMAT.parse(getField("steuer").getValue()).doubleValue());
      }
      catch (NumberFormatException e)
      {
        GUI.setActionText(I18N.tr("Steuersatz ung�ltig."));
        return;
      }
      catch (ParseException e)
      {
        GUI.setActionText(I18N.tr("Steuersatz ung�ltig."));
        return;
      }
      //
      //////////////////////////////////////////////////////////////////////////

      //////////////////////////////////////////////////////////////////////////
      // Datum checken
      
      Date datum = null;
      try {
        datum = Fibu.DATEFORMAT.parse(getField("datum").getValue());
      }
      catch (ParseException e)
      {
        // ok, evtl. ein Datum in Kurzformat, wir versuchen's mal
        try {
          datum = Fibu.FASTDATEFORMAT.parse(getField("datum").getValue());
        }
        catch (ParseException e2)
        {
          try {
            // ok, evtl. 4-stelliges Datum mit GJ vom Mandanten
            datum = Fibu.FASTDATEFORMAT.parse(getField("datum").getValue() + Settings.getActiveMandant().getGeschaeftsjahr());
          }
          catch (ParseException e3)
          {
            GUI.setActionText(I18N.tr("Datum ung�ltig."));
            return;
          }
        }
      }

      buchung.setDatum(datum);
      //
      //////////////////////////////////////////////////////////////////////////
      
      //////////////////////////////////////////////////////////////////////////
      // Konto checken
      
      DBIterator konten = Settings.getDatabase().createList(Konto.class);
      konten.addFilter("kontonummer = '"+getField("konto").getValue()+"'");
      if (!konten.hasNext())
      {
        GUI.setActionText(I18N.tr("Ausgew�hltes Konto existiert nicht."));
        return;
      }
      buchung.setKonto((Konto) konten.next());
      //
      //////////////////////////////////////////////////////////////////////////
      
      //////////////////////////////////////////////////////////////////////////
      // GeldKonto checken
      
      DBIterator geldkonten = Settings.getDatabase().createList(GeldKonto.class);
      geldkonten.addFilter("kontonummer = '"+getField("geldkonto").getValue()+"'");
      if (!geldkonten.hasNext())
      {
        GUI.setActionText(I18N.tr("Ausgew�hltes Geld-Konto existiert nicht."));
        return;
      }
      buchung.setGeldKonto((GeldKonto) geldkonten.next());
      //
      //////////////////////////////////////////////////////////////////////////

      buchung.setText(getField("text").getValue());
      
      // wir speichern grundsaetzlich den aktiven Mandanten als Inhaber der Buchung
      buchung.setMandant(Settings.getActiveMandant());

      // und jetzt speichern wir.
      buchung.store();
      GUI.setActionText(I18N.tr("Buchung Nr.") + " " + buchung.getBelegnummer() + " " + I18N.tr("gespeichert."));
      // jetzt machen wir die Buchung leer, damit sie beim naechsten Druck
      // auf Speichern als neue Buchung gespeichert wird.
      buchung.clear();
      GUI.startView(BuchungNeu.class.getName(),buchung);

    }
    catch (ApplicationException e1)
    {
      GUI.setActionText(e1.getLocalizedMessage());
    }
    catch (RemoteException e)
    {
			Application.getLog().error("unable to store buchung",e);
      GUI.setActionText("Fehler beim Speichern der Buchung.");
    }
    
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleChooseFromList(java.lang.String)
   */
  public void handleLoad(String id)
  {
    try {
      Buchung buchung = (Buchung) Settings.getDatabase().createObject(Buchung.class,id);
      GUI.startView(BuchungNeu.class.getName(),buchung);
    }
    catch (RemoteException e)
    {
      Application.getLog().error("unable to load buchung with id " + id);
      GUI.setActionText(I18N.tr("Buchung wurde nicht gefunden."));
    }
        
  }

  /**
   * @see de.willuhn.jameica.views.parts.Controller#handleCreate()
   */
  public void handleCreate()
  {
    GUI.startView(BuchungNeu.class.getName(),null);
  }

}

/*********************************************************************
 * $Log: BuchungControl.java,v $
 * Revision 1.15  2004/01/27 00:09:10  willuhn
 * *** empty log message ***
 *
 * Revision 1.14  2004/01/03 18:07:22  willuhn
 * @N Exception logging
 *
 * Revision 1.13  2003/12/16 02:27:32  willuhn
 * @N BuchungsEngine
 *
 * Revision 1.12  2003/12/15 19:08:04  willuhn
 * *** empty log message ***
 *
 * Revision 1.11  2003/12/12 01:28:07  willuhn
 * *** empty log message ***
 *
 * Revision 1.10  2003/12/11 21:00:35  willuhn
 * @C refactoring
 *
 * Revision 1.9  2003/12/05 17:11:58  willuhn
 * @N added GeldKonto, Kontoart
 *
 * Revision 1.8  2003/12/01 21:23:00  willuhn
 * *** empty log message ***
 *
 * Revision 1.7  2003/11/30 16:23:11  willuhn
 * *** empty log message ***
 *
 * Revision 1.6  2003/11/27 00:21:05  willuhn
 * @N Checks via insertCheck(), deleteCheck() updateCheck() in Business-Logik verlagert
 *
 * Revision 1.5  2003/11/25 01:23:19  willuhn
 * @N added Menu shortcuts
 *
 * Revision 1.4  2003/11/24 23:02:11  willuhn
 * @N added settings
 *
 * Revision 1.3  2003/11/24 17:27:53  willuhn
 * @N Context menu in table
 *
 * Revision 1.2  2003/11/24 14:21:56  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2003/11/22 20:43:07  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2003/11/21 02:10:57  willuhn
 * @N buchung dialog works now
 *
 **********************************************************************/