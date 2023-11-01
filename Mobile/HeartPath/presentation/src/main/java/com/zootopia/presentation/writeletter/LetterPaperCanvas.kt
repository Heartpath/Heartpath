package com.zootopia.presentation.writeletter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.zootopia.presentation.R

private const val TAG = "LetterPaperCanvas"
class LetterPaperCanvas(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs){

    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas
    private lateinit var mPath: Path
    private lateinit var mPaint: Paint

    //이 view가 생성될때 호출된다. 그 크기를 w, h로 준다
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        mPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.black)
            setStrokeWidth(10f)
            setStyle(Paint.Style.STROKE)
            setStrokeCap(Paint.Cap.ROUND)
            setStrokeJoin(Paint.Join.ROUND)
            setAntiAlias(true)
        }
        mPath = Path()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw: onDraw")
        canvas.drawBitmap(mBitmap, 0F, 0F, null) //지금까지 그려진 내용
        canvas.drawPath(mPath, mPaint) //현재 그리고 있는 내용
    }

    // 이 view 에 일어나는 터치 이벤트 처리
    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
        if (motionEvent != null) {
            var x = motionEvent.x
            var y = motionEvent.y

            if (motionEvent.action == android.view.MotionEvent.ACTION_DOWN) {
                Log.d(TAG, "onTouchEvent: down")
                mPath.moveTo(x, y)
            } else if (motionEvent.action == android.view.MotionEvent.ACTION_MOVE) {
                Log.d(TAG, "onTouchEvent: move")
                mPath.lineTo(x, y)
            } else if (motionEvent.action == android.view.MotionEvent.ACTION_UP) {
                Log.d(TAG, "onTouchEvent: up")
                mPath.lineTo(x, y)
                mCanvas.drawPath(mPath, mPaint)
                mPath.reset()
            }

        }
        invalidate()
        return super.onTouchEvent(motionEvent)
    }

}