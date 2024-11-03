package es.juliogtrenard.miprimeraapp

/**
 * Clase para gestionar una tarea.
 * @param id Identificador
 * @param titulo Titulo de la tarea
 * @param descripcion Descripcion de la tarea
 */
data class Task (val id:Int, val titulo:String, val descripcion:String){
    /**
     * Obtener el título de la tarea
     *
     * @return El título de la tarea
     */
    fun getTitle(): String {
        return titulo
    }
}