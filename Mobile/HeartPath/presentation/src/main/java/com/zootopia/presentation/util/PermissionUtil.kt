package com.zootopia.presentation.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.MainViewModel

private const val TAG = "PermissionUtil_HeartPath"

fun Context.hasPermissions(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
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
){
    val requestMultiplePermission =
        (fragment?:activity).registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            var lastResult = false // 초기값을 false 설정
            results.forEach {
                if(!it.value) {
                    mainViewModel.getPermissionRejected(it.key)
                } else {
                    mainViewModel.setPermissionRejected(it.key, 0)
                }
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