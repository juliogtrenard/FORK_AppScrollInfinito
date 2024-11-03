package es.juliogtrenard.miprimeraapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Clase que recibe el listado de tareas y hereda de RecyclerView.Adapter recibiendo el ViewHolder creado
 * Tambien recibe una funcion que toma un entero como argumento y no devuelve ningún valor (Unit es equivalente a void),
 * maneja la acción de marcar una tarea como completada
 *
 * @param tasks Lista de cadenas que representan las tareas.
 * @param onItemDone Función que se ejecuta cuando se marca una tarea como completada,
 *                   recibiendo la posición de la tarea como parámetro.
 */
class TaskAdapter(private val tasks:MutableList<Task>, private val onItemDone: (Int) -> Unit):RecyclerView.Adapter<TaskViewHolder>() {
    /**
     * Contiene la vista para un elemento en la lista.
     *
     * @param parent El ViewGroup al que se añadirá el nuevo ViewHolder.
     * @param viewType Tipo de vista del nuevo ViewHolder.
     * @return Un nuevo TaskViewHolder que contiene la vista inflada desde el XML.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        //LayoutInflater permite cargar xml para el diseño
        val layoutInflater = LayoutInflater.from(parent.context)

        //convierte el archivo xml en una vista correspondiente en codigo
        //R.layout.item_task -> Contiene las vistas
        //parent -> donde se añadira la vista
        //false -> la vista no se añade inmediatamente al parent
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    /**
     * Devuelve la cantidad total de elementos que se mostrarán en el RecyclerView.
     *
     * @return Número de tareas en la lista.
     */
    override fun getItemCount() = tasks.size

    /**
     * Actualiza el ViewHolder con los datos correspondientes a la posición dada.
     *
     * @param holder El ViewHolder que se actualizará con los datos.
     * @param position La posición del elemento en la lista de tareas.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position].getTitle(), onItemDone, holder.itemView.context)
    }

}