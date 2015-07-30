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
	
	public static final int WIDTH  = 165;
	public static final int HEIGHT = 30;
	
	private static Icon getIcon(String path) {
		String absolutePath = PropertiesManager.getResource(path);
		
		Image dimg = getScaledImage(absolutePath);
		
		return new ImageIcon(dimg);
	}
	
	private static Image getScaledImage(String absolutePath) {
		BufferedImage img = null;
		
		try { img = ImageIO.read(new File(absolutePath)); }
		catch (IOException exception) { exception.printStackTrace(); }
		
		Image dimg = img.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		
		return dimg;
	}
	
}
