package com.homeworkandroid.trabajofragment.tourment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.homeworkandroid.trabajofragment.R
import com.homeworkandroid.trabajofragment.notice.NoticeAdapter
import com.homeworkandroid.trabajofragment.notice.NoticeViewModel
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.android.synthetic.main.fragment_notice.shimmer_view_container
import kotlinx.android.synthetic.main.fragment_tourment.*


class FragmentTourment : Fragment() {


    private lateinit var adapter: TourmentAdapter
    private val viewModel by lazy{ ViewModelProviders.of(this).get(TourmentViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tourment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TourmentAdapter(this)

        rv_tourment.layoutManager = LinearLayoutManager(activity)
        rv_tourment.adapter = adapter

        observeNotice()

    }

    fun observeNotice(){
        shimmer_view_container.startShimmer()
        viewModel.fetchTourmentData().observe(viewLifecycleOwner, Observer{
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
            adapter.setListTourment(it)
            adapter.notifyDataSetChanged()
        })

    }



}
