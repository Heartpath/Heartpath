package com.zootopia.presentation.arcore

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.Pose
import com.google.ar.core.TrackingFailureReason
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentArCoreReadBinding
import com.zootopia.presentation.map.MapViewModel
import com.zootopia.presentation.util.setFullScreen
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt


private const val TAG = "ArCoreReadFragment_HP"

class ArCoreReadFragment
    : BaseFragment<FragmentArCoreReadBinding>(
    FragmentArCoreReadBinding::bind,
    R.layout.fragment_ar_core_read
) {
    
    private lateinit var mainActivity: MainActivity
    private lateinit var currentFrame: Frame
    private val mapViewModel: MapViewModel by activityViewModels()
    
    var isLoading = false
        set(value) {
            field = value
            binding.loadingView.isGone = !value
        }
    
    var anchorNode: AnchorNode? = null
        set(value) {
            if (field != value) {
                field = value
                updateInstructions()
            }
        }
    
    var trackingFailureReason: TrackingFailureReason? = null
        set(value) {
            if (field != value) {
                field = value
                updateInstructions()
            }
        }
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    
    fun updateInstructions() {
        binding.instructionText.text = trackingFailureReason?.let {
            it.getDescription(mainActivity)
        } ?: if (anchorNode == null) {
            "원하는 장소에 편지를 놓아주세요."
        } else {
            null
        }
    }
    
    private fun initView() {
        this@ArCoreReadFragment.setFullScreen(
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = true,
        )
        
        
        binding.sceneView.apply {
            planeRenderer.isEnabled = true
            configureSession { session, config ->
                config.depthMode = when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    true -> Config.DepthMode.AUTOMATIC
                    else -> Config.DepthMode.DISABLED
                }
                config.instantPlacementMode = Config.InstantPlacementMode.DISABLED
                config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            }
            onSessionUpdated = { _, frame ->
                if (anchorNode == null) {
                    
                    frame.getUpdatedPlanes()
                        .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                        ?.let { plane ->
                            // 모델 올리기 (plane 정 중앙)
                            addAnchorNode(plane.createAnchor(plane.centerPose))
                            
                            //카메라 부터 모델까지 거리 구하기 (dist는 m 단위)
                            currentFrame = frame
                            measureDistanceFromCamera()
                        }
                }
            }
            
            
            onTrackingFailureChanged = { reason ->
                this@ArCoreReadFragment.trackingFailureReason = reason
            }
    
//            anchorNode?.onTap?.apply {
//                this.invoke(modelClickEvent())
//            }
//
//            anchorNode.
        }
    }
    
    fun addAnchorNode(anchor: Anchor) {
        binding.sceneView.addChildNode(
            AnchorNode(binding.sceneView.engine, anchor).apply {
                isEditable = true
                Log.d(TAG, "addAnchorNode: $this")
                lifecycleScope.launch {
                    isLoading = true
                    binding.sceneView.modelLoader.loadModelInstance("models/lamborghini.glb")
                        ?.let { modelInstance ->
                            addChildNode(
                                ModelNode(
                                    modelInstance = modelInstance,
                                    // Scale to fit in a 0.5 meters cube
                                    scaleToUnits = 0.2f,
                                    // Bottom origin instead of center so the model base is on floor
                                    centerOrigin = Position(y = -0.5f),
                                ).apply {
                                    isEditable = true
                                },
                            )
    
                            // Model이 나타났으므로 PlaneRenderer를 비활성화
                            binding.sceneView.planeRenderer.isEnabled = false
                        }
                    isLoading = false
                }
                anchorNode = this
            },
        )
    }
    
    fun modelClickEvent(): MotionEvent {
        Log.d(TAG, "modelClickEvent: 모델 클릭!!")
        
        return TODO("Provide the return value")
    }
    
    private fun measureDistanceFromCamera() {
        if (anchorNode != null) {
            val distanceMeter = calculateDistance(
                anchorNode!!.worldPosition,
                currentFrame!!.camera.pose
            )
            measureDistanceOf2Points(distanceMeter)
        }
    }
    
    private fun measureDistanceOf2Points(distanceMeter: Float) {
        Log.d(TAG, "distance: ${distanceMeter}")
    }
    
    private fun calculateDistance(x: Float, y: Float, z: Float): Float {
        return sqrt(x.pow(2) + y.pow(2) + z.pow(2))
    }
    
    private fun calculateDistance(objectPose0: Position, objectPose1: Pose): Float {
        return calculateDistance(
            objectPose0.x - objectPose1.tx(),
            objectPose0.y - objectPose1.ty(),
            objectPose0.z - objectPose1.tz()
        )
    }
}