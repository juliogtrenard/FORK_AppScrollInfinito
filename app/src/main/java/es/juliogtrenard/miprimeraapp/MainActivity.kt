package es.juliogtrenard.miprimeraapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.juliogtrenard.miprimeraapp.TaskApplication.Companion.prefs

class MainActivity : AppCompatActivity() {
    private lateinit var btnAddTask:Button
    private lateinit var etTask:EditText
    private lateinit var rvTask:RecyclerView
    private var tasks = mutableListOf<String>()
    private lateinit var adapter:TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        initRecyclerView()
    }

    //Configura el RecyclerView para añadir el adapter
    private fun initRecyclerView() {
        tasks = prefs.getTasks()
        rvTask.layoutManager = LinearLayoutManager(this) //LayoutManager controla como se van a ver las vistas
        adapter = TaskAdapter(tasks) { deleteTask(it) } //Cuando se pulsa la imagen se llama a la funcion para que la borre
        rvTask.adapter = adapter
    }

    //Llama a los metodos que inicializan y controlan el evento
    private fun initUI() {
        initView()
        initListeners()
    }

    //Añade una tarea cuando ocurre el evento click en el boton
    private fun initListeners() {
        btnAddTask.setOnClickListener {addTask()}
    }

    //Añade una tarea
    @SuppressLint("NotifyDataSetChanged")
    private fun addTask() {
        val taskToAdd:String = etTask.text.toString()
        tasks.add(taskToAdd)
        adapter.notifyDataSetChanged() //Notifica que se han añadido nuevos valores
        etTask.setText("")
        prefs.saveTasks(tasks)

        val mediaPlayerAddSound = MediaPlayer.create(this, R.raw.add_task)
        mediaPlayerAddSound.start()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteTask(position:Int) {
        tasks.removeAt(position)
        adapter.notifyDataSetChanged()
        prefs.saveTasks(tasks)
    }

    //Inicializar las variables buscando por su id
    private fun initView() {
        btnAddTask = findViewById(R.id.btnAddTask)
        etTask = findViewById(R.id.etTask)
        rvTask = findViewById(R.id.rvTask)
    }
}