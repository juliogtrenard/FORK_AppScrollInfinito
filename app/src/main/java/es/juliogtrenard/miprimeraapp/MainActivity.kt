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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        initView()
        initListeners()
    }

    private fun initListeners() {
        btnAddTask.setOnClickListener {addTask()}
    }

    private fun addTask() {
        val taskToAdd:String = etTask.text.toString()
    }

    private fun initView() {
        btnAddTask = findViewById(R.id.btnAddTask)
        etTask = findViewById(R.id.etTask)
        rvTask = findViewById(R.id.rvTask)
    }
}