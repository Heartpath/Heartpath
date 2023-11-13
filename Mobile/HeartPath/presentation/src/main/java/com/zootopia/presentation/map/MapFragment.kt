package com.zootopia.presentation.map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
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
import com.zootopia.domain.model.letter.uncheckedletter.UncheckLetterDto
import com.zootopia.domain.model.tmap.FeatureCollectionDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.MainActivity.Companion.CAMERA_PERMISSION_REJECTED
import com.zootopia.presentation.MainActivity.Companion.IMAGE_PERMISSION_REJECTED
import com.zootopia.presentation.MainViewModel
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentMapBinding
import com.zootopia.presentation.util.WalkWorker
import com.zootopia.presentation.util.checkAllPermission
import com.zootopia.presentation.util.distanceIntToString
import com.zootopia.presentation.util.hasPermissions
import com.zootopia.presentation.util.requestPermissionsOnClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MapFragment_HP"

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    OnMapReadyCallback {

    private val mapViewModel: MapViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private lateinit var mapLetterAdapter: MapLetterAdapter
    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>

    // Map
    private var path: PathOverlay? = null
    private var marker: Marker? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private lateinit var locationSource: FusedLocationSource
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    // 내 위치를 가져오는 코드
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient // 자동으로 gps값을 받아온다.
    private var locationCallback: LocationCallback? = null // gps응답 값을 가져온다.

    // WorkManager
    private lateinit var workManager: WorkManager
    private lateinit var workRequest: WorkRequest

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: ")
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setWalkView()
        initView()
        initAdapter()
        initCollect()
        initClickEvent()
        initData()

        mapView = binding.mapviewNaver
        if (checkBasePermission()) {
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this)
            locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            requestBasePermission()
        }
    }

    private fun initView() = with(binding) {
        // toolbar setting
        toolbarHeartpathMap.apply {
            textviewCurrentPageTitle.text = resources.getString(R.string.toolbar_map_title)
            imageviewBackButton.setOnClickListener {
                mapViewModel.resetTmapWalkRoadInfo()
                stopWalk()
                stopLocationUpdates()
                findNavController().popBackStack()
            }
            toolbarHeartpath.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.PrimaryLightBlue,
                ),
            )
        }
    }

    private fun initData() {
        // Unchecked 편지 리스트 얻기
        mapViewModel.getUncheckedLetterList()
        
        // 워커 매니저 초기화
        workManager = WorkManager.getInstance(mainActivity)
        binding.textviewDistance.text = distanceIntToString(walkDist.toInt())
    }

    private fun initClickEvent() = with(binding) {
        // 신고 버튼 활성화 이벤트
        imageviewReport.setOnClickListener {
            Log.d(TAG, "initClickEvent: 신고버튼 클릭")
            mapViewModel.apply {
                isReport = !isReport
                uncheckedLetterList.map { UncheckLetterDto ->
                    UncheckLetterDto.isSelected = !UncheckLetterDto.isSelected
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
        
        // 신고 요청 이벤트
        buttonReport.setOnClickListener {
        
        }

        // workManager 종료 버튼
        buttonWorkStop.setOnClickListener {
            stopWalk()
            path?.map = null // 도보 초기화
        }

        // 카메라 버튼
        buttonCamera.setOnClickListener {
            Log.d(TAG, "initClickEvent: 카메라 버튼 클릭!")
            // 권한 확인
            if (checkCameraPermission()) {
                findNavController().navigate(R.id.action_mapFragment_to_arCoreReadFragment)
            } else {
                requestCameraPermission()
            }
        }

        // 이 콜백은 MyFragment가 최소한 Started일 때만 호출됩니다.
        val callback = mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // 뒤로 버튼 이벤트 처리
            Log.e(TAG, "뒤로가기 클릭")
            mapViewModel.resetTmapWalkRoadInfo()
            stopWalk()
            stopLocationUpdates()
            findNavController().popBackStack()
        }
        mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    
    private fun initAdapter() {
        mapLetterAdapter = MapLetterAdapter(mapViewModel = mapViewModel).apply {
            itemClickListener = object : MapLetterAdapter.ItemClickListener {
                override fun itemClick(view: View, position: Int, uncheckLetterDto: UncheckLetterDto) {
                    Log.d(TAG, "itemClick: 받은 편지 item 클릭됨")

                    if (mapViewModel.lastLatitude != 0.0 && mapViewModel.lastLongitude != 0.0) {
                        mapViewModel.apply {
                            goalLatitude = uncheckLetterDto.lat
                            goalLongitude = uncheckLetterDto.lng
                            mapViewModel.makeGoalLocataion()
                            // 선택된 편지
                            selectLetter = uncheckLetterDto
                            
                            // 현재 위치와 마커위치를 계산
                            calculateDistance()
                            // 마커 포지션
                            setMarkerLocation(
                                uncheckLetterDto = uncheckLetterDto,
                                latitude = goalLatitude,
                                longitude = goalLongitude,
                            )
                            // 카메라 포커스 (맵, 시작, 도착)
                            setCameraToIncludeMyLocationAndMarker(
                                naverMap,
                                LatLng(lastLatitude, lastLongitude),
                                LatLng(goalLatitude, goalLongitude),
                            )
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
            mapViewModel.tmapWalkRoadInfo.collect {
                Log.d(TAG, "initCollect: $it")
                DrawLoad(it) // 경로 그리기
                startWalk() // WorkManager 실행
                mapViewModel.walkRoad = it
            }
        }

        // 목적지 까지 거리 계산
        viewLifecycleOwner.lifecycleScope.launch {
            mapViewModel.walkDistance.collectLatest {
                mapViewModel.dist = distanceIntToString(it.toInt())
                if (mapViewModel.isStartWalk) {
                    binding.textviewDistance.text = mapViewModel.dist
                }
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

        rewriteMap() // 다시 그려야 하는 경로가 있다면 다시 그리기
    }

    // 다시 그려야 하는 경로가 있다면 다시 그리기
    private fun rewriteMap() = with(mapViewModel) {
        if (walkRoad != null) {
            Log.d(TAG, "onViewCreated: 기존 경로 다시 그리기 ")
            DrawLoad(walkRoad!!)

            // 현재 위치와 마커위치를 계산
            calculateDistance()

            // 마커 포지션
            setMarkerLocation(
                latitude = goalLatitude,
                longitude = goalLongitude,
            )

            // 카메라 위치 지정
            setCameraToIncludeMyLocationAndMarker(
                naverMap,
                LatLng(lastLatitude, lastLongitude),
                LatLng(goalLatitude, goalLongitude),
            )
        }
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
        if (locationCallback == null) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult ?: return
                    for ((i, location) in locationResult.locations.withIndex()) {
//                        Log.d(TAG, " $locationCallback    mapOn -> latitude: ${location.latitude}, longitude: ${location.longitude}")
                        mapViewModel.apply {
                            setLocation(
                                latitude = location.latitude,
                                longitude = location.longitude,
                            )
                            makeUserLocataion()

                            if (mapViewModel.isStartWalk) { // 편지 찾기 시작시에만 실시간으로 받아오는 위치와 편지 위치를 계산
                                calculateDistance()
                            }
                        }
                    }
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            Looper.myLooper(),
        )
    }

    // 위치 서비스 콜백 취소
    fun stopLocationUpdates() {
        if (locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback!!)
        }
    }

    fun setMarkerLocation(uncheckLetterDto: UncheckLetterDto? = null, latitude: Double, longitude: Double) {
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
            subCaptionText = mapViewModel.dist
            subCaptionColor = ContextCompat.getColor(mainActivity, R.color.black)
            setCaptionAligns(Align.Bottom)
            captionColor = ContextCompat.getColor(mainActivity, R.color.AlertRed)
            map = naverMap
        }

        marker?.setOnClickListener {
            if (mapViewModel.isStartWalk) {
                return@setOnClickListener false
            }

            if (it is Marker && checkBasePermission() && uncheckLetterDto != null) {
                // 확인 다이얼로그
                val readyGoDialog = ReadyGoDialogFragment(uncheckLetterDto = uncheckLetterDto)
                readyGoDialog.show(mainActivity.supportFragmentManager, "ReadyGoDialogFragment")
            } else {
                requestBasePermission()
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
            300,
            0,
            300,
            400, // padding 값을 조정하여 여백을 설정할 수 있습니다.

        )
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 21.0
        naverMap.minZoom = 5.0
    }

    private fun setWalkView() = with(binding) {
        Log.d(TAG, "setWalkView: ${mapViewModel.isStartWalk}")
        if (!mapViewModel.isStartWalk) {
            presidentBottomSheet.visibility = View.VISIBLE
            cardviewWork.visibility = View.GONE
        } else {
            presidentBottomSheet.visibility = View.GONE
            cardviewWork.visibility = View.VISIBLE
        }
    }

    // WorkManager
    // 산책 종료
    private fun stopWalk() {
        mapViewModel.isStartWalk = false
        mainActivity.showToast("편지 찾기를 종료합니다.")
        workManager.cancelAllWork() // 백그라운드 작업 종료
        mapViewModel.resetTmapWalkRoadInfo()
        setUpdateLocationListner()
        setWalkView()
    }

    @SuppressLint("RestrictedApi")
    private fun startWalk() {
        mapViewModel.isStartWalk = true
        mainActivity.showToast("편지 찾기를 시작합니다.")
        setWalkView()

        val inputData = Data.Builder()
            .putDouble("goalLatitude", mapViewModel.goalLatitude)
            .putDouble("goalLongitude", mapViewModel.goalLongitude)
            .putDouble("userLatitude", mapViewModel.lastLatitude)
            .putDouble("userLongitude", mapViewModel.lastLongitude)
            .build()

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

    // 권한 Check
    private fun checkBasePermission(): Boolean = with(mainActivity) {
        return hasPermissions(ACCESS_COARSE_LOCATION) && hasPermissions(ACCESS_FINE_LOCATION) && hasPermissions(MainActivity.POST_NOTIFICATIONS)
    }

    private fun checkCameraPermission(): Boolean = with(mainActivity) {
        return hasPermissions(CAMERA_PERMISSION_REJECTED) && hasPermissions(IMAGE_PERMISSION_REJECTED)
    }

    private fun requestBasePermission() {
        checkAllPermission(
            fragment = this@MapFragment,
            activity = mainActivity,
            mainViewModel = mainViewModel,
            permissionList = MainActivity.INIT_PERMISSION_REQUEST,
        )
    }
    private fun requestCameraPermission() {
        requestPermissionsOnClick(
            activity = mainActivity,
            mainViewModel = mainViewModel,
            permissionList = MainActivity.PERMISSION_LIST_UP33,
        )
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
        Log.d(TAG, "onDestroyView: MapFragment")
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
