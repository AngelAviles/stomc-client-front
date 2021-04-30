package print;

import dominio.Turn;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaSize;

public class PrintController {

    public static void imprimirTurno(Turn turno) {

        String cadena = "TURNO";

        cadena += darEspacio();

        switch (turno.getType()) {
            case CAJA -> cadena += "C" + turno.getTurnNumber() + darEspacio() + "CAJAS";
            case MODULO -> cadena += "M" + turno.getTurnNumber() + darEspacio() + "MODULOS";
            case GENERIC -> cadena += "G" + turno.getTurnNumber() + darEspacio() + "CAJAS/MODULOS";
        }

        cadena += darEspacio();
        cadena += darEspacio();

        cadena += "TOME ASIENTO Y ESPERE UN";
        cadena += "MOMENTO PORFAVOR";

        cadena += turno.getDateTimeCreated().toLocaleString();


        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(new Copies(1));

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        DocPrintJob docPrintJob = printService.createPrintJob();
        Doc doc = new SimpleDoc(cadena.getBytes(), flavor, null);
        try {
            docPrintJob.print(doc, pras);
        } catch (PrintException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String darEspacio() {
        return "\n";
    }
}
