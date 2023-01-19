package com.example.submissionfundamentalretrofit.ui.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.example.submissionfundamentalretrofit.R
import com.example.submissionfundamentalretrofit.databinding.FragmentSettingBinding
import com.example.submissionfundamentalretrofit.ui.FactoryViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null

    private val binding get() = _binding!!
    private val viewModel: SettingViewModel by viewModels {factory}
    private lateinit var factory: FactoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        factory = FactoryViewModel.getInstance(requireActivity())

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val switchTheme: SwitchMaterial = binding.switchMaterial

        viewModel.getThemeSetting().observe(requireActivity()){ isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.imgSwitchMode.setImageResource(R.drawable.ic_baseline_dark_mode_24)
                switchTheme.isChecked = true
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.imgSwitchMode.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener{ _: CompoundButton?, isCheck: Boolean->
            viewModel.saveThemeSetting(isCheck)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}