package es.juliogtrenard.miprimeraapp

import android.content.Context
import android.media.MediaPlayer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder para una tarea.
 *
 * Se encarga de gestionar la vista correspondiente a una tarea,
 * incluyendo el texto de la tarea y el botón de "hecho".
 *
 * @author Julio González
 */
class TaskViewHolder(view: View):RecyclerView.ViewHolder(view) {
    /**
     * TextView para mostrar el contenido de la tarea.
     */
    private val tvTask: TextView = view.findViewById(R.id.tvTask)

    /**
     * ImageView que representa el botón de "hecho" para la tarea.
     */
    private val ivTaskDone: ImageView = view.findViewById(R.id.ivTaskDone)

    /**
     * Actualiza el contenido de la tarea y maneja la acción de completar la tarea.
     *
     * @param task El texto de la tarea a mostrar.
     * @param onItemDone Función que se llama cuando se marca la tarea como completada.
     * @param context El contexto utilizado para reproducir el sonido de tarea completada.
     */
    fun render(task: String, onItemDone: (Int) -> Unit, context: Context) {
        tvTask.text = task
        ivTaskDone.setOnClickListener {
            // Borra la tarea cuando se hace click en el check
            onItemDone(adapterPosition)

            val mediaPlayerDeleteSound = MediaPlayer.create(context, R.raw.task_done)
            mediaPlayerDeleteSound.start()
        }
    }
}