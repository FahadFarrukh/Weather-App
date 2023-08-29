package com.example.weather.ui.home

import com.example.weather.R
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.ForecastAdapter
import com.example.weather.ForecastItem
import com.example.weather.TimeApiResponse
import com.example.weather.TimeApiService
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.marginTop
import com.example.weather.WeatherApiResponse
import com.example.weather.WeatherApiService
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.ui.gallery.SharedCityViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedCityViewModel: SharedCityViewModel

    private var forecast5Day: WeatherApiResponse? = null
    private var weatherApiResponse: WeatherApiResponse? = null
    private lateinit var cloudAnimation: Animation
    private lateinit var recyclerView: RecyclerView
    private lateinit var forecastAdapter: ForecastAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val weatherApiService = retrofit.create(WeatherApiService::class.java)
    private val TimeApiResponse = retrofit.create(WeatherApiService::class.java)
    private lateinit var zoomAnimation: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sharedCityViewModel = ViewModelProvider(requireActivity()).get(SharedCityViewModel::class.java)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.example.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val timeApiService = retrofit.create(TimeApiService::class.java)


        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        forecastAdapter = ForecastAdapter(emptyList())
        recyclerView.adapter = forecastAdapter
        Picasso.get().setLoggingEnabled(true)
        val apiKey = "0f50bf604e4241eb90c90615231808"
        val city = sharedCityViewModel.myString

//        val rainView = binding.rainView
//        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rain_fall)
//        rainView.startAnimation(animation)

