package test.app.exchange_app_yandex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import test.app.exchange_app_yandex.favorite.FavoriteFragment
import test.app.exchange_app_yandex.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, ListFragment.newInstance())
                    .commit()

        val tabLayout: TabLayout = findViewById(R.id.tabLayout_id)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.text.toString() == "Stocks")
                    supportFragmentManager
                            .popBackStack()

                if (tab?.text.toString() == "FAVORITE")
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, FavoriteFragment.newInstance())
                            .addToBackStack(null)
                            .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        //c114bi748v6t4vgvsoj0
        //sandbox_c114bi748v6t4vgvsojg
    }
}