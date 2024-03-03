package com.example.hw_4_1.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.hw_4_1.R
import com.example.hw_4_1.databinding.FragmentOnBoardingBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var adapter: OnboardingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        tabLayoutLogic()
    }

    private fun setup() {
        adapter = OnboardingAdapter(fm = childFragmentManager, lifecycle)
        binding.vpOnboarding.adapter = adapter
        TabLayoutMediator(binding.tbOnboarding, binding.vpOnboarding) { _, _ -> }.attach()
    }

    private fun tabLayoutLogic() {
        var currentPage = 0
        val viewPager: ViewPager2 = binding.vpOnboarding

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
                binding.btnNext.text = if (position == 2) "Finish" else "Next"
            }
        })

        binding.btnNext.setOnClickListener {
            if (currentPage == 2) {
                findNavController().navigate(R.id.navigation_home)
            } else {
                viewPager.setCurrentItem(currentPage + 1,true)
            }
        }

        binding.btnSkip.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
    }
}