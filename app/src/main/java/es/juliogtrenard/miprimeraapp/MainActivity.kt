package es.juliogtrenard.miprimeraapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var btnAddTask:Button
    private lateinit var etTask:EditText
    private lateinit var rvTask:RecyclerView
    private var tasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
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
    private fun addTask() {
        val taskToAdd:String = etTask.text.toString()
        tasks.add(taskToAdd)
    }

    //Inicializar las variables buscando por su id
    private fun initView() {
        btnAddTask = findViewById(R.id.btnAddTask)
        etTask = findViewById(R.id.etTask)
        rvTask = findViewById(R.id.rvTask)
    }
}