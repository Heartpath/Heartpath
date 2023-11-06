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
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.zootopia.domain.model.navermap.MapLetterDto
import com.zootopia.domain.model.tmap.FeatureCollectionDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentMapBinding
import com.zootopia.presentation.util.WalkWorker
import com.zootopia.presentation.util.distanceIntToString
import com.zootopia.presentation.util.hasPermissions
import com.zootopia.presentation.util.showPermissionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MapFragment_HP"

@AndroidEntryPoint
class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    OnMapReadyCallback {
    
    private val mapViewModel: MapViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private lateinit var mapLetterAdapter: MapLetterAdapter
    
    // Map
    private var path: PathOverlay? = null
    private var marker: Marker? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private lateinit var locationSource: FusedLocationSource
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    
    // 내 위치를 가져오는 코드
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient // 자동으로 gps값을 받아온다.
    lateinit var locationCallback: LocationCallback // gps응답 값을 가져온다.
    
    
    // WorkManager
    private lateinit var workManager: WorkManager
    private lateinit var workRequest: WorkRequest
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        
        initAdapter()
        initCollect()
        initClickEvent()
        initData()
        
        // 테스트 (임시)
//        mapViewModel.test()
        
        mapView = binding.mapviewNaver
        if (initCheckPermission()) {
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this)
            locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
    
    private fun initData() {
        mapViewModel.getDummyList()
        
        binding.textviewDistance.text = distanceIntToString(walkDist.toInt())
    }
    
    private fun initClickEvent() = with(binding) {
        // 신고 버튼
        imageviewReport.setOnClickListener {
            Log.d(TAG, "initClickEvent: 신고버튼 클릭")
            mapViewModel.apply {
                isReport = !isReport
                LetterList.map { MapLetterDto ->
                    MapLetterDto.isSelected = !MapLetterDto.isSelected
                }
                if (isReport) {
                    buttonReport.visibility = View.VISIBLE
                } else {
                    buttonReport.visibility = View.GONE
                }
            }
            initAdapter()
            initData()
        }
        
        // workManager 종료 버튼
        buttonWorkStop.setOnClickListener {
            stopWalk()
            path?.map = null // 도보 초기화
        }
    }
    
