package com.nazartaranyuk.game_play_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.nazartaranyuk.game_play_screen.databinding.ActivityPlayBinding
import com.nazartaranyuk.game_play_screen.intentions.Intentions
import com.nazartaranyuk.game_play_screen.recycler.ItemsAdapter
import com.nazartaranyuk.game_play_screen.states.States
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayActivity : AppCompatActivity() {

    private var binding: ActivityPlayBinding? = null
    private val adapter by lazy {
        ItemsAdapter()
    }
    private val viewModel by viewModel<PlayActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        binding?.let { binding ->
            setContentView(binding.root)
            binding.rvList.adapter = adapter
            viewModel.sendIntent(Intentions.UpdateCoinsBalance)
            viewModel.sendIntent(Intentions.UpdateListIntent)
            viewModel.sendIntent(Intentions.UpdateLastWinIntent)
            val manager = object : GridLayoutManager(this, 3) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            binding.rvList.layoutManager = manager
            lifecycleScope.launch {
                viewModel.stateFlow.collect { state ->
                    when (state) {
                        is States.EmptyState -> {
                            binding.tvCoinsBalance.text = "Coins: 05"
                        }
                        is States.UpdatedCoinsState -> {
                            binding.tvCoinsBalance.text = "Coins: ${state.coins}"
                        }
                        is States.UpdatedListState -> {
                            adapter.updateList(state.list)
                        }
                        is States.UpdatedLastWinState -> {
                            binding.tvWin.text = "Your win is ${state.win}"
                            binding.tvLastWin.text = "Your last win is ${state.win}"
                        }
                        is States.ErrorState -> {
                            Toast.makeText(
                                this@PlayActivity,
                                "You don't have money!!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
            binding.btnSpin.setOnClickListener {
                viewModel.sendIntent(Intentions.SetWinIntent)
                viewModel.sendIntent(Intentions.DecreaseCoinsBalance)
                viewModel.sendIntent(Intentions.UpdateCoinsBalance)
                viewModel.sendIntent(Intentions.UpdateListIntent)
            }
        }
    }
}