package com.mvvm.ui.activity.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mvvm.data.api.RetrofitService
import com.mvvm.data.factory.UserViewModelFactory
import com.mvvm.data.repository.UserRepository
import com.mvvm.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserBinding
    lateinit var login: String
    lateinit var retrofitService: RetrofitService
    lateinit var mainRepository: UserRepository
    lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arg = intent.extras
        login = arg?.get("LOGIN").toString()

        init()

        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(mainRepository)
        ).get(UserViewModel::class.java)

        viewModel.loading.observe(this) { show ->
            if (show) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

        viewModel.user.observe(this) { user ->
            binding.name.text = user.name
            Glide.with(this).load(user.avatar).into(binding.imageview)
        }

        viewModel.getUser(login)
    }

    private fun init() {
        retrofitService = RetrofitService.getInstance()
        mainRepository = UserRepository(retrofitService)
    }

    override fun onDestroy() {
        viewModel
        super.onDestroy()
    }

}