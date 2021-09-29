package com.mqa.smartspeaker.ui.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.SetSkillFavorite
import com.mqa.smartspeaker.core.data.source.remote.request.SkillInfoState
import com.mqa.smartspeaker.databinding.DialogSkillInfoBinding
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.mqa.smartspeaker.ui.pickFavoriteSkill.PickFavoriteActivity
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddRemoveSkillInfoDialog : DialogFragment() {
    private lateinit var _binding: DialogSkillInfoBinding
    private val binding get() = _binding!!
    private val addRemoveSkillInfoViewModel: AddRemoveSkillInfoViewModel by viewModels()
    var state: Boolean = false
    var skillId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSkillInfoBinding.inflate(inflater, container, false)
        binding.LLSetFavorite.visibility = VISIBLE
        state = Prefs.getBoolean("state_favorite", false)
        if (state) {
            binding.IVSetFavorite.setImageResource(R.drawable.ic_baseline_remove_red)
            binding.TVSetFavorite.text = context?.getString(R.string.remove_favorite)
        } else {
            binding.IVSetFavorite.setImageResource(R.drawable.ic_baseline_add_yellow)
            binding.TVSetFavorite.text = context?.getString(R.string.add_favorite)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        skillId = Prefs.getInt(WhatSkillActivity.SKILL_ID, 0)
        Log.e("cek state", state.toString())

        binding.TVNameInfo.text = Prefs.getString(WhatSkillActivity.SKILL_NAME, "")
        binding.TVCategoryInfo.text = Prefs.getString(WhatSkillActivity.SKILL_CATEGORY, "")
        binding.TVDescInfo.text = Prefs.getString(WhatSkillActivity.SKILL_DESC, "")
        Glide.with(requireContext()).load(
            "${requireContext().getString(R.string.base_url)}images/banner_skills/${
                Prefs.getString(
                    WhatSkillActivity.SKILL_IMAGE, ""
                )
            }"
        ).into(binding.IVInfo)
        binding.CBDontShow.visibility = GONE

        binding.LLSetFavorite.setOnClickListener {
            if (!state) {
                addRemoveSkillInfoViewModel.setSkillFavorite(
                    "Bearer " + Prefs.getString(LoginActivity.TOKEN, ""),
                    SetSkillFavorite(skillId, true)
                )
                observeSetFavorite()
            }else{
                val dialog = RemoveSkillFavoriteDialog()
                dialog.show((activity as PickFavoriteActivity).supportFragmentManager,"remove_dialog")
            }
        }

        binding.IVClose.setOnClickListener {
            dialog?.cancel()
        }
        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

    fun observeSetFavorite() {
        with(binding) {
            addRemoveSkillInfoViewModel.setSkillFavorite.observe(requireActivity(), { results ->
                Log.e("result cek", results.data.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBSkillInfo.visibility = VISIBLE
                    }
                    is Resource.Success -> {
                        Toast.makeText(requireActivity(), results.data?.message, Toast.LENGTH_LONG).show()
                        dialog?.dismiss()
                        (activity as PickFavoriteActivity).finish()
                        binding.PBSkillInfo.visibility = GONE
                    }
                    is Resource.Error -> {
                        binding.PBSkillInfo.visibility = GONE
                        Toast.makeText(requireActivity(), results.message, Toast.LENGTH_LONG).show()
                        Log.e("error", "" + results.message.toString())
                    }
                }
            })
        }
    }

}