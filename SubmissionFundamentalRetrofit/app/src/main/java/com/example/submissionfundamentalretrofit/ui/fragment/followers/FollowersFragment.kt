package com.example.submissionfundamentalretrofit.ui.fragment.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamentalretrofit.R
import com.example.submissionfundamentalretrofit.adapter.ListAdapter
import com.example.submissionfundamentalretrofit.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private var  _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var name: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        name = args?.getString(ARG_NAME).toString()
        _binding = FragmentFollowersBinding.bind(view)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]
        adapter = ListAdapter()
        binding.apply {
            rvFollowersUser.layoutManager = LinearLayoutManager(activity)
            rvFollowersUser.adapter = adapter
        }
        binding.progressBarFollowers.visibility = View.VISIBLE
        viewModel.setFollower(name)
        viewModel.list.observe(viewLifecycleOwner){
            if (it != null){
                adapter.setData(it)
                binding.progressBarFollowers.visibility = View.GONE
            }
        }
        viewModel.toastMsg.observe(viewLifecycleOwner){ message: String? ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val ARG_NAME = "username"
    }
}
