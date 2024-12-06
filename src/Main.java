import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal que gestiona el menú de opciones.
 */

public class Main {
    private static final ArrayList<Event> events = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addEvent();
                    break;
                case "2":
                    deleteEvent();
                    break;
                case "3":
                    listEvents();
                    break;
                case "4":
                    toggleTaskCompletion();
                    break;
                case "5":
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida. Seleccione una de las opciones anteriores.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nBienvenido a Event Planner. Seleccione una opción:");
        System.out.println("[1] Añadir evento");
        System.out.println("[2] Borrar evento");
        System.out.println("[3] Listar eventos");
        System.out.println("[4] Marcar/desmarcar tarea de un evento como completada");
        System.out.println("[5] Salir");
        System.out.print("Opción: ");
    }

    private static void addEvent() {
        System.out.print("Introduce el título del evento: ");
        String title = scanner.nextLine();

        LocalDate date = null;
        while (date == null) {
            try {
                System.out.print("Introduce el día del evento: ");
                int dia = Integer.parseInt(scanner.nextLine());

                System.out.print("Introduce el mes del evento: ");
                int mes = Integer.parseInt(scanner.nextLine());

                System.out.print("Introduce el año del evento: ");
                int anio = Integer.parseInt(scanner.nextLine());

                if (ControlFecha.esFechaDentroRango(dia, mes, anio, 5)) {
                    date = LocalDate.of(anio, mes, dia);
                } else {
                    System.out.println("La fecha no es válida o está fuera del rango permitido (5 años a partir de hoy).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Asegúrate de introducir números para el día, mes y año.");
            }
        }

        System.out.print("Introduce la prioridad (HIGH, MEDIUM, LOW): ");
        Event.Priority priority = null;
        while (priority == null) {
            try {
                priority = Event.Priority.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridad inválida. Introduce HIGH, MEDIUM o LOW.");
            }
        }

        Event event = new Event(title, date, priority);

        System.out.print("¿Quieres añadir tareas? (s/n): ");
        boolean addingTasks = scanner.nextLine().equalsIgnoreCase("s");

        while (addingTasks) {
            System.out.print("Introduce la descripción de la tarea: ");
            event.addTask(new EventTask(scanner.nextLine()));

            System.out.print("¿Agregar otra tarea? (s/n): ");
            addingTasks = scanner.nextLine().equalsIgnoreCase("s");
        }

        events.add(event);
        System.out.println("Evento añadido correctamente.");
    }

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

    private static void listEvents() {
        if (events.isEmpty()) {
            System.out.println("No hay eventos registrados.");
            return;
        }
        events.forEach(System.out::println);
    }

    private static void toggleTaskCompletion() {
        System.out.print("Introduce el título del evento: ");
        String title = scanner.nextLine();

        Event event = events.stream()
                .filter(e -> e.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (event == null) {
            System.out.println("No se encontró ningún evento con ese título.");
            return;
        }

        ArrayList<EventTask> tasks = event.getTasks();
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
            EventTask task = tasks.get(taskIndex);
            task.setCompleted(!task.isCompleted());
            System.out.println("Tarea actualizada correctamente.");
        } else {
            System.out.println("Número de tarea inválido.");
        }
    }
}