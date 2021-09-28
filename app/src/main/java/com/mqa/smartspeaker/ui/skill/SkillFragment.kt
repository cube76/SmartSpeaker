package com.mqa.smartspeaker.ui.skill

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.Skills
import com.mqa.smartspeaker.core.ui.SkillAdapter
import com.mqa.smartspeaker.databinding.FragmentSkillBinding
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.mqa.smartspeaker.core.ui.SkillAdapter.Companion.FULL_LIST
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SkillFragment : Fragment() {

    private var _binding: FragmentSkillBinding? = null
    private val binding get() = _binding!!
    private val skillViewModel: SkillViewModel by viewModels()
    lateinit var adapter: SkillAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkillBinding.inflate(inflater, container, false)
            binding.RVSkill.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = SkillAdapter(requireContext(), FULL_LIST)
        binding.RVSkill.adapter = adapter
        skillViewModel.getListSkill("Bearer " + Prefs.getString(LoginActivity.TOKEN, ""))
        binding.refreshSkill.setOnRefreshListener {
            observeData()
        }

        observeData()
        return binding.root
    }

    private fun observeData() {
        with(binding) {
            skillViewModel.getListSkill.observe(requireActivity(), { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.refreshSkill.isRefreshing = true
                    }
                    is Resource.Success -> {
                        val data = results.data
                        for (result in data!!) {
                            Log.e("result1", result.name)
                        }
                        adapter.data = data as ArrayList<Skills>
                        adapter.notifyDataSetChanged()
                        binding.refreshSkill.isRefreshing = false
                    }
                    is Resource.Error -> {
                        binding.refreshSkill.isRefreshing = false
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
                    }
                }
            })
        }
    }

    private fun showError(errorMsg: String) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
    }

}