package udovyk.k.coins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import udovyk.k.coins.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val binding = DataBindingUtil.
            setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                setLifecycleOwner(this@MainActivity)
                viewModel = mainViewModel
            }


    }

}
