package es.juliogtrenard.miprimeraapp

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    companion object {
        const val PREFS_NAME = "myDatabase"
        const val TASKS = "tasks_value"
    }
    private val prefs:SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    //Guardar la informacion
    fun saveTasks(tasks:List<String>) {
        prefs.edit().putStringSet(TASKS, tasks.toSet()).apply()
    }

    //Recuperar la información
    fun getTasks():MutableList<String> = prefs.getStringSet(TASKS, emptySet<String>())?.toMutableList() ?: mutableListOf()
}