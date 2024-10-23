package es.juliogtrenard.miprimeraapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//Clase que recibe el listado de tareas y hereda de RecyclerView.Adapter recibiendo el ViewHolder creado
//Tambien recibe una funcion que toma un entero como argumento y no devuelve ningún valor (Unit es equivalente a void),
//maneja la acción de marcar una tarea como completada
class TaskAdapter(private val tasks:List<String>, private val onItemDone: (Int) -> Unit):RecyclerView.Adapter<TaskViewHolder>() {
    //Contiene la vista para un elemento en la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        //LayoutInflater permite cargar xml para el diseño
        val layoutInflater = LayoutInflater.from(parent.context)

        //convierte el archivo xml en una vista correspondiente en codigo
        //R.layout.item_task -> Contiene las vistas
        //parent -> donde se añadira la vista
        //false -> la vista no se añade inmediatamente al parent
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    //Cuantos items tiene que mostrar
    override fun getItemCount() = tasks.size

    //Actualiza la vista con los datos correspondientes a la posicion dada
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position], onItemDone)
    }

}