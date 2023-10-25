package com.zootopia.presentation.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zootopia.presentation.MainActivity
import android.net.Uri

private const val TAG = "PermissionUtil_HeartPath"

fun Context.hasPermissions(permission: String): Boolean{
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

/**
 * permissionList에 있는 권한들에 대해서 요청합니다.
 * getPermissionRejected -> 해당 권한에 대해서 한 번 거절했었는지 가져오는 함수
 * setPermissionRejected -> 해당 권한에 대해 거절하면 거절했다는 정보를 저장하는 함수
 * getIsShowedPermissionDialog -> 해당 권한에 대해서 설정으로 이동하는 dialog를 보여줬는지 가져오는 함수
 * setIsShowedPermissionDialog -> 해당 권한에 대해서 설정으로 이동하는 dialog를 보여줘야 한다고 값 갱신하는 함수
 * isShowDialog -> 다이얼로그를 총 한 번만 띄우기 위해 실행하는 함수
 */
fun checkAllPermission(
    fragment: Fragment?,
    activity: MainActivity,
    permissionList: Array<String>,
    getPermissionRejected: (String) -> Boolean,
    setPermissionRejected: (String) -> Unit,
    getIsShowedPermissionDialog: (String) -> Boolean,
    setIsShowedPermissionDialog: (String) -> Unit,
    isShowDialog: () -> Unit
){
    val requestMultiplePermission =
        (fragment?:activity).registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            var lastResult = false // 초기값을 false 설정
            results.forEach {
                if(!it.value) {
                    if(!getPermissionRejected(it.key)){ //거절한 적이 없다면
                        setPermissionRejected(it.key) //거절했다고 값 갱신
                    }else{
                        if(!getIsShowedPermissionDialog(it.key)) { //두 번 거절해서 다이얼로그 띄운 적 없으면
                            setIsShowedPermissionDialog(it.key) //해당 권한에 대해서 다이얼로그 띄워야 한다고 값 갱신
                            lastResult = true // 다이얼로그 총 한 번만 띄우기 위해 값 저장
                        }
                    }
                }
            }
            // 다이얼로그 띄워야 한다면
            if(lastResult){
                //여기서 다이얼로그 띄우는 변수 갱신
                isShowDialog()
            }
        }
    requestMultiplePermission.launch(permissionList)
}

// 다이얼로그를 띄우기 위한 함수
fun showPermissionDialog(activity: MainActivity) {
    val builder = AlertDialog.Builder(activity)
    
    builder.setTitle("권한 요청")
    builder.setMessage("앱을 사용하려면 권한을 허용해야 합니다. 설정으로 이동하여 권한을 허용하시겠습니까?")
    
    builder.setPositiveButton("확인") { dialog, which ->
        // 사용자가 확인 버튼을 누를 때 설정으로 이동하는 코드
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }
    
    builder.setNegativeButton("취소") { dialog, which ->
        // 사용자가 취소 버튼을 누를 때 수행할 작업 (옵션)
    }
    
    val dialog = builder.create()
    dialog.show()
}