package com.example.drawapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.drawapp.data.*
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class CostumeCanvas(private val mContext: Context, attr: AttributeSet? = null) :
    View(mContext, attr) {

    private val TAG = "Costume Canvas"
    private var currentBox: Box? = null
    private var currentArrow: Arrow? = null
    private var currentEllipse: Ellipse? = null
    private var currentPencil: Pencil? = null
    private var boxen = mutableListOf<Box>()
    private var circles = mutableListOf<Ellipse>()
    private var arrows = mutableListOf<Arrow>()
    private var pencils = mutableListOf<Pencil>()
    var currentToolsState = Tools.Pencil


    var boxColor: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    private val backgroundColor = Paint().apply {
        color = 0xffffffff.toInt()
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundColor)
        val path = Path()

        when (currentToolsState) {
            Tools.Pencil -> {
                currentPencil?.let {

                    canvas.drawPath(it.path, boxColor)
                }
            }
            Tools.Rectangle -> {
                currentBox?.let { box ->
                    canvas.drawRect(box.left, box.top, box.right, box.bottom, boxColor)
                }
            }
            Tools.Arrow -> {
                currentArrow?.let {
                    val radius = 50
                    val angle = 60
                    val anglerad = (PI * angle / 180.0f).toFloat()
                    val lineangle =
                        atan2((it.endY - it.startY).toDouble(), (it.endX - it.startX).toDouble())

                    path.moveTo(it.endX, it.endY)
                    path.lineTo(
                        (it.endX - radius * cos(lineangle - (anglerad / 2.0))).toFloat(),
                        (it.endY - radius * sin(lineangle - (anglerad / 2.0))).toFloat()
                    )
                    path.moveTo(
                        (it.endX - radius * cos(lineangle + (anglerad / 2.0))).toFloat(),
                        (it.endY - radius * sin(lineangle + (anglerad / 2.0))).toFloat()
                    )
                    path.lineTo(it.endX, it.endY);

                    path.close()

                    canvas.drawPath(path, boxColor)
                    canvas.drawLine(it.startX, it.startY, it.endX, it.endY, boxColor)

                }
            }
            Tools.Ellipse -> {
                currentEllipse?.let {
                    canvas.drawOval(RectF(it.left, it.top, it.right, it.bottom), boxColor)
                }
            }
        }


        arrows.forEach {
            showArrow(it, canvas)
        }
        boxen.forEach {
            showCircle(it, canvas)
        }
        circles.forEach {
            showCircle(it, canvas)
        }
        pencils.forEach {
            showPencil(it, canvas)
        }
    }


    fun drawLine(event: MotionEvent, current: PointF) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentArrow = Arrow(event.x, event.y, boxColor.color).also {
                    arrows.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                updateCurrentArrow(current)

            }
            MotionEvent.ACTION_UP -> {
                updateCurrentArrow(current)
                currentArrow?.let {
                    updateArrowPath(it)
                }
                currentArrow = null
            }
            MotionEvent.ACTION_CANCEL -> {
                currentArrow = null
            }
        }

    }

    fun showArrow(it: Arrow, canvas: Canvas) {
        val paint = Paint().apply {
            isAntiAlias = true
            color = it.color
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawLine(it.startX, it.startY, it.endX, it.endY, paint)
        canvas.drawPath(it.path, paint)
    }

    fun drawRect(event: MotionEvent, current: PointF) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentBox = Box(current, boxColor.color).also {
                    boxen.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {

                updateCurrentBox(current)

            }
            MotionEvent.ACTION_UP -> {
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                currentBox = null
            }
        }
    }

    fun drawEllipse(event: MotionEvent, current: PointF) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentEllipse = Ellipse(current, boxColor.color).also {
                    circles.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {

                updateCurrentEllipse(current)

            }
            MotionEvent.ACTION_UP -> {
                updateCurrentEllipse(current)
                currentEllipse = null
            }
            MotionEvent.ACTION_CANCEL -> {
                currentEllipse = null
            }
        }
    }

    fun drawPencil(event: MotionEvent, current: PointF) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPencil = Pencil(current, boxColor.color).also {
                    pencils.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {

                updatePencil(current)

            }
            MotionEvent.ACTION_UP -> {
                updatePencil(current)
                currentPencil = null
            }
            MotionEvent.ACTION_CANCEL -> {
                currentPencil = null
            }
        }
    }

    fun showCircle(box: Box, canvas: Canvas) {
        val paint = Paint().apply {
            isAntiAlias = true
            color = box.boxColor
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawRect(box.left, box.top, box.right, box.bottom, paint)
    }

    fun showCircle(circle: Ellipse, canvas: Canvas) {
        val paint = Paint().apply {
            isAntiAlias = true
            color = circle.color
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawOval(RectF(circle.left, circle.top, circle.right, circle.bottom), paint)
    }

    fun showPencil(it: Pencil, canvas: Canvas) {
        val paint = Paint().apply {
            isAntiAlias = true
            color = it.color
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawPath(it.path, paint)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        /*Depending on the tool selected we call corresponding draw function*/
        when (currentToolsState) {
            Tools.Pencil -> drawPencil(event, current)
            Tools.Rectangle -> drawRect(event, current)
            Tools.Arrow -> drawLine(event, current)
            Tools.Ellipse -> drawEllipse(event, current)
        }
        return true
    }

    /**** Update Box when finger is moving*/
    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }

    /***** update ellipse when finger is moving*/
    private fun updateCurrentEllipse(current: PointF) {
        currentEllipse?.let {
            it.end = current
            invalidate()
        }
    }

    /***** update arrow when finger is moving*/
    private fun updateCurrentArrow(current: PointF) {
        currentArrow?.let {
            it.endX = current.x
            it.endY = current.y
            invalidate()
        }
    }

    /***** update Arrow head  when finger is up from the screen */
    private fun updateArrowPath(it: Arrow) {
        val radius = 50
        val angle = 60
        val anglerad = (PI * angle / 180.0f).toFloat()
        val lineangle =
            atan2((it.endY - it.startY).toDouble(), (it.endX - it.startX).toDouble())

        it.path.moveTo(it.endX, it.endY)
        it.path.lineTo(
            (it.endX - radius * cos(lineangle - (anglerad / 2.0))).toFloat(),
            (it.endY - radius * sin(lineangle - (anglerad / 2.0))).toFloat()
        )
        it.path.moveTo(
            (it.endX - radius * cos(lineangle + (anglerad / 2.0))).toFloat(),
            (it.endY - radius * sin(lineangle + (anglerad / 2.0))).toFloat()
        )
        it.path.lineTo(it.endX, it.endY);

        it.path.close()
        invalidate()
    }

    /***** update pencil when finger is moving*/
    fun updatePencil(current: PointF) {
        currentPencil?.let {
            it.end = current
            it.drawline()
            invalidate()
        }
    }


}