/**
 * 
 */
package banco.tarea.maestria.modelo;

import java.time.LocalTime;
import java.util.ArrayDeque;

/**
 * @author lcampo
 *
 */
public class Evento  implements Comparable<Evento> {
	private String tipoEvento;
	private LocalTime hora;
	private int duracion;
	private ArrayDeque<Cliente> cola;
	public String getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public ArrayDeque<Cliente> getCola() {
		return cola;
	}
	public void setCola(ArrayDeque<Cliente> cola) {
		this.cola = cola;
	}
	
	@Override
	public String toString() {
		return "Evento [tipoEvento=" + tipoEvento + ", hora=" + hora + ", duracion=" + duracion + "]";
	}
	@Override
	public int compareTo(Evento o) {
		return this.hora.compareTo(o.getHora());
	}	
}
