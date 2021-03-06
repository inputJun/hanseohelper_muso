package com.example.hanseohelper_muso

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.example.hanseohelper_muso.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_start.*


class main : AppCompatActivity(), OnMapReadyCallback {

    val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val PERM_FLAG = 99

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent_id_main = intent.extras?.getString("ID")
        intent.putExtra("ID",intent_id_main)

        binding.btnMyinfoMain.setOnClickListener {
            var popup = PopupMenu(this, it)
            menuInflater.inflate(com.example.hanseohelper_muso.R.menu.popup, popup.menu)

            popup.setOnMenuItemClickListener {
                when (it?.itemId) {
                    com.example.hanseohelper_muso.R.id.popup_menu1 -> {
                        val intent = Intent(this, myinfo::class.java)
                        intent.putExtra("ID",intent_id_main)
                        startActivity(intent)
                    }
                    com.example.hanseohelper_muso.R.id.popup_menu2 -> {
                        val intent = Intent(this, myclient::class.java)
                        intent.putExtra("ID",intent_id_main)
                        startActivity(intent)
                    }
                    com.example.hanseohelper_muso.R.id.popup_menu3 -> {
                        val intent = Intent(this, myservice::class.java)
                        intent.putExtra("ID",intent_id_main)
                        startActivity(intent)
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popup.show()
        }

        if (isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERM_FLAG)
        }


        btn_list_main.setOnClickListener {
            val intent = Intent(this, list::class.java)
            intent.putExtra("ID",intent_id_main)
            startActivity(intent)
        }

        btn_request.setOnClickListener {
            val intent = Intent(this, request::class.java)
            intent.putExtra("ID",intent_id_main)
            startActivity(intent)
        }

    }

    fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun startProcess() {
        val mapFragment = supportFragmentManager
            .findFragmentById(com.example.hanseohelper_muso.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupdateLocationListener()
    }

    // -- ??? ????????? ???????????? ??????
    lateinit var fusedLocationClient:FusedLocationProviderClient
    lateinit var locationCallback:LocationCallback

    @SuppressLint("MissingPermission")
    fun setupdateLocationListener() {
        val locationRequest = LocationRequest.create()
        locationRequest.run{
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }

        locationCallback = object : LocationCallback () {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for((i, location) in it.locations.withIndex()){
                        Log.d("????????????", "$i ${location.latitude}, ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }

        // ???????????? ?????? ?????? ?????? (locationRequest, LocationCallback)

        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())

    }

    fun setLastLocation(location : Location) {
        val myLocation = LatLng(location.latitude, location.longitude)
        val markerOption = MarkerOptions()
            .position(myLocation)
            .title("I am here!")
        val cameraOption = CameraPosition.Builder()
            .target(myLocation)
            .zoom(15.0f)
            .build()

        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)

        mMap.addMarker(markerOption)
        mMap.moveCamera(camera)
    }

    fun onRequestPermssionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            PERM_FLAG -> {
                var check = true
                for(grant in grantResults) {
                    if(grant != PackageManager.PERMISSION_GRANTED) {
                        check = false
                        break
                    }
                }

                if(check) {
                    startProcess()
                }else{
                    Toast.makeText(this, "????????? ????????????????????? ?????? ????????? ??? ????????????.", Toast. LENGTH_LONG).show()
                    finish()
                }

            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        val intent = Intent(this, request::class.java)
        //intent.putExtra("data", "test data");
        startActivityForResult(intent, 1)
        return true
        return super.onOptionsItemSelected(item)
    }
}