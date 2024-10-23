package es.juliogtrenard.miprimeraapp

import android.app.Application

//La primera clase que lea la app cuando se inicie
class TaskApplication:Application() {
    companion object {
        lateinit var prefs:Preferences
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Preferences(baseContext)
    }
}