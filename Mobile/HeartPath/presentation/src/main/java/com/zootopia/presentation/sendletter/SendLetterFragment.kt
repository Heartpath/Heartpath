package com.zootopia.presentation.sendletter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.zootopia.domain.model.letter.unplacedletter.UnplacedLetterDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.MainViewModel
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSendLetterBinding
import com.zootopia.presentation.util.hasPermissions
import com.zootopia.presentation.util.requestPermissionsOnClick
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "SendLetterFragment_HP"

class SendLetterFragment :
    BaseFragment<FragmentSendLetterBinding>(
        FragmentSendLetterBinding::bind,
        R.layout.fragment_send_letter,
    ) {

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private lateinit var sendLetterAdapter: SendLetterAdapter
    private val sendLetterViewModel: SendLetterViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    // 내 위치를 가져오는 코드
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient // 자동으로 gps값을 받아온다.
    private var locationCallback: LocationCallback? = null // gps응답 값을 가져온다.

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendLetterViewModel.getUnplacedLetter()

        navController = Navigation.findNavController(view)
        setUpdateLocationListner()
        initAppBar()
        initAdapter()
        initClickEvent()
        initCollect()
    }

    private fun initAdapter() = with(binding) {
        sendLetterAdapter = SendLetterAdapter().apply {
            itemClickListener = object : SendLetterAdapter.ItemClickListener {
                // 편지 클릭 이벤트
                override fun itemClick(view: View, unplacedLetterDto: UnplacedLetterDto, position: Int) {
                    Log.d(TAG, "itemClick: 편지 리스트 클릭됨 $position")
                    // 권한 확인
                    if (checkCameraPermission()) {
                        sendLetterViewModel.isSelectLetterId = unplacedLetterDto.index
                        findNavController().navigate(R.id.action_sendLetterFragment_to_arCoreWriteFragment)
                    } else {
                        requestCameraPermission()
                    }
                }
            }
        }

        recyclerviewSendLetter.apply {
            adapter = sendLetterAdapter
            layoutManager = GridLayoutManager(mainActivity, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun initClickEvent() = with(binding) {
        // 이 콜백은 MyFragment가 최소한 Started일 때만 호출됩니다.
        val callback = mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            stopLocationUpdates()
            findNavController().popBackStack()
        }
        mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initAppBar() = with(binding) {
        // toolbar setting
        toolbarHeartpathSendLetter.apply {
            textviewCurrentPageTitle.text = resources.getString(R.string.toolbar_letter_storage_title)
            imageviewBackButton.setOnClickListener {
                stopLocationUpdates()
                findNavController().popBackStack()
            }
        }
    }

    private fun initCollect() = with(sendLetterViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            unplacedLetter.collectLatest {
                sendLetterAdapter.apply {
                    it.unPlacedLetterList?.let { letterList -> submitData(letterList) }
                    notifyDataSetChanged()
                }
            }
        }
    }

    // 좌표계를 주기적으로 갱신
    @SuppressLint("MissingPermission")
    fun setUpdateLocationListner() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mainActivity) // gps 자동으로 받아오기

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
//                        Log.d(TAG, "mapOn -> latitude: ${location.latitude}, longitude: ${location.longitude}")
                        sendLetterViewModel.apply {
                            setLocation(
                                latitude = location.latitude,
                                longitude = location.longitude,
                            )
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

    fun stopLocationUpdates() {
        if (locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback!!)
        }
    }

    private fun checkCameraPermission(): Boolean = with(mainActivity) {
        return hasPermissions(MainActivity.CAMERA_PERMISSION_REJECTED) && hasPermissions(
            MainActivity.IMAGE_PERMISSION_REJECTED,
        )
    }
    private fun requestCameraPermission() {
        requestPermissionsOnClick(
            activity = mainActivity,
            mainViewModel = mainViewModel,
            permissionList = MainActivity.PERMISSION_LIST_UP33,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
