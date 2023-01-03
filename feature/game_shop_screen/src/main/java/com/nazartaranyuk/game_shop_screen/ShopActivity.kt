package com.nazartaranyuk.game_shop_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.nazartaranyuk.game_shop_screen.databinding.ActivityShopBinding
import com.nazartaranyuk.game_shop_screen.intentions.Intentions
import com.nazartaranyuk.game_shop_screen.states.States
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShopActivity : AppCompatActivity() {

    private var binding: ActivityShopBinding? = null

    private val viewModel by viewModel<ShopActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        binding?.let { binding ->
            setContentView(binding.root)
            viewModel.sendIntent(Intentions.UpdateCoinsBalanceIntent)
            lifecycleScope.launch {
                viewModel.stateFlow.collect { state ->
                    when(state) {
                        is States.EmptyState -> {
                            binding.tvCoinsBalance.text = "Coins balance: 0"
                        }
                        is States.UpdatedCoinsBalance -> {
                            binding.tvCoinsBalance.text = "Coins balance: ${state.coins}"
                        }
                    }
                }
            }
            setupListeners()
        }
    }

    private fun setupListeners() {
        binding?.let { binding ->
            binding.btnBuy5.setOnClickListener {
                viewModel.sendIntent(Intentions.BuyCoinsIntent(5))
            }
            binding.btnBuy10.setOnClickListener {
                viewModel.sendIntent(Intentions.BuyCoinsIntent(10))
            }
            binding.btnBuy15.setOnClickListener {
                viewModel.sendIntent(Intentions.BuyCoinsIntent(15))
            }
            binding.btnBuy20.setOnClickListener {
                viewModel.sendIntent(Intentions.BuyCoinsIntent(20))
            }
            binding.btnBuy25.setOnClickListener {
                viewModel.sendIntent(Intentions.BuyCoinsIntent(25))
            }
        }
    }
}