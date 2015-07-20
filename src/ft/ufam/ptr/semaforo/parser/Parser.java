package ft.ufam.ptr.semaforo.parser;

import java.io.*;
import ft.ufam.ptr.semaforo.utils.*;

public class Parser {

	public static void main(String[] args) throws Throwable {
		new Parser();
	}

	public Parser() throws IOException {
		String aux;
		File arquivo = new File(PropertiesManager.getResource("config/simulation.ssf"));
		BufferedReader stream = new BufferedReader(new FileReader(arquivo));
		
		while ((aux = stream.readLine()) != null) {
			String[] split = aux.split(" ");
			analyse(split);
		}
		
		stream.close();
	}
	
	private void analyse(String[] args) {
		switch (args[0]) {
			case "gera":
				System.out.println("gerador de " + args[3] + " para " + args[4] + " instanciado!");
				break;
			default:
				System.out.println("Comando " + args[0] + " inválido!");
				break;
		}
	}
	
}
