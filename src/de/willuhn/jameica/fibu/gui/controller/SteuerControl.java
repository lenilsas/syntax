/**********************************************************************
 *
 * Copyright (c) 2004 Olaf Willuhn
 * All rights reserved.
 * 
 * This software is copyrighted work licensed under the terms of the
 * Jameica License.  Please consult the file "LICENSE" for details. 
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.gui.controller;

import java.rmi.RemoteException;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.gui.input.KontoInput;
import de.willuhn.jameica.fibu.rmi.Geschaeftsjahr;
import de.willuhn.jameica.fibu.rmi.Konto;
import de.willuhn.jameica.fibu.rmi.Kontoart;
import de.willuhn.jameica.fibu.rmi.Mandant;
import de.willuhn.jameica.fibu.rmi.Steuer;
import de.willuhn.jameica.gui.AbstractControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.input.DecimalInput;
import de.willuhn.jameica.gui.input.Input;
import de.willuhn.jameica.gui.input.TextInput;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Controller fuer den Steuer-Dialog.
 */
public class SteuerControl extends AbstractControl
{

	// Fach-Objekte.
	private Steuer steuer = null;

	// Eingabe-Felder
	private Input name					    = null;
	private Input satz    			    = null;
  private KontoInput kontoauswahl	= null;
  private TextInput ustBemessung  = null;
  private TextInput ustSteuer     = null;

  
  private I18N i18n;
  
  /**
   * @param view
   */
  public SteuerControl(AbstractView view)
  {
    super(view);
    i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
  }

	/**
	 * Liefert die Steuer.
   * @return Steuer.
   * @throws RemoteException
   */
  public Steuer getSteuer() throws RemoteException
	{
		if (steuer != null)
			return steuer;
		
		Mandant m = Settings.getActiveGeschaeftsjahr().getMandant();
		
		Object o = getCurrentObject();
		if(o instanceof Steuer)
		{
			steuer = (Steuer)o;
			return steuer;
		}
		else if(o instanceof Mandant)
		{
			m = (Mandant)o;
		}

		steuer = (Steuer) Settings.getDBService().createObject(Steuer.class,null);
        steuer.setMandant(m);
		return steuer;
	}

	/**
	 * Liefert ein Eingabe-Feld fuer den Namen des Steuersatzes.
   * @return Eingabe-Feld.
   * @throws RemoteException
   */
  public Input getName() throws RemoteException
	{
		if (name != null)
			return name;

    name = new TextInput(getSteuer().getName());
    name.setEnabled(getSteuer().canChange());
    name.setName(i18n.tr("Name"));
		return name;
	}

	/**
	 * Liefert ein Eingabe-Feld fuer den Steuersatz.
	 * @return Eingabe-Feld.
	 * @throws RemoteException
	 */
	public Input getSatz() throws RemoteException
	{
		if (satz != null)
			return satz;

    satz = new DecimalInput(getSteuer().getSatz(), Settings.DECIMALFORMAT);
    satz.setName(i18n.tr("Steuersatz"));
		satz.setComment(i18n.tr("Angabe in \"%\""));
		satz.setEnabled(getSteuer().canChange());
		return satz;
	}

	/**
	 * Liefert ein Auswahl-Feld fuer das Steuer-Konto.
	 * @return Eingabe-Feld.
	 * @throws RemoteException
	 */
	public KontoInput getKontoAuswahl() throws RemoteException
	{
		if (kontoauswahl != null)
			return kontoauswahl;

    Geschaeftsjahr jahr = Settings.getActiveGeschaeftsjahr();
    DBIterator list = jahr.getKontenrahmen().getKonten();
    list.addFilter("kontoart_id = " + Kontoart.KONTOART_STEUER);
    kontoauswahl = new KontoInput(list,getSteuer().getSteuerKonto());
    kontoauswahl.setName(i18n.tr("Steuer-Sammelkonto"));
    kontoauswahl.setEnabled(getSteuer().canChange());
    return kontoauswahl;
	}
	
	/**
	 * Liefert ein Eingabefeld fuer das Kennzeichen der Bemessungsgrundlage in der UST-Voranmeldung.
	 * @return Eingabefeld.
	 * @throws RemoteException
	 */
	public TextInput getUstBemessung() throws RemoteException
	{
	  if (this.ustBemessung != null)
	    return this.ustBemessung;
	  this.ustBemessung = new TextInput(this.getSteuer().getUstNrBemessung());
	  this.ustBemessung.setName(i18n.tr("Kennzeichen f�r Bemessungsgrundlage"));
	  this.ustBemessung.setValidChars("0123456789");
    this.ustBemessung.setEnabled(getSteuer().canChange());
	  this.ustBemessung.setMaxLength(3);
	  return this.ustBemessung;
	}
	
