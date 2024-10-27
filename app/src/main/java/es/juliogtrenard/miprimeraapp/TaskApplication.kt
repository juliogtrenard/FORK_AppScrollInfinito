package es.juliogtrenard.miprimeraapp

import android.app.Application

/**
 * Esta clase se utiliza para inicializar componentes globales como las preferencias
 * compartidas que se utilizarán en toda la aplicación.
 */
class TaskApplication:Application() {
    companion object {
        /**
         * Atributo que contiene el objeto de preferencias que se usa para guardar la información.
         */
        lateinit var prefs:Preferences
    }

    /**
     * Inicializar el objeto de preferencias.
     */
    override fun onCreate() {
        super.onCreate()
        prefs = Preferences(baseContext)
    }
}