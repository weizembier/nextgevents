package com.homeworkandroid.trabajofragment.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoticeViewModel: ViewModel() {

    private val repositorioNotice = RepositorioNotice()

    fun fetchNoticeData():LiveData<MutableList<Notice>>{

        val mutableNotice = MutableLiveData<MutableList<Notice>>()

        repositorioNotice.getNoticeData().observeForever{
            mutableNotice.value = it
        }
        return mutableNotice
    }
}