package com.jaxfire.spacexinfo.ui.rocket_info.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaxfire.spacexinfo.R
import com.jaxfire.spacexinfo.data.network.response.RocketResponse
import kotlinx.android.synthetic.main.rocket_list_item.view.*


class RocketListAdapter(
    val context: Context?,
    private val items: List<RocketResponse>,
    private val clickListener: (RocketResponse) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.rocket_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName?.text = items[position].rocketName
        holder.tvCountry?.text = items[position].country
        holder.tvEngineCount?.text = items[position].engines.number.toString()
        holder.container.setOnClickListener {
            clickListener(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.rocket_list_item_text_view_name
    val tvCountry = view.rocket_list_item_text_view_country
    val tvEngineCount = view.rocket_list_item_text_view_engine_count
    val container = view.rocket_list_item_container
}