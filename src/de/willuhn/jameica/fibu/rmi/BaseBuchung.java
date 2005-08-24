/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/rmi/BaseBuchung.java,v $
 * $Revision: 1.4 $
 * $Date: 2005/08/22 21:44:09 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.rmi;

import java.rmi.RemoteException;
import java.util.Date;

import de.willuhn.datasource.rmi.DBObject;

/**
 * Diese Klasse bildet die Buchungen in Fibu ab.
 * @author willuhn
 */
public interface BaseBuchung extends DBObject
{

  /**
   * Liefert das Datum der Buchung.
   * Wenn es eine neue Buchung ist, wird das aktuelle Datum geliefert.
   * @return Datum der Buchung.
   * @throws RemoteException
   */
  public Date getDatum() throws RemoteException;

  /**
   * Liefert das Soll-Konto zu dieser Buchung.
   * @return Konto der Buchung.
   * @throws RemoteException
   */
  public Konto getSollKonto() throws RemoteException;

  /**
   * Liefert das Haben-Konto zu dieser Buchung.
   * @return Haben-Konto der Buchung.
   * @throws RemoteException
   */
  public Konto getHabenKonto() throws RemoteException;
  
  /**
   * Liefert den Mandanten zu dieser Buchung.
   * @return Mandant der Buchung.
   * @throws RemoteException
   */
  public Mandant getMandant() throws RemoteException;

  /**
   * Liefert den Text der Buchung.
   * @return Text der Buchung.
   * @throws RemoteException
   */
  public String getText() throws RemoteException;

  /**
   * Liefert die Belegnummer oder erzeugt eine neue, wenn sie null ist.
   * @return Belegnummer.
   * @throws RemoteException
   */
  public int getBelegnummer() throws RemoteException;

  /**
   * Liefert den Netto-Betrag der Buchung.
   * @return Brutto-Betrag der Buchung.
   * @throws RemoteException
   */
  public double getBetrag() throws RemoteException;

  /**
   * Liefert Steuersatz der Buchung.
   * @return Steuersatz der Buchung.
   * @throws RemoteException
   */
  public double getSteuer() throws RemoteException;

  /**
   * Setzt das Datum der Buchung.
   * @param d Datum.
   * @throws RemoteException
   */
  public void setDatum(Date d) throws RemoteException;

  /**
   * Setzt das Soll-Konto zu dieser Buchung.
   * @param k Soll-Konto der Buchung.
   * @throws RemoteException
   */
  public void setSollKonto(Konto k) throws RemoteException;

  /**
   * Setzt das Haben-Konto zu dieser Buchung.
   * @param k Haben-Konto der Buchung.
   * @throws RemoteException
   */
  public void setHabenKonto(Konto k) throws RemoteException;

  /**
   * Setzt den Text der Buchung.
   * @param text Text der Buchung.
   * @throws RemoteException
   */
  public void setText(String text) throws RemoteException;

  /**
   * Setzt die Belegnummer.
   * @param belegnummer Belegnummer der Buchung.
   * @throws RemoteException
   */
  public void setBelegnummer(int belegnummer) throws RemoteException;

  /**
   * Setzt den Netto-Betrag der Buchung.
   * @param betrag Betrag der Buchung.
   * @throws RemoteException
   */
  public void setBetrag(double betrag) throws RemoteException;

  /**
   * Setzt den Steuersatz der Buchung.
   * @param steuer Steuersatz der Buchung.
   * @throws RemoteException
   */
  public void setSteuer(double steuer) throws RemoteException;

  /**
   * Setzt den Mandanten der Buchung.
   * @param mandant Mandant der Buchung.
   * @throws RemoteException
   */
  public void setMandant(Mandant mandant) throws RemoteException;

}

/*********************************************************************
 * $Log: BaseBuchung.java,v $
 * Revision 1.4  2005/08/22 21:44:09  willuhn
 * @N Anfangsbestaende
 *
 * Revision 1.3  2005/08/22 16:37:22  willuhn
 * @N Anfangsbestaende
 *
 * Revision 1.2  2005/08/15 13:18:44  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2005/08/12 00:10:59  willuhn
 * @B bugfixing
 *
 * Revision 1.13  2005/08/09 23:53:34  willuhn
 * @N massive refactoring
 *
 * Revision 1.12  2004/01/25 19:44:03  willuhn
 * *** empty log message ***
 *
 * Revision 1.11  2003/12/16 02:27:32  willuhn
 * @N BuchungsEngine
 *
 * Revision 1.10  2003/12/15 19:08:04  willuhn
 * *** empty log message ***
 *
 * Revision 1.9  2003/12/11 21:00:34  willuhn
 * @C refactoring
 *
 * Revision 1.8  2003/12/10 23:51:52  willuhn
 * *** empty log message ***
 *
 * Revision 1.7  2003/12/05 17:11:58  willuhn
 * @N added GeldKonto, Kontoart
 *
 * Revision 1.6  2003/12/01 21:23:00  willuhn
 * *** empty log message ***
 *
 * Revision 1.5  2003/11/30 16:23:11  willuhn
 * *** empty log message ***
 *
 * Revision 1.4  2003/11/24 23:02:11  willuhn
 * @N added settings
 *
 * Revision 1.3  2003/11/24 14:21:56  willuhn
 * *** empty log message ***
 *
 * Revision 1.2  2003/11/21 02:10:56  willuhn
 * @N buchung dialog works now
 *
 * Revision 1.1  2003/11/20 03:48:44  willuhn
 * @N first dialogues
 *
 **********************************************************************/