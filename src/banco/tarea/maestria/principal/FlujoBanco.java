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
	private static PriorityQueue<Evento> colaEventos;
	private static final String LLEGADA = "LLEGADA";
	private static final String SALIDA = "SALIDA";
	
	private static LocalTime horaAbrir = LocalTime.now();
	 public static void crearColaEventos(String archivo) throws FileNotFoundException, IOException {
	        String cadena;
	        FileReader f = new FileReader(archivo);
	        BufferedReader b = new BufferedReader(f);
	        Evento eventoLLegada = null;
	        colaEventos = new PriorityQueue<Evento>();
	        while((cadena = b.readLine())!=null) {
	            String[] c = cadena.split(" ");
	            int minLlegada = Integer.parseInt(c[0]);
	            int duracionOper = Integer.parseInt(c[1]);
	            eventoLLegada = new Evento();
	            eventoLLegada.setTipoEvento(LLEGADA);
	            eventoLLegada.setHora(horaAbrir.plusMinutes(minLlegada));
	            eventoLLegada.setDuracion(duracionOper);
	            colaEventos.add(eventoLLegada);
	            System.out.println("Hora llegada cliente: "+eventoLLegada.getHora());
	        }
	        b.close();
	    }
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		URL url = FlujoBanco.class.getResource("/llegada.txt");
		crearColaEventos(url.getFile()); 
	}

}
