package com.mqa.smartspeaker.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.SetSkillInfo
import com.mqa.smartspeaker.databinding.DialogSkillInfoBinding
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.mqa.smartspeaker.ui.skill.SkillViewModel
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SkillInfoDialog : DialogFragment() {
    private lateinit var _binding: DialogSkillInfoBinding
    private val binding get() = _binding!!
    private val skillInfoDialogViewModel: SkillInfoDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSkillInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.TVNameInfo.text = Prefs.getString(WhatSkillActivity.SKILL_NAME, "")
        binding.TVCategoryInfo.text = Prefs.getString(WhatSkillActivity.SKILL_CATEGORY, "")
        binding.TVDescInfo.text = Prefs.getString(WhatSkillActivity.SKILL_DESC, "")
        Glide.with(requireContext()).load("${requireContext().getString(R.string.base_url)}images/banner_skills/${Prefs.getString(WhatSkillActivity.SKILL_IMAGE, "")}").into(binding.IVInfo)

        val show = Prefs.getBoolean("skillInfo", false)
        binding.CBDontShow.apply {
            isChecked = !show
            setOnCheckedChangeListener { buttonView, isChecked ->
                skillInfoDialogViewModel.setSkillInfoState("Bearer " + Prefs.getString(LoginActivity.TOKEN, ""),
                    SetSkillInfo(Prefs.getInt(WhatSkillActivity.SKILL_ID, 0), isChecked))
                observeData()
            }
        }

        binding.IVClose.setOnClickListener {
            dialog?.cancel()
        }
        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

    private fun observeData() {
        with(binding) {
            skillInfoDialogViewModel.setSkillInfoState.observe(requireActivity(), { results ->
                Log.e("result0", results.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBSkillInfo.visibility = View.VISIBLE
                        binding.CBDontShow.isEnabled = false
                    }
                    is Resource.Success -> {
                        binding.CBDontShow.isEnabled = true
                        val data = results.data
                        Log.e("results", "" + data?.message)
                        Toast.makeText(requireContext(),data?.message,Toast.LENGTH_SHORT).show()
                        binding.PBSkillInfo.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.CBDontShow.isEnabled = true
                        binding.PBSkillInfo.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
//                        showError(results.message.toString())
                    }
                }
            })
        }
    }
}