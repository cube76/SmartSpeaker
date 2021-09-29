package com.mqa.smartspeaker.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.SetSkillFavorite
import com.mqa.smartspeaker.core.data.source.remote.response.Skills
import com.mqa.smartspeaker.databinding.FragmentHomeBinding
import com.mqa.smartspeaker.ui.detailSmartSpeaker.DetailSmartSpeakerActivity
import com.mqa.smartspeaker.ui.dialog.RemoveSkillFavoriteDialog
import com.mqa.smartspeaker.ui.dialog.RemoveSkillFavoriteDialog.Companion.ASAL
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: FavoriteSkillAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    var item: ArrayList<String> = arrayListOf()
    var editState: Boolean = false
    val dialog= RemoveSkillFavoriteDialog()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.RVSkillFav.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        item.add("add favorite")
        adapter = FavoriteSkillAdapter(this@HomeFragment)
        binding.RVSkillFav.adapter = adapter
        binding.refreshFavorite.setOnRefreshListener {
            getList()
        }
        binding.TVEdit.setOnClickListener {
            if (editState){
                editState = false
                adapter.state = false
            }else{
                editState = true
                Prefs.putBoolean("edit", true)
                adapter.state = true
            }
            getList()
        }

        getList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includeAddSmartSpeaker.root.setOnClickListener {
            val i = Intent(context, DetailSmartSpeakerActivity::class.java)
            context?.startActivity(i)
        }
    }

    private fun getList(){
        homeViewModel.getListSkillFavourite("Bearer " + Prefs.getString(LoginActivity.TOKEN, ""))
        observeData()
    }

    private fun observeData() {
        with(binding) {
            homeViewModel.getListSkillFavourite.observe(requireActivity(), { results ->
                Log.e("result0", results.data.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.refreshFavorite.isRefreshing = true
                    }
                    is Resource.Success -> {
                        val data = results.data
                        adapter.data = data as ArrayList<Skills>
                        adapter.context = requireContext()
                        adapter.string = item
                        adapter.notifyDataSetChanged()
                        binding.refreshFavorite.isRefreshing = false
                    }
                    is Resource.Error -> {
                        binding.refreshFavorite.isRefreshing = false
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

    fun homeViewModel(skillId: Int) {
        homeViewModel.setSkillFavorite(
            "Bearer " + Prefs.getString(LoginActivity.TOKEN, ""),
            SetSkillFavorite(skillId, true)
        )
        with(binding) {
            homeViewModel.setSkillFavorite.observe(requireActivity(), { results ->
                Log.e("result cek", results.data.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.refreshFavorite.isRefreshing = true
                    }
                    is Resource.Success -> {
                        Toast.makeText(requireActivity(), results.data?.message, Toast.LENGTH_LONG).show()
                        binding.refreshFavorite.isRefreshing = false
                        getList()
                    }
                    is Resource.Error -> {
                        binding.refreshFavorite.isRefreshing = false
                        Toast.makeText(requireActivity(), results.message, Toast.LENGTH_LONG).show()
                        Log.e("error", "" + results.message.toString())
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getListSkillFavourite("Bearer " + Prefs.getString(LoginActivity.TOKEN, ""))
        observeData()
    }

    fun removeFavoriteSkill(){
        dialog.show(childFragmentManager, tag)
        Prefs.putString(ASAL, "home")
    }

}