

/**
 * Clase que representa una tarea asociada a un evento.
 * aunque el warning nos indica que text deberia ser final
 * no la vamos a modificar puesto que así podremos
 * modificar los datos de esta entrada
 */

public class EventTask {
    private String text;
    private boolean isCompleted;

    /**
     * Constructor de la clase EventTask.
     *
     * @param text Descripción de la tarea.
     */
    public EventTask(String text) {
        this.text = text;
        this.isCompleted = false;
    }

    /**
     * Marca la tarea como completada o no completada.
     *
     * @param completed Estado de la tarea (true: completada, false: sin hacer).
     */
    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    /**
     * Devuelve el estado de la tarea.
     *
     * @return true si está completada, false en caso contrario.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Devuelve una representación en texto de la tarea.
     *
     * @return Detalles de la tarea en formato String.
     */
    @Override
    public String toString() {
        return String.format("Tarea: %s - Estado: %s", text, isCompleted ? "Completada" : "Pendiente");
    }
}
