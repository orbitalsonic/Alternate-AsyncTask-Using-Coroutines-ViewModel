package com.orbitalsonic.alternateasynctask

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class LoadingViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = Transformations.map(_loading) { it }

    fun setLoading(mLoading: Boolean) {
        _loading.value = mLoading
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        setLoading(false)
        Log.i("myTesting",exception.toString())
    }

    fun performSomething(context: Activity) {
        GlobalScope.launch(Dispatchers.Main + handler) {
            async(Dispatchers.IO + handler) {
                // Do Something
                delay(5000)
            }.await()
            setLoading(true)
        }
    }



}