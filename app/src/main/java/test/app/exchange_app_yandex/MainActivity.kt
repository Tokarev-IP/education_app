package test.app.exchange_app_yandex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import test.app.exchange_app_yandex.chart.ChartFragment
import test.app.exchange_app_yandex.favorite.FavoriteFragment
import test.app.exchange_app_yandex.list.ListFragment
import test.app.exchange_app_yandex.news.NewsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_main)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, ListFragment.newInstance())
                    .commit()

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_one -> supportFragmentManager
                    .beginTransaction()
                        .replace(R.id.container, ListFragment.newInstance())
                        .commit()

                R.id.action_two -> supportFragmentManager
                    .beginTransaction()
                        .replace(R.id.container, FavoriteFragment.newInstance())
                        .commit()

                R.id.action_three -> supportFragmentManager
                    .beginTransaction()
                        .replace(R.id.container, NewsFragment.newInstance())
                        .commit()
            }
            true
        }

        }
    }