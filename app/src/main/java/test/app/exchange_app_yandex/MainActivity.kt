package test.app.exchange_app_yandex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import test.app.exchange_app_yandex.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, ListFragment.newInstance())
                    .addToBackStack(null)
                    .commit()

        //c114bi748v6t4vgvsoj0
        //sandbox_c114bi748v6t4vgvsojg
    }
}