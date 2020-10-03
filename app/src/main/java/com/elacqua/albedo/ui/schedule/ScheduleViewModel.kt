package com.elacqua.albedo.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.Schedule
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {

    private val remoteRepository = RemoteRepository()
    private val _scheduleAllDays = MutableLiveData<Schedule>()
    val scheduleAllDays : LiveData<Schedule> = _scheduleAllDays

    init {
        viewModelScope.launch {
            getScheduleAllDays()
        }
    }

    private suspend fun getScheduleAllDays(){
        val result = remoteRepository.getScheduleAllDays()
        _scheduleAllDays.postValue(result)
    }

}