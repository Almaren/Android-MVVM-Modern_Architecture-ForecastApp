package com.study.forecast.ui.weather.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.forecast.R
import com.study.forecast.data.db.LocalDateConverter
import com.study.forecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate

class FutureListWeatherFragment : Fragment() {

    private val viewModel: FutureListWeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
        bindUI()
    }

    private fun loadData() {
        viewModel.loadFutureWeather()
        viewModel.loadWeatherLocation()
    }

    private fun bindUI() {
        viewModel.locationData.observe(this, Observer {
            if (it == null) return@Observer
            updateLocation(it.name)
        })

        viewModel.weatherEntriesData.observe(this, Observer { weatherEntries ->
            if (weatherEntries == null) return@Observer

            futureListGroupLoading.visibility = View.GONE
            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next week"
            initRecycleView(weatherEntries)
        })
    }

    private fun initRecycleView(entries: List<UnitSpecificSimpleFutureWeatherEntry>) {
        val futureListWeatherAdapter = FutureListWeatherAdapter(entries, get()) {
            onItemListClicked(it)
        }
        futureListRecycleView.layoutManager = LinearLayoutManager(activity)
        futureListRecycleView.itemAnimator = DefaultItemAnimator()
        futureListRecycleView.adapter = futureListWeatherAdapter
    }

    private fun onItemListClicked(itemEntry: UnitSpecificSimpleFutureWeatherEntry) {
        showWeatherDetails(itemEntry.date, futureListRecycleView)
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun showWeatherDetails(date: LocalDate, view: View) {
        val dateStr = LocalDateConverter.dateToString(date)!!
        val actionDetails = FutureListWeatherFragmentDirections.actionWeatherDetails(dateStr)
        Navigation.findNavController(view).navigate(actionDetails)
    }

}
