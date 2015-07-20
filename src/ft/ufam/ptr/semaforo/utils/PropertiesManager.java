package ft.ufam.ptr.semaforo.utils;

import java.util.*;
import java.io.*;
import java.nio.file.*;

/** Faz a ponte entre o sistema Java e arquivos de propriedades externos.
 *  @author Felipe André
 *  @version 2.5, 07/01/2015 */
public class PropertiesManager {
	
	/** Atualiza uma propriedade */
	public static boolean setProperty(String key, String value) {
		try {
			Properties properties = getProperties();
			properties.setProperty(key, value);
			FileOutputStream os = new FileOutputStream(getPropertiesFile());
			properties.store(os, null);
			return true;
		}
		catch (IOException exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	/** Retorna uma propriedade */
	public static String getProperty(String key) {
		try {
			Properties properties = getProperties();
			String property = properties.getProperty(key);
			return property;
		}
		catch (IOException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	/** Instancia a classe de propriedades do sistema */
	private static Properties getProperties() throws IOException {
		Properties props = new Properties();
		File arquivo = getPropertiesFile();
		FileInputStream stream = new FileInputStream(arquivo);
		props.load(stream);
		return props;
	}

	/** Busca o arquivo de propriedades do sistema */
	private static File getPropertiesFile() {
		String arquivo = getResource("configs/program.properties");
		return new File(arquivo);
	}
	
	/** Retorna o diretório de trabalho atual */
	public static String getCurrentPath() {
		Path currentRelativePath = Paths.get("");
		return currentRelativePath.toAbsolutePath().toString();
	}
	
	/** Retorna o diretório de trabalho atual */
	public static String getResource(String resource) {
		String baseDirectory = getCurrentPath();
		return (baseDirectory + "/res/" + resource);
	}

}
