package com.example.weather.ui.gallery

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.City
import com.example.weather.CityAdapter
import com.example.weather.R
import com.example.weather.databinding.FragmentGalleryBinding
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weather.ApiInterface
import com.example.weather.CityTable
import com.example.weather.CurrentWeatherData
import com.example.weather.TimeDatabase
import com.example.weather.Weather
import com.example.weather.WeatherApiResponse
import com.example.weather.WeatherApiService
import com.example.weather.WeatherDatabase
import com.example.weather.WorldTimeApiService
import com.example.weather.WorldTimeData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class GalleryFragment : Fragment(), CityAdapter.OnItemClickListener {
    private lateinit var db: WeatherDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var cityAdapter: CityAdapter
    private var _binding: FragmentGalleryBinding? = null
    private lateinit var sharedCityViewModel: SharedCityViewModel
    private val binding get() = _binding!!

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://worldtimeapi.org/api/timezone/") // Base URL for the API
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService: WorldTimeApiService = retrofit.create(WorldTimeApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        sharedCityViewModel = ViewModelProvider(
            requireActivity(),

            ).get(SharedCityViewModel::class.java)

        sharedCityViewModel = ViewModelProvider(requireActivity()).get(SharedCityViewModel::class.java)




        val cities = mutableListOf(
            City("Istanbul", R.drawable.turkey),
            City("Jeddah", R.drawable.ksa),
            City("Madinah", R.drawable.ksa),
            City("Riyadh", R.drawable.ksa),
            City("Luxembourg", R.drawable.luxembourg),
            City("Soleuvre", R.drawable.luxembourg),
            City("Differdange", R.drawable.luxembourg),
            City("Orleans", R.drawable.france),
            City("Paris", R.drawable.france),
            City("Metz", R.drawable.france),
            City("Karachi", R.drawable.pakistan),
            City("Islamabad", R.drawable.pakistan),
            City("Lahore", R.drawable.pakistan),
            City("Pidakkesh", R.drawable.pakistan),
            City("Berlin", R.drawable.german),
            City("Frankfurt", R.drawable.german),
            City("Munich", R.drawable.german),
            City("Dubai", R.drawable.uae),
            City("Abu Dhabi", R.drawable.uae),
            City("Amsterdam", R.drawable.netherlands),
            City("Doha", R.drawable.qatar),
            City("Rome", R.drawable.italy),
            City("Milan", R.drawable.italy),
            City("Sydney", R.drawable.australia),
            City("Melbourne", R.drawable.australia),
            City("London", R.drawable.uk),
            City("Manchester", R.drawable.uk),
            City("Liverpool", R.drawable.uk),
            City("Auckland", R.drawable.newzealand),
            City("Zurich", R.drawable.switzerland),
            City("Tokyo", R.drawable.japan),
            City("Yonago", R.drawable.japan),
            City("Cairo", R.drawable.egypt),
            City("Casablanca", R.drawable.morocco),
            City("Moscow", R.drawable.russia),
            City("Brussels", R.drawable.belgium),
            City("Los Angeles", R.drawable.us),
            City("New York", R.drawable.us),
            City("Fayette", R.drawable.us),
            City("Toronto", R.drawable.canada),
            City("Thunder Bay", R.drawable.canada),
            City("Shanghai", R.drawable.china),
            City("Beijing", R.drawable.china),
            City("Buenos Aires", R.drawable.argentina),
            City("Bariloche", R.drawable.argentina),
            City("Puente Del Inca", R.drawable.argentina),

            // Add more cities here...
        )

        cityAdapter = CityAdapter(cities, this)
        recyclerView.adapter = cityAdapter

        return root
    }

    override fun onItemClick(city: City) {
        val myString: String="Istanbul"

        when (city.name) {
            "Madinah" -> {
                val myString="Madinah"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Jeddah" -> {
                val myString="Jeddah"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Riyadh" -> {
                val myString="Riyadh"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Pidakkesh" -> {
                val myString="Pidakkesh"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Istanbul" -> {
                val myString="Istanbul"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Thunder Bay" -> {
                val myString="Thunder Bay"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }

            "Yonago" -> {
                val myString="Yonago"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Karachi" -> {
                val myString="Karachi"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }

            "Beijing" -> {
                val myString="Beijing"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Differdange" -> {
                val myString="Differdange"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Soleuvre" -> {
                val myString="Soleuvre"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Abu Dhabi" -> {
                val myString="Abu Dhabi"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }

            "Orleans" -> {
                val myString="Orleans"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Zurich" -> {
                val myString="Zurich"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Islamabad" -> {
                val myString="Islamabad"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Melbourne" -> {
                val myString="Melbourne"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Sydney" -> {
                val myString="Sydney"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Berlin" -> {
                val myString="Berlin"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Bariloche" -> {
                val myString="Bariloche"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }

            "Munich" -> {
                val myString="Munich"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }

            "Buenos Aires" -> {
                val myString="Buenos Aires"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Amsterdam" -> {
                val myString="Amsterdam"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Metz" -> {
                val myString="Metz"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Puente Del Inca" -> {
                val myString="Puente Del Inca"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }

            "Frankfurt" -> {
                val myString="Frankfurt"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Luxembourg" -> {
                val myString="Luxembourg"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Rome" -> {
                val myString="Rome"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Doha" -> {
                val myString="Doha"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Milan" -> {
                val myString="Milan"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Los Angeles" -> {
                val myString="Los Angeles"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "New York" -> {
                val myString="New York"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Liverpool" -> {
                val myString="Liverpool"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Manchester" -> {
                val myString="Manchester"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Lahore" -> {
                val myString="Lahore"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "London" -> {
                val myString="London"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Tokyo" -> {
                val myString="Tokyo"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Paris" -> {
                val myString="Paris"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Cairo" -> {
                val myString="Cairo"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Casablanca" -> {
                val myString="Casablanca"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Moscow" -> {
                val myString="Moscow"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Brussels" -> {

                val myString="Brussels"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Toronto" -> {
                val myString="Toronto"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Shanghai" -> {
                val myString="Shanghai"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Dubai" -> {
                val myString="Dubai"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Auckland" -> {
                val myString="Auckland"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }
            "Fayette" -> {
                val myString="Fayette"
                sharedCityViewModel.myString=myString
//            insert(myString)
                val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("lastSelectedCity", myString)
                editor.apply()
                nextpageWithDelay()
            }


        }




    }



    private fun nextpageWithDelay() {
        findNavController().navigate(R.id.action_GalleryFragment_to_HomeFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }






}