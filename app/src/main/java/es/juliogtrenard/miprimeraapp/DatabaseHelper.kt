package es.juliogtrenard.miprimeraapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
/**
 * Clase para gestionar la base de datos
 */
class DatabaseHelper(context: Context):SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        private const val   DATABASE_NAME = "tareas.db"
        private const val   DATABASE_VERSION = 1
        private const val   TABLE_NAME = "tareas"
        private const val   COLUMN_ID = "id"
        private const val   COLUMN_TITLE = "titulo"
        private const val   COLUMN_DESCRIPCION = "descripcion"
    }

    /**
     * Crear tabla
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY, " +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_DESCRIPCION TEXT)"
        db?.execSQL(createTableQuery)
    }

    /**
     * Modifica la tabla de la base de datos
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery =
            "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)

        onCreate(db)
    }

    /**
     * Añadir a la base de datos
     *
     * @param tarea la tarea a añadir
     */
    fun insertNota(tarea: Task){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,       tarea.titulo)
            put(COLUMN_DESCRIPCION, tarea.descripcion)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    /**
     * Lee la base de datos y rellena una List de tipo Tarea
     */
    fun getAllTasks() : List<Task> {
        val listaTareas = mutableListOf<Task>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))

            val nota =  Task(id, titulo, descripcion)

            listaTareas.add(nota)
        }

        cursor.close()
        db.close()

        return listaTareas
    }

    /**
     * Obtiene los datos de la tarea
     *
     * @return Una tarea
     * @param idTarea el número de la posición de la nota
     */
    fun getIdTarea(idTarea:Int):Task {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$idTarea"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))

        cursor.close()
        db.close()

        return Task(id, titulo, descripcion)
    }

    /**
     * Actualiza el contenido
     */
    fun updateTarea(tarea:Task) {
        val db = writableDatabase

        //parametro con los nuevos valores
        val values = ContentValues().apply {
            put(COLUMN_TITLE, tarea.titulo)
            put(COLUMN_DESCRIPCION, tarea.descripcion)
        }

        //clausula con el id
        val whereClausula = "$COLUMN_ID = ?"

        //parametro con el id
        val whereArgs = arrayOf(tarea.id.toString())

        //actualizar el objeto
        db.update(TABLE_NAME, values, whereClausula, whereArgs)
        db.close()
    }

    /**
     * Elimina una tarea
     */
    fun deleteTarea(idTarea:Int) {
        val db = writableDatabase
        val whereClauses = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idTarea.toString())

        db.delete(TABLE_NAME,whereClauses,whereArgs)
        db.close()
    }
}