//        val snowView: SnowView = binding.snowView
//        val snowfall: AnimationUtils.loadAnimation(requireContext(), R.anim.rain_fall)
//        snowView.startAnimation(rain_fall)
        binding.rainView.visibility = View.GONE
        binding.snowView.visibility = View.GONE
        binding.cloud1.visibility = View.GONE
        binding.cloud2.visibility = View.GONE
        binding.cloud3.visibility = View.GONE
        binding.cloud4.visibility = View.GONE
        binding.stars.visibility = View.GONE
        binding.stars2.visibility = View.GONE
        binding.fog.visibility = View.GONE
        binding.day.visibility = View.GONE
        binding.cloud5.visibility = View.GONE


        cloudAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.cloud_animation)






        if (root.background == ContextCompat.getDrawable(requireContext(), R.drawable.day)) {

            binding.cloud1.alpha = 0.5f
            binding.cloud2.alpha = 0.5f
            binding.cloud3.alpha = 0.5f
            binding.cloud4.alpha = 0.5f
        } else if (root.background == ContextCompat.getDrawable(requireContext(), R.drawable.night)) {

            binding.cloud1.alpha = 0.2f
            binding.cloud2.alpha = 0.2f
            binding.cloud3.alpha = 0.2f
            binding.cloud4.alpha = 0.2f
        }




        val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val lastSelectedCity = sharedPref.getString("lastSelectedCity", "DefaultCity") ?: "DefaultCity"


        sharedCityViewModel.myString = lastSelectedCity

        binding.weatherIcon3.visibility = View.GONE
        binding.sunn.visibility = View.GONE
        binding.details.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        zoomAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in)


        zoomAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                binding.cloud2.clearAnimation()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })




        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiKey = "0f50bf604e4241eb90c90615231808"
                val city = sharedCityViewModel.myString

                withContext(Dispatchers.Main) {

                    binding.weatherIcon3.visibility = View.GONE
                    binding.sunn.visibility = View.GONE
                    binding.details.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                val responseCurrentWeather = fetchCurrentWeather(apiKey, city)
                val response5DayForecast = fetch5DayForecast(apiKey, city)
                val responseHourlyForecast = fetchHourlyForecast(apiKey, city)
                val forecastList: List<ForecastItem> = emptyList()

                val call5DayForecast: Call<WeatherApiResponse> = weatherApiService.get5DayForecast(apiKey, city)
                val callHourlyForecast: Call<WeatherApiResponse> = weatherApiService.getHourlyForecast(apiKey, city)

                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.weatherIcon3.visibility = View.VISIBLE
                    binding.sunn.visibility = View.VISIBLE
                    binding.details.visibility = View.VISIBLE

                    if (responseCurrentWeather.isSuccessful && response5DayForecast.isSuccessful && responseHourlyForecast.isSuccessful) {
                        val currentWeather = responseCurrentWeather.body()
                        val forecast5DayResponse = response5DayForecast.body()
                        val forecastHourlyResponse = responseHourlyForecast.body()
                        val currentDayForecast = forecast5DayResponse?.forecast?.forecastday?.get(0)


                        if (currentDayForecast != null) {

                            val chanceOfRain = currentDayForecast.day.daily_chance_of_rain
                            val avgHumidity = currentDayForecast.day.avghumidity
                            val uv = currentDayForecast.day.uv
                            val wind = currentDayForecast.day.maxwind_kph


                            val realFeel = forecast5DayResponse.current.feelslike_c
                            val pressure = forecast5DayResponse.current.pressure_mb


//                            val pressureMb = currentWeather?.pressure_mb
//                            val realFeelCelsius = currentWeather?.feelslike_c
                            val sunrise = currentDayForecast?.astro?.sunrise
                            val sunset = currentDayForecast?.astro?.sunset

                            binding.a.text = "Chance of rain   $chanceOfRain%"
                            binding.b.text = "Humidity   $avgHumidity%"
                            binding.c.text = "UV   $uv"
                            binding.d.text = "Wind   $wind kph"
                            binding.sun.text="Sunrise  $sunrise"
                            binding.sunset.text="Sunset  $sunset"



                            binding.e.text = "Pressure   ${pressure?.toString() ?: "N/A"} mb"


                            val realFeelCelsius = weatherApiResponse?.current?.feelslike_c
                            binding.f.text = "Real Feel   ${realFeel?.toString() ?: "N/A"}°C"



                            val city2 = sharedCityViewModel.myString
                            getCurrentTime2(city2) { currentTimeForCity ->
                                if (currentTimeForCity != null) {

                                    val s1 = currentDayForecast?.astro?.sunrise
                                    val s2 = currentDayForecast?.astro?.sunset


                                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())

                                    try {

                                        val sunriseParsed = sdf.parse(s1)
                                        val sunsetParsed = sdf.parse(s2)
                                        val currentTimeParsed = sdf.parse(currentTimeForCity)

                                        if (sunriseParsed != null && sunsetParsed != null && currentTimeParsed != null) {

                                            if (currentTimeParsed.after(sunriseParsed) && currentTimeParsed.before(sunsetParsed)) {

                                                setActionBarColor(R.color.daycolor)
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                                    val window: Window? = activity?.window


                                                    window?.let {

                                                        it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)


                                                        it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)


                                                        it.statusBarColor = ContextCompat.getColor(requireContext(), R.color.daycolor)
                                                    }
                                                }


                                                root.setBackgroundResource(R.drawable.day)
                                                binding.cloud1.alpha = 0.7f
                                                binding.cloud2.alpha = 0.9f
                                                binding.cloud3.alpha = 1f
                                                binding.cloud4.alpha = 1f
                                                binding.cloud4.alpha = 0.7f

                                            } else {


                                                setActionBarColor(R.color.nightcolor)
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                                    val window: Window? = activity?.window


                                                    window?.let {

                                                        it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)


                                                        it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)


                                                        it.statusBarColor = ContextCompat.getColor(requireContext(), R.color.nightcolor)
                                                    }
                                                }


                                                root.setBackgroundResource(R.drawable.night)
                                                binding.stars.visibility = View.VISIBLE
                                                binding.stars2.visibility = View.VISIBLE
                                                binding.cloud1.alpha = 0.5f
                                                binding.cloud2.alpha = 0.6f
                                                binding.cloud3.alpha = 0.5f
                                                binding.cloud4.alpha = 0.7f
                                                binding.cloud5.alpha = 0.4f
                                                binding.fog.alpha = 0.5f
                                            }
                                        } else {

                                            // Log.d("Debug", "Error parsing times")
                                        }
                                    } catch (e: ParseException) {

                                        // Log.d("Debug", "Error parsing times: ${e.message}")
                                    }
                                } else {

                                    // Log.d("Debug", "Error fetching current time for city")
                                }
                            }



                        }


