package es.juliogtrenard.miprimeraapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.juliogtrenard.miprimeraapp.TaskApplication.Companion.prefs

/**
 * Actividad principal de la aplicación que gestiona la lista de tareas.
 */
class MainActivity : AppCompatActivity() {
    /**
     * Botón que permite añadir una nueva tarea a la lista.
     */
    private lateinit var btnAddTask:Button
    /**
     * Campo de texto donde el usuario ingresa la nueva tarea.
     */
    private lateinit var etTask:EditText
    /**
     * RecyclerView que muestra la lista de tareas.
     */
    private lateinit var rvTask:RecyclerView
    /**
     * Lista mutable que contiene las tareas actuales.
     */
    private var tasks = mutableListOf<String>()
    /**
     * Adaptador que gestiona la visualización de las tareas en el RecyclerView.
     */
    private lateinit var adapter:TaskAdapter

    /**
     * Método que se llama al crear la actividad.
     * Inicializa la interfaz de usuario y el RecyclerView.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        initRecyclerView()
    }

    /**
     * Configura el RecyclerView y añade el adaptador.
     * Además, añade funcionalidad de deslizar para eliminar una tarea.
     */
    private fun initRecyclerView() {
        tasks = prefs.getTasks()
        rvTask.layoutManager = LinearLayoutManager(this) // LayoutManager controla como se van a ver las vistas
        adapter = TaskAdapter(tasks) { deleteTask(it) } // Cuando se pulsa la imagen se llama a la funcion para que la borre
        rvTask.adapter = adapter

        deslizarParaEliminar()
    }

    /**
     * Inicializa la interfaz de usuario y configura los eventos.
     */
    private fun initUI() {
        initView()
        initListeners()
    }

    /**
     * Configura el evento click del botón para añadir una tarea.
     */
    private fun initListeners() {
        btnAddTask.setOnClickListener {addTask()}
    }

    /**
     * Añade una nueva tarea a la lista.
     * Notifica al adaptador sobre el cambio y guarda la lista actualizada en las preferencias.
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun addTask() {
        val taskToAdd:String = etTask.text.toString()

        if(taskToAdd.isEmpty()) {
            etTask.error = "Escribe una tarea!"
        } else {
            tasks.add(taskToAdd)
            adapter.notifyDataSetChanged() //Notifica que se han añadido nuevos valores
            etTask.setText("")
            prefs.saveTasks(tasks)

            val mediaPlayerAddSound = MediaPlayer.create(this, R.raw.add_task)
            mediaPlayerAddSound.start()
        }
    }

    /**
     * Elimina una tarea de la lista en la posición especificada.
     * Notifica al adaptador sobre el cambio y guarda la lista actualizada en las preferencias.
     *
     * @param position Posición de la tarea a eliminar.
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun deleteTask(position:Int) {
        tasks.removeAt(position)
        adapter.notifyDataSetChanged()
        prefs.saveTasks(tasks)
    }

    /**
     * Inicializa las vistas buscando por su ID en el layout.
     */
    private fun initView() {
        btnAddTask = findViewById(R.id.btnAddTask)
        etTask = findViewById(R.id.etTask)
        rvTask = findViewById(R.id.rvTask)
    }

    /**
     * Permite eliminar una tarea deslizando hacia la izquierda o derecha de la pantalla.
     */
    private fun deslizarParaEliminar() {
        // Configuración de Swipe to Delete para eliminar tareas al deslizar
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTask(viewHolder.adapterPosition) // Llama al método para eliminar la tarea en la posición desliz
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvTask) // Asigna el helper al RecyclerView
    }
}