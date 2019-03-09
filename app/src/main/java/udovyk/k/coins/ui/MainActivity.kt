package udovyk.k.coins.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import udovyk.k.coins.R
import udovyk.k.coins.databinding.ActivityMainBinding
import udovyk.k.coins.data.CoinAppeared

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    var imageView: ImageView? = null

    private val imageIdObserver: Observer<CoinAppeared> = Observer {
        val data = it

        if (imageView != null) {
            clearImage()
            imageView = null
        } else {
            imageView = findViewById(it.id!!)
            imageView!!.apply {
                setImageDrawable(getDrawable(it.coin!!.image))
                isClickable = true
                setOnClickListener {
                    data.coin.let {
                        mainViewModel.updateScore(data.coin!!.value)
                    }
                }
            }
        }
    }

    private val scoreObserver: Observer<Int> = Observer {
        if (it < 0) {
            Toast.makeText(this, getString(R.string.game_over), Toast.LENGTH_LONG).show()
            mainViewModel.stopGame()
            clearImage()
            showButton(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                setLifecycleOwner(this@MainActivity)
                viewModel = mainViewModel
            }
        mainViewModel.liveCoinAppeared.observe(this, imageIdObserver)
        mainViewModel.liveScore.observe(this, scoreObserver)

        binding.btnAction.setOnClickListener {
            mainViewModel.beginGame()
            showButton(false)
        }
    }

    private fun clearImage() {
        imageView!!.apply {
            setImageDrawable(null)
            isClickable = false
        }
    }

    private fun showButton(isShown: Boolean) {
        binding.run {
            if (isShown) {
                btnAction.visibility = View.VISIBLE
            } else {
                btnAction.visibility = View.GONE
            }
        }

    }

}
