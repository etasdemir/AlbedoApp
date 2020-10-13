package com.elacqua.albedo.data.remote.jikan_api.service

import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.Schedule
import retrofit2.http.GET
import retrofit2.http.Path

interface ScheduleService {

    @GET("schedule")
    suspend fun getScheduleAllDays(): Schedule

    // Example day input: friday , no uppercase
    @GET("schedule/{day}")
    suspend fun getScheduleSingleDay(@Path("day") day: String): List<Anime>

}