	 /**
   * Liefert ein Eingabefeld fuer das Kennzeichen der Steuer in der UST-Voranmeldung.
   * @return Eingabefeld.
   * @throws RemoteException
   */
  public TextInput getUstSteuer() throws RemoteException
  {
    if (this.ustSteuer != null)
      return this.ustSteuer;
    this.ustSteuer = new TextInput(this.getSteuer().getUstNrSteuer());
    this.ustSteuer.setName(i18n.tr("Kennzeichen f�r Steuerbetrag"));
    this.ustSteuer.setValidChars("0123456789");
    this.ustSteuer.setEnabled(getSteuer().canChange());
    this.ustSteuer.setMaxLength(3);
    return this.ustSteuer;
  }



  /**
   * Speichert den Steuersatz.
   */
  public void handleStore()
  {
    try {

      if (!getSteuer().canChange())
      {
        GUI.getView().setErrorText(i18n.tr("System-Steuerkonto darf nicht ge�ndert werden."));
        return;
      }

      getSteuer().setName((String)  getName().getValue());
      getSteuer().setSatz(((Double) getSatz().getValue()).doubleValue());
      getSteuer().setSteuerKonto((Konto)getKontoAuswahl().getValue());
      getSteuer().setUstNrBemessung((String)getUstBemessung().getValue());
      getSteuer().setUstNrSteuer((String)getUstSteuer().getValue());

      // und jetzt speichern wir.
      getSteuer().store();
      GUI.getStatusBar().setSuccessText(i18n.tr("Steuersatz gespeichert."));
    }
    catch (ApplicationException e1)
    {
      GUI.getView().setErrorText(e1.getLocalizedMessage());
    }
    catch (RemoteException e)
    {
      GUI.getView().setErrorText("Fehler beim Speichern des Steuersatzes.");
      Logger.error("unable to store steuer",e);
    }
    
  }
}

/*********************************************************************
 * $Log: SteuerControl.java,v $
 * Revision 1.29  2010/06/07 16:34:22  willuhn
 * @N Code zum Aendern der UST-Voranmelde-Kennzeichen im Steuersatz
 *
 * Revision 1.28  2010/06/04 00:33:56  willuhn
 * @B Debugging
 * @N Mehr Icons
 * @C GUI-Cleanup
 *
 * Revision 1.27  2010/06/01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 * Revision 1.26  2009/07/03 10:52:19  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.24  2006/01/03 17:55:53  willuhn
 * @N a lot more checks
 * @B NPEs
 * @N BuchungsTemplates pro Mandant/Kontenrahmen
 * @N Default-Geschaeftsjahr in init.sql verschoben
 * @N Handling von Eingabe von Altbestaenden im AV
 *
 * Revision 1.23  2006/01/02 15:18:29  willuhn
 * @N Buchungs-Vorlagen
 *
 * Revision 1.22  2005/10/06 22:27:16  willuhn
 * @N KontoInput
 *
 * Revision 1.21  2005/10/06 17:27:59  willuhn
 * @N KontoInput
 * @N Einstellungen
 *
 * Revision 1.20  2005/10/05 17:52:33  willuhn
 * @N steuer behaviour
 *
 * Revision 1.19  2005/09/26 23:52:00  willuhn
 * *** empty log message ***
 *
 * Revision 1.18  2005/09/01 21:18:01  willuhn
 * *** empty log message ***
 *
 * Revision 1.17  2005/08/29 12:17:29  willuhn
 * @N Geschaeftsjahr
 *
 * Revision 1.16  2005/08/22 16:37:22  willuhn
 * @N Anfangsbestaende
 *
 * Revision 1.15  2005/08/16 17:39:24  willuhn
 * *** empty log message ***
 *
 * Revision 1.14  2005/08/09 23:53:34  willuhn
 * @N massive refactoring
 *
 * Revision 1.13  2005/08/08 22:54:15  willuhn
 * @N massive refactoring
 *
 * Revision 1.12  2005/08/08 21:35:46  willuhn
 * @N massive refactoring
 *
 * Revision 1.11  2004/02/24 22:48:08  willuhn
 * *** empty log message ***
 *
 * Revision 1.10  2004/01/29 01:21:51  willuhn
 * *** empty log message ***
 *
 * Revision 1.9  2004/01/29 01:14:52  willuhn
 * *** empty log message ***
 *
 * Revision 1.8  2004/01/29 00:06:46  willuhn
 * *** empty log message ***
 *
 * Revision 1.7  2004/01/27 00:09:10  willuhn
 * *** empty log message ***
 *
 * Revision 1.6  2004/01/03 18:07:22  willuhn
 * @N Exception logging
 *
 * Revision 1.5  2003/12/15 19:08:04  willuhn
 * *** empty log message ***
 *
 * Revision 1.4  2003/12/12 01:28:07  willuhn
 * *** empty log message ***
 *
 * Revision 1.3  2003/12/11 21:00:35  willuhn
 * @C refactoring
 *
 * Revision 1.2  2003/12/10 23:51:53  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2003/12/01 20:29:00  willuhn
 * @B filter in DBIteratorImpl
 * @N InputFelder generalisiert
 *
 **********************************************************************/