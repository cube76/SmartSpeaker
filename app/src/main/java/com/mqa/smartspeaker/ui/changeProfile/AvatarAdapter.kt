package com.mqa.smartspeaker.ui.changeProfile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.source.remote.response.AvatarResponse

class AvatarAdapter(context: Context) : RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {
    var data: ArrayList<AvatarResponse> = arrayListOf()
    var mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val holder = ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_avatar, parent, false)
        )
        holder.itemView.setOnClickListener {
            (mContext as ChangeProfileActivity).changeImage(
                data[holder.bindingAdapterPosition].image
            )
        }
        return holder
    }

    override fun getItemCount(): Int {
        Log.e("data size", data.size.toString())
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = data[position]
        Log.e("data ", data.toString())
        Glide.with(mContext)
            .load("${mContext.getString(R.string.base_url)}images/avatars_indi/${bean.image}")
            .into(holder.ivAvatar)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar = itemView.findViewById<ShapeableImageView>(R.id.IV_avatar)
    }
}