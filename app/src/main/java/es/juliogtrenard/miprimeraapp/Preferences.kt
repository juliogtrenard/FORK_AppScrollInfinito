package es.juliogtrenard.miprimeraapp

import android.content.Context
import android.content.SharedPreferences

/**
 * Clase para gestionar las preferencias compartidas en la aplicación.
 *
 * @param context Contexto de la aplicación, necesario para acceder a las SharedPreferences.
 */
class Preferences(context: Context) {
    companion object {
        const val PREFS_NAME = "myDatabase" // Nombre de las preferencias compartidas
        const val TASKS = "tasks_value" // Clave para almacenar las tareas
    }
    private val prefs:SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    /**
     * Guarda una lista de tareas en las preferencias compartidas.
     *
     * @param tasks Lista de tareas a guardar.
     */
    fun saveTasks(tasks:List<String>) {
        prefs.edit().putStringSet(TASKS, tasks.toSet()).apply()
    }

    /**
     * Recupera la lista de tareas almacenadas en las preferencias compartidas.
     *
     * @return Una lista mutable de tareas. Devuelve una lista vacía si no hay tareas almacenadas.
     */
    fun getTasks():MutableList<String> = prefs.getStringSet(TASKS, emptySet<String>())?.toMutableList() ?: mutableListOf()
}