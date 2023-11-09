package com.zootopia.presentation.arcore

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
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
import com.zootopia.presentation.databinding.FragmentArCoreBinding
import com.zootopia.presentation.util.setFullScreen
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt

private const val TAG = "ArCoreFragment_HP"

class ArCoreFragment
    : BaseFragment<FragmentArCoreBinding>(FragmentArCoreBinding::bind, R.layout.fragment_ar_core) {
    
    private lateinit var mainActivity: MainActivity
    private lateinit var currentFrame: Frame
    
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
        this@ArCoreFragment.setFullScreen(
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = false,
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
                            Log.d(TAG, "initView: plane $plane")
                            // plane 활성화 & 버튼 클릭시
                            binding.buttonSetItem.setOnClickListener {
                                Log.d(TAG, "initView: ->> $anchorNode")
                                
                                // 모델 올리기+
                                addAnchorNode(plane.createAnchor(plane.centerPose))
                                
                                //카메라 부터 모델까지 거리 구하기 (dist는 m 단위)
                                Log.d(TAG, "initView: ${frame.camera.pose}")
                                currentFrame = frame
                                measureDistanceFromCamera()
                            }
                        }
                    
                }
            }

            onTrackingFailureChanged = { reason ->
                this@ArCoreFragment.trackingFailureReason = reason
            }
        }
    }
    
    
    fun addAnchorNode(anchor: Anchor) {
    
        removeModelNode()
        
        binding.sceneView.addChildNode(
            AnchorNode(binding.sceneView.engine, anchor).apply {
                    isEditable = true
                
                    lifecycleScope.launch {
                        isLoading = true
                        binding.sceneView.modelLoader.loadModelInstance("models/lamborghini.glb")?.let { modelInstance ->
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
                        }
                        isLoading = false
                    }
                    anchorNode = this
                },
        )
    }
    
    fun removeModelNode() {
        
        anchorNode?.let { anchorNode ->
            binding.sceneView.removeChildNode(anchorNode) // AnchorNode를 제거하여 하위의 ModelNode도 함께 제거
            anchorNode.anchor.detach() // Anchor를 분리합니다.
//            anchorNode.isEditable = false // 선택 사항: 모델 노드가 더 이상 편집되지 않도록 설정
//            anchorNode.detachAnchor() // 선택 사항: 비활성화하여 모델 노드가 더 이상 렌더링되지 않도록 합니다.
            anchorNode.parent = null // 선택 사항: 부모 노드에서 제거합니다.

            
            // 모델을 삭제했으므로 anchorNode를 null로
            // 설정하여 참조를 제거
            this.anchorNode = null
            Log.d(TAG, "removeModelNode: 앵커 초기화 실행")
        }
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
    
    private fun changeUnit(distanceMeter: Float, unit: String): Float {
        return when (unit) {
            "cm" -> distanceMeter * 100
            "mm" -> distanceMeter * 1000
            else -> distanceMeter
        }
    }
    
}