import java.time.LocalDate;
import java.time.DateTimeException;

public class ControlFecha {

    /**
     * Comprueba si una fecha es válida y está dentro de un rango permitido.
     * Esta función verifica que la fecha proporcionada no sea antes de la fecha actual
     * ni después de un número máximo de años en el futuro especificado.
     * @param dia         El día del mes (1-31). Debe ser un número válido para el mes especificado.
     * @param mes         El mes del año (1-12). Debe ser un número válido entre 1 y 12.
     * @param anio        El año de la fecha. Debe ser un año válido.
     * @param rangoFuturo El número máximo de años en el futuro permitidos. Este valor debe ser positivo.
     *                    Define el rango máximo de la fecha permitida a partir de la fecha actual.
     * @return true si la fecha es válida y está dentro del rango permitido, false en caso contrario.
     * Si la fecha es inválida (por ejemplo, día fuera del rango para el mes, fecha incorrecta),
     * o si la fecha está fuera del rango permitido (más allá del rango de años futuro especificado),
     * el método retornará {@code false}.
     */

    public static boolean esFechaDentroRango(int dia, int mes, int anio, int rangoFuturo) {
        try {
            // Intentar crear una fecha con los parámetros proporcionados
            LocalDate fecha = LocalDate.of(anio, mes, dia);
            LocalDate hoy = LocalDate.now();
            LocalDate maxFecha = hoy.plusYears(rangoFuturo);

            // Comprobar si la fecha está dentro del rango permitido
            return !fecha.isBefore(hoy) && !fecha.isAfter(maxFecha);
        } catch (DateTimeException e) {
            // Si la fecha no es válida (por ejemplo, el día no existe para el mes/año dado)
            return false;
        }
    }
}
