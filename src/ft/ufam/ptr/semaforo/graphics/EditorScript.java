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
	private JMenuItem menuNovo, menuAbrir, menuPrint, menuSalvar, menuSaveAs, menuSair;
	private JTextArea textArea;
	private File arquivo;
	private JMenu menuAjuda;
	private JMenuItem menuSobre;

	/** Constrói a janela gráfica */
	public EditorScript() {
		super("Editor de Script");
		
		Container container = getContentPane();
		
		textArea = new JTextArea();
		textArea.setFont(GraphicsHelper.getFont());
		textArea.setForeground(GraphicsHelper.getColor());
		
		JScrollPane scroll = new JScrollPane(textArea);
		
		container.add(scroll);

		onCreateOptionsMenu();
		
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
		
		menuAbrir = new JMenuItem("Abrir");
		menuAbrir.addActionListener(this);
		
		menuNovo = new JMenuItem("Novo");
		menuNovo.addActionListener(this);
		mnArquivo.add(menuNovo);
		mnArquivo.add(menuAbrir);
		
		menuPrint = new JMenuItem("Imprimir");
		menuPrint.addActionListener(this);
		mnArquivo.add(menuPrint);
		mnArquivo.add(menuSalvar);
		
		menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(this);
		
		menuSaveAs = new JMenuItem("Salvar Como");
		menuSaveAs.addActionListener(this);
		mnArquivo.add(menuSaveAs);
		
		JSeparator separator = new JSeparator();
		mnArquivo.add(separator);
		mnArquivo.add(menuSair);
		
		menuAjuda = new JMenu("Ajuda");
		menuBar.add(menuAjuda);
		
		menuSobre = new JMenuItem("Sobre");
		menuSobre.addActionListener(this);
		menuAjuda.add(menuSobre);
	}
	
	/** Carrega um arquivo */
	private void carregaArquivo() {
		String texto = "";
		try {
			String line;
			BufferedReader stream = new BufferedReader(new FileReader(arquivo));
			
			while ((line = stream.readLine()) != null)
				texto += (line + "\n");
			
			stream.close();
		}
		catch (Exception exception) {
			
		}
		finally {
			textArea.setText(texto.trim());
		}
	}
	
	/** Imprime o código em impressora */
	private void print() {
		 PrintSupport.printComponent(textArea);
	}
	
	/** Salva o conteúdo da janela no arquivo */
	private void salvarArquivo() {
		
		if (arquivo == null) {
			salvarComo();
			return;
		}
		
		try {
			PrintWriter stream = new PrintWriter(arquivo);
			stream.println(textArea.getText().trim());
			stream.close();
			AlertDialog.informativo("Script salvo com sucesso!");
		}
		catch (Exception exception) {
			AlertDialog.erro("Falha ao salvar o arquivo!\n" + exception.getMessage());
		}
	}
	
	/** Abre um diálogo de salvamento de arquivo */
	private void salvarComo() {
		File arquivo = FileChooserHelper.dialog(this, true);
		
		if (arquivo != null) {
			updateFile(arquivo);
			salvarArquivo();
		}
	}
	
	/** Abre um arquivo */
	private void abrirArquivo() {
		File arquivo = FileChooserHelper.dialog(this, false);
		
		if (arquivo != null) {
			updateFile(arquivo);
			carregaArquivo();
		}
	}
	
	/** Atualiza a referência interna do arquivo */
	private void updateFile(File arquivo) {
		this.arquivo = arquivo;
		setTitle("Editor de Script - " + arquivo.getName());
	}
	
	/** Imprime informações legais */
	private void about() {
		String texto = " -> Editor de Script <-\n\n"
					 + "Desenvolvido por: Felipe André";
		AlertDialog.informativo("Sobre", texto);
	}
	
	/** Tratamento de eventos do menu */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == menuSair)
			dispose();
		
		else if (source == menuNovo)
			new EditorScript();
		
		else if (source == menuSalvar)
			salvarArquivo();
		
		else if (source == menuSaveAs)
			salvarComo();
		
		else if (source == menuAbrir)
			abrirArquivo();
		
		else if (source == menuSobre)
			about();
		
		else if (source == menuPrint)
			print();
		
	}

}
