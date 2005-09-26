/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/action/BuchungDelete.java,v $
 * $Revision: 1.7 $
 * $Date: 2005/09/26 15:15:39 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.action;

import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.rmi.BaseBuchung;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.dialogs.YesNoDialog;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Aktion zum Loeschen einer einzelnen oder einer Liste von Buchungen.
 */
public class BuchungDelete implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {
    if (context == null || (!(context instanceof BaseBuchung) && !(context instanceof BaseBuchung[])))
      return;
    
    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
    
    try
    {
      BaseBuchung[] b = null;
      
      if (context instanceof BaseBuchung)
        b = new BaseBuchung[] {(BaseBuchung) context};
      else
        b = (BaseBuchung[]) context;
      
      YesNoDialog d = new YesNoDialog(YesNoDialog.POSITION_CENTER);
      if (b.length > 1)
      {
        d.setTitle(i18n.tr("Buchungen wirklich stornieren?"));
        d.setText(i18n.tr("Wollen Sie diese {0} Buchungen wirklich stornieren?",""+b.length));
      }
      else
      {
        d.setTitle(i18n.tr("Buchung wirklich stornieren?"));
        d.setText(i18n.tr("Wollen Sie die Buchung \"{0}\" [Beleg {1}] wirklich stornieren?",new String[]{b[0].getText(),""+b[0].getBelegnummer()}));
      }
      

      if (!((Boolean) d.open()).booleanValue())
      {
        Logger.info("operation cancelled");
        return;
      }

      b[0].transactionBegin();
      try
      {
        for (int i=0;i<b.length;++i)
        {
          b[i].delete();
        }
        b[0].transactionCommit();

        if (b.length > 1)
          GUI.getStatusBar().setSuccessText(i18n.tr("{0} Buchungen storniert.", ""+b.length));
        else
          GUI.getStatusBar().setSuccessText(i18n.tr("Buchung Nr. {0} storniert.", ""+b[0].getBelegnummer()));
      }
      catch (ApplicationException ae)
      {
        b[0].transactionRollback();
        throw ae;
      }
      catch (Exception e)
      {
        b[0].transactionRollback();
        Logger.error("unable to delete buchung",e);
        throw new ApplicationException(i18n.tr("Fehler beim Stornieren der Buchung(en)"));
      }

    }
    catch (Exception e)
    {
      Logger.error("unable to delete buchung",e);
      throw new ApplicationException(i18n.tr("Fehler beim Stornieren der Buchung(en)"));
    }
  }

}


/*********************************************************************
 * $Log: BuchungDelete.java,v $
 * Revision 1.7  2005/09/26 15:15:39  willuhn
 * *** empty log message ***
 *
 * Revision 1.6  2005/08/16 17:39:24  willuhn
 * *** empty log message ***
 *
 * Revision 1.5  2005/08/15 23:38:27  willuhn
 * *** empty log message ***
 *
 * Revision 1.4  2005/08/15 13:18:44  willuhn
 * *** empty log message ***
 *
 * Revision 1.3  2005/08/12 00:10:59  willuhn
 * @B bugfixing
 *
 * Revision 1.2  2005/08/09 23:53:34  willuhn
 * @N massive refactoring
 *
 * Revision 1.1  2005/08/08 21:35:46  willuhn
 * @N massive refactoring
 *
 **********************************************************************/