package com.example.hw_4_1.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.hw_4_1.R
import com.example.hw_4_1.databinding.FragmentProfileBinding
import com.example.hw_4_1.prefs.Prefs

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val prefs: Prefs by lazy {
        Prefs(requireContext())
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try {
            binding.imgProfile.setImageURI(galleryUri)
            prefs.saveImage(galleryUri.toString())
            prefs.getImage()?.let { url ->
                Glide.with(binding.imgProfile).load(url).into(binding.imgProfile)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs.getImage()?.let { url ->
            Glide.with(binding.imgProfile).load(url).into(binding.imgProfile)
        }
        prefs.getName()?.let {
            binding.tvUsername.text = it
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.editUsernameFragment)
        }
        binding.imgProfile.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
    }
}