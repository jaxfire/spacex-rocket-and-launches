package com.jaxfire.spacexinfo.ui.rocket_info.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jaxfire.spacexinfo.R
import com.jaxfire.spacexinfo.data.network.response.LaunchResponse
import com.jaxfire.spacexinfo.internal.glide.GlideApp
import kotlinx.android.synthetic.main.launch_list_item.view.*


class LaunchListAdapter(
    val context: Context?,
    private var items: List<LaunchResponse>
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.launch_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMissionName?.text = items[position].missionName

        var launchDate = items[position].launchDateUtc
        launchDate = launchDate.indexOf('T').let { if (it == -1) launchDate else launchDate.substring(0, it) }
        holder.tvLaunchDate?.text = launchDate
        holder.ivMissionSuccess.setImageResource(if (items[position].launchSuccess) R.drawable.ic_check_blue_24dp else R.drawable.ic_cross_red_24dp)

        GlideApp.with(holder.container)
            .load(items[position].links.missionPatchSmall)
            .error(R.drawable.space_x_logo)
            .fallback(R.drawable.space_x_logo)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(holder.ivMissionPatch)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(it: List<LaunchResponse>) {
        items = it
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvMissionName = view.launch_list_item_text_view_mission_name
    val tvLaunchDate = view.launch_list_item_text_view_launch_date
    val ivMissionPatch = view.launch_list_item_image_view_mission_patch_image
    val ivMissionSuccess = view.launch_list_item_image_view_launch_success
    val container = view.launch_list_item_container
}