    private fun initAdapter() {
        mapLetterAdapter = MapLetterAdapter(mapViewModel = mapViewModel).apply {
            itemClickListener = object : MapLetterAdapter.ItemClickListener {
                override fun itemClick(view: View, position: Int, mapLetterDto: MapLetterDto) {
                    Log.d(TAG, "itemClick: 받은 편지 item 클릭됨")
                    
                    if (mapViewModel.lastLatitude != 0.0 && mapViewModel.lastLongitude != 0.0) {
                        mapViewModel.apply {
                            goalLatitude = mapLetterDto.latitude.toDouble()
                            goalLongitude = mapLetterDto.longitude.toDouble()
                            mapViewModel.makeLocataion()
    
                            // 마커 포지션
                            setMarkerLocation(
                                mapLetterDto = mapLetterDto,
                                latitude = goalLatitude,
                                longitude = goalLongitude
                            )
                            // 카메라 포커스 (맵, 시작, 도착)
                            setCameraToIncludeMyLocationAndMarker(
                                naverMap,
                                LatLng(
                                    lastLatitude.toDouble(),
                                    lastLongitude.toDouble(),
                                ),
                                LatLng(
                                    goalLatitude,
                                    goalLongitude
                                ),
                            )
                            
                            //
//                            calculateDistance( userLocation = location )
                        }
                        
                    } else {
                        mainActivity.showToast("사용자의 위치를 불러오는 중입니다.")
                    }
                    
                }
                
                override fun reportClick(view: View, position: Int) {
                    Log.d(TAG, "itemClick: 받은 편지 신고버튼 클릭됨")
                }
            }
        }
        
        binding.recyclerviewLetterList.apply {
            adapter = mapLetterAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
    
    private fun initCollect() {
        // error 처리
        viewLifecycleOwner.lifecycleScope.launch {
            mapViewModel.error.collectLatest {
                mainActivity.showToast(it.message.toString())
            }
        }
        
        // 편지 리스트
        viewLifecycleOwner.lifecycleScope.launch {
            mapViewModel.mapLetterList.collectLatest {
                mapLetterAdapter.submitList(it.toMutableList())
            }
        }
        
        // 길 찾기 data 수신[Tmap] -> 경로 그리기 & WorkManager 실행
        viewLifecycleOwner.lifecycleScope.launch {
            mapViewModel.tmapWalkRoadInfo.collectLatest {
                Log.d(TAG, "initCollect: $it")
                DrawLoad(it) // 경로 그리기
                startWalk() // WorkManager 실행
                mapViewModel.isStartWalk = true
            }
        }
        
        // 목적지 까지 거리 계산
        viewLifecycleOwner.lifecycleScope.launch{
            mapViewModel.walkDistance.collectLatest {
                mapViewModel.dist = distanceIntToString(it.toInt())
                if(mapViewModel.isStartWalk) {
                    binding.textviewDistance.text = mapViewModel.dist
                } else {
                    binding.textviewTopDistance.text = mapViewModel.dist
                }
            }
        }
        
        // WorkManager
        viewLifecycleOwner.lifecycleScope.launch {
            mapViewModel.isWorkManager.collectLatest {
            
            }
        }
    }
    
    private fun DrawLoad(featureCollectionDto: FeatureCollectionDto) {
        path?.map = null
        
        val featureList = featureCollectionDto.features
        path = PathOverlay()
        // MutableList에 add 기능 쓰기 위해 더미 원소 하나 넣어둠
        val path_container: MutableList<LatLng>? = mutableListOf(LatLng(0.1, 0.1))
        for (feature in featureList) {
            val pathList = feature.geometry.coordinates as? List<List<Double>> ?: emptyList()
            
            for (path_cords_xy in pathList) {
                path_container?.add(LatLng(path_cords_xy[1], path_cords_xy[0]))
            }
        }
        
        Log.d(TAG, "DrawLoad: $path_container")
        // 더미원소 드랍후 path.coords에 path들을 넣어줌.
        path!!.coords = path_container?.drop(1)!!
        path!!.color = Color.RED
        path!!.map = naverMap
    }
    
    override fun onMapReady(naverMap: NaverMap) {
        val cameraPosition = CameraPosition(
            LatLng(37.5666102, 126.9783881), // 위치 지정
            16.0, // 줌 레벨
        )
        this.naverMap = naverMap
        naverMap.cameraPosition = cameraPosition
        
        initNaverMapSetting()
        
        naverMap.mapType = NaverMap.MapType.Basic // 지도 타입
        naverMap.locationSource = locationSource // 현재 위치
        naverMap.isIndoorEnabled = true // 실내지도
        naverMap.locationTrackingMode =
            LocationTrackingMode.Follow // 위치를 추적하면서 카메라도 따라 움직인다. (구글 서비스 버전 21.0.1 이상 사용해야함)
        
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(mainActivity) // gps 자동으로 받아오기
        setUpdateLocationListner() // 내위치를 가져오는 코드
    }
    
    private fun initNaverMapSetting() {
        naverMap.uiSettings.apply {
            isCompassEnabled = false
            isScaleBarEnabled = false
            isLocationButtonEnabled = false
            isZoomControlEnabled = false
            logoGravity = Gravity.LEFT or Gravity.TOP
            setLogoMargin(20, 20, 0, 0)
        }
        
        binding.apply {
            zoom.map = naverMap
            location.map = naverMap
        }
    }
    
    // 좌표계를 주기적으로 갱신
    @SuppressLint("MissingPermission")
    fun setUpdateLocationListner() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.setInterval(2000)
        }
        
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for ((i, location) in locationResult.locations.withIndex()) {
                    Log.d(TAG, "mapOn -> latitude: ${location.latitude}, longitude: ${location.longitude}")
                    mapViewModel.apply {
                        setLocation(
                            latitude = location.latitude,
                            longitude = location.longitude,
                        )
                        
                        if(mapViewModel.isStartWalk) {
                            calculateDistance( userLocation = location )
                        }
                    }
                }
            }
        }
        
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper(),
        )
    }
    
    fun setMarkerLocation(mapLetterDto: MapLetterDto, latitude: Double, longitude: Double) {
        // 기존 마커가 존재하는 경우 제거합니다
        marker?.map = null
        
        val myLocation = LatLng(latitude, longitude)
        // 새로운 마커를 생성합니다
        marker = Marker()
        marker?.apply {
            position = myLocation
            icon = OverlayImage.fromResource(R.drawable.ic_marker_letter)
            width = 100
            height = 160
            captionText = "Click!"
//            subCaptionText = mapLetterDto.title
//            subCaptionColor = ContextCompat.getColor(mainActivity, R.color.black)
            setCaptionAligns(Align.Bottom)
            captionColor = ContextCompat.getColor(mainActivity, R.color.AlertRed)
            map = naverMap
        }
        
        marker?.setOnClickListener {
            if (it is Marker && initCheckPermission()) {
                // 확인 다이얼로그
                val readyGoDialog = ReadyGoDialogFragment(mapLetterDto = mapLetterDto)
                readyGoDialog.show(mainActivity.supportFragmentManager, "ReadyGoDialogFragment")
            }
            true
        }
    }
    
    // 카메라 포커스 이동
    fun setCameraToIncludeMyLocationAndMarker(
        naverMap: NaverMap,
        myLocation: LatLng,
        markerLocation: LatLng,
    ) {
        val cameraUpdate = CameraUpdate.fitBounds(
            LatLngBounds.Builder()
                .include(myLocation) // 내 위치
                .include(markerLocation) // 마커 위치
                .build(),
            300, 0, 300, 400, // padding 값을 조정하여 여백을 설정할 수 있습니다.
        
        )
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 21.0
        naverMap.minZoom = 5.0
    }
    
    // 권한 Check
    private fun initCheckPermission(): Boolean = with(mainActivity) {
        if (!hasPermissions(ACCESS_COARSE_LOCATION)
            && !hasPermissions(ACCESS_FINE_LOCATION)
            && !hasPermissions(MainActivity.POST_NOTIFICATIONS)
        ) {
            showPermissionDialog(mainActivity)
            return false
        }
        return true
    }
    
    // WorkManager
    // 산책 종료
    private fun stopWalk() {
        mainActivity.showToast("편지 찾기를 종료합니다.")
        binding.apply {
            presidentBottomSheet.visibility = View.VISIBLE
            textviewTopDistance.visibility = View.VISIBLE
            cardviewWork.visibility = View.GONE
        }
        workManager.cancelAllWork()
        mapViewModel.resetTmapWalkRoadInfo()
        setUpdateLocationListner()
        mapViewModel.isStartWalk = false
    }
    
    @SuppressLint("RestrictedApi")
    private fun startWalk() {
        mainActivity.showToast("편지 찾기를 시작합니다.")
        
        // 현재 포그라운드에서 받아오는 위치서비스를 중단
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        
        
        binding.apply {
            presidentBottomSheet.visibility = View.GONE
            textviewTopDistance.visibility = View.GONE
            cardviewWork.visibility = View.VISIBLE
        }
        
        val inputData = Data.Builder()
            .putDouble("goalLatitude", mapViewModel.goalLatitude)
            .putDouble("goalLongitude", mapViewModel.goalLongitude)
            .putDouble("userLatitude", mapViewModel.lastLatitude.toDouble())
            .putDouble("userLongitude", mapViewModel.lastLongitude.toDouble())
            .build()
        
        workManager = WorkManager.getInstance(mainActivity)
        workRequest = OneTimeWorkRequestBuilder<WalkWorker>()
            .setInputData(inputData)
            .build()
        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(
                mainActivity,
            ) { workInfo ->
                if (workInfo != null) {
                    val outputData = workInfo.outputData
                    when (workInfo.state) {
                        WorkInfo.State.RUNNING -> {
                            Log.d(TAG, "startWalk:Running ")
                        }
                        
                        WorkInfo.State.SUCCEEDED -> {
                            Log.d(TAG, "onCreate: success")
                            mapViewModel.isStartWalk = false
                        }
                        
                        WorkInfo.State.FAILED -> {
                            Log.d(TAG, "onCreate: fail")
                            mapViewModel.isStartWalk = false
                        }
                        
                        WorkInfo.State.CANCELLED -> {
                            Log.d(TAG, "종료 - 거리: $walkDist / 시간: $walkTime")
                            mapViewModel.isStartWalk = false
                        }
                        
                        else -> {
                            Log.d(TAG, "onCreate: else")
                        }
                    }
                }
            }
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
        Log.d(TAG, "onDestroyView: ")
        mapView.onDestroy()
    }
    
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    
    companion object {
        var walkDist = 0F
        var walkTime = 0
    }
}
