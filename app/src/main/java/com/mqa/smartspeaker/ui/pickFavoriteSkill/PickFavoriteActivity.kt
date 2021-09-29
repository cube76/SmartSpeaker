package com.mqa.smartspeaker.ui.pickFavoriteSkill

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.SetSkillFavorite
import com.mqa.smartspeaker.core.data.source.remote.request.SkillInfoState
import com.mqa.smartspeaker.core.data.source.remote.response.Skills
import com.mqa.smartspeaker.core.ui.SkillAdapter
import com.mqa.smartspeaker.core.ui.SkillAdapter.Companion.CATEGORY_LIST
import com.mqa.smartspeaker.databinding.ActivityPickFavoriteBinding
import com.mqa.smartspeaker.ui.dialog.AddRemoveSkillInfoDialog
import com.mqa.smartspeaker.ui.dialog.RemoveSkillFavoriteDialog
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.pixplicity.easyprefs.library.Prefs
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PickFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPickFavoriteBinding
    private val pickFavoriteViewModel: PickFavoriteViewModel by viewModels()
    lateinit var skillAdapter: SkillAdapter
    lateinit var kontenAdapter: SkillAdapter
    lateinit var islamiAdapter: SkillAdapter
    val dialog= AddRemoveSkillInfoDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        skillAdapter = SkillAdapter(applicationContext, CATEGORY_LIST)
        kontenAdapter = SkillAdapter(applicationContext, CATEGORY_LIST)
        islamiAdapter = SkillAdapter(applicationContext, CATEGORY_LIST)
        binding.RVAsisten.apply {
            layoutManager = LinearLayoutManager(this@PickFavoriteActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LayoutMarginDecoration(1, context.resources.getDimensionPixelSize(
                R.dimen.list)))
            adapter = skillAdapter
        }
        binding.RVKonten.apply {
            layoutManager = LinearLayoutManager(this@PickFavoriteActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LayoutMarginDecoration(1, context.resources.getDimensionPixelSize(
                R.dimen.list)))
            adapter = kontenAdapter
        }
        binding.RVIslami.apply {
            layoutManager = LinearLayoutManager(this@PickFavoriteActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LayoutMarginDecoration(1, context.resources.getDimensionPixelSize(
                R.dimen.list)))
            adapter = islamiAdapter
        }

        observeDataAsisten()
        observeDataKonten()
        observeDataIslami()
    }

    private fun observeDataAsisten() {
        pickFavoriteViewModel.getListSkillAsisten("Bearer " + Prefs.getString(LoginActivity.TOKEN, ""))
        with(binding) {
            pickFavoriteViewModel.getListSkillCategory.observe(this@PickFavoriteActivity, { results ->
                Log.e("result0", results.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.shimmerAsisten.startShimmer()
                    }
                    is Resource.Success -> {
                        val data = results.data
                        for (result in data!!) {
                            Log.e("result1", result.name)
                        }
                        skillAdapter.data = data as ArrayList<Skills>
                        skillAdapter.notifyDataSetChanged()
                        binding.shimmerAsisten.visibility = GONE
                    }
                    is Resource.Error -> {
                        binding.shimmerAsisten.visibility = GONE
                        Log.e("error", "" + results.message.toString())
                    }
                }
            })
        }
    }

    private fun observeDataKonten() {
        pickFavoriteViewModel.getListSkillKonten("Bearer " + Prefs.getString(LoginActivity.TOKEN, ""))
        with(binding) {
            pickFavoriteViewModel.getListSkillCategory.observe(this@PickFavoriteActivity, { results ->
                Log.e("result0 konten", results.data.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.shimmerKonten.startShimmer()
                    }
                    is Resource.Success -> {
                        val data = results.data
                        for (result in data!!) {
                            Log.e("result konten", result.name)
                        }
                        kontenAdapter.data = data as ArrayList<Skills>
                        kontenAdapter.notifyDataSetChanged()
                        binding.shimmerKonten.visibility = GONE
                    }
                    is Resource.Error -> {
                        binding.shimmerKonten.visibility = GONE
                        Log.e("error", "" + results.message.toString())
                    }
                }
            })
        }
    }

    private fun observeDataIslami() {
        pickFavoriteViewModel.getListSkillIslami("Bearer " + Prefs.getString(LoginActivity.TOKEN, ""))
        with(binding) {
            pickFavoriteViewModel.getListSkillCategory.observe(this@PickFavoriteActivity, { results ->
                Log.e("result0 konten", results.data.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.shimmerIslami.startShimmer()
                    }
                    is Resource.Success -> {
                        val data = results.data
                        for (result in data!!) {
                            Log.e("result konten", result.name)
                        }
                        islamiAdapter.data = data as ArrayList<Skills>
                        islamiAdapter.notifyDataSetChanged()
                        binding.shimmerIslami.visibility = GONE
                    }
                    is Resource.Error -> {
                        binding.shimmerIslami.visibility = GONE
                        Log.e("error", "" + results.message.toString())
                    }
                }
            })
        }
    }

    fun observeGetFavorite(skillId: Int) {
        pickFavoriteViewModel.getSkillFavoriteState(
            "Bearer " + Prefs.getString(LoginActivity.TOKEN, ""),
            SkillInfoState(skillId)
        )
        with(binding) {
            pickFavoriteViewModel.getSkillFavoriteState.observe(this@PickFavoriteActivity, { results ->
                Log.e(" cek id", skillId.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBPickSkill.visibility = VISIBLE
                    }
                    is Resource.Success -> {
                        binding.PBPickSkill.visibility = GONE
                        Prefs.putBoolean("state_favorite", results.data?.favorite!!)

                        dialog.show(this@PickFavoriteActivity.supportFragmentManager, "dialog_favorite")
                    }
                    is Resource.Error -> {
                        binding.PBPickSkill.visibility = GONE
//                        Toast.makeText(requireContext(), results.message, Toast.LENGTH_LONG)
//                            .show()
                        Log.e("error", "" + results.message.toString())
                    }
                }
            })
        }
    }

}