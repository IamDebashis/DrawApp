package com.example.drawapp.data

import android.graphics.PointF

class Ellipse(val start: PointF, val color: Int) {
    var end : PointF = start

    val left: Float
        get() = Math.min(start.x, end.x)

    val right : Float
        get() = Math.max(start.x,end.x)

    val top: Float
        get()= Math.min(start.y,end.y)

    val bottom: Float
        get()= Math.max(start.y,end.y)
}