package com.mqa.smartspeaker.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mqa.smartspeaker.MainActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.SetSkillFavorite
import com.mqa.smartspeaker.databinding.DialogRemoveBinding
import com.mqa.smartspeaker.ui.home.HomeFragment
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.mqa.smartspeaker.ui.pickFavoriteSkill.PickFavoriteActivity
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemoveSkillFavoriteDialog : DialogFragment() {
    private lateinit var _binding: DialogRemoveBinding
    private val binding get() = _binding!!

    private val addRemoveSkillInfoViewModel: AddRemoveSkillInfoViewModel by viewModels()

    companion object{
        const val ASAL = "asal"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogRemoveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.TVTitle.text = getString(R.string.hapus_skill)

        binding.btnYes.setOnClickListener {
            addRemoveSkillInfoViewModel.setSkillFavorite(
                "Bearer " + Prefs.getString(LoginActivity.TOKEN, ""),
                SetSkillFavorite(Prefs.getInt(WhatSkillActivity.SKILL_ID, 0), false)
            )
            Log.e("skill", Prefs.getInt(WhatSkillActivity.SKILL_ID, 0).toString())
            observeSetFavorite()
        }

        binding.btnNo.setOnClickListener {
            dialog?.cancel()
        }

        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

    fun observeSetFavorite() {
        with(binding) {
            addRemoveSkillInfoViewModel.setSkillFavorite.observe(requireActivity(), { results ->
                when (results) {
                    is Resource.Loading -> {
                        binding.PBDialog.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        Toast.makeText(requireActivity(), results.data?.message, Toast.LENGTH_LONG).show()
                        binding.PBDialog.visibility = View.GONE
                        dialog?.cancel()
                        if (Prefs.getString(ASAL, "")== "pick_favorite") {
                            (activity as PickFavoriteActivity).dialog.dismiss()
                            (activity as PickFavoriteActivity).finish()
                        }else{
                            (activity as MainActivity).openHome()
                        }
                    }
                    is Resource.Error -> {
                        binding.PBDialog.visibility = View.GONE
                        Toast.makeText(requireActivity(), results.message, Toast.LENGTH_LONG).show()
                        Log.e("error", "" + results.message.toString())
                    }
                }
            })
        }
    }

}
