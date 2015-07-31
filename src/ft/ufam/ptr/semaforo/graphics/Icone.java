package ft.ufam.ptr.semaforo.graphics;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import ft.ufam.ptr.semaforo.utils.*;

/** Contém as imagens dos estados dos semáforos...
 *  @author Felipe André
 *  @version 1.0, 30/07/2015 */
public class Icone {
	
	/** ...de veículos */
	public static class Veiculo {
		
		/* Dimensões do JLabel */
		public static final Dimension BOUNDS = new Dimension(165,30);
		
		/* Arquivos com as imagens */
		public static final Icon VERDE    = getIcon("img/semaforo-verde.png"   );
		public static final Icon AMARELO  = getIcon("img/semaforo-amarelo.png" );
		public static final Icon VERMELHO = getIcon("img/semaforo-vermelho.png");
		public static final Icon OFF      = getIcon("img/semaforo-off.png"     );
		
	}
	
	/** ...e de pedestres */
	public static class Pedestre {
		
		/* Dimensões do JLabel */
		public static final Dimension BOUNDS = new Dimension(165,30);
		
		/* Arquivos com as imagens */
		public static final Icon VERDE    = getIcon("img/pedestre-verde.png"   );
		public static final Icon VERMELHO = getIcon("img/pedestre-vermelho.png");
		public static final Icon OFF      = getIcon("img/pedestre-off.png"     );
		
	}
	
	/** Carrega uma imagem redimensionada a partir de um arquivo */
	private static Image getScaledImage(String absolutePath, Dimension dimensao) {
		BufferedImage imagem = null;
		
		try   { imagem = ImageIO.read(new File(absolutePath)); }
		catch (IOException exception) { exception.printStackTrace(); }
		
		Image scaled = imagem.getScaledInstance((int)dimensao.getWidth(), (int)dimensao.getHeight(), Image.SCALE_SMOOTH);
		
		return scaled;
	}
	
	/** Carrega uma imagem */
	private static Icon getIcon(String path) {
		String absolutePath = PropertiesManager.getResource(path);
		
		Image scaled = getScaledImage(absolutePath, Veiculo.BOUNDS);
		
		return new ImageIcon(scaled);
	}
	
}
