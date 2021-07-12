package com.mqa.smartspeaker.ui.register

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var job: RegisterResponse
    private lateinit var checkBox: CheckBox
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val firstName = binding.TVFirstName.text.toString()
            val lastName = binding.TVLastName.text.toString()
            val email = binding.TVEmail.text.toString()
            val password = binding.TVPassword.text.toString()
            val passwordConfirmation = binding.TVPasswordConfirmation.text.toString()
            val data = RegisterRequest(firstName,lastName,email,password,passwordConfirmation)

            if (checkBox.isChecked) {
                registerViewModel.postRegister(data)
                observeData()
            }else{
                binding.TVError.visibility = View.VISIBLE
                binding.TVError.text = getString(R.string.TnC)
            }
        }

    }

    private fun observeData() {
        with(binding) {
            registerViewModel.postRegister.observe(this@RegisterActivity, { results ->
                Log.e("result0", results.message.toString())
                    when (results) {
                        is Resource.Loading -> binding.PBRegister.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.PBRegister.visibility = View.GONE

                            val result = results.data
                            Log.e("result1", result?.message.toString())
                            if (result != null) {
                                job = result
                                verification(job)
                            }
                        }
                        is Resource.Error -> {
                            binding.PBRegister.visibility = View.GONE
                            Log.e("error", ""+results.message.toString())
                            showError(results.message.toString())
//                            binding.content.viewError.root.visibility = View.VISIBLE
                        }
                    }

            })
        }
    }

    private fun verification(result: RegisterResponse){
        Log.e("last result", result.toString())
    }

    private fun showError(errorMsg: String){
        Log.e("last result", errorMsg)
        binding.TVError.visibility = View.VISIBLE
        binding.TVError.text = errorMsg
    }
}