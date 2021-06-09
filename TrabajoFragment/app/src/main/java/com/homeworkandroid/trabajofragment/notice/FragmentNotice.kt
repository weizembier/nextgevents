package com.homeworkandroid.trabajofragment.notice


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.LinearLayoutManager
import com.homeworkandroid.trabajofragment.R
import kotlinx.android.synthetic.main.fragment_notice.*


class FragmentNotice : Fragment() {

    private lateinit var adapter: NoticeAdapter
    private val viewModel by lazy{ ViewModelProviders.of(this).get(NoticeViewModel::class.java)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_notice, container, false)

        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoticeAdapter(this)

        rv_notice.layoutManager = LinearLayoutManager(activity)
        rv_notice.adapter = adapter

        observeNotice()

    }

    fun observeNotice(){
        shimmer_view_container.startShimmer()
        viewModel.fetchNoticeData().observe(viewLifecycleOwner, Observer{
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
            adapter.setListNotice(it)
            adapter.notifyDataSetChanged()
        })
    }



}
