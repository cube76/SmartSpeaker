package com.mqa.smartspeaker.core.ui

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
import com.mqa.smartspeaker.MainActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.source.remote.response.Skills
import com.mqa.smartspeaker.ui.pairing.PairingActivity
import com.mqa.smartspeaker.ui.pickFavoriteSkill.PickFavoriteActivity
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity.Companion.SKILL_CATEGORY
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity.Companion.SKILL_DESC
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity.Companion.SKILL_ID
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity.Companion.SKILL_IMAGE
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity.Companion.SKILL_NAME
import com.pixplicity.easyprefs.library.Prefs

class SkillAdapter(context: Context, val type: Int) : RecyclerView.Adapter<SkillAdapter.ViewHolder>() {
    var data: ArrayList<Skills> = arrayListOf()
    private var mContext = context

    companion object {
        const val FULL_LIST = 100
        const val CATEGORY_LIST = 200
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (type) {
            FULL_LIST -> {
                val holder = ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
                )
                holder.itemView.setOnClickListener {
                    val intent = Intent(it.context, WhatSkillActivity::class.java)
                    intent.putExtra(SKILL_NAME, data[holder.bindingAdapterPosition].name)
                    it.context.startActivity(intent)
                    Prefs.putInt(SKILL_ID, data[holder.bindingAdapterPosition].id)
                    Prefs.putString(SKILL_NAME, data[holder.bindingAdapterPosition].name)
                    Prefs.putString(SKILL_CATEGORY, data[holder.bindingAdapterPosition].category)
                    Prefs.putString(SKILL_DESC, data[holder.bindingAdapterPosition].description)
                    Prefs.putString(SKILL_IMAGE, data[holder.bindingAdapterPosition].image)
                }
                return holder
            }
            CATEGORY_LIST -> {
                val holder = ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_pick_favorite_skill, parent, false)
                )
                holder.itemView.setOnClickListener {
                    (parent.context as PickFavoriteActivity).observeSetFavorite(data[holder.bindingAdapterPosition].id, true)
                }
                return holder
            }
            else -> {
                throw IllegalArgumentException("Undefined view type")
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = data[position]
        Log.e("data", data.toString())
        holder.tvName.text = bean.name
        Glide.with(mContext)
            .load("${mContext.getString(R.string.base_url)}images/banner_skills/${bean.image}")
            .into(holder.ivSkill);
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.TV_skill_title)
        val ivSkill = itemView.findViewById<ImageView>(R.id.IV_skill_image)
    }
}