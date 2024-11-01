package com.ovedev.coordinadoraconnect.presentation.ui

import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.ovedev.coordinadoraconnect.BuildConfig
import com.ovedev.coordinadoraconnect.R
import com.ovedev.coordinadoraconnect.databinding.ActivityMapBinding
import com.ovedev.coordinadoraconnect.presentation.model.Position
import com.ovedev.coordinadoraconnect.presentation.ui.base.BaseActivity
import com.ovedev.coordinadoraconnect.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MapActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding

    private lateinit var gMap: GoogleMap
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupView()
    }

    private fun setupView() {

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.GOOGLE_MAPS_API_KEY)
        }

        placesClient = Places.createClient(this)

    }

    private fun setupListeners() {
        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.uiSettings.isZoomControlsEnabled = true

        val positionsList = intent.getParcelableArrayListExtra<Position>(Constant.BUNDLE_KEY_POSITIONS) ?: arrayListOf()
        if (positionsList.isEmpty()) {
            val defaultLocation = LatLng(-34.0, 151.0)
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
        } else {
            val boundsBuilder = LatLngBounds.Builder()
            for (position in positionsList) {
                val location = LatLng(position.latitude, position.longitude)
                val titlePosition = getString(R.string.text_position).replace(Constant.GLOBAL_REPLACEMENT, "${position.latitude - position.longitude}")
                gMap.addMarker(MarkerOptions().position(location).title(titlePosition))
                boundsBuilder.include(location)
            }

            val bounds = boundsBuilder.build()
            val padding = 100
            Handler(Looper.getMainLooper()).post {
                gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
            }
        }
        setupMarkerListener()
    }

    private fun setupMarkerListener() {
        gMap.setOnMarkerClickListener { marker ->
            Toast.makeText(this, marker.title, Toast.LENGTH_SHORT).show()
            true
        }
        gMap.setOnMarkerClickListener { marker ->
            val position = marker.position

            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val neighborhood = address.subLocality ?: ""
                val city = address.locality ?: ""
                val location = getString(R.string.text_neighborhood_city).replaceFirst(Constant.GLOBAL_REPLACEMENT, neighborhood)
                    .replace(Constant.GLOBAL_REPLACEMENT, city)
                Toast.makeText(this, location, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.text_location_no_found), Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

}