package com.elacqua.albedo.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import kotlinx.android.synthetic.main.fragment_schedule.*
import timber.log.Timber

class ScheduleFragment : Fragment() {

    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private lateinit var adapter: ScheduleRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        adapter = ScheduleRecyclerAdapter(object: OnScheduleAnimeSelected {
            override fun onClick(anime: Anime) {
                val args = bundleOf("animeId" to anime.malId)
                findNavController()
                    .navigate(R.id.action_navigation_schedule_to_animeDetailFragment, args)
            }
        })
        val llm =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler_schedule.layoutManager = llm
        recycler_schedule.adapter = adapter

    }

    private fun initObservers() {
        scheduleViewModel.scheduleAllDays.observe(viewLifecycleOwner, {
            adapter.setSchedule(it)
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }
}