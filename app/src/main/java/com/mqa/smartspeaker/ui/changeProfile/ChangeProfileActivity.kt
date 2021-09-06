package com.mqa.smartspeaker.ui.changeProfile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mqa.smartspeaker.BuildConfig.BASE_URL
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.UpdateProfileRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegularResponse
import com.mqa.smartspeaker.core.utils.encodeImage
import com.mqa.smartspeaker.databinding.ActivityChangeProfileBinding
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream

@AndroidEntryPoint
class ChangeProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileBinding

    val REQUEST_PERMISSION = 100
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_PICK_IMAGE = 2
    private val pickPhotoDialogSheet = PickPhotoDialogSheet()
    private val changeProfileViewModel: ChangeProfileViewModel by viewModels()

    var encodedImage: String = ""
    private val id = Prefs.getInt(LoginActivity.USER_ID, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include6.TVTitle.text = "Profile Saya"
        val email:String = intent.getStringExtra("email").toString()
        val firstName:String = intent.getStringExtra("firstName").toString()
        val lastName:String = intent.getStringExtra("lastName").toString()
        val profileImage:String = intent.getStringExtra("profileImage").toString()
        Log.e("email", firstName)
        binding.TVEmail.setText(email)
        binding.TVFirstName.setText(firstName)
        binding.TVLastName.setText(lastName)
        Glide.with(this).load(profileImage).diskCacheStrategy(DiskCacheStrategy.NONE )
            .skipMemoryCache(true).into(binding.IVProfileImage)

        binding.LLEditPhoto.setOnClickListener {
            pickPhotoDialogSheet.show(supportFragmentManager, "pick_photo")
        }

        binding.btnSaveProfile.setOnClickListener {
            val data =
                UpdateProfileRequest(
                    id,
                    binding.TVFirstName.text.toString(),
                    binding.TVLastName.text.toString(),
                    encodedImage
                )

            changeProfileViewModel.postUpdateProfile("Bearer "+Prefs.getString(LoginActivity.TOKEN, ""),data)
            observeData()

        }
    }

    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION
            )
        }
    }

    fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
        pickPhotoDialogSheet.dismiss()
    }

    fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        }
        pickPhotoDialogSheet.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.extras?.get("data") as Bitmap
                binding.IVProfileImage.setImageBitmap(bitmap)
                encodedImage = encodeImage(bitmap)!!
            } else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.data
                binding.IVProfileImage.setImageURI(uri)
                val imageStream: InputStream? = contentResolver.openInputStream(uri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                encodedImage = encodeImage(selectedImage)!!
            }
        }
    }

    private fun observeData() {
        with(binding) {
            changeProfileViewModel.postUpdateProfile.observe(this@ChangeProfileActivity, { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> binding.PBProfile.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.PBProfile.visibility = View.GONE

                        val result = results.data
                        Log.e("result1", result.toString())
                        if (result != null) {
                            showSuccess(result)
                        }
                    }
                    is Resource.Error -> {
                        binding.PBProfile.visibility = View.GONE
                        showError(results.message.toString())
                    }
                }

            })
        }
    }

    private fun showSuccess(result: RegularResponse) {
        Log.d("last result", result.toString())
        finish()
    }

    private fun showError(errorMsg: String) {
        Log.d("errorMsg result", errorMsg)
        binding.TVError.visibility = View.VISIBLE
        binding.TVError.text = errorMsg
    }
}