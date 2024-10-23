package es.juliogtrenard.miprimeraapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//Clase que recibe el listado de tareas y hereda de RecyclerView.Adapter recibiendo el ViewHolder creado
class TaskAdapter(private val tasks:List<String>):RecyclerView.Adapter<TaskViewHolder>() {
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
        holder.render(tasks[position])
    }

}