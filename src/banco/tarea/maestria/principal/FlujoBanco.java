/**
 * 
 */
package banco.tarea.maestria.principal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

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
	//La hora en la que abre el Banco
	private static LocalTime horaAbrir = LocalTime.now();
	private static double acumLongitudColas;
	private static double acumTiempoBanco;
	private static double cantClientes;
	/**
	 * Crea la cola de eventos de llegada desde el archivo
	 * @param archivo - Nombre del archivo que contiene las llegadas de los clientes
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
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
	  * Crea una lista de cola de cajeros
	  * @param cantidadCajeros - Cantidad de cajeros a crear
	  * @return List<ArrayDeque<Cliente>>
	  */
	 private static List<ArrayDeque<Cliente>> crearColaCajeros(int cantidadCajeros){
		 List<ArrayDeque<Cliente>> listaCajeros = new ArrayList<ArrayDeque<Cliente>>();
		 ArrayDeque<Cliente> colaCajero = null;
		 for(int i = 0;i<cantidadCajeros;i++) {
			 colaCajero = new ArrayDeque<Cliente>();
			 listaCajeros.add(colaCajero);
		 }
		 return listaCajeros;
	 }
	 /**
	  * Procesa la llegada de un cliente
	  * @param horaLlegada
	  * @param duracion
	  */
	 private static void procesarLLegada(LocalTime horaLlegada, int duracion) {
		 ArrayDeque<Cliente> cola = getColaMasCorta();
		 Cliente cliente = new Cliente();
		 cliente.setCola(cola);
		 cliente.setDuracion(duracion);
		 cliente.setHora(horaLlegada);
		 if(cola.isEmpty()) {
			 cola.add(cliente);
			 Evento evento = new Evento();
			 evento.setCola(cola);
			 evento.setTipoEvento(SALIDA);
			 evento.setHora(horaLlegada.plusMinutes(duracion));
			 colaEventos.add(evento);
		 } else {
			 cola.add(cliente);
		 }
	 }
	 /**
	  * Procesa la salida de un cliente
	  * @param horaSalida
	  * @param cola
	  */
	 private static void procesarSalida(LocalTime horaSalida, ArrayDeque<Cliente> cola) {
		 acumLongitudColas = acumLongitudColas + cola.size();
		 Cliente cliente = cola.poll();
		 Duration duracion = Duration.between(cliente.getHora(), horaSalida);
		 long tiempoBanco = duracion.getSeconds();
		 cantClientes++;
		 acumTiempoBanco = acumTiempoBanco + tiempoBanco; 
		 if(!cola.isEmpty()) {
			 Cliente pCliente = cola.peek();
			 Evento evento = new Evento();
			 evento.setCola(cola);
			 evento.setTipoEvento(SALIDA);
			 evento.setHora(horaSalida.plusMinutes(pCliente.getDuracion()));
			 colaEventos.add(evento);
		 }
	 }
	 /**
	  * Devuelve la cola mas corta
	  * @return ArrayDeque<Cliente>
	  */
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
	 * Simulacion de atencion de clientes de un banco
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("El banco abrió a las "+horaAbrir);
		DecimalFormat df = new DecimalFormat("##.##");
		cantClientes = 0.0;
		acumTiempoBanco = 0.0;
		acumLongitudColas = 0.0;
		System.out.println("Por favor ingrese el nombre del archivo(con su extensión) que contiene la llegada de los clientes:");
		Scanner scanner = new Scanner(System.in);
		int reintento = 0;
		String nombreArchivo = scanner.nextLine();
		URL url = FlujoBanco.class.getResource("/"+nombreArchivo);
		while(url ==  null && reintento < 3) {
			System.out.println("El archivo no existe, por  favor ingrese el nombre correcto.");
			nombreArchivo = scanner.nextLine();
			url = FlujoBanco.class.getResource("/" + nombreArchivo);
			reintento++;
		}
		if(url == null) {
			System.out.println("El archivo no se encuentra. Programa terminado.");
			return;
		}
		crearColaEventos(url.getFile()); 
		
		int cantidadCajeros = 0;
		reintento = 0;
		while(reintento < 3) {
			try {
				System.out.println("Por favor ingrese la cantidad de cajeros que desea crear:");
				cantidadCajeros = Integer.parseInt(scanner.nextLine());
				if(cantidadCajeros <= 0) {
					System.out.println("Cantidad de cajeros invalida. Programa terminado.");
					return;
				}
				break;
			}catch(Exception ex) {
				System.out.println("Cantidad de cajeros invalida. Por favor ingrese una cantidad valida.");
				reintento++;
			}
		}
		if(reintento == 3) {
			System.out.println("Cantidad de cajeros invalida. Programa terminado.");
			return;
		}
		listaCajeros = crearColaCajeros(cantidadCajeros);
		Evento evento = null;
		while(colaEventos.size() != 0) {
			evento = colaEventos.poll();
			if(evento.getTipoEvento().equals(LLEGADA)) {
				System.out.println(evento);
				procesarLLegada(evento.getHora(), evento.getDuracion());
			} else if(evento.getTipoEvento().equals(SALIDA)) {
				System.out.println(evento);
				procesarSalida(evento.getHora(), evento.getCola());
			}	
		}
		double tiempoPromBancoCliente = acumTiempoBanco / cantClientes;
		double tiempoMinutos = tiempoPromBancoCliente/60.0;
		double longPromColas = Math.round(acumLongitudColas / cantClientes);
		System.out.println("El tiempo promedio que permanecio un cliente en el banco es: "+df.format(tiempoMinutos)+" Minutos");
		System.out.println("La longitud promedio de las colas es: "+df.format(longPromColas));
	}
}
