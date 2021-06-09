package com.homeworkandroid.trabajofragment.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class EventsViewModel : ViewModel() {

    private val repositorioEvents = RepositorioEvents()

    fun fetchEventsData(): LiveData<MutableList<Events>> {

        val mutableEvents = MutableLiveData<MutableList<Events>>()

        repositorioEvents.getEventsData().observeForever{
            mutableEvents.value = it
        }

        return mutableEvents
    }
}