package test.app.exchange_app_yandex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import test.app.exchange_app_yandex.chart.ChartFragment
import test.app.exchange_app_yandex.favorite.FavoriteFragment
import test.app.exchange_app_yandex.list.ListFragment
import test.app.exchange_app_yandex.news.NewsFragment

class MainActivity : AppCompatActivity() {

    lateinit var FRAGMENT: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState != null)
            FRAGMENT = savedInstanceState.getString("STATE").toString()

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, ListFragment.newInstance())
                    .commit()
            FRAGMENT = "List"
        }
        else {
            
            when (FRAGMENT){
                "List" -> {
                    supportFragmentManager
                            .beginTransaction()
                            .add(R.id.container, ListFragment.newInstance())
                            .commit()
                    FRAGMENT = "List"
                }
                "Favorite" -> {
                    supportFragmentManager
                            .beginTransaction()
                            .add(R.id.container, FavoriteFragment.newInstance())
                            .commit()
                    FRAGMENT = "Favorite"
                }
                "News" -> {
                    supportFragmentManager
                            .beginTransaction()
                            .add(R.id.container, NewsFragment.newInstance())
                            .commit()
                    FRAGMENT = "News"
                }
            }
        }

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_one -> {
                    if (FRAGMENT != "List")
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.container, ListFragment.newInstance())
                                .commit()
                    FRAGMENT = "List"
                }
                R.id.action_two -> {
                    if (FRAGMENT != "Favorite")
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.container, FavoriteFragment.newInstance())
                                .commit()
                    FRAGMENT = "Favorite"
                }
                R.id.action_three -> {
                    if (FRAGMENT != "News")
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.container, NewsFragment.newInstance())
                                .commit()
                    FRAGMENT = "News"
                }
            }
            true
        }

        val toolBar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("STATE", FRAGMENT)
    }
}