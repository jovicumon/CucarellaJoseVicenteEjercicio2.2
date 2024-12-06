/**
 * Clase que representa una tarea asociada a un evento.
 * Aunque el warning indica que el atributo {@code text} debería ser {@code final},
 * no se hará esta modificación para permitir modificar los datos de la tarea en el futuro.
 */

public class EventTask {
    private String text;
    private boolean isCompleted;

    /**
     * Constructor de la clase {@code EventTask}.
     *
     * @param text Descripción de la tarea. No puede ser {@code null}.
     */

    public EventTask(String text) {
        this.text = text;
        this.isCompleted = false;
    }

    /**
     * Marca la tarea como completada o no completada.
     * @param completed El estado de la tarea:
     *                  {@code true} si la tarea está completada,
     *                  {@code false} si la tarea está pendiente.
     */

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    /**
     * Devuelve el estado de la tarea.
     *
     * @return {@code true} si la tarea está completada,
     *         {@code false} si está pendiente.
     */

    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Devuelve una representación en texto de la tarea.
     * La representación incluye la descripción de la tarea y su estado (completada o pendiente).
     * @return Una cadena de texto con la descripción y el estado de la tarea.
     */

    @Override
    public String toString() {
        return String.format("Tarea: %s - Estado: %s", text, isCompleted ? "Completada" : "Pendiente");
    }
}
