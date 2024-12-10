import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Scanner;

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
     * El menú incluye opciones para añadir, borrar, listar eventos, y marcar tareas asociadas como completadas.
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
     */
    private static void addEvent() {
        System.out.print("Introduce el título del evento: ");
        String title = scanner.nextLine();

        // Validar fecha del evento
        Integer dia = null; // Variables auxiliares para validar individualmente cada dato
        Integer mes = null;
        Integer anio = null;

        // Validar año
        while (anio == null) {
            System.out.print("Introduce el año del evento: ");
            try {
                anio = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un valor numérico válido para el año.");
                continue; // Continuar pidiendo el año
            }

            // Obtener el año actual
            int currentYear = LocalDate.now().getYear();

            // Verificar que el año esté dentro del rango válido (entre el año actual y +5 años)
            if (anio < currentYear || anio > currentYear + 5) {
                System.out.println("El año debe estar entre " + currentYear + " y " + (currentYear + 5) + " (inclusive).");
                anio = null; // Reiniciar si no es correcto
            }
        }

        // Validar mes
        boolean mesValido = false;
        while (!mesValido) {
            System.out.print("Introduce el mes del evento (1-12): ");
            try {
                mes = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un valor numérico válido para el mes.");
                continue; // Continuar pidiendo el mes
            }

            if (mes >= 1 && mes <= 12) {
                int currentMonth = LocalDate.now().getMonthValue();
                // Verificar si el mes es mayor o igual al mes actual
                if (anio.equals(LocalDate.now().getYear()) && mes < currentMonth) {
                    System.out.println("El mes introducido ya ha pasado. Introduce un mes válido.");
                } else {
                    mesValido = true; // El mes es válido
                }
            } else {
                System.out.println("El mes debe estar entre 1 y 12.");
            }
        }

        // Validar día según el mes y año
        boolean diaValido = false;
        while (!diaValido) {
            System.out.print("Introduce el día del evento (1-31): ");
            try {
                dia = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un valor numérico válido para el día.");
                continue; // Continuar pidiendo el día
            }

            // Verifica si el día es válido para el mes y el año
            if (dia < 1 || dia > 31) {
                System.out.println("El día debe estar entre 1 y 31.");
                continue;
            }

            // Comprobamos si la fecha es válida (mes y día dentro del año)
            if (!esFechaValida(dia, mes, anio)) {
                System.out.println("El día introducido no es válido para el mes y el año.");
                continue;
            }

            // Verificar si la fecha ya ha pasado en relación a la fecha actual
            LocalDate fechaIntroducida = LocalDate.of(anio, mes, dia);
            LocalDate fechaHoy = LocalDate.now();

            // Si la fecha es anterior a la actual, reiniciar el mes o día
            if (fechaIntroducida.isBefore(fechaHoy)) {
                if (mes < fechaHoy.getMonthValue() || (mes == fechaHoy.getMonthValue() && dia < fechaHoy.getDayOfMonth())) {
                    System.out.println("La fecha introducida ya ha pasado. Introduce una fecha futura.");
                    continue; // Continuamos con la validación del día o mes
                }
            }

            diaValido = true; // Si la fecha es válida, salimos del bucle
        }

        // Crear el evento con los datos válidos (solo crear el objeto una vez con fecha validada)
        LocalDate date = LocalDate.of(anio, mes, dia);

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
        String input = scanner.nextLine().trim().toLowerCase();

        // Validar la entrada inicial
        while (!input.equals("s") && !input.equals("n")) {
            System.out.println("Entrada no válida. Por favor, introduce 's' para sí o 'n' para no.");
            System.out.print("¿Quieres añadir tareas? (s/n): ");
            input = scanner.nextLine().trim().toLowerCase();
        }

        boolean addingTasks = input.equals("s");
        while (addingTasks) {
            System.out.print("Introduce la descripción de la tarea: ");
            String taskDescription = scanner.nextLine().trim();

            if (taskDescription.isEmpty()) {
                System.out.println("La descripción de la tarea no puede estar vacía. Por favor, introduce una descripción válida.");
            } else {
                event.addTask(new CucarellaJoseVicenteEjercicio2EventTask(taskDescription));
                System.out.println("Tarea añadida correctamente.");
            }

            System.out.print("¿Agregar otra tarea? (s/n): ");
            input = scanner.nextLine().trim().toLowerCase();

            while (!input.equals("s") && !input.equals("n")) {
                System.out.println("Entrada no válida. Por favor, introduce 's' para sí o 'n' para no.");
                System.out.print("¿Agregar otra tarea? (s/n): ");
                input = scanner.nextLine().trim().toLowerCase();
            }

            addingTasks = input.equals("s");
        }

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
     * y luego permite marcar o desmarcar las tareas asociadas.
     */
    private static void completeTask() {
        CucarellaJoseVicenteEjercicio2Event event = null;

        // Bucle para obtener un evento válido
        while (event == null) {
            System.out.print("Introduce el título del evento: ");
            String title = scanner.nextLine();

            event = events.stream()
                    .filter(e -> e.getTitle().equalsIgnoreCase(title))
                    .findFirst()
                    .orElse(null);

            if (event == null) {
                System.out.println("Evento no encontrado. Inténtalo de nuevo.");
            }
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

        boolean validInput = false; // Variable de control para el bucle de selección de tarea
        do {
            System.out.print("Selecciona el número de la tarea a marcar/desmarcar: ");
            try {
                int taskIndex = Integer.parseInt(scanner.nextLine()) - 1;

                if (taskIndex >= 0 && taskIndex < tasks.size()) {
                    CucarellaJoseVicenteEjercicio2EventTask task = tasks.get(taskIndex);
                    task.setCompleted(!task.isCompleted());
                    System.out.println("Tarea actualizada correctamente.");
                    validInput = true; // Salir del bucle
                } else {
                    System.out.println("Número de tarea inválido. Inténtalo de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Introduce un número correspondiente a una tarea.");
            }
        } while (!validInput); // Repite hasta que la entrada sea válida
    }



    /**
     * Método para verificar si una fecha es válida según el mes y el año proporcionados.
     * @param dia El día a verificar.
     * @param mes El mes a verificar.
     * @param anio El año a verificar.
     * @return true si la fecha es válida, false si no lo es.
     */
    private static boolean esFechaValida(int dia, int mes, int anio) {
        try {
            LocalDate.of(anio, mes, dia); // Si es una fecha válida, no lanzará una excepción
            return true;
        } catch (DateTimeException e) {
            return false; // Fecha inválida
        }
    }
}
