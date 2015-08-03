package ft.ufam.ptr.semaforo.interpreter;

import java.io.*;
import ft.ufam.ptr.semaforo.model.*;

/** Implementação do interpretador de configurações do semáforo.
 *  Com este é possível configurar a base de tempo do sistema e
 *  a instanciação dos geradores de fluxo das vias.
 *  @author Felipe André
 *  @version 2.0, 03/08/2015 */
public class Interpreter extends Thread {
	
	/* Atributos da classe */
	private final Simulador simulador;
	private final BufferedReader stream;
	private final File arquivo;
	
	/** Inicializa os atributos */
	public Interpreter(Simulador simulador, File arquivo) {
		this.simulador = simulador;
		this.arquivo = arquivo;
		this.stream = getArquivoComandos();
	}
	
	/** Cria uma stream de leitura de dados a partir de um arquivo */
	private BufferedReader getArquivoComandos() {
		BufferedReader str = null;
		
		try {
			str = new BufferedReader(new FileReader(arquivo));
		}
		catch (Exception exception) {
			System.err.println("Falha ao abrir o arquivo!");
			exception.printStackTrace();
		}
		
		return str;
	}
	
	/** Faz a leitura e interpretação dos comandos do sistema */
	@Override
	public void run() {
		String line = null;
		
		try {
			while ( (line = stream.readLine()) != null ) {
				String[] splitted = line.trim().split(" ");
				String comando = splitted[0];
				parseCommand(comando, splitted, line);
			}
			stream.close();
		}
		
		catch (IOException exception) {
			System.out.println("Falha ao abrir o arquivo de configurações!");
		}
		catch (NumberFormatException exception) {
			System.out.println("Quantidade de Veículos inválida!");
		}
		catch (IllegalArgumentException exception) {
			System.out.println("Via inválida\n" + line);
		}
		catch (Exception exception) {
			System.out.println("Comando não reconhecido:\n" + line);
		}
	}
	
	/** Faz a seleção dos comandos */
	private void parseCommand(String comando, String[] args, String line) throws Exception {
		
		if (comando.startsWith("#"))
			return;
		
		switch (comando) {
		
			case "":
				break;
		
			case "BASE_TEMPO":
				setBaseTempo(args);
			break;
			
			case "GERA":
				criaGerador(args);
			break;
			
			default:
				System.out.println("Comando não reconhecido: " + line);
			break;
		}
	}
	
	/** Atualiza a base de tempo do simulador */
	private void setBaseTempo(String[] args) throws Exception {
		int ciclos = Integer.parseInt(args[1]);
		simulador.setBaseTempo(ciclos);
	}
	
	/** Adiciona um gerador de veículos no simulador */
	private void criaGerador(String[] args) throws Exception {
		
		int quantidade = Integer.parseInt(args[1]);
		Taxa  taxa     = Taxa .valueOf(args[2]);
		Local origem   = Local.valueOf(args[3]);
		Local destino  = Local.valueOf(args[4]);
		
		simulador.criaGerador(quantidade, taxa, origem, destino);
		
	}
	
}
