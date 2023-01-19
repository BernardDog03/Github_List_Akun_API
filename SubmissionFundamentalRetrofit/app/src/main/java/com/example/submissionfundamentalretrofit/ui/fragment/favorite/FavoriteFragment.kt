package com.example.submissionfundamentalretrofit.ui.fragment.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamentalretrofit.adapter.FavAdapter
import com.example.submissionfundamentalretrofit.adapter.ListAdapter
import com.example.submissionfundamentalretrofit.data.local.UserEntity
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem
import com.example.submissionfundamentalretrofit.databinding.FragmentFavoriteBinding
import com.example.submissionfundamentalretrofit.ui.FactoryViewModel
import com.example.submissionfundamentalretrofit.ui.activity.detail.DetailActivity
import com.example.submissionfundamentalretrofit.ui.fragment.home.HomeFragment

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels {factory}
    private lateinit var factory: FactoryViewModel

    private lateinit var adapter: FavAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        factory = HomeFragment.getInstance(requireActivity())
        adapter = FavAdapter()
        adapter.setOnItemClickCallBack(object: FavAdapter.OnItemClickCallback{
            override fun onItemClicked(userEntity: UserEntity) {
                onSelectedUser(userEntity)
            }
        })

        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(context)
            rvFavorite.adapter = adapter
            viewModel.showFavorite().observe(requireActivity()){
                adapter.setData(it as ArrayList<UserEntity>)
            }
        }

        return root
    }

    private fun onSelectedUser(userEntity: UserEntity){
        val intentFavData = Intent(activity, DetailActivity::class.java)
        intentFavData.putExtra(DetailActivity.EXTRA_USER, userEntity.username)
        startActivity(intentFavData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}