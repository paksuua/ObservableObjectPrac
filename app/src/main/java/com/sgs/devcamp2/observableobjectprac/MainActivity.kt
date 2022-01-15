package com.sgs.devcamp2.observableobjectprac

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.sgs.devcamp2.observableobjectprac.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLivedata.setOnClickListener {
            viewModel.triggerLiveData()
        }

        binding.btnStateflow.setOnClickListener {
            viewModel.triggerStateFlow()
        }

        // 다시 onCreate 되면 ui 초기화
        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.tvFlow.text = it
                }
            }
        }

        binding.btnSharedlow.setOnClickListener {
            viewModel.triggerSharedFlow()
        }
        subscribeToObserves()
    }

    private fun subscribeToObserves() {
        viewModel.liveData.observe(this) {
            binding.tvLivedata.text = it
        }

        lifecycleScope.launchWhenStarted {
            // StateFlow
//            viewModel.stateFlow.collectLatest {
//                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
//            }

            // SharedFlow
            viewModel.sharedFlow.collectLatest {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
