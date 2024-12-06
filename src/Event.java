
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase que representa un evento.
 * aunque nos indica los warnings como que puedan ser final
 * vamos a dejarlo así para que puedan modificar estos
 * campos en caso necesario
 */

public class Event {
    private String title;
    private LocalDate date;
    private Priority priority;
    private ArrayList<EventTask> tasks;

    /**
     * Enumeración que define la prioridad del evento.
     */
    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    /**
     * Constructor de la clase Event.
     *
     * @param title    Título del evento.
     * @param date     Fecha del evento.
     * @param priority Prioridad del evento.
     */
    public Event(String title, LocalDate date, Priority priority) {
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.tasks = new ArrayList<>();
    }

    /**
     * Agrega una tarea al evento.
     *
     * @param task Tarea a agregar.
     */
    public void addTask(EventTask task) {
        tasks.add(task);
    }

    /**
     * Devuelve una representación en texto del evento, incluyendo tareas.
     *
     * @return Detalles del evento y el estado de las tareas.
     */
    @Override
    public String toString() {
        long completedTasks = tasks.stream().filter(EventTask::isCompleted).count();
        return String.format("Evento: %s | Fecha: %s | Prioridad: %s | Tareas completadas: %d/%d",
                title, date, priority, completedTasks, tasks.size());
    }

    /**
     * Obtiene el título del evento.
     *
     * @return Título del evento.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Devuelve la lista de tareas.
     *
     * @return Lista de tareas del evento.
     */
    public ArrayList<EventTask> getTasks() {
        return tasks;
    }
}
