package udovyk.k.coins.ui

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import udovyk.k.coins.R
import udovyk.k.coins.data.Coins
import udovyk.k.coins.data.GameState
import udovyk.k.coins.data.CoinAppeared
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val REPEAT_INTERVAL = 2000L
    private val DELAY_INTERVAL = 1000L

    private val cellsList = listOf(R.id.im1, R.id.im2, R.id.im3, R.id.im4, R.id.im5, R.id.im6, R.id.im7, R.id.im8, R.id.im9)
    private val coinsList = Coins.values()

    private var score = 0
    val liveScore = MutableLiveData<Int>()

    private var state = GameState.START.text
    val liveState = MutableLiveData<String>().apply { postValue(GameState.START.text) }
    val liveCoinAppeared = MutableLiveData<CoinAppeared>()

    private var timer: CountDownTimer? = null

    fun updateScore(value: Int) {
        score += value
        liveScore.postValue(score)
    }

    fun beginGame() {
        changeState()
        if (timer != null) {
            timer!!.cancel()
            timer = null
        } else {
            timer = object : CountDownTimer(DELAY_INTERVAL, REPEAT_INTERVAL) {
                override fun onFinish() {
                    start()
                }

                override fun onTick(millisUntilFinished: Long) {
                    liveCoinAppeared.postValue(
                        CoinAppeared(id = getRandCellVal(), coin = getRandCoinVal())
                    )
                }
            }.start()
        }
    }

    fun stopGame() {
        timer.let {
            it!!.cancel()
        }
        timer = null
        score = 0
        liveScore.postValue(0)
        changeState()
    }

    private fun changeState() {
        if (state == GameState.START.text) {
            state = GameState.STOP.text
            liveState.postValue(GameState.STOP.text)
        } else {
            state = GameState.START.text
            liveState.postValue(GameState.START.text)
        }
    }

    private fun getRandCellVal() : Int = cellsList[Random.nextInt(0, cellsList.size)]

    private fun getRandCoinVal() : Coins = coinsList[Random.nextInt(0, coinsList.size)]

}

