package com.example.submissionfundamentalretrofit.ui.fragment.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.submissionfundamentalretrofit.adapter.ListAdapter
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem
import com.example.submissionfundamentalretrofit.databinding.FragmentHomeBinding
import com.example.submissionfundamentalretrofit.ui.FactoryViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Query
import com.example.submissionfundamentalretrofit.R
import com.example.submissionfundamentalretrofit.ui.activity.detail.DetailActivity
import com.example.submissionfundamentalretrofit.utils.Injection

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding?= null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {factory}
    private lateinit var factory: FactoryViewModel

    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        factory = getInstance(requireActivity())
        adapter = ListAdapter()
        adapter.setOnItemClickCallBack(object: ListAdapter.OnItemClickCallback{
            override fun onItemClicked(userResponseItem: UserResponseItem) {
                onSelectedUser(userResponseItem)
            }
        })

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
            searchView.queryHint =getString(R.string.search_here)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchUser()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return false
                }
            })
        }
        viewModel.list.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
        viewModel.toastMsg.observe(requireActivity()){ message: String ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
        return root
    }

    private fun searchUser(){
        binding.apply {
            val query = searchView.query.toString()
            viewModel.isLoading.observe(this@HomeFragment){
                showLoading(it)
            }
            viewModel.searchUser(query)
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressCircular.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun  onSelectedUser(userResponseItem: UserResponseItem){
        val intentExtra = Intent(activity, DetailActivity::class.java)
        intentExtra.putExtra(DetailActivity.EXTRA_USER, userResponseItem.login)
        startActivity(intentExtra)
    }

    companion object{
        @Volatile
        private var instance: FactoryViewModel? = null
        fun getInstance(context: Context): FactoryViewModel = instance ?: synchronized(this){
            instance ?: FactoryViewModel(Injection.provideRepository(context))
        }.also {
            instance = it
        }
    }
}