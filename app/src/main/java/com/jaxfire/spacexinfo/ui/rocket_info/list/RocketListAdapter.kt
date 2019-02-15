package com.jaxfire.spacexinfo.ui.rocket_info.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jaxfire.spacexinfo.R
import com.jaxfire.spacexinfo.data.network.response.RocketResponse
import com.jaxfire.spacexinfo.internal.RocketResponseDiffCallback
import kotlinx.android.synthetic.main.rocket_list_item.view.*


class RocketListAdapter(
    val context: Context?,
    private var rockets: List<RocketResponse>,
    private val clickListener: (RocketResponse) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    fun setData(newRockets: List<RocketResponse>) {

        val diffCallBack = RocketResponseDiffCallback(rockets, newRockets)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        rockets = newRockets
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.rocket_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName?.text = rockets[position].rocketName
        holder.tvCountry?.text = rockets[position].country
        holder.tvEngineCount?.text = rockets[position].engines.number.toString()
        holder.container.setOnClickListener {
            clickListener(rockets[position])
        }
    }

    override fun getItemCount(): Int {
        return rockets.size
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.rocket_list_item_text_view_name
    val tvCountry = view.rocket_list_item_text_view_country
    val tvEngineCount = view.rocket_list_item_text_view_engine_count
    val container = view.rocket_list_item_container
}