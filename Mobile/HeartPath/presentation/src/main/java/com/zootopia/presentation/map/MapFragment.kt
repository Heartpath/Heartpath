package com.zootopia.presentation.map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentMapBinding
import com.zootopia.presentation.util.hasPermissions
import com.zootopia.presentation.util.showPermissionDialog
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MapFragment_HP"

@AndroidEntryPoint
class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    OnMapReadyCallback {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val mapViewModel: MapViewModel by viewModels()
    
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private lateinit var locationSource: FusedLocationSource
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    
        mapViewModel.getMapDirection()
        
        mapView = binding.mapviewNaver
        
        if (initCheckPermission()) {
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this)
            locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
    
    override fun onMapReady(naverMap: NaverMap) {
        val cameraPosition = CameraPosition(
            LatLng(37.5666102, 126.9783881),  // 위치 지정
            16.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition
        
        this.naverMap = naverMap
        
        // 지도 타입
        naverMap.mapType = NaverMap.MapType.Basic
        // 현재 위치
        naverMap.locationSource = locationSource
        // 실내지도
        naverMap.isIndoorEnabled = true
        // 현재 위치 버튼 기능
        naverMap.uiSettings.isLocationButtonEnabled = true
        // 위치를 추적하면서 카메라도 따라 움직인다.
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(mainActivity) //gps 자동으로 받아오기
        setUpdateLocationListner() //내위치를 가져오는 코드
        setLastLocation(36.1071402, 128.417931)
    }
    
    //내 위치를 가져오는 코드
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient //자동으로 gps값을 받아온다.
    lateinit var locationCallback: LocationCallback //gps응답 값을 가져온다.
    
    @SuppressLint("MissingPermission")
    fun setUpdateLocationListner() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도
            interval = 1000 //1초에 한번씩 GPS 요청
        }
        
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for ((i, location) in locationResult.locations.withIndex()) {
                    Log.d(TAG, "${location.latitude}, ${location.longitude}")
//                    setLastLocation(location)
                }
            }
        }
        
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }// 좌표계를 주기적으로 갱신
    
    
    //  fun setLastLocation(location: Location) {
    fun setLastLocation(latitude: Double, longitude: Double) {
//        val myLocation = LatLng(location.latitude, location.longitude)
        val myLocation = LatLng(latitude, longitude)
        val marker = Marker()
        marker.position = myLocation
        marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_letter)
        marker.width = 100
        marker.height = 160
        marker.map = naverMap
        //마커
        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0
        
        //marker.map = null
    }
    
    private fun initCheckPermission(): Boolean = with(mainActivity) {
        if (!hasPermissions(ACCESS_COARSE_LOCATION) && !hasPermissions(ACCESS_FINE_LOCATION)) {
            showPermissionDialog(mainActivity)
            return false
        }
        return true
    }
    
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
    
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
    
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }
    
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    
    
}
