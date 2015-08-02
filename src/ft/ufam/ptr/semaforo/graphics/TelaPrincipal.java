package ft.ufam.ptr.semaforo.graphics;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.clock.*;
import ft.ufam.ptr.semaforo.model.*;
import ft.ufam.ptr.semaforo.utils.*;
import ft.ufam.ptr.semaforo.interpreter.*;
import ft.ufam.ptr.semaforo.graphics.lights.*;

/** Contém a implementação da interface gráfica principal do sistema.
 *  @author Felipe André
 *  @version 1.5, 29/07/2015 */
public class TelaPrincipal extends JFrame implements ActionListener, ClockListener, SemaforoListener {

	/* Atributos funcionais da classe */
	private static final long serialVersionUID = 1L;
	private final JPanel painelMaster;
	private final Simulador simulador;
	
	/* Itens de Menu */
	private JMenuItem menuEditor, menuSair, menuLoadScript, menuInicia, menuSincroniza, menuFastStart, menuSobre;
	
	/* Gerenciadores de Efeitos Gráficos dos Semáforos */
	private VehicleLightsManager vehicl01, vehicl02, vehicl03, vehicl04;
	private WalkerLightsManager  walker01, walker02, walker03, walker04;
	
	/* Semáforos de Veículos e Pedestres */
	private JLabel labelVeiculo01 , labelVeiculo02 , labelVeiculo03 , labelVeiculo04 ;
	private JLabel labelPedestre01, labelPedestre02, labelPedestre03, labelPedestre04;
	
	/* Informações das caixas de texto */
	private JTextField textOcupacao01, textOcupacao02, textOcupacao03, textOcupacao04;
	private JTextField textFluxo01, textFluxo02, textFluxo03, textFluxo04;
	private JTextArea textArea;
	
	/* Informações dos Ciclos de Clock */
	private JLabel labelCiclos, textCiclos;
	private JSeparator separator;

	/** Função principal */
	public static void main(String[] args) {
		new TelaPrincipal();
	}
	
	private class StdoutMonitor extends OutputStream {

		@Override
		public void write(int args) throws IOException {
			write(new byte[] {(byte) args}, 0, 1);
		}
		
		@Override
		public void write(byte[] charSequence, int offset, int length) throws IOException {
			String nova = new String(charSequence,offset,length);
			UpdateText job = new UpdateText(nova);
			SwingUtilities.invokeLater(job);
		}
		
	}
	
	private class UpdateText implements Runnable {

		private final String string;
		
		public UpdateText(String string) {
			this.string = string;
		}
		
		@Override
		public void run() {
			textArea.append(string);
		}
		
	}
	
