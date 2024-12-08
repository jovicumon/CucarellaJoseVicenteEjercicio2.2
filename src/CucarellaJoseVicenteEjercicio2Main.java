import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.DateTimeException;

/**
 * Clase principal que gestiona el menú de opciones.
 * Permite al usuario gestionar eventos, incluyendo la creación, eliminación, listado y la manipulación de tareas asociadas a los eventos.
 */

public class CucarellaJoseVicenteEjercicio2Main {
    private static final ArrayList<CucarellaJoseVicenteEjercicio2Event> events = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Método principal que ejecuta el menú de opciones.
     * Controla el flujo de ejecución y las opciones que el usuario puede seleccionar.
     * El menú incluye opciones para añadir, borrar, listar eventos, y marcar tareas como completadas.
     */

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addEvent();
                    break;
                case "2":
                    listEvents();
                    deleteEvent();
                    break;
                case "3":
                    listEvents();
                    break;
                case "4":
                    listEvents();
                    completeTask();
                    break;
                case "5":
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida. Seleccione una de las opciones anteriores.");
            }
        }
    }

    /**
     * Muestra el menú de opciones al usuario.
     * Incluye opciones para añadir, borrar, listar eventos, marcar tareas y salir del programa.
     */

    private static void printMenu() {
        System.out.println("\nBienvenido a Event Planner. Seleccione una opción:");
        System.out.println("[1] Añadir evento");
        System.out.println("[2] Borrar evento");
        System.out.println("[3] Listar eventos");
        System.out.println("[4] Marcar/desmarcar tarea de un evento como completada");
        System.out.println("[5] Salir");
        System.out.print("Opción: ");
    }

    /**
     * Método para añadir un nuevo evento.
     * Solicita al usuario el título del evento, la fecha (día, mes y año), y la prioridad (HIGH, MEDIUM o LOW).
     * Valida de forma interactiva cada parte de la fecha hasta que se introduzca correctamente.
     * También permite al usuario añadir tareas opcionales asociadas al evento.
     * Detalles de validación:
     * - El día debe estar entre 1 y 31.
     * - El mes debe estar entre 1 y 12.
     * - El año debe ser una fecha válida dentro del rango permitido (5 años a partir de hoy).
     * - La prioridad debe ser HIGH, MEDIUM o LOW.
     */
    private static void addEvent() {
        System.out.print("Introduce el título del evento: ");
        String title = scanner.nextLine();

        // Validar fecha del evento
        LocalDate date = null;
        Integer dia = null; // Variables auxiliares para validar individualmente cada dato
        Integer mes = null;
        Integer anio = null;

        while (date == null) {
            try {
                if (anio == null) {
                    System.out.print("Introduce el año del evento: ");
                    anio = Integer.parseInt(scanner.nextLine());
                }

                if (mes == null) {
                    System.out.print("Introduce el mes del evento: ");
                    mes = Integer.parseInt(scanner.nextLine());
                    if (mes < 1 || mes > 12) {
                        System.out.println("El mes tiene que estar entre 1 y 12.");
                        mes = null; // Reiniciar si no es correcto
                        continue;
                    }
                }

                if (dia == null) {
                    System.out.print("Introduce el día del evento: ");
                    dia = Integer.parseInt(scanner.nextLine());
                    if (dia < 1 || dia > 31) {
                        System.out.println("El día tiene que estar entre 1 y 31.");
                        dia = null; // Reiniciar si no es correcto
                        continue;
                    }else if (!esFechaDentroRango(dia, mes, anio, 5)){
                        System.out.println("El día no es válido para el mes y año especificados.");
                        dia = null;
                        continue;
                    }
                }

                if (esFechaDentroRango(dia, mes, anio, 5)) {
                    date = LocalDate.of(anio, mes, dia);
                } else {
                    System.out.println("La fecha no es válida o está fuera del rango permitido (5 años a partir de hoy).");
                    anio = null; // Reiniciar si no es correcto
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Asegúrate de introducir valores númericos para el día, mes y año.");
            }
        }

        // Validar prioridad del evento
        System.out.print("Introduce la prioridad (HIGH, MEDIUM, LOW): ");
        CucarellaJoseVicenteEjercicio2Event.Priority priority = null;
        while (priority == null) {
            try {
                priority = CucarellaJoseVicenteEjercicio2Event.Priority.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridad incorrecta. Introduce HIGH, MEDIUM o LOW.");
            }
        }

        // Crear el evento con los datos válidos
        CucarellaJoseVicenteEjercicio2Event event = new CucarellaJoseVicenteEjercicio2Event(title, date, priority);

        // Añadir tareas opcionales
        System.out.print("¿Quieres añadir tareas? (s/n): ");
        boolean addingTasks = scanner.nextLine().equalsIgnoreCase("s");

        while (addingTasks) {
            System.out.print("Introduce la descripción de la tarea: ");
            event.addTask(new CucarellaJoseVicenteEjercicio2EventTask(scanner.nextLine()));

            System.out.print("¿Agregar otra tarea? (s/n): ");
            addingTasks = scanner.nextLine().equalsIgnoreCase("s");
        }

        // Añadir el evento a la lista global
        events.add(event);
        System.out.println("Evento añadido correctamente.");
    }

    /**
     * Método para borrar un evento por su título.
     * Si el evento existe, se elimina de la lista de eventos registrados.
     * Si no se encuentra el evento, se muestra un mensaje de error.
     */

    private static void deleteEvent() {
        System.out.print("Introduce el título del evento a borrar: ");
        String title = scanner.nextLine();

        boolean removed = events.removeIf(event -> event.getTitle().equalsIgnoreCase(title));
        if (removed) {
            System.out.println("Evento borrado correctamente.");
        } else {
            System.out.println("No se encontró ningún evento con ese título.");
        }
    }

    /**
     * Método para listar todos los eventos registrados.
     * Si no hay eventos, muestra un mensaje indicándolo.
     */

    private static void listEvents() {
        if (events.isEmpty()) {
            System.out.println("No hay eventos registrados.");
            return;
        }
        events.forEach(System.out::println);
    }

    /**
     * Método para marcar o desmarcar una tarea de un evento como completada.
     * Solicita al usuario el título del evento, busca el evento correspondiente,
     * y luego permite marcar o desmarcar tareas como completadas.
     */

    private static void completeTask() {
        System.out.print("Introduce el título del evento: ");
        String title = scanner.nextLine();

        CucarellaJoseVicenteEjercicio2Event event = events.stream()
                .filter(e -> e.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (event == null) {
            System.out.println("No se encontró ningún evento con ese título.");
            return;
        }

        ArrayList<CucarellaJoseVicenteEjercicio2EventTask> tasks = event.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("Este evento no tiene tareas.");
            return;
        }

        System.out.println("Lista de tareas:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, tasks.get(i));
        }

        System.out.print("Selecciona el número de la tarea a marcar/desmarcar: ");
        int taskIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            CucarellaJoseVicenteEjercicio2EventTask task = tasks.get(taskIndex);
            task.setCompleted(!task.isCompleted());
            System.out.println("Tarea actualizada correctamente.");
        } else {
            System.out.println("Número de tarea inválido.");
        }
    }

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