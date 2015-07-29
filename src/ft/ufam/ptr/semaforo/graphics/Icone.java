package ft.ufam.ptr.semaforo.graphics;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;
import ft.ufam.ptr.semaforo.utils.*;

public class Icone {

	public static final Icon VERDE    = getIcon("img/semaforo-verde.png"   );
	public static final Icon AMARELO  = getIcon("img/semaforo-amarelo.png" );
	public static final Icon VERMELHO = getIcon("img/semaforo-vermelho.png");
	public static final Icon OFF      = getIcon("img/semaforo-off.png"     );
	
	private static Icon getIcon(String path) {
		String absolutePath = PropertiesManager.getResource(path);
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(absolutePath));
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("erroroorior");
		}
		
		Image dimg = img.getScaledInstance(166, 20, Image.SCALE_SMOOTH);
		
		return new ImageIcon(dimg);
	}
	
}
