package ft.ufam.ptr.semaforo.graphics;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import ft.ufam.ptr.semaforo.utils.*;

/** Implementação de um editor de texto em modo gráfico.
 *  Útil para a edição dos arquivos de configuração.
 *  @author Felipe André
 *  @version 1.5, 01/08/2015 */
public class EditorScript extends JFrame implements ActionListener {

	/* Atributos da classe */
	private static final long serialVersionUID = 1L;
	private JMenuItem menuSalvar, menuSair;
	private JTextArea textArea;
	private File arquivo;

	/** Constrói a janela gráfica */
	public EditorScript(File arquivo) {
		super("Editor de Script de Comando");
		
		Container container = getContentPane();
		this.arquivo = arquivo;
		
		textArea = new JTextArea();
		textArea.setFont(GraphicsHelper.getFont());
		textArea.setForeground(GraphicsHelper.getColor());
		
		JScrollPane scroll = new JScrollPane(textArea);
		
		container.add(scroll);

		onCreateOptionsMenu();
		carregaArquivo();
		
		setSize(640,480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/** Cria a barra de menu e seus subitens */
	private void onCreateOptionsMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		menuSalvar = new JMenuItem("Salvar");
		menuSalvar.addActionListener(this);
		mnArquivo.add(menuSalvar);
		
		menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(this);
		mnArquivo.add(menuSair);
	}
	
	/** Carrega um arquivo */
	private void carregaArquivo() {
		try {
			String line;
			BufferedReader stream = new BufferedReader(new FileReader(arquivo));
			
			while ((line = stream.readLine()) != null)
				textArea.append(line + "\n");
			
			stream.close();
		}
		catch (Exception exception) {
			
		}
	}
	
	/** Salva o conteúdo da janela no arquivo */
	private void salvarArquivo() {
		try {
			PrintWriter stream = new PrintWriter(arquivo);
			stream.println(textArea.getText());
			stream.close();
			AlertDialog.informativo("Script salvo com sucesso!");
		}
		catch (Exception exception) {
			AlertDialog.erro("Falha ao salvar o arquivo!\n" + exception.getMessage());
		}
	}
	
	/** Tratamento de eventos do menu */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == menuSair)
			dispose();
		else if (source == menuSalvar)
			salvarArquivo();
	}

}
