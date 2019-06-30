package com.study.forecast.ui.weather.future.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.forecast.R
import com.study.forecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.study.forecast.data.provider.UnitProvider
import com.study.forecast.utils.glide.GlideApp
import kotlinx.android.synthetic.main.item_future_weather.view.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 29-Jun-19.
 * @since 1.0.0
 */
class FutureListWeatherAdapter(
    private val entries: List<UnitSpecificSimpleFutureWeatherEntry>,
    private val unitProvider: UnitProvider,
    private val itemClickListener: (UnitSpecificSimpleFutureWeatherEntry) -> Unit
) : RecyclerView.Adapter<FutureListWeatherAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_future_weather, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with (entries[position]) {
            holder.txtDate.text = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            holder.txtTemperature.text = "${avgTemperature}${unitProvider.getUnitSystem().temperatureUnit}"
            holder.txtCondition.text = conditionText
            GlideApp.with(holder.itemView).load("http:${conditionIconUrl}").into(holder.imgIcon)

            holder.itemView.setOnClickListener {
                itemClickListener(this)
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtDate = itemView.tvFutureWeatherDate
        var txtTemperature = itemView.tvFutureWeatherTemperature
        var txtCondition = itemView.tvFutureWeatherCondition
        var imgIcon = itemView.imgFutureWeatherIcon

    }

}