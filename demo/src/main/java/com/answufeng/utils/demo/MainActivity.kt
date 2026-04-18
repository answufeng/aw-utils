package com.answufeng.utils.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.answufeng.utils.demo.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val tabs = listOf(
        "字符串" to StringFragment::class.java,
        "日期" to DateFragment::class.java,
        "文件" to FileFragment::class.java,
        "设备" to DeviceFragment::class.java,
        "网络" to NetworkFragment::class.java,
        "视图" to ViewFragment::class.java,
        "富文本" to SpanFragment::class.java,
        "更多" to MoreFragment::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = tabs.size
            override fun createFragment(position: Int): Fragment {
                return tabs[position].second.getDeclaredConstructor().newInstance()
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabs[position].first
        }.attach()
    }
}
