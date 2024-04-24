/**********************************************************************
 *
 * Copyright (c) 2004 Olaf Willuhn
 * All rights reserved.
 * 
 * This software is copyrighted work licensed under the terms of the
 * Jameica License.  Please consult the file "LICENSE" for details. 
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.io.report;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.fibu.rmi.Anfangsbestand;
import de.willuhn.jameica.fibu.rmi.Geschaeftsjahr;
import de.willuhn.jameica.fibu.rmi.Konto;
import de.willuhn.jameica.fibu.rmi.Buchung;
import de.willuhn.jameica.fibu.rmi.Kontoart;

/**
 * Report fuer Konten.
 */
public class VelocityReportKontoAuszug extends AbstractVelocityReport
{
  private static Map filenameMap = new HashMap();
  
  static
  {
    filenameMap.put("Anlageverm�gen"    ,"anlagevermoegen");
    filenameMap.put("Aufw�nde"          ,"aufwaende");
    filenameMap.put("Erl�se"            ,"erloese");
    filenameMap.put("Geldkonto"         ,"geldkonto");
    filenameMap.put("Privatkonto"       ,"privatkonto");
    filenameMap.put("Steuer-Sammelkonto","steuer-sammelkonto");
  }
  
  /**
   * @see de.willuhn.jameica.fibu.io.report.AbstractVelocityReport#getData(de.willuhn.jameica.fibu.io.report.ReportData)
   */
  protected VelocityReportData getData(ReportData data) throws Exception
  {
    Geschaeftsjahr jahr = data.getGeschaeftsjahr();
    Date startDate = data.getStartDatum();
    Date endDate   = data.getEndDatum();
      

    VelocityReportData export = new VelocityReportData();
    export.addObject("filenames",filenameMap);
    export.setTemplate("kontoauszug.vm");

    //////////////////////////////////////////////////////////////////////////
    // Konten
    DBIterator konten   = jahr.getKontenrahmen().getKonten();
    Konto start = data.getStartKonto();
    Konto end   = data.getEndKonto();
    if (start != null) konten.addFilter("kontonummer >= ?", start.getKontonummer());
    if (end != null) konten.addFilter("kontonummer <= ?",   end.getKontonummer());

    List<Konto> l = new LinkedList<Konto>();
    while (konten.hasNext())
    {
      Konto k1 = (Konto) konten.next();
      Anfangsbestand ab = k1.getAnfangsbestand(jahr);
      if (!data.isLeereKonten() && k1.getNumBuchungen(jahr,startDate,endDate) == 0 && (ab == null || ab.getBetrag() == 0.0d))
        continue;
      l.add(k1);
    }
    export.addObject("konten",l);
    //
    //////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////
    // Buchungen
    for (Konto k:l)
    {
      Vector buchungen = new Vector();

      DBIterator list = k.getHauptBuchungen(jahr,startDate,endDate,true);
      while (list.hasNext()) {
    	  Buchung b = (Buchung)list.next();
    	   buchungen.add(b);
      }

      Kontoart ka = k.getKontoArt();
      if (ka != null && ka.getKontoArt() == Kontoart.KONTOART_STEUER)
      {
        // Ein Steuerkonto enthaelt normalerweise nur automatisch
        // erzeugte Hilfsbuchungen. Da der User aber auch echte
        // Hauptbuchungen darauf erzeugen kann, muss die Liste
        // hier noch um die Hauptbuchungen ergaenzt werden.
        list = k.getHilfsBuchungen(jahr,startDate,endDate);
        while (list.hasNext())
          buchungen.add(list.next());
      }
      export.addObject("buchungen." + k.getKontonummer(),buchungen);
    }
    //////////////////////////////////////////////////////////////////////////

    return export;
  }

  /**
   * @see de.willuhn.jameica.fibu.io.report.AbstractReport#createPreset()
   */
  public ReportData createPreset()
  {
    ReportData data = super.createPreset();
    data.setNeedLeereKonten(true);
    data.setTarget(i18n.tr("syntax-{0}-kontoauszug.html",DATEFORMAT.format(new Date())));
    return data;
  }

  /**
   * @see de.willuhn.jameica.fibu.io.report.Report#getName()
   */
  public String getName()
  {
    return i18n.tr("Konten: Auszug");
  }
}


/*********************************************************************
 * $Log: VelocityReportKontoAuszug.java,v $
 * Revision 1.5  2011/08/08 10:44:35  willuhn
 * @C compiler warnings
 *
 * Revision 1.4  2011-03-10 16:10:49  willuhn
 * @B Auswertung Kontoauszug erlaubt die Auswahl eines Zeitraumes innerhalb des Jahres - das muss in getNumBuchungen() auch beachtet werden
 *
 * Revision 1.3  2011-03-10 13:42:26  willuhn
 * @B BUGZILLA 1001
 *
 * Revision 1.2  2010-11-30 23:32:18  willuhn
 * @B BUGZILLA 953
 * @C Velocity kann inzwischen mit java.util.List-Objekten umgehen. Das Erzeugen der Arrays ist daher nicht mehr noetig
 *
 * Revision 1.1  2010-08-27 10:18:14  willuhn
 * @C Export umbenannt in Report
 *
 * Revision 1.2  2009/07/03 10:52:18  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.1.2.2  2009/06/24 10:35:55  willuhn
 * @N Jameica 1.7 Kompatibilitaet
 * @N Neue Auswertungen funktionieren - werden jetzt im Hintergrund ausgefuehrt
 *
 * Revision 1.1.2.1  2009/06/23 16:53:22  willuhn
 * @N Velocity-Export komplett ueberarbeitet
 *
 **********************************************************************/