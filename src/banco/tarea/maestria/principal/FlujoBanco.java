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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import banco.tarea.maestria.modelo.Cliente;
import banco.tarea.maestria.modelo.Evento;

/**
 * @author lcampo
 *
 */
public class FlujoBanco {
	private static PriorityQueue<Evento> colaEventos;
	private static final String LLEGADA = "LLEGADA";
	private static final String SALIDA = "SALIDA";
	private static List<ArrayDeque<Cliente>> listaCajeros;
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
	 private static List<ArrayDeque<Cliente>> crearColaCajeros(int cantidadCajeros){
		 List<ArrayDeque<Cliente>> listaCajeros = new ArrayList<ArrayDeque<Cliente>>();
		 ArrayDeque<Cliente> colaCajero = null;
		 for(int i = 0;i<cantidadCajeros;i++) {
			 colaCajero = new ArrayDeque<Cliente>();
			 listaCajeros.add(colaCajero);
		 }
		 return listaCajeros;
	 }
	 
	 private static void procesarLLegada(LocalTime horaLlegada, int duracion) {
		 ArrayDeque<Cliente> cola = getColaMasCorta();
		 Cliente cliente = new Cliente();
		 cliente.setCola(cola);
		 cliente.setDuracion(duracion);
		 cliente.setHora(horaLlegada);
		 if(cola.isEmpty()) {
			 Evento evento = new Evento();
			 evento.setCola(cola);
			 evento.setTipoEvento(SALIDA);
			 evento.setHora(horaLlegada.plusMinutes(duracion));
			 colaEventos.add(evento);
			 cola.add(cliente);
		 } else {
			 cola.add(cliente);
		 }
	 }
	 
	 private static void procesarSalida(LocalTime horaLlegada, ArrayDeque<Cliente> cola) {
		 
	 }
	 private static ArrayDeque<Cliente> getColaMasCorta(){
		 int tamColaMasCorta = listaCajeros.get(0).size();
		 int indiceColaMasCorta = 0;
		 for(int i = 1;i<listaCajeros.size();i++) {
			 if(tamColaMasCorta > listaCajeros.get(i).size()) {
				 tamColaMasCorta = listaCajeros.get(i).size();
				 indiceColaMasCorta = i;
			 }
		 }
		 return listaCajeros.get(indiceColaMasCorta);
	 }
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		URL url = FlujoBanco.class.getResource("/llegada.txt");
		crearColaEventos(url.getFile()); 
		listaCajeros = crearColaCajeros(4);
		Evento evento = null;
		while(colaEventos.size() != 0) {
			evento = colaEventos.poll();
			if(evento.getTipoEvento().equals(LLEGADA)) {
				procesarLLegada(evento.getHora(), evento.getDuracion());
			} else if(evento.getTipoEvento().equals(SALIDA)) {
				
			}
			
		}
//		for(Evento evento: colaEventos) {
//			
//		}
	}

}
