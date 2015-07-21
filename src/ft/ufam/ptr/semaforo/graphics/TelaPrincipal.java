package ft.ufam.ptr.semaforo.graphics;

import javax.swing.*;

import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.model.*;
import ft.ufam.ptr.semaforo.utils.*;

/** Contém a implementação da interface gráfica principal do sistema.
 *  @author Felipe André
 *  @version 1.0, 16/04/2015 */
public class TelaPrincipal extends JFrame implements SemaforoListener {

	/* Atributos da classe */
	private static final long serialVersionUID = 1L;
	private final JPanel painel;
	private JTextField textSemaforoX;
	private JTextField textFluxoX;
	private JTextField textOcupacaoX;

	/** Função principal */
	public static void main(String[] args) {
		new TelaPrincipal();
	}

	/** Desenha a janela gráfica */
	public TelaPrincipal() {
		super("Semáforo Inteligente");
		
		painel = new JPaintedPanel("img/frente-ufam.jpg");
		setContentPane(painel);
		painel.setLayout(null);
		
		JPanel painelInfoX = new JPanel();
		painelInfoX.setBounds(24, 286, 213, 154);
		//panel.setOpaque(false);
		painel.add(painelInfoX);
		painelInfoX.setLayout(null);
		
		JPanel panelSemaforoX = new JPanel();
		panelSemaforoX.setBorder(GraphicsHelper.getTitledBorder("Semáforo"));
		panelSemaforoX.setBounds(12, 12, 189, 53);
		painelInfoX.add(panelSemaforoX);
		panelSemaforoX.setLayout(null);
		
		textSemaforoX = new JTextField();
		textSemaforoX.setBounds(12, 22, 166, 20);
		panelSemaforoX.add(textSemaforoX);
		textSemaforoX.setColumns(10);
		
		JPanel painelViaX = new JPanel();
		painelViaX.setBorder(GraphicsHelper.getTitledBorder("Via"));
		painelViaX.setBounds(12, 71, 189, 71);
		painelInfoX.add(painelViaX);
		painelViaX.setLayout(null);
		
		JLabel labelFluxoX = new JLabel("Fluxo:");
		labelFluxoX.setBounds(12, 26, 70, 15);
		painelViaX.add(labelFluxoX);
		
		textFluxoX = new JTextField();
		textFluxoX.setBounds(100, 24, 77, 19);
		painelViaX.add(textFluxoX);
		textFluxoX.setColumns(10);
		
		JLabel labelOcupacaoX = new JLabel("Ocupação:");
		labelOcupacaoX.setBounds(12, 44, 87, 15);
		painelViaX.add(labelOcupacaoX);
		
		textOcupacaoX = new JTextField();
		textOcupacaoX.setColumns(10);
		textOcupacaoX.setBounds(100, 43, 77, 19);
		painelViaX.add(textOcupacaoX);
		
		setSize(1024,489);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		new Simulador(this);
	}
	
	public void fireFluxoUpdate(Local chave, int valor) {
		
		String texto = String.valueOf(valor);
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				fireTextFieldUpdate(textFluxoX, texto);
			break;
			
			case ENTRADA_SAIDA_CAMPUS:
			break;
			
			case COROADO_ENTRADA_CAMPUS:
			break;
			
			case SAIDA_CAMPUS_COROADO:
			break;
			
			default:
			break;
		}
	}
	
	private void fireSemaforoUpdate(Local chave, Estado atual) {
		
		String texto = atual.name();
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				fireTextFieldUpdate(textSemaforoX, texto);
				break;
		
			case ENTRADA_SAIDA_CAMPUS:
				break;
		
			case COROADO_ENTRADA_CAMPUS:
				break;
		
			case SAIDA_CAMPUS_COROADO:
				break;
		
			default:
				break;
		}
	}
	
	public void fireOcupacaoUpdate(Local chave, int valor) {
		
		String texto = String.valueOf(valor);
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				fireTextFieldUpdate(textOcupacaoX, texto);
				break;
		
			case ENTRADA_SAIDA_CAMPUS:
				break;
		
			case COROADO_ENTRADA_CAMPUS:
				break;
		
			case SAIDA_CAMPUS_COROADO:
				break;
		
			default:
				break;
	}
	}
	
	public void fireTextFieldUpdate(JTextField key, String value) {
		GraphicsUpdater thread = new GraphicsUpdater(key, value);
		SwingUtilities.invokeLater(thread);
	}
	
	private class GraphicsUpdater implements Runnable {

		private JTextField textField;
		private String value;
		
		public GraphicsUpdater(JTextField textField, String value) {
			this.textField = textField;
			this.value = value;
		}
		
		@Override
		public void run() {
			textField.setText(value);
		}
		
	}

	@Override
	public void onStateChange(SemaforoEvent event) {
		Semaforo semaforo = (Semaforo) event.getSource();
		
		Estado atual = semaforo.getEstadoAtual();
		Local  local = semaforo.getLocalizacao();
		
		fireSemaforoUpdate(local, atual);
	}
}
