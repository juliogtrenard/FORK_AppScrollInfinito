package es.juliogtrenard.miprimeraapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Actividad principal de la aplicación que gestiona la lista de tareas.
 */
class MainActivity : AppCompatActivity() {
    /** Muestra una imagen cuando no hay tareas */
    private lateinit var imageViewNoTasks: ImageView

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
    private var tasks = mutableListOf<Task>()
    /**
     * Adaptador que gestiona la visualización de las tareas en el RecyclerView.
     */
    private lateinit var adapter:TaskAdapter

    /**
     * Gestiona la base de datos de tareas.
     */
    private lateinit var taskBDHelper: DatabaseHelper

    /**
     * Método que se llama al crear la actividad.
     * Inicializa la interfaz de usuario y el RecyclerView.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskBDHelper = DatabaseHelper(this)

        initUI()
        initRecyclerView()
    }

    /**
     * Configura el RecyclerView y añade el adaptador.
     * Además, añade funcionalidad de deslizar para eliminar una tarea.
     */
    private fun initRecyclerView() {
        tasks = taskBDHelper.getAllTasks().toMutableList() // Obtener tareas de SQLite
        rvTask.layoutManager = LinearLayoutManager(this) // LayoutManager controla como se van a ver las vistas
        adapter = TaskAdapter(tasks) { deleteTask(it) } // Cuando se pulsa la imagen se llama a la funcion para que la borre
        rvTask.adapter = adapter

        updateNoTasksVisibility()

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
        val tareaTitulo:String = etTask.text.toString()

        if(tareaTitulo.isEmpty()) {
            etTask.error = "Escribe una tarea!"
        } else {
            // Crear un nuevo objeto Task y lo agrega a la base de datos
            val newTask = Task(0, tareaTitulo, "")
            taskBDHelper.insertNota(newTask)
            tasks.add(newTask)
            adapter.notifyDataSetChanged() //Notifica que se han añadido nuevos valores
            etTask.setText("")
            updateNoTasksVisibility()

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
    private fun deleteTask(position: Int) {
        if (tasks.isNotEmpty() && position >= 0 && position < tasks.size) {
            val taskToDelete = tasks[position]
            tasks.removeAt(position)
            taskBDHelper.deleteTarea(taskToDelete.id) // Eliminar de SQLite
            taskBDHelper.actualizarTareas(tasks) // Actualizar la lista de tareas en la base de datos

            adapter.notifyDataSetChanged()
            updateNoTasksVisibility()
        } else {
            Log.e("MainActivity", "Error al eliminar tarea: posición inválida")
        }
    }

    /**
     * Inicializa las vistas buscando por su ID en el layout.
     */
    private fun initView() {
        btnAddTask = findViewById(R.id.btnAddTask)
        etTask = findViewById(R.id.etTask)
        rvTask = findViewById(R.id.rvTask)
        imageViewNoTasks = findViewById(R.id.imageViewNoTasks)
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

    /**
     * Actualiza la visibilidad de la vista de la lista de tareas y de la imagen
     * que indica que no hay tareas disponibles. Si la lista de tareas está vacía,
     * oculta el RecyclerView y muestra la imagen que indica que no hay tareas.
     * Si hay tareas en la lista, muestra el RecyclerView y oculta la imagen.
     */
    private fun updateNoTasksVisibility() {
        if (tasks.isEmpty()) {
            rvTask.visibility = View.GONE
            imageViewNoTasks.visibility = View.VISIBLE // Muestra la imagen
        } else {
            rvTask.visibility = View.VISIBLE
            imageViewNoTasks.visibility = View.GONE // Oculta la imagen
        }
    }
}