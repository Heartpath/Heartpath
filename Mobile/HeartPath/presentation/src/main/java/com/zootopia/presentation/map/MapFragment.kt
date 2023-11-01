package com.zootopia.presentation.map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.zootopia.domain.model.map.MapDirectionDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentMapBinding
import com.zootopia.presentation.util.hasPermissions
import com.zootopia.presentation.util.showPermissionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private const val TAG = "MapFragment_HP"

@AndroidEntryPoint
class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map), OnMapReadyCallback {
    
    private val mapViewModel: MapViewModel by viewModels()
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    
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
        
        // todo 임시 길찾기 호출 API
//        mapViewModel.getMapDirection()
        
        initAdapter()
        initCollect()
        mapView = binding.mapviewNaver

        if (initCheckPermission()) {
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this)
            locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
    
    private fun initAdapter() {
    
    }
    
    private fun initCollect() {
        viewLifecycleOwner.lifecycleScope.launch {
            mapViewModel.mapDirectionInfo.collectLatest {
                DrawLoad(it)
                setLastLocation(
                    it.route.trafast[0].summary.goal.location[1],
                    it.route.trafast[0].summary.goal.location[0],
                )
            }
        }
    }
    
    private fun DrawLoad(mapDirectionDto: MapDirectionDto) {
        val pathList = mapDirectionDto.route.trafast[0].path
        val path = PathOverlay()
        //MutableList에 add 기능 쓰기 위해 더미 원소 하나 넣어둠
        val path_container: MutableList<LatLng>? = mutableListOf(LatLng(0.1, 0.1))
        for (path_cords_xy in pathList) {
            path_container?.add(LatLng(path_cords_xy[1], path_cords_xy[0]))
        }
        //더미원소 드랍후 path.coords에 path들을 넣어줌.
        path.coords = path_container?.drop(1)!!
        path.color = Color.RED
        path.map = naverMap
    }
    
    
    override fun onMapReady(naverMap: NaverMap) {
        val cameraPosition = CameraPosition(
            LatLng(37.5666102, 126.9783881),  // 위치 지정
            16.0 // 줌 레벨
        )
        this.naverMap = naverMap
        naverMap.cameraPosition = cameraPosition
        
        initNaverMapSetting()
        
        // 지도 타입
        naverMap.mapType = NaverMap.MapType.Basic
        // 현재 위치
        naverMap.locationSource = locationSource
        // 실내지도
        naverMap.isIndoorEnabled = true
        // 위치를 추적하면서 카메라도 따라 움직인다.
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(mainActivity) //gps 자동으로 받아오기
        setUpdateLocationListner() //내위치를 가져오는 코드
    }
    
    private fun initNaverMapSetting() {
        naverMap.uiSettings.apply {
            isCompassEnabled = false
            isScaleBarEnabled = false
            isLocationButtonEnabled = false
            isZoomControlEnabled = false
            logoGravity = Gravity.LEFT or Gravity.TOP
            setLogoMargin(20,20, 0, 0)
        }
        
        binding.apply {
//            compass.map = naverMap
//            scalebar.map = naverMap
            zoom.map = naverMap
            location.map = naverMap
        }
        
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
//                    Log.d(TAG, "initNaverMapSetting: locationation button is clicked")
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
    
    
    fun setLastLocation(latitude: Double, longitude: Double) {
        val myLocation = LatLng(latitude, longitude)
        //마커
        val marker = Marker()
        marker.apply {
            position = myLocation
            icon = OverlayImage.fromResource(R.drawable.ic_marker_letter)
            width = 100
            height = 160
            map = naverMap
        }
        
        // 카메라
        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0
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
