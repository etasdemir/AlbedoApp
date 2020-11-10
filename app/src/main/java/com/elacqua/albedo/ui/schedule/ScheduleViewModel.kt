package com.elacqua.albedo.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.remote.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.Schedule
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _scheduleAllDays = MutableLiveData<Schedule>()
    val scheduleAllDays: LiveData<Schedule> = _scheduleAllDays

    init {
        viewModelScope.launch {
            getScheduleAllDays()
        }
    }

    private suspend fun getScheduleAllDays() {
        val result = remoteRepository.getScheduleAllDays()
        _scheduleAllDays.postValue(result)
    }

}