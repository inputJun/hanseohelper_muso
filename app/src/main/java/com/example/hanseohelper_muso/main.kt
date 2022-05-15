package com.example.hanseohelper_muso

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition

class main : AppCompatActivity(), OnMapReadyCallback {

    val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val PERM_FLAG = 99

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERM_FLAG)
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
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupdateLocationListener()
    }

    // -- 내 위치를 가져오는 코드
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
                        Log.d("로케이션", "$i ${location.latitude}, ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }

        // 로케이션 요청 함수 호출 (locationRequest, LocationCallback)

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
                    Toast.makeText(this, "권한을 승인해야하지만 앱을 사용할 수 있습니다.", Toast. LENGTH_LONG).show()
                    finish()
                }

            }

        }
    }
}