package de.willuhn.jameica.fibu.gui.menus;

import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.gui.action.BuchungDelete;
import de.willuhn.jameica.fibu.gui.action.BuchungMarkChecked;
import de.willuhn.jameica.fibu.gui.action.BuchungNeu;
import de.willuhn.jameica.fibu.rmi.Buchung;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.I18N;

public class BuchungSplitListMenu extends ContextMenu{
	/**
	   * ct.
	   */
	  public BuchungSplitListMenu()
	  {
	    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
	    this.addItem(new SingleItem(i18n.tr("�ffnen"), new BuchungNeu(),"document-open.png"));
	    this.addItem(new GJCheckedContextMenuItem(i18n.tr("L�schen..."), new BuchungDelete(),"user-trash-full.png"));
	    this.addItem(ContextMenuItem.SEPARATOR);
	    this.addItem(new GeprueftItem(i18n.tr("Als \"gepr�ft\" markieren"), new BuchungMarkChecked(true),false,"emblem-default.png"));
	    this.addItem(new GeprueftItem(i18n.tr("Als \"ungepr�ft\" markieren"), new BuchungMarkChecked(false),true,"edit-undo.png"));
	  }
	  
	  /**
	   * Ueberschrieben, um zu pruefen, ob ein Array oder ein einzelnes Element markiert ist.
	   */
	  private static class SingleItem extends GJCheckedContextMenuItem
	  {
	    /**
	     * @param text
	     * @param action
	     * @param icon
	     */
	    private SingleItem(String text, Action action, String icon)
	    {
	      super(text,action, icon);
	    }
	    /**
	     * @see de.willuhn.jameica.gui.parts.ContextMenuItem#isEnabledFor(java.lang.Object)
	     */
	    public boolean isEnabledFor(Object o)
	    {
	      if (o instanceof Buchung[])
	        return false;
	      return super.isEnabledFor(o);
	    }
	  }

	  /**
	   * Ueberschrieben, um zu pruefen, ob die Buchung als geprueft oder ungeprueft markiert werden kann.
	   */
	  private static class GeprueftItem extends GJCheckedContextMenuItem
	  {
	    private boolean geprueft = false;
	    
	    /**
	     * @param text
	     * @param action
	     * @param geprueft
	     * @param icon
	     */
	    private GeprueftItem(String text, Action action, boolean geprueft, String icon)
	    {
	      super(text,action,icon);
	      this.geprueft = geprueft;
	    }

	    /**
	     * @see de.willuhn.jameica.gui.parts.ContextMenuItem#isEnabledFor(java.lang.Object)
	     */
	    public boolean isEnabledFor(Object o)
	    {
	      if (o instanceof Buchung)
	      {
	        try
	        {
	          Buchung b = (Buchung) o;
	          return super.isEnabledFor(o) && ((b.isGeprueft() && geprueft) || (!b.isGeprueft() && !geprueft));
	        }
	        catch (Exception e)
	        {
	          Logger.error("unable to check buchung",e);
	        }
	      }
	      return super.isEnabledFor(o);
	    }
	  }
}
