package com.example.petroltest.viewmodels

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.petroltest.repository.Repository
import java.lang.Exception
import java.util.*

class PetrolViewModel: ViewModel() {

    val resultChanger: LiveData<Int> = Repository.notification
    val errorCatcher: LiveData<String> = Repository.error
    val locale = Locale("ru", "RU")
    val numDecs = 6
    var petrolInCorrect = true
    var petrolOutCorrect = true

    fun calculateResult(petrolInVal: Editable, petrloTwoVal: Editable): Float? {
        val one = strToFloat(petrolInVal)
        val two = strToFloat(petrloTwoVal)
        petrolInCorrect = when(one) {
            null ->
                false
            else ->
                true
        }
        petrolOutCorrect = when(two) {
            null ->
                false
            else ->
                true
        }

        return if(two != null && one != null) {
            Log.d("OYOY", (one - two).toString())
            one - two
        } else {
            null
        }
    }

    private fun strToFloat(editable: Editable?): Float? {
        editable
            .toString()
            .replace(",", ".")
            .replace("\\s".toRegex(), "")
            .let {
                return try {
                    it.toFloat()
                } catch(e: Exception) {
                    null
                }
            }
    }

    fun setError(exception: String) {
        Repository.error.value = exception.toString()
    }

}
