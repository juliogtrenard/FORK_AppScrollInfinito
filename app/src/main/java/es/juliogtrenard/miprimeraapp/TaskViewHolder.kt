package es.juliogtrenard.miprimeraapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Maneja un elemento del RecyclerView extendiendo de RecyclerView.ViewHolder
class TaskViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val tvTask:TextView = view.findViewById(R.id.tvTask)

    //Actualiza el contenido de la tarea
    fun render(task:String) {
        tvTask.text = task
    }
}