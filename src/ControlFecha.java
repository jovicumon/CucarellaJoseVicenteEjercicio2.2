import java.time.LocalDate;
import java.time.DateTimeException;

public class ControlFecha {

    /**
     * Comprueba si una fecha es válida y está dentro de un rango permitido.
     *
     * @param dia         el día del mes (1-31)
     * @param mes         el mes (1-12)
     * @param anio        el año
     * @param rangoFuturo el número máximo de años en el futuro permitido
     * @return true si la fecha es válida y está dentro del rango, false en caso contrario.
     */
    public static boolean esFechaDentroRango(int dia, int mes, int anio, int rangoFuturo) {
        try {
            LocalDate fecha = LocalDate.of(anio, mes, dia);
            LocalDate hoy = LocalDate.now();
            LocalDate maxFecha = hoy.plusYears(rangoFuturo);
            return !fecha.isBefore(hoy) && !fecha.isAfter(maxFecha);
        } catch (DateTimeException e) {
            return false;
        }
    }
}
