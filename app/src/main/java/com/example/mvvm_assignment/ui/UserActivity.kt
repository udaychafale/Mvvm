package com.example.mvvm_assignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_assignment.adapter.UserAdapter
import com.example.mvvm_assignment.api.RetrofitBuilder
import com.example.mvvm_assignment.data.UserRepositoryImpl
import com.example.mvvm_assignment.databinding.ActivityUserBinding
import com.example.mvvm_assignment.model.User
import com.example.mvvm_assignment.utils.DefaultDispatcherProvider
import com.example.mvvm_assignment.utils.UiState
import com.example.mvvm_assignment.viewmodel.UserViewModel
import com.example.mvvm_assignment.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    private var _binding: ActivityUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.recyclerView.setLayoutManager(layoutManager)

        adapter = UserAdapter(userList)

        binding.recyclerView.adapter = userAdapter

    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success<*> -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data as List<User>)
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@UserActivity,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                UserRepositoryImpl(RetrofitBuilder.apiService),
                DefaultDispatcherProvider()
            )
        )[UserViewModel::class.java]
    }
}