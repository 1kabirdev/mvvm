package com.mvvm.ui.activity.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mvvm.data.api.RetrofitService
import com.mvvm.data.repository.MainRepository
import com.mvvm.data.factory.MyViewModelFactory
import com.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private val adapter = UsersAdapter()
    lateinit var binding: ActivityMainBinding
    lateinit var retrofitService: RetrofitService
    lateinit var mainRepository: MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)

        getViewModel(viewModel)

    }
    private fun init() {
        retrofitService = RetrofitService.getInstance()
        mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter
    }

    private fun getViewModel(viewModel: MainViewModel) {
        viewModel.movieList.observe(this) { user ->
            adapter.setUsers(user)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this) { show ->
            if (show) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        }

        viewModel.getAllMovies()
    }

}
