package com.plcoding.kotlinchannels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.plcoding.kotlinchannels.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.btnShowSnackbar.setOnClickListener {
            viewModel.triggerEvent()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { event ->
                when(event) {
                    is MainViewModel.MyEvent.ErrorEvent -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}