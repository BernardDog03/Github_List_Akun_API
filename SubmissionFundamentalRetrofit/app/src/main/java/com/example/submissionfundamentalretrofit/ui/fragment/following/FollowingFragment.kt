package com.example.submissionfundamentalretrofit.ui.fragment.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamentalretrofit.R
import com.example.submissionfundamentalretrofit.adapter.ListAdapter
import com.example.submissionfundamentalretrofit.databinding.FragmentFollowersBinding
import com.example.submissionfundamentalretrofit.databinding.FragmentFollowingBinding
import com.example.submissionfundamentalretrofit.ui.fragment.followers.FollowersViewModel

class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var  _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var name: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        name = args?.getString(ARG_NAME).toString()
        _binding = FragmentFollowingBinding.bind(view)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        adapter = ListAdapter()
        binding.apply {
            rvFollowingUser.layoutManager = LinearLayoutManager(activity)
            rvFollowingUser.adapter = adapter
        }
        binding.progressBarFollowing.visibility = View.VISIBLE
        viewModel.setFollowing(name)
        viewModel.list.observe(viewLifecycleOwner){
            if (it != null){
                adapter.setData(it)
                binding.progressBarFollowing.visibility = View.GONE
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
