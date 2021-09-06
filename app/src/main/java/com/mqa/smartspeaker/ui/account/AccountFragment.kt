package com.mqa.smartspeaker.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mqa.smartspeaker.MainActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.LoginResponse
import com.mqa.smartspeaker.databinding.FragmentAccountBinding
import com.mqa.smartspeaker.databinding.FragmentDeviceBinding
import com.mqa.smartspeaker.ui.changePassword.ChangePasswordActivity
import com.mqa.smartspeaker.ui.changeProfile.ChangeProfileActivity
import com.mqa.smartspeaker.ui.dialog.SuccessDialogForgetPass
import com.mqa.smartspeaker.ui.intro.Intro2Activity
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.mqa.smartspeaker.ui.login.LoginActivity.Companion.EMAIL
import com.mqa.smartspeaker.ui.login.LoginViewModel
import com.mqa.smartspeaker.ui.register.RegisterActivity
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.android.user.api.ILoginCallback
import com.tuya.smart.android.user.bean.User
import com.tuya.smart.home.sdk.TuyaHomeSdk
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel: AccountViewModel by viewModels()
    var email = ""
    var firstName = ""
    var lastName = ""
    var profileImage = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        accountViewModel.getUser("Bearer "+Prefs.getString(LoginActivity.TOKEN, ""))
        observeDataUser()
        
        binding.groupEdit.setAllOnClickListener(View.OnClickListener {
            val intent = Intent(activity, ChangeProfileActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("firstName", firstName)
            intent.putExtra("lastName", lastName)
            intent.putExtra("profileImage", profileImage)
            startActivity(intent)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.LLChangePasswrod.setOnClickListener {
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            Prefs.clear()
            activity?.finish()
        }
    }

    fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }

    private fun observeDataUser() {
        with(binding) {
            accountViewModel.getUser.observe(requireActivity(), { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBAccount.bringToFront()
                        binding.PBAccount.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val result = results.data
                        Log.e("result1", result?.user.toString())
                        showSuccess(result!!)
                    }
                    is Resource.Error -> {
                        binding.PBAccount.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
                    }
                }
            })
        }
    }

    private fun showSuccess(result: com.mqa.smartspeaker.core.data.source.remote.response.User) {
//        Prefs.putString(LoginActivity.TOKEN, result.token)
        email = result.user.email
        firstName = result.user.first_name
        lastName = result.user.last_name
        profileImage = result.user.profile_image

        binding.TVUserEmail.text = email
        binding.TVUserName.text = firstName + " " + lastName
        Glide.with(this).load(profileImage).diskCacheStrategy(DiskCacheStrategy.NONE )
            .skipMemoryCache(true).into(binding.IVProfileImage)
        binding.PBAccount.visibility = View.GONE
    }

    private fun showError(errorMsg: String) {
        Log.e("last result", errorMsg)
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
    }

}