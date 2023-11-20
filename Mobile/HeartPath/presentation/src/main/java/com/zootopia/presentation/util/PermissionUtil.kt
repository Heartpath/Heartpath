package com.zootopia.presentation.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.MainViewModel

private const val TAG = "PermissionUtil_HeartPath"

fun Context.hasPermissions(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission,
    ) == PackageManager.PERMISSION_GRANTED
}

/**
 * permissionList에 있는 권한들에 대해서 요청합니다.
 */
fun checkAllPermission(
    fragment: Fragment?,
    activity: MainActivity,
    mainViewModel: MainViewModel,
    permissionList: Array<String>,
) {
    val permissionsToRequest = mutableListOf<String>()
    val requestMultiplePermission =
        (fragment ?: activity).registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                Log.d(TAG, "checkAllPermission: ${it.key}  / ${it.value}")
                if (!it.value) {
                    permissionsToRequest.add(it.key)
                } else {
                    mainViewModel.setPermissionRejected(it.key, 0)
                }
            }
        }

    mainViewModel.getPermissionRejected(permissionsToRequest)
    requestMultiplePermission.launch(permissionList)
}

// 권한 요청 버튼 클릭 핸들러
fun requestPermissionsOnClick(
    activity: MainActivity,
    mainViewModel: MainViewModel,
    permissionList: Array<String>,
) {
    // 권한을 이미 허용했는지 확인
    val permissionsToRequest = mutableListOf<String>()
    for (permission in permissionList) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(permission)
        }
    }
    
    // 아직 허용되지 않은 권한이 있다면 요청
    if (permissionsToRequest.isNotEmpty()) {
        mainViewModel.getPermissionRejected(permissionsToRequest)
        ActivityCompat.requestPermissions(activity, permissionsToRequest.toTypedArray(), 123)
    } else {
        // 이미 모든 권한이 허용되었음
    }
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
