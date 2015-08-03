package ft.ufam.ptr.semaforo.utils;

import java.nio.file.*;

/** Faz a ponte entre o sistema Java e arquivos de propriedades externos.
 *  @author Felipe André
 *  @version 2.5, 07/01/2015 */
public class PropertiesManager {
	
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
