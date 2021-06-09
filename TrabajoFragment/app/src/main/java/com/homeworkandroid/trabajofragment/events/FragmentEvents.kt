package com.homeworkandroid.trabajofragment.events


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.homeworkandroid.trabajofragment.R
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_notice.shimmer_view_container


class FragmentEvents : Fragment() {

    private lateinit var adapter: EventsAdapter
    private val viewModel by lazy{ ViewModelProviders.of(this).get(EventsViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EventsAdapter(this)

        rv_events.layoutManager = LinearLayoutManager(activity)
        rv_events.adapter = adapter

        observeNotice()

    }

    fun observeNotice(){
        shimmer_view_container.startShimmer()
        viewModel.fetchEventsData().observe(viewLifecycleOwner, Observer{
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
            adapter.setListEvents(it)
            adapter.notifyDataSetChanged()
        })
    }



}
