package com.answufeng.utils.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.answufeng.utils.demo.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        setSupportActionBar(binding.toolbar)
        setupViewPager()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_demo_playbook -> {
                val lines = tabs.joinToString("\n") { "• ${it.first}" }
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.demo_playbook_title)
                    .setMessage("$lines\n\n${getString(R.string.demo_playbook_footer)}")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                true
            }
            R.id.action_theme_follow_system -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                true
            }
            R.id.action_theme_light -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
            R.id.action_theme_dark -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
