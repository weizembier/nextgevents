package com.homeworkandroid.trabajofragment.tourment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homeworkandroid.trabajofragment.events.Events
import com.homeworkandroid.trabajofragment.events.RepositorioEvents

class TourmentViewModel : ViewModel() {

    private val repositorioTourment = RepositorioTourment()

    fun fetchTourmentData(): LiveData<MutableList<Tourment>> {

        val mutableTourment = MutableLiveData<MutableList<Tourment>>()

        repositorioTourment.getTourmentData().observeForever{
            mutableTourment.value = it
        }

        return mutableTourment
    }
}