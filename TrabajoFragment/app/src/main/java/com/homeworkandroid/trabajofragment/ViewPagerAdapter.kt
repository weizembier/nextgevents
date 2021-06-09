package com.homeworkandroid.trabajofragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.homeworkandroid.trabajofragment.events.FragmentEvents
import com.homeworkandroid.trabajofragment.notice.FragmentNotice
import com.homeworkandroid.trabajofragment.tourment.FragmentTourment


class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {


    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { FragmentNotice()
            }
            1 -> { FragmentEvents() }
            2 -> { FragmentTourment()
            }
            else -> FragmentNotice()
        }
    }


}