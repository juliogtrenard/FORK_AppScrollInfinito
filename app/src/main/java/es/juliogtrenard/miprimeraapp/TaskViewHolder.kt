package es.juliogtrenard.miprimeraapp

import android.content.Context
import android.media.MediaPlayer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Maneja un elemento del RecyclerView extendiendo de RecyclerView.ViewHolder
class TaskViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val tvTask:TextView = view.findViewById(R.id.tvTask)
    private val ivTaskDone:ImageView = view.findViewById(R.id.ivTaskDone)

    //Actualiza el contenido de la tarea
    fun render(task:String, onItemDone: (Int) -> Unit, context: Context) {
        tvTask.text = task
        ivTaskDone.setOnClickListener { //Borra la tarea cuando se hace click en el check
            onItemDone(adapterPosition)

            val mediaPlayerDeleteSound = MediaPlayer.create(context, R.raw.task_done)
            mediaPlayerDeleteSound.start()
        }
    }
}