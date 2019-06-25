/**
 * 
 */
package banco.tarea.maestria.principal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.PriorityQueue;

import banco.tarea.maestria.modelo.Evento;

/**
 * @author lcampo
 *
 */
public class FlujoBanco {
	private PriorityQueue<Evento> colaEventos;
	 public static void crearColaEventos(String archivo) throws FileNotFoundException, IOException {
	        String cadena;
	        FileReader f = new FileReader(archivo);
	        BufferedReader b = new BufferedReader(f);
	        while((cadena = b.readLine())!=null) {
	            System.out.println(cadena);
	        }
	        b.close();
	    }
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		LocalTime horaAbrir = LocalTime.now();
		URL url = FlujoBanco.class.getResource("/llegada.txt");
		crearColaEventos(url.getFile()); 
	}

}