// Load the weather icon using Picasso

                        if (currentWeather != null && forecast5DayResponse != null && forecastHourlyResponse != null) {

                            displayForecastData(forecast5DayResponse, forecastHourlyResponse)
                            getCurrentTime(city)


                            val dayNames = getDayNames()


                            val forecastList: List<ForecastItem> = forecast5DayResponse.forecast.forecastday.mapIndexed { index, forecastDay ->
                                val date = dayNames[index]

                                val maxTempString = forecastDay.day.maxtemp_c.toString()
                                val minTempString = forecastDay.day.mintemp_c.toString()

                                val maxTemp = maxTempString.toDoubleOrNull() ?: 0.0
                                val minTemp = minTempString.toDoubleOrNull() ?: 0.0

                                ForecastItem(
                                    date = date,
                                    maxTemp = maxTemp,
                                    minTemp = minTemp
                                )
                            }

                            forecastAdapter = ForecastAdapter(forecastList)
                            recyclerView.adapter = forecastAdapter
                            forecastAdapter.notifyDataSetChanged()
                        } else {
                            // Handle null response bodies
                        }
                    } else {
                        // Handle API errors for any of the calls
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.weatherIcon3.visibility = View.GONE
                    binding.sunn.visibility = View.GONE
                    binding.details.visibility = View.GONE


                    binding.txtForecastDay1.text = "Error: No Data"
                }
            }
        }

        return root
    }

    private fun getCurrentTime(city: String) {
        val apiKey = "0f50bf604e4241eb90c90615231808"
        val weatherApiService = retrofit.create(WeatherApiService::class.java)

        val callCurrentTime: Call<WeatherApiResponse> = weatherApiService.getCurrentWeather(apiKey, city)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val responseCurrentTime = callCurrentTime.execute()

                withContext(Dispatchers.Main) {
                    if (responseCurrentTime.isSuccessful) {
                        val currentTimeResponse: WeatherApiResponse? = responseCurrentTime.body()

                        if (currentTimeResponse != null) {
                            val currentTime = currentTimeResponse.location.localtime

                            val parts = currentTime.split(" ")
                            val currentDate = parts[0]
                            val rawTime = parts[1]

                            val sdf24 = SimpleDateFormat("HH:mm", Locale.getDefault())
                            val date24: Date = sdf24.parse(rawTime)

                            val sdf12 = SimpleDateFormat("hh:mm a", Locale.getDefault())
                            val time12 = sdf12.format(date24)

                            // Get the day of the week from the current date
                            val dateParser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val date = dateParser.parse(currentDate)
                            val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)

                            binding.day.visibility = View.VISIBLE
                            binding.day.text = dayOfWeek
                            binding.txtForecastDay1.text = currentDate
                            binding.txtForecastDay2.text = time12

                        } else {
                            // Handle null response bodies
                        }
                    } else {
                        // Handle API errors
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.txtForecastDay1.text = "Error fetching current time"
                }
            }
        }
    }

    private fun displayForecastData(forecast5Day: WeatherApiResponse, forecastHourly: WeatherApiResponse) {
        val currentWeather = forecast5Day.current
        val temperatureCelsius = currentWeather.temp_c
        val conditionText = currentWeather.condition.text
        val conditionimage = currentWeather.condition.icon
        val cityName = forecast5Day.location.name
        Picasso.get().setLoggingEnabled(true)
        val weatherIconResource2 = when {
            conditionText.contains("partial sunny", true) -> R.drawable.partial
            conditionText.contains("sunny", true) -> R.drawable.sun
            conditionText.contains("clear", true) -> R.drawable.sun
            conditionText.contains("rain", true) -> R.drawable.rain
            conditionText.contains("cloud", true) -> R.drawable.cloud
            conditionText.contains("storm", true) -> R.drawable.storm
            conditionText.contains("thunder", true) -> R.drawable.storm
            conditionText.contains("snow", true) -> R.drawable.storm
            else -> R.drawable.ic_default_weather
        }

        if (conditionText.contains("partial sunny", true)) {
            binding.cloud1.visibility = View.VISIBLE
            binding.cloud2.visibility = View.VISIBLE
            binding.cloud3.visibility = View.VISIBLE
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud1.startAnimation(zoomAnimation)
            binding.cloud2.startAnimation(zoomAnimation)
            binding.cloud3.startAnimation(zoomAnimation)
            binding.cloud4.startAnimation(cloudAnimation)
        }
        if (conditionText.equals("Patchy rain possible", ignoreCase = true)) {

            binding.cloud1.visibility = View.VISIBLE
            binding.cloud3.visibility = View.VISIBLE
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud5.visibility = View.VISIBLE
            binding.cloud1.startAnimation(zoomAnimation)
            binding.cloud5.startAnimation(zoomAnimation)
            binding.cloud3.startAnimation(zoomAnimation)
            binding.cloud4.startAnimation(cloudAnimation)

        }
        else if (conditionText.contains("rain", true)){
            binding.rainView.visibility = View.VISIBLE
            binding.cloud1.visibility = View.VISIBLE
            binding.cloud3.visibility = View.VISIBLE
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud5.visibility = View.VISIBLE
            binding.cloud1.startAnimation(zoomAnimation)
            binding.cloud5.startAnimation(zoomAnimation)
            binding.cloud3.startAnimation(zoomAnimation)
            binding.cloud4.startAnimation(cloudAnimation)
            if (conditionText.equals("Moderate or heavy rain with thunder", ignoreCase = true)) {

                val params5 = binding.txtWorldTime4.layoutParams as ViewGroup.MarginLayoutParams
                params5.leftMargin = 160
                binding.txtWorldTime4.layoutParams = params5

            }
        }
        else if (conditionText.contains("drizzle", true)){
            binding.rainView.visibility = View.VISIBLE
            binding.cloud1.visibility = View.VISIBLE
            binding.cloud3.visibility = View.VISIBLE
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud5.visibility = View.VISIBLE
            binding.cloud1.startAnimation(zoomAnimation)
            binding.cloud5.startAnimation(zoomAnimation)
            binding.cloud3.startAnimation(zoomAnimation)
            binding.cloud4.startAnimation(cloudAnimation)

        }
        else if (conditionText.contains("partly cloudy", true)){
            binding.cloud1.visibility = View.VISIBLE
            binding.cloud2.visibility = View.VISIBLE
            binding.cloud3.visibility = View.VISIBLE
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud1.startAnimation(zoomAnimation)
            binding.cloud2.startAnimation(zoomAnimation)
            binding.cloud3.startAnimation(zoomAnimation)
            binding.cloud4.startAnimation(cloudAnimation)

            val params1 = binding.cloud1.layoutParams as ViewGroup.MarginLayoutParams
            params1.topMargin = 420
            binding.cloud1.layoutParams = params1

            val params2 = binding.cloud2.layoutParams as ViewGroup.MarginLayoutParams
            params2.topMargin = 0
            binding.cloud2.layoutParams = params2

            val params3 = binding.cloud3.layoutParams as ViewGroup.MarginLayoutParams
            params3.topMargin = 620
            binding.cloud3.layoutParams = params3
        }
        else if (conditionText.contains("cloud", true)){
            binding.cloud1.visibility = View.VISIBLE
            binding.cloud5.visibility = View.VISIBLE
            binding.cloud3.visibility = View.VISIBLE
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud1.startAnimation(zoomAnimation)
            binding.cloud5.startAnimation(zoomAnimation)
            binding.cloud3.startAnimation(zoomAnimation)
            binding.cloud4.startAnimation(cloudAnimation)

        }
        else if (conditionText.contains("mist", true)){


            binding.fog.visibility = View.VISIBLE
            binding.fog.startAnimation(zoomAnimation)
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud4.startAnimation(cloudAnimation)

        }
        else if (conditionText.contains("fog", true)){

            binding.fog.visibility = View.VISIBLE
            binding.fog.startAnimation(zoomAnimation)

        }
        else if (conditionText.contains("overcast", true)){
            binding.cloud1.visibility = View.VISIBLE
            binding.cloud2.visibility = View.VISIBLE
            binding.cloud3.visibility = View.VISIBLE
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud1.startAnimation(zoomAnimation)
            binding.cloud2.startAnimation(zoomAnimation)
            binding.cloud3.startAnimation(zoomAnimation)
            binding.cloud4.startAnimation(cloudAnimation)

            val params1 = binding.cloud1.layoutParams as ViewGroup.MarginLayoutParams
            params1.topMargin = 420
            binding.cloud1.layoutParams = params1

            val params2 = binding.cloud2.layoutParams as ViewGroup.MarginLayoutParams
            params2.topMargin = 0
            binding.cloud2.layoutParams = params2

            val params3 = binding.cloud3.layoutParams as ViewGroup.MarginLayoutParams
            params3.topMargin = 620
            binding.cloud3.layoutParams = params3
        }

        else if (conditionText.contains("storm", true)){

            binding.rainView.visibility = View.VISIBLE
            binding.cloud1.visibility = View.VISIBLE
            binding.cloud2.visibility = View.VISIBLE
            binding.cloud3.visibility = View.VISIBLE
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud5.visibility = View.VISIBLE
            binding.cloud1.startAnimation(zoomAnimation)
            binding.cloud2.startAnimation(zoomAnimation)
            binding.cloud3.startAnimation(zoomAnimation)
            binding.cloud4.startAnimation(cloudAnimation)
            binding.cloud5.startAnimation(cloudAnimation)
        }
        else if (conditionText.contains("snow", true)){

            binding.snowView.visibility = View.VISIBLE
            binding.cloud1.visibility = View.VISIBLE
            binding.cloud2.visibility = View.VISIBLE
            binding.cloud3.visibility = View.VISIBLE
            binding.cloud4.visibility = View.VISIBLE
            binding.cloud1.startAnimation(zoomAnimation)
            binding.cloud2.startAnimation(zoomAnimation)
            binding.cloud3.startAnimation(zoomAnimation)
            binding.cloud4.startAnimation(cloudAnimation)

            val params1 = binding.cloud1.layoutParams as ViewGroup.MarginLayoutParams
            params1.topMargin = 420
            binding.cloud1.layoutParams = params1

            val params2 = binding.cloud2.layoutParams as ViewGroup.MarginLayoutParams
            params2.topMargin = 0
            binding.cloud2.layoutParams = params2

            val params3 = binding.cloud3.layoutParams as ViewGroup.MarginLayoutParams
            params3.topMargin = 620
            binding.cloud3.layoutParams = params3
            if (conditionText.equals("Moderate or heavy snow with thunder", ignoreCase = true)) {

                val params5 = binding.txtWorldTime4.layoutParams as ViewGroup.MarginLayoutParams
                params5.leftMargin = 160
                binding.txtWorldTime4.layoutParams = params5

            }
        }


        binding.txtWorldTime.text = cityName
        binding.txtWorldTime2.text = "$temperatureCelsius°C"

        binding.txtWorldTime4.text = conditionText
        val weatherIconImageView = binding.conditionIcon

        Picasso.get()
            .load("https:$conditionimage")
            .into(weatherIconImageView)


    }
    private fun fetchCurrentWeather(apiKey: String, city: String): Response<WeatherApiResponse> {
        val callCurrentWeather: Call<WeatherApiResponse> = weatherApiService.getCurrentWeather(apiKey, city)
        return callCurrentWeather.execute()
    }

    private fun fetch5DayForecast(apiKey: String, city: String): Response<WeatherApiResponse> {
        val call5DayForecast: Call<WeatherApiResponse> = weatherApiService.get5DayForecast(apiKey, city)
        return call5DayForecast.execute()
    }

    private fun fetchHourlyForecast(apiKey: String, city: String): Response<WeatherApiResponse> {
        val callHourlyForecast: Call<WeatherApiResponse> = weatherApiService.getHourlyForecast(apiKey, city)
        return callHourlyForecast.execute()
    }
    private fun getDayNames(): List<String> {
        val calendar = Calendar.getInstance()
        val dayNames = mutableListOf<String>()

        for (i in 0..4) {
            val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            dayNames.add(dayOfWeek)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return dayNames
    }
    private fun fetchCurrentTime(apiKey: String, city: String, timeApiService: TimeApiService): String? {
        val callCurrentTime: Call<TimeApiResponse> = timeApiService.getCurrentTime(apiKey, city)

        try {
            val responseCurrentTime = callCurrentTime.execute()

            if (responseCurrentTime.isSuccessful) {
                val currentTimeResponse: TimeApiResponse? = responseCurrentTime.body()

                if (currentTimeResponse != null) {
                    return currentTimeResponse.currentTime // Return the current time
                }
            }
        } catch (e: Exception) {
            // Handle any errors or exceptions here
        }

        return null
    }

    private fun getCurrentTime2(city: String, callback: (String?) -> Unit) {
        val apiKey = "0f50bf604e4241eb90c90615231808"
        val weatherApiService = retrofit.create(WeatherApiService::class.java)

        val callCurrentTime: Call<WeatherApiResponse> = weatherApiService.getCurrentWeather(apiKey, city)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val responseCurrentTime = callCurrentTime.execute()

                withContext(Dispatchers.Main) {
                    if (responseCurrentTime.isSuccessful) {
                        val currentTimeResponse: WeatherApiResponse? = responseCurrentTime.body()

                        if (currentTimeResponse != null) {
                            val currentTime = currentTimeResponse.location.localtime
                            val parts = currentTime.split(" ")
                            val rawTime = parts[1]

                            val sdf24 = SimpleDateFormat("HH:mm", Locale.getDefault())
                            val date24: Date = sdf24.parse(rawTime)

                            val sdf12 = SimpleDateFormat("hh:mm a", Locale.getDefault())
                            val time12 = sdf12.format(date24)

                            // Call the callback function with the retrieved time
                            callback(time12)
                        } else {
                            // Handle null response bodies
                            callback(null)
                        }
                    } else {
                        // Handle API errors
                        callback(null)
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions
                callback(null)
            }
        }
    }

    fun setActionBarColor(@ColorRes colorResId: Int) {
        (activity as? AppCompatActivity)?.supportActionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(requireContext(), colorResId))
        )
    }
    override fun onResume() {
        super.onResume()

        // Start the zoomAnimation if binding.cloud3 is visible
        if (binding.cloud4.visibility == View.VISIBLE) {
            binding.cloud4.startAnimation(cloudAnimation) // Use cloudAnimation here
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}