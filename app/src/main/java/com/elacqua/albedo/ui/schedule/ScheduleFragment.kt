package com.elacqua.albedo.ui.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.albedo.AlbedoApp
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import kotlinx.android.synthetic.main.fragment_schedule.*
import javax.inject.Inject

class ScheduleFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val scheduleViewModel: ScheduleViewModel by viewModels { vmFactory }
    private lateinit var adapter: ScheduleRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        adapter = ScheduleRecyclerAdapter(object : OnScheduleAnimeSelected {
            override fun onClick(anime: Anime) {
                val args = bundleOf(getString(R.string.key_anime_id) to anime.malId)
                findNavController()
                    .navigate(R.id.action_navigation_schedule_to_animeDetailFragment, args)
            }
        })
        val llm =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler_schedule.layoutManager = llm
        recycler_schedule.setHasFixedSize(true)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbedoApp).appComponent.inject(this)
    }

    override fun onStart() {
        recycler_schedule.adapter = adapter
        super.onStart()
    }

    override fun onStop() {
        recycler_schedule.adapter = null
        super.onStop()
    }
}