package ft.ufam.ptr.semaforo.graphics;

import javax.swing.*;
import ft.ufam.ptr.semaforo.utils.*;

/** Contém a implementação da interface gráfica principal do sistema.
 *  @author Felipe André
 *  @version 1.0, 16/04/2015 */
public class TelaPrincipal extends JFrame {

	/* Atributos da classe */
	private static final long serialVersionUID = 1L;
	private JPanel painel;

	/** Função principal */
	public static void main(String[] args) {
		new TelaPrincipal();
	}

	/** Desenha a janela gráfica */
	public TelaPrincipal() {
		super("Semáforo Inteligente");
		
		painel = new JPaintedPanel("img/frente-ufam.jpg");
		setContentPane(painel);
		
		setSize(1024,489);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

}
