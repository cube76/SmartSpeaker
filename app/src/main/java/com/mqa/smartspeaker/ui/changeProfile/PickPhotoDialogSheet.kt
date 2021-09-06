package com.mqa.smartspeaker.ui.changeProfile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mqa.smartspeaker.databinding.DialogPickImageBinding
import com.mqa.smartspeaker.databinding.FrameAddScheduleBinding
import com.mqa.smartspeaker.ui.pairing.PairingActivity

class PickPhotoDialogSheet : BottomSheetDialogFragment() {
    private lateinit var _binding: DialogPickImageBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPickImageBinding.inflate(inflater, container, false)

        binding.LLCameraPick.setOnClickListener {
            (activity as ChangeProfileActivity).openCamera()
        }

        binding.LLGalleryPick.setOnClickListener {
            (activity as ChangeProfileActivity).openGallery()
        }

        return binding.root
    }
}