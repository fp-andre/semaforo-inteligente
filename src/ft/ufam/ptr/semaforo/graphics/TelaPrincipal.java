package ft.ufam.ptr.semaforo.graphics;

import javax.swing.*;

import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.model.*;
import ft.ufam.ptr.semaforo.utils.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

/** Contém a implementação da interface gráfica principal do sistema.
 *  @author Felipe André
 *  @version 1.5, 29/07/2015 */
public class TelaPrincipal extends JFrame implements SemaforoListener {

	/* Atributos da classe */
	private static final long serialVersionUID = 1L;
	private final JPanel painelMaster;
	private final Simulador simulador;
	private JLabel labelSemaforo01, labelSemaforo02, labelSemaforo03, labelSemaforo04;
	private LightsManager manager01, manager02, manager03, manager04;
	private JTextField textOcupacao01, textFluxo01;
	private JTextField textFluxo02;
	private JTextField textOcupacao02;
	private JTextField textFluxo03;
	private JTextField textOcupacao03;
	private JTextField textFluxo04;
	private JTextField textOcupacao04;
	private JButton botaoSync, botaoSair;

	/** Função principal */
	public static void main(String[] args) {
		new TelaPrincipal();
	}
	
	/** Desenha a janela gráfica */
	public TelaPrincipal() {
		super("Semáforo Inteligente");
		
		painelMaster = new JPaintedPanel("img/frente-ufam-novo.jpg");
		setContentPane(painelMaster);
		painelMaster.setLayout(null);
		
		JPanel painelInfo01 = new JPanel();
		painelInfo01.setOpaque(false);
		painelInfo01.setBounds(153, 0, 190, 133);
		painelMaster.add(painelInfo01);
		painelInfo01.setLayout(null);
		
		labelSemaforo01 = new JLabel();
		labelSemaforo01.setBounds(12, 95, 165, 30);
		painelInfo01.add(labelSemaforo01);
		
		JPanel painelVia01 = new JPanel();
		painelVia01.setBorder(GraphicsHelper.getTitledBorder("Via"));
		painelVia01.setBounds(51, 12, 82, 71);
		painelInfo01.add(painelVia01);
		painelVia01.setLayout(null);
		
		JLabel labelFluxo01 = new JLabel("F:");
		labelFluxo01.setBounds(12, 26, 25, 15);
		painelVia01.add(labelFluxo01);
		
		textFluxo01 = new JTextField();
		textFluxo01.setBounds(35, 21, 36, 19);
		painelVia01.add(textFluxo01);
		textFluxo01.setColumns(10);
		
		JLabel labelOcupacao01 = new JLabel("O:");
		labelOcupacao01.setBounds(12, 44, 25, 15);
		painelVia01.add(labelOcupacao01);
		
		textOcupacao01 = new JTextField();
		textOcupacao01.setColumns(10);
		textOcupacao01.setBounds(35, 40, 36, 19);
		painelVia01.add(textOcupacao01);
		
		botaoSync = new JButton("Sync");
		botaoSync.addActionListener(new SyncEvent());
		
		JPanel painelInfo02 = new JPanel();
		painelInfo02.setLayout(null);
		painelInfo02.setOpaque(false);
		painelInfo02.setBounds(893, 537, 190, 133);
		painelMaster.add(painelInfo02);
		
		labelSemaforo02 = new JLabel();
		labelSemaforo02.setBounds(12, 95, 165, 30);
		painelInfo02.add(labelSemaforo02);
		
		JPanel painelVia02 = new JPanel();
		painelVia02.setLayout(null);
		painelVia02.setBorder(GraphicsHelper.getTitledBorder("Via"));
		painelVia02.setBounds(59, 12, 82, 71);
		painelInfo02.add(painelVia02);
		
		JLabel labelFluxo02 = new JLabel("F:");
		labelFluxo02.setBounds(12, 26, 25, 15);
		painelVia02.add(labelFluxo02);
		
		textFluxo02 = new JTextField();
		textFluxo02.setColumns(10);
		textFluxo02.setBounds(35, 21, 36, 19);
		painelVia02.add(textFluxo02);
		
		JLabel labelOcupacao02 = new JLabel("O:");
		labelOcupacao02.setBounds(12, 44, 25, 15);
		painelVia02.add(labelOcupacao02);
		
		textOcupacao02 = new JTextField();
		textOcupacao02.setColumns(10);
		textOcupacao02.setBounds(35, 40, 36, 19);
		painelVia02.add(textOcupacao02);
		
		JPanel painelInfo03 = new JPanel();
		painelInfo03.setLayout(null);
		painelInfo03.setOpaque(false);
		painelInfo03.setBounds(12, 33, 190, 133);
		painelMaster.add(painelInfo03);
		
		labelSemaforo03 = new JLabel();
		labelSemaforo03.setBounds(12, 95, 165, 30);
		painelInfo03.add(labelSemaforo03);
		
		JPanel painelVia03 = new JPanel();
		painelVia03.setLayout(null);
		painelVia03.setBorder(GraphicsHelper.getTitledBorder("Via"));
		painelVia03.setBounds(51, 12, 82, 71);
		painelInfo03.add(painelVia03);
		
		JLabel labelFluxo03 = new JLabel("F:");
		labelFluxo03.setBounds(12, 26, 25, 15);
		painelVia03.add(labelFluxo03);
		
		textFluxo03 = new JTextField();
		textFluxo03.setColumns(10);
		textFluxo03.setBounds(35, 21, 36, 19);
		painelVia03.add(textFluxo03);
		
		JLabel labelOcupacao03 = new JLabel("O:");
		labelOcupacao03.setBounds(12, 44, 25, 15);
		painelVia03.add(labelOcupacao03);
		
		textOcupacao03 = new JTextField();
		textOcupacao03.setColumns(10);
		textOcupacao03.setBounds(35, 40, 36, 19);
		painelVia03.add(textOcupacao03);
		
		JPanel painelInfo04 = new JPanel();
		painelInfo04.setLayout(null);
		painelInfo04.setOpaque(false);
		painelInfo04.setBounds(1152, 599, 190, 133);
		painelMaster.add(painelInfo04);
		
		labelSemaforo04 = new JLabel();
		labelSemaforo04.setBounds(12, 95, 165, 30);
		painelInfo04.add(labelSemaforo04);
		
		JPanel painelVia04 = new JPanel();
		painelVia04.setLayout(null);
		painelVia04.setBorder(GraphicsHelper.getTitledBorder("Via"));
		painelVia04.setBounds(51, 12, 82, 71);
		painelInfo04.add(painelVia04);
		
		JLabel labelFluxo04 = new JLabel("F:");
		labelFluxo04.setBounds(12, 26, 25, 15);
		painelVia04.add(labelFluxo04);
		
		textFluxo04 = new JTextField();
		textFluxo04.setColumns(10);
		textFluxo04.setBounds(35, 21, 36, 19);
		painelVia04.add(textFluxo04);
		
		JLabel labelOcupacao04 = new JLabel("O:");
		labelOcupacao04.setBounds(12, 44, 25, 15);
		painelVia04.add(labelOcupacao04);
		
		textOcupacao04 = new JTextField();
		textOcupacao04.setColumns(10);
		textOcupacao04.setBounds(35, 40, 36, 19);
		painelVia04.add(textOcupacao04);
		botaoSync.setBounds(476, 731, 117, 25);
		painelMaster.add(botaoSync);
		
		botaoSair = new JButton("Sair");
		botaoSair.setVisible(false);
		botaoSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		botaoSair.setBounds(476, 731, 117, 25);
		painelMaster.add(botaoSair);
		
		buildScreen();
		
		simulador = new Simulador(this);
		inicializaGerenciadores();
	}
	
