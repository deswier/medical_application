package tools

import com.myapplication.exception.DataException
import com.myapplication.model.Note
import factory.RequestFactory
import factory.call

data class ListOfNotes(var notes: MutableList<Note> = mutableListOf()) {

    init {
        RequestFactory.noteService.allNotes().call(onSuccess = { _, v2 ->
            v2.body()?.forEach {
                add(it)
            }
        })
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    @Throws(DataException::class)
    public fun add(n: Note?): Boolean {
        return notes.add(n!!)
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