	/** Desenha a janela gráfica */
	public TelaPrincipal() {
		super("Semáforo Inteligente");
		
		onCreateOptionsMenu();
		
		Font  fonte = GraphicsHelper.getFont();
		
		StdoutMonitor monitor = new StdoutMonitor();
		PrintStream stream = new PrintStream(monitor);
		System.setOut(stream);
		
		painelMaster = new JPaintedPanel("img/frente-ufam-novo.jpg");
		setContentPane(painelMaster);
		painelMaster.setLayout(null);
		
		JPanel painelInfo01 = new JPanel();
		painelInfo01.setOpaque(false);
		painelInfo01.setBounds(239, 0, 190, 183);
		painelMaster.add(painelInfo01);
		painelInfo01.setLayout(null);
		
		labelVeiculo01 = new JLabel(Icone.Veiculo.OFF);
		labelVeiculo01.setBounds(12, 95, 165, 30);
		painelInfo01.add(labelVeiculo01);
		
		labelPedestre01 = new JLabel(Icone.Pedestre.OFF);
		labelPedestre01.setBounds(40, 137, 115, 30);
		painelInfo01.add(labelPedestre01);
		
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
		
		JPanel painelInfo02 = new JPanel();
		painelInfo02.setLayout(null);
		painelInfo02.setOpaque(false);
		painelInfo02.setBounds(1045, 414, 190, 183);
		painelMaster.add(painelInfo02);
		
		labelVeiculo02 = new JLabel(Icone.Veiculo.OFF);
		labelVeiculo02.setBounds(12, 95, 165, 30);
		painelInfo02.add(labelVeiculo02);
		
		labelPedestre02 = new JLabel(Icone.Pedestre.OFF);
		labelPedestre02.setBounds(40, 141, 115, 30);
		painelInfo02.add(labelPedestre02);
		
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
		painelInfo03.setBounds(97, 33, 190, 183);
		painelMaster.add(painelInfo03);
		
		labelVeiculo03 = new JLabel(Icone.Veiculo.OFF);
		labelVeiculo03.setBounds(12, 95, 165, 30);
		painelInfo03.add(labelVeiculo03);
		
		labelPedestre03 = new JLabel(Icone.Pedestre.OFF);
		labelPedestre03.setBounds(40, 137, 115, 30);
		painelInfo03.add(labelPedestre03);
		
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
		painelInfo04.setBounds(855, 414, 190, 183);
		painelMaster.add(painelInfo04);
		
		labelVeiculo04 = new JLabel(Icone.Veiculo.OFF);
		labelVeiculo04.setBounds(12, 95, 165, 30);
		painelInfo04.add(labelVeiculo04);
		
		labelPedestre04 = new JLabel(Icone.Pedestre.OFF);
		labelPedestre04.setBounds(40, 141, 115, 30);
		painelInfo04.add(labelPedestre04);
		
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
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(975, 12, 293, 106);
		scroll.setOpaque(false);
		
		painelMaster.add(scroll);
		
		labelCiclos = new JLabel("Ciclos:");
		labelCiclos.setForeground(Color.WHITE);
		labelCiclos.setFont(fonte);
		labelCiclos.setBounds(1104, 130, 62, 15);
		painelMaster.add(labelCiclos);
		
		textCiclos = new JLabel("0");
		textCiclos.setFont(fonte);
		textCiclos.setForeground(Color.WHITE);
		textCiclos.setBounds(1165, 130, 70, 15);
		painelMaster.add(textCiclos);
		
		buildScreen();
		
		simulador = new Simulador(this);
		inicializaGerenciadores();
	}
	
	private void onCreateOptionsMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
		
		menuEditor = new JMenuItem("Editor de Script");
		menuEditor.addActionListener(this);
		menuArquivo.add(menuEditor);
		
		menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(this);
		menuArquivo.add(menuSair);
		
		JMenu menuSimulacao = new JMenu("Simulação");
		menuBar.add(menuSimulacao);
		
		menuLoadScript = new JMenuItem("Carregar Script");
		menuLoadScript.addActionListener(this);
		menuSimulacao.add(menuLoadScript);
		
		menuInicia = new JMenuItem("Iniciar Simulação");
		menuInicia.addActionListener(this);
		menuSimulacao.add(menuInicia);
		
		menuSincroniza = new JMenuItem("Sincronizar Semáforos");
		menuSincroniza.addActionListener(this);
		menuSimulacao.add(menuSincroniza);
		
		separator = new JSeparator();
		menuSimulacao.add(separator);
		
		menuFastStart = new JMenuItem("Início Rápido");
		menuFastStart.addActionListener(this);
		menuSimulacao.add(menuFastStart);
		
		JMenu menuAjuda = new JMenu("Ajuda");
		menuBar.add(menuAjuda);
		
		menuSobre = new JMenu("Sobre");
		menuSobre.addActionListener(this);
		menuAjuda.add(menuSobre);
	}
	
	private void loadScript(Simulador simulador) {
		new Interpreter(simulador).start();
	}
	
	private void buildScreen() {
		setSize(1280,680);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	    setVisible(true);
	}
	
	private void inicializaGerenciadores() {
		this.vehicl01 = new VehicleLightsManager(labelVeiculo01);
		this.vehicl02 = new VehicleLightsManager(labelVeiculo02);
		this.vehicl03 = new VehicleLightsManager(labelVeiculo03);
		this.vehicl04 = new VehicleLightsManager(labelVeiculo04);
		
		this.walker01 = new WalkerLightsManager(labelPedestre01);
		this.walker02 = new WalkerLightsManager(labelPedestre02);
		this.walker03 = new WalkerLightsManager(labelPedestre03);
		this.walker04 = new WalkerLightsManager(labelPedestre04);
	}
	
	public void fireFluxoUpdate(Local chave, int valor) {
		
		final String texto = String.valueOf(valor);
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				fireTextFieldUpdate(textFluxo02, texto);
			break;
			
			case ENTRADA_SAIDA_CAMPUS:
				fireTextFieldUpdate(textFluxo01, texto);
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
	
	private void fireSemaforoVeiculoUpdate(Local chave, Estado atual) {
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				vehicl01.changeState(atual);
				break;
		
			case ENTRADA_SAIDA_CAMPUS:
				vehicl02.changeState(atual);
				break;
		
			case COROADO_ENTRADA_CAMPUS:
				vehicl03.changeState(atual);
				break;
		
			case SAIDA_CAMPUS_COROADO:
				vehicl04.changeState(atual);
				break;
		
			default:
				break;
		}
	}
	
	private void fireSemaforoPedestreUpdate(Local chave, Estado atual) {
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				walker01.changeState(atual);
				break;
		
			case ENTRADA_SAIDA_CAMPUS:
				walker02.changeState(atual);
				break;
		
			case COROADO_ENTRADA_CAMPUS:
				walker03.changeState(atual);
				break;
		
			case SAIDA_CAMPUS_COROADO:
				walker04.changeState(atual);
				break;
		
			default:
				break;
		}
	}
	
	public void fireOcupacaoUpdate(Local chave, int valor) {
		
		final String texto = String.valueOf(valor);
		
		switch (chave) {
			case JAPIIM_ENTRADA_CAMPUS:
				fireTextFieldUpdate(textOcupacao02, texto);
				break;
		
			case ENTRADA_SAIDA_CAMPUS:
				fireTextFieldUpdate(textOcupacao01, texto);
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
		
		if (semaforo instanceof SemaforoPedestre)
			fireSemaforoPedestreUpdate(local, atual);
		else
			fireSemaforoVeiculoUpdate (local, atual);
	}

	private void sincronizaSemaforos() {
		simulador.sincroniza();
	}
	
	private void iniciaSimulacao() {
		simulador.start();
	}
	
	private void carregaScript() {
		loadScript(simulador);
	}
	
	private void fastStart() {
		carregaScript();
		sincronizaSemaforos();
		iniciaSimulacao();
	}
	
	/** Tratamento de eventos de menu */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == menuSincroniza)
			sincronizaSemaforos();
		
		else if (source == menuInicia)
			iniciaSimulacao();
		
		else if (source == menuEditor)
			new EditorScript(new File(PropertiesManager.getResource("config/script.ssf")));
		
		else if (source == menuLoadScript)
			carregaScript();
		
		else if (source == menuFastStart)
			fastStart();
		
		else if (source == menuSair)
			dispose();
	}

	@Override
	public void evento(ClockEvent event) {
		long ciclos = ((Clock) event.getSource()).getCiclos();
		UpdateCiclo job = new UpdateCiclo(ciclos);
		SwingUtilities.invokeLater(job);
	}
	
	private class UpdateCiclo implements Runnable {

		private String ciclos;
		
		public UpdateCiclo(long ciclos) {
			this.ciclos = Long.toString(ciclos);
		}
		
		@Override
		public void run() {
			textCiclos.setText(ciclos);
		}
		
	}
}