	private void buildScreen() {
		setSize(1366,768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
	    setBounds(0,0,getToolkit().getScreenSize().width,getToolkit().getScreenSize().height);
	    setVisible(true);
	}
	
	private void inicializaGerenciadores() {
		this.manager01 = new LightsManager(labelSemaforo01);
		this.manager02 = new LightsManager(labelSemaforo02);
		this.manager03 = new LightsManager(labelSemaforo03);
		this.manager04 = new LightsManager(labelSemaforo04);
	}
	
	private class SyncEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			simulador.sincroniza();
			botaoSync.setVisible(false);
			botaoSair.setVisible(true);
		}
		
	}
	
	public void fireFluxoUpdate(Local chave, int valor) {
		
		final String texto = String.valueOf(valor);
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				fireTextFieldUpdate(textFluxo01, texto);
			break;
			
			case ENTRADA_SAIDA_CAMPUS:
				fireTextFieldUpdate(textFluxo02, texto);
			break;
			
			case COROADO_ENTRADA_CAMPUS:
				fireTextFieldUpdate(textFluxo03, texto);
			break;
			
			case SAIDA_CAMPUS_COROADO:
				fireTextFieldUpdate(textFluxo04, texto);
			break;
			
			default:
			break;
		}
	}
	
	private void fireSemaforoUpdate(Local chave, Estado atual) {
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				manager01.changeState(atual);
				break;
		
			case ENTRADA_SAIDA_CAMPUS:
				manager02.changeState(atual);
				break;
		
			case COROADO_ENTRADA_CAMPUS:
				manager03.changeState(atual);
				break;
		
			case SAIDA_CAMPUS_COROADO:
				manager04.changeState(atual);
				break;
		
			default:
				break;
		}
	}
	
	public void fireOcupacaoUpdate(Local chave, int valor) {
		
		final String texto = String.valueOf(valor);
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				fireTextFieldUpdate(textOcupacao01, texto);
				break;
		
			case ENTRADA_SAIDA_CAMPUS:
				fireTextFieldUpdate(textOcupacao02, texto);
				break;
		
			case COROADO_ENTRADA_CAMPUS:
				fireTextFieldUpdate(textOcupacao03, texto);
				break;
		
			case SAIDA_CAMPUS_COROADO:
				fireTextFieldUpdate(textOcupacao04, texto);
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

		private final JTextField textField;
		private final String value;
		
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
