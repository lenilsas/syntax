/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/action/Attic/FirstStart.java,v $
 * $Revision: 1.2 $
 * $Date: 2006/06/12 14:08:30 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.action;

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Prueft, ob die Datenbank schon eingerichtet ist und startet ggf einen Wizard.
 */
public class FirstStart implements Action
{
  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {
    GUI.startView(de.willuhn.jameica.fibu.gui.views.FirstStart.class,context);
  }

}


/*********************************************************************
 * $Log: FirstStart.java,v $
 * Revision 1.2  2006/06/12 14:08:30  willuhn
 * @N DB-Wizard
 *
 * Revision 1.1  2006/05/29 23:05:07  willuhn
 * *** empty log message ***
 *
 **********************************************************************/