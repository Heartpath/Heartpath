package com.zootopia.presentation.util

import android.animation.ObjectAnimator
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 김희웅님의 애니메이션 라이브러리 코드
object AnimUtil {
    enum class AnimDirection {
        X,
        Y,
        XY
    }
    object Speed {
        const val FAST: Long = 125
        const val COMMON: Long = 250
        const val SLOW: Long = 500
    }
    object Size {
        const val MIN: Float = 0f
        const val HALF: Float = 0.5f
        const val ORIGIN: Float = 1f
        const val MAX: Float = 2f
    }
}

/**
 * View에 크기 변경 애니메이션을 적용합니다.
 */
fun View.scaleAnimation(direction: AnimUtil.AnimDirection, values: Float, speed: Long) {
    when(direction) {
        AnimUtil.AnimDirection.X -> {
            ObjectAnimator.ofFloat(this, "scaleX", values).apply {
                duration = speed
                start()
            }
        }
        AnimUtil.AnimDirection.Y -> {
            ObjectAnimator.ofFloat(this, "scaleY", values).apply {
                duration = speed
                start()
            }
        }
        AnimUtil.AnimDirection.XY -> {
            ObjectAnimator.ofFloat(this, "scaleX", values).apply {
                duration = speed
                start()
            }
            ObjectAnimator.ofFloat(this, "scaleY", values).apply {
                duration = speed
                start()
            }
        }
    }
}

/**
 * View에 위치 이동 애니메이션을 적용합니다. (속도 직접 입력)
 */
fun View.translateAnimation(direction: AnimUtil.AnimDirection, values: Float, speed: Long) {
    when(direction) {
        AnimUtil.AnimDirection.X -> {
            ObjectAnimator.ofFloat(this, "translationX", values).apply {
                duration = speed
                start()
            }
        }
        AnimUtil.AnimDirection.Y -> {
            ObjectAnimator.ofFloat(this, "translationY", values).apply {
                duration = speed
                start()
            }
        }
        AnimUtil.AnimDirection.XY -> {
            // XY 동시에 일어나는 일은 없다.
        }
    }
}

/**
 * View에 클릭 애니메이션을 적용합니다.
 */
fun View.clickAnimation(lifeCycleOwner: LifecycleOwner) {
    lifeCycleOwner.lifecycleScope.launch {
        this@clickAnimation.scaleAnimation(AnimUtil.AnimDirection.XY, 0.9f, 100)
        delay(100)
        this@clickAnimation.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 80)
        delay(80)
    }
}