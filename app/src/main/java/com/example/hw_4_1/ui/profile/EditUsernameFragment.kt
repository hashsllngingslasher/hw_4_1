package com.example.hw_4_1.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hw_4_1.R
import com.example.hw_4_1.databinding.FragmentEditUsernameBinding
import com.example.hw_4_1.ui.data.Prefs

class EditUsernameFragment : Fragment() {

    private lateinit var binding: FragmentEditUsernameBinding
    private val prefs: Prefs by lazy {
        Prefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditUsernameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            val userName = binding.etUsername.text.toString()
            prefs.saveName(userName)
            findNavController().navigateUp()
        }
    }
}