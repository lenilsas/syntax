/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/menus/KontoListMenu.java,v $
 * $Revision: 1.1 $
 * $Date: 2005/08/16 23:14:36 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.menus;

import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.gui.action.KontoDelete;
import de.willuhn.jameica.fibu.gui.action.KontoExport;
import de.willuhn.jameica.fibu.gui.action.KontoNeu;
import de.willuhn.jameica.fibu.rmi.Konto;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Vorkonfiguriertes Kontext-Menu fuer Konten-Listen.
 */
public class KontoListMenu extends ContextMenu
{
  /**
   * ct.
   */
  public KontoListMenu()
  {
    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
    this.addItem(new SingleItem(i18n.tr("Bearbeiten"), new KontoNeu()));
    this.addItem(new CheckedContextMenuItem(i18n.tr("L�schen"), new KontoDelete()));
    this.addItem(ContextMenuItem.SEPARATOR);
    this.addItem(new ContextMenuItem(i18n.tr("Neues Konto"), new KNeu()));
    this.addItem(ContextMenuItem.SEPARATOR);
    this.addItem(new CheckedContextMenuItem(i18n.tr("Report erzeugen..."), new KontoExport()));
  }
  
  /**
   * Ueberschrieben, um zu pruefen, ob ein Array oder ein einzelnes Element markiert ist.
   */
  private class SingleItem extends CheckedContextMenuItem
  {
    /**
     * @param text
     * @param action
     */
    private SingleItem(String text, Action action)
    {
      super(text,action);
    }
    /**
     * @see de.willuhn.jameica.gui.parts.ContextMenuItem#isEnabledFor(java.lang.Object)
     */
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof Konto[])
        return false;
      return super.isEnabledFor(o);
    }
  }

  /**
   * Erzeugt immer ein neues Konto - unabhaengig vom Kontext.
   */
  private class KNeu extends KontoNeu
  {
    /**
     * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
     */
    public void handleAction(Object context) throws ApplicationException
    {
      super.handleAction(null);
    }
    
  }
}


/*********************************************************************
 * $Log: KontoListMenu.java,v $
 * Revision 1.1  2005/08/16 23:14:36  willuhn
 * @N velocity export
 * @N context menus
 * @B bugfixes
 *
 **********************************************************************/