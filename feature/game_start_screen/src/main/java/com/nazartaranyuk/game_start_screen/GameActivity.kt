package com.nazartaranyuk.game_start_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.nazartaranyuk.game_play_screen.PlayActivity
import com.nazartaranyuk.game_shop_screen.ShopActivity
import com.nazartaranyuk.game_start_screen.databinding.ActivityGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    private var binding: ActivityGameBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        binding?.let { binding ->
            setContentView(binding.root)
            setUpListeners(binding)
        }
    }

    private fun setUpListeners(binding: ActivityGameBinding) {

        binding.btnShop.setOnClickListener {
            startActivity(Intent(this, ShopActivity::class.java))
        }

        binding.btnStartGame.setOnClickListener {
            // Тут возник баг с Koin, он не успевает заинжектить дао класс во вью модель,
            // По-этому если нажать на кнопку сразу после запуска GameActivity то вылетит ошибка
            // О том что dao null reference
            // Скорее всего проблема в самом Koin, который не справляется с многомодульностью.
            // Koin не лучший вариант для многомодульного решения
            // Временное решение: Сделать delay для того чтобы коин успел заинжектить дао.
            lifecycleScope.launch() {
                startActivity(Intent(this@GameActivity, PlayActivity::class.java))
            }
        }
    }
}