package com.example.drawapp.data

import android.graphics.Paint
import android.graphics.PointF

class Box(private val start : PointF,val boxColor: Int) {

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