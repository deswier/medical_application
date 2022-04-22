package tools

import com.myapplication.exception.DataException
import com.myapplication.model.Note
import factory.RequestFactory
import factory.call

object ListOfNotes {
    public var notes: MutableList<Note> = mutableListOf()

    init {
        RequestFactory.noteService.allNotes().call(onSuccess = { v1, v2 ->
            v2.body()?.forEach {
                add(it)
            }
        })
    }

    @Throws(DataException::class)
    fun add(n: Note?) {
        notes.add(n!!)
    }

    public fun searchNote(search: String): MutableList<Note> {
        val res: MutableList<Note> = mutableListOf()
        for (note in notes) {
            if (isSubstring(search, note.test)) res.add(note)
        }
        return res
    }

    private fun isSubstring(substr: String, str: String): Boolean {
        val strArray = str.split(" ".toRegex()).toTypedArray()
        for (s in strArray) {
            if (s.length >= substr.length) if (s.substring(0, substr.length)
                    .lowercase() == substr.lowercase()
            ) return true
        }
        return false
    }
}