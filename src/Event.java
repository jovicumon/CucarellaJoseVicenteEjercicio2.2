import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase que representa un evento con un título, fecha, prioridad y una lista de tareas asociadas.
 * Aunque se indica que los atributos podrían ser {@code final}, no se hará esta modificación
 * para permitir que los campos puedan ser modificados en caso necesario.
 */

public class Event {
    private String title;
    private LocalDate date;
    private Priority priority;
    private ArrayList<EventTask> tasks;

    /**
     * Enumeración que define la prioridad del evento.
     * La prioridad puede ser una de las siguientes:
     * {@code HIGH}, {@code MEDIUM} o {@code LOW}.
     */

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    /**
     * Constructor de la clase {@code Event}.
     * Inicializa un nuevo evento con un título, fecha y prioridad especificados.
     *
     * @param title    Título del evento. No puede ser {@code null}.
     * @param date     Fecha del evento. No puede ser {@code null}.
     * @param priority Prioridad del evento. No puede ser {@code null}.
     */

    public Event(String title, LocalDate date, Priority priority) {
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.tasks = new ArrayList<>();
    }

    /**
     * Agrega una tarea al evento.
     * @param task Tarea a agregar. No puede ser {@code null}.
     */

    public void addTask(EventTask task) {
        tasks.add(task);
    }

    /**
     * Devuelve una representación en texto del evento, incluyendo el título, la fecha,
     * la prioridad y el estado de las tareas.
     * La representación incluye el número de tareas completadas y el total de tareas.
     * @return Una cadena de texto con los detalles del evento y las tareas.
     */

    @Override
    public String toString() {
        long completedTasks = tasks.stream().filter(EventTask::isCompleted).count();
        return String.format("Evento: %s | Fecha: %s | Prioridad: %s | Tareas completadas: %d/%d",
                title, date, priority, completedTasks, tasks.size());
    }

    /**
     * Obtiene el título del evento.
     * @return Título del evento.
     */

    public String getTitle() {
        return title;
    }

    /**
     * Devuelve la lista de tareas.
     * @return Lista de tareas del evento.
     */

    public ArrayList<EventTask> getTasks() {
        return tasks;
    }
}
