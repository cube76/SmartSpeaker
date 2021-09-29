package com.mqa.smartspeaker.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.source.remote.response.Skills
import com.mqa.smartspeaker.ui.pickFavoriteSkill.PickFavoriteActivity
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity
import com.pixplicity.easyprefs.library.Prefs


class FavoriteSkillAdapter(fragments: HomeFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: ArrayList<Skills> = arrayListOf()
    var string: ArrayList<String> = arrayListOf()
    lateinit var context: Context
    var state: Boolean = false
    var fragment = fragments

    companion object {
        private const val ITEM_FOOTER = 0
        private const val ITEM_MENU = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < data.size) {
            ITEM_MENU
        } else {
            ITEM_FOOTER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_MENU -> {
                val holder = ViewHolderMain(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_skill_favorite, parent, false)
                )
                holder.itemView.setOnClickListener {
                    val intent = Intent(it.context, WhatSkillActivity::class.java)
                    intent.putExtra(WhatSkillActivity.SKILL_NAME, data[holder.bindingAdapterPosition].name)
                    it.context.startActivity(intent)
                    Prefs.putInt(WhatSkillActivity.SKILL_ID, data[holder.bindingAdapterPosition].id)
                    Prefs.putString(WhatSkillActivity.SKILL_NAME, data[holder.bindingAdapterPosition].name)
                    Prefs.putString(WhatSkillActivity.SKILL_CATEGORY, data[holder.bindingAdapterPosition].category)
                    Prefs.putString(WhatSkillActivity.SKILL_DESC, data[holder.bindingAdapterPosition].description)
                    Prefs.putString(WhatSkillActivity.SKILL_IMAGE, data[holder.bindingAdapterPosition].image)
                }
                holder.ivRemove.setOnClickListener {
                    fragment.removeFavoriteSkill()
                    Prefs.putInt(WhatSkillActivity.SKILL_ID, data[holder.bindingAdapterPosition].id)
                }
                return holder
            }
            ITEM_FOOTER -> {
                val holder = ViewFooterHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_add_favorite_skill, parent, false)
                )
                holder.itemView.setOnClickListener {
                    val intent = Intent(it.context, PickFavoriteActivity::class.java)
                    it.context.startActivity(intent)
                }
                return holder
            }
            else -> {
                throw IllegalArgumentException("Undefined view type")
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size + string.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderMain) {
            val bean = data[position]
            holder.tvName.text = bean.name
            Glide.with(context)
                .load("${context.getString(R.string.base_url)}images/banner_skills/${bean.image}")
                .into(holder.ivSkill)

            if (state){
                holder.ivRemove.visibility = View.VISIBLE
            }else{
                holder.ivRemove.visibility = View.GONE
            }
        }
    }

    class ViewHolderMain(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.TV_skill_title)
        val ivSkill = itemView.findViewById<ImageView>(R.id.IV_skill_image)
        val ivRemove = itemView.findViewById<ImageView>(R.id.IV_remove)
    }
    class ViewFooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}