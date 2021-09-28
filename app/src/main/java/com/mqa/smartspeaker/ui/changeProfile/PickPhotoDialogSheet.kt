package com.mqa.smartspeaker.ui.changeProfile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.AvatarResponse
import com.mqa.smartspeaker.core.data.source.remote.response.Skills
import com.mqa.smartspeaker.core.ui.SkillAdapter
import com.mqa.smartspeaker.core.utils.SpacesItemDecoration
import com.mqa.smartspeaker.databinding.DialogPickImageBinding
import com.mqa.smartspeaker.databinding.FrameAddScheduleBinding
import com.mqa.smartspeaker.databinding.FrameAvatarBinding
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.mqa.smartspeaker.ui.pairing.PairingActivity
import com.mqa.smartspeaker.ui.skill.SkillViewModel
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickPhotoDialogSheet : BottomSheetDialogFragment() {
    private lateinit var _binding: FrameAvatarBinding
    private val binding get() = _binding
    private val pickPhotoViewModel: PickPhotoViewModel by viewModels()
    lateinit var adapter: AvatarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrameAvatarBinding.inflate(inflater, container, false)


//        binding.LLCameraPick.setOnClickListener {
//            (activity as ChangeProfileActivity).openCamera()
//        }
//
//        binding.LLGalleryPick.setOnClickListener {
//            (activity as ChangeProfileActivity).openGallery()
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AvatarAdapter(requireActivity())
        binding.RVAvatar.adapter = adapter
        binding.RVAvatar.layoutManager = GridLayoutManager(context, 2)
//        binding.RVAvatar.layoutManager =LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val itemDecoration: SpacesItemDecoration = SpacesItemDecoration(requireContext(), R.dimen.list)
        binding.RVAvatar.addItemDecoration(itemDecoration)
        pickPhotoViewModel.getAvatarList("Bearer " + Prefs.getString(LoginActivity.TOKEN, ""))
        observeData()
    }

    private fun observeData() {
        with(binding) {
            pickPhotoViewModel.getAvatarList.observe(requireActivity(), { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.shimmerAvatar.startShimmer()
                    }
                    is Resource.Success -> {
                        val data = results.data
                        for (result in data!!) {
                            Log.e("result1 dalam", result.image)
                        }
                        adapter.data = data as ArrayList<AvatarResponse>
                        adapter.notifyDataSetChanged()
                        binding.shimmerAvatar.visibility= GONE
                    }
                    is Resource.Error -> {
                        binding.shimmerAvatar.visibility= GONE
                        Log.e("error", "" + results.message.toString())
                        Toast.makeText(requireContext(), results.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}