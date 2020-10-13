package com.elacqua.albedo.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.Schedule
import com.elacqua.albedo.util.WeekDays
import kotlinx.android.synthetic.main.fragment_schedule_recycler_item.view.*

private const val GRID_SPAN_COUNT = 2

class ScheduleRecyclerAdapter(
    private val listener: OnScheduleAnimeSelected
)
    : RecyclerView.Adapter<ScheduleRecyclerAdapter.ScheduleViewHolder>(){

    private val scheduleDayList = ArrayList<List<Anime>>()

    fun setSchedule(schedule: Schedule){
        scheduleDayList.clear()
        scheduleDayList.addAll(
            arrayListOf(schedule.monday, schedule.tuesday, schedule.wednesday,
            schedule.thursday, schedule.friday, schedule.saturday, schedule.sunday)
        )
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            inflater.inflate(R.layout.fragment_schedule_recycler_item, parent, false)
        view.recycler_schedule_inner.layoutManager =
            GridLayoutManager(parent.context, GRID_SPAN_COUNT)
        view.recycler_schedule_inner.setRecycledViewPool(RecyclerView.RecycledViewPool())

        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return scheduleDayList.size
    }

    inner class ScheduleViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bindView(position: Int){
            view.txt_schedule_day.text = WeekDays.getDayByIndex(position)
            view.recycler_schedule_inner.adapter =
                ScheduleInnerFragmentAdapter(scheduleDayList[position], listener)
        }

    }

}