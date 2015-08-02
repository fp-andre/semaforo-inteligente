package ft.ufam.ptr.semaforo.graphics;

import java.awt.*;
import javax.swing.*;
import java.awt.print.*;
import ft.ufam.ptr.semaforo.utils.*;

/** Encapsula os trabalhos de impressão da interface gráfica
 *  @author Felipe André
 *  @version 1.0, 02/08/2015 */
public class PrintSupport implements Printable {

	/** Componente a ser impresso */
    private Component print_component;

    /** Instância estática desta classe */
    public static void printComponent(Component component) {
        new PrintSupport(component).doPrint();
    }

    /** Inicializa os parâmentros */
    public PrintSupport(Component component) {
        this.print_component = component;
    }

    /** Imprime o componente usando as impressoras do sistema */
    public void doPrint() {
    	
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);
        
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException exception) {
            	AlertDialog.erro("Impressão", "Falha ao imprimir!\n" + exception.getMessage());
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
    	
        if (pageIndex > 0)
            return NO_SUCH_PAGE;
            
        else {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            disableDoubleBuffering(print_component);
            print_component.paint(g2d);
            enableDoubleBuffering(print_component);
            return PAGE_EXISTS;
        }
    }

    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }
}
