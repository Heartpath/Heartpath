package com.zootopia.presentation.writeletter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.zootopia.presentation.R
import com.zootopia.presentation.util.PenPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private const val TAG = "LetterPaperCanvas_HP"

class LetterPaperCanvas(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {

    private lateinit var viewModel: WriteLetterViewModel
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas
    private lateinit var mPath: Path
    private lateinit var mPaint: Paint
    private lateinit var mEraser: Paint
    private var isEraserSelected: Boolean = false
    private var clear = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    private var pointList : ArrayList<PenPoint> = arrayListOf()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        isEraserSelected = false
        mPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.black)
            setStrokeWidth(10f * 0.3f)
            setStyle(Paint.Style.STROKE)
            setStrokeCap(Paint.Cap.ROUND)
            setStrokeJoin(Paint.Join.ROUND)
            setAntiAlias(true)
        }
        mEraser = Paint().apply {
            color = ContextCompat.getColor(context, android.R.color.transparent)
            setStrokeWidth(10f * 0.3f)
            setStyle(Paint.Style.STROKE)
            setStrokeCap(Paint.Cap.ROUND)
            setStrokeJoin(Paint.Join.ROUND)
            setAntiAlias(true)
            setXfermode(clear)
        }
        mPath = Path()

        val activity = context as? AppCompatActivity
        activity?.let {
            viewModel = ViewModelProvider(it).get(WriteLetterViewModel::class.java)

            activity.lifecycleScope.launch {
                viewModel.selectedColor.collectLatest {
                    Log.d(TAG, "onAttachedToWindow: color changed ${it}")
                    mPaint.setColor(resources.getColor(it))
                }
            }

            activity.lifecycleScope.launch {
                viewModel.penSize.collectLatest {
                    mPaint.strokeWidth = it * 0.3f
                    mEraser.strokeWidth = it * 0.3f
                }
            }

            activity.lifecycleScope.launch {
                viewModel.isEraserSelected.collectLatest {
                    isEraserSelected = it
                }
            }
        }

    }

    //이 view가 생성될때 호출된다. 그 크기를 w, h로 준다
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawPathOnCanvas()
        canvas.drawBitmap(mBitmap, 0F, 0F, null) //지금까지 그려진 내용
    }

    // 이 view 에 일어나는 터치 이벤트 처리
    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
        if (motionEvent != null) {
            var x = motionEvent.x
            var y = motionEvent.y

            if (motionEvent.action == android.view.MotionEvent.ACTION_DOWN) {
                //mPath.moveTo(x, y)
                pointList.add(PenPoint(x, y))
            } else if (motionEvent.action == android.view.MotionEvent.ACTION_MOVE) {
               // mPath.lineTo(x, y)
               // drawPathOnCanvas()
                pointList.add(PenPoint(x, y))
            } else if (motionEvent.action == android.view.MotionEvent.ACTION_UP) {
//                mPath.lineTo(x, y)
//                drawPathOnCanvas()
//                mPath.reset()
                pointList.clear()
            }
            invalidate()
        }

        return super.onTouchEvent(motionEvent)
    }

    private fun drawPathOnCanvas(){
        if(isEraserSelected){
            //mCanvas.drawPath(mPath, mEraser)
            pointList.forEachIndexed { index, penPoint ->
                if(index >=1 ){
                    mCanvas.drawLine(
                        pointList[index - 1].x,
                        pointList[index - 1].y,
                        penPoint.x,
                        penPoint.y,
                        mEraser
                    )
                }
            }
        }else{
            //mCanvas.drawPath(mPath, mPaint)
            pointList.forEachIndexed { index, penPoint ->
                if(index >=1 ){
                    mCanvas.drawLine(
                        pointList[index - 1].x,
                        pointList[index - 1].y,
                        penPoint.x,
                        penPoint.y,
                        mPaint
                    )
                }
            }
        }
    }
}