package com.homeworkandroid.trabajofragment


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val adapter by lazy { ViewPagerAdapter(this) }
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TAG: ","==========> MAIN")
        container()
        

    }

    private fun container(){
        pager.adapter = adapter
        val tabLayoutMediator = TabLayoutMediator(tab_layout,pager
        ) { tab, position ->
            when (position + 1) {
                1 -> {
                    tab.text = getString(R.string.noticeString)
                    tab.setIcon(R.drawable.notice_op1)
                    val badge: BadgeDrawable = tab.orCreateBadge
                    badge.backgroundColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)
                    badge.isVisible = true
                }
                2 -> {
                    tab.text = getString(R.string.eventString)
                    tab.setIcon(R.drawable.event_op2)
                    val badge: BadgeDrawable = tab.orCreateBadge
                    badge.backgroundColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)
                    badge.number = 3
                    badge.isVisible = true

                }
                3 -> {
                    tab.text = getString(R.string.tourmentString)
                    tab.setIcon(R.drawable.tourment_op3)
                    val badge: BadgeDrawable = tab.orCreateBadge
                    badge.backgroundColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)
                    badge.number = 13
                    badge.maxCharacterCount = 2
                    badge.isVisible = true
                }
            }
        }
        tabLayoutMediator.attach()
    }

    override fun onBackPressed(){
        if(viewPager2.currentItem == 0){
            super.onBackPressed()
        }else{
            viewPager2.currentItem = viewPager2.currentItem-1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        return when(item.itemId){
            R.id.accountMenu ->{
                startActivity(Intent(this, UserActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }








}
