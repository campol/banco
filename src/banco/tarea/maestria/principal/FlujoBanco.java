/**
 * 
 */
package banco.tarea.maestria.principal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 * @author lcampo
 *
 */
public class FlujoBanco {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LocalDateTime ahora = LocalDateTime.now(); 
	       System.out.printf("La hora es: %s%n", ahora); 
	       LocalDateTime algunDia = LocalDateTime.of(1976, Month.MARCH, 27, 6, 10); 
	       System.out.printf("Yo nací el %s%n", algunDia); 
	       System.out.printf("Hace seis meses fue %s%n", LocalDateTime.now().minusMonths(6)); 
	       
	       LocalTime justoAhora = LocalTime.now(); 
	       System.out.printf("En este momento son las %d horas con %d minutos y %d segundos\n", justoAhora.getHour(),  
	       justoAhora.getMinute(), justoAhora.getSecond()); 
	}

}
