package com.example.drawapp.data

import android.graphics.Path
import android.graphics.PointF

class Pencil(val start: PointF, val color: Int) {
    var end = start
    var path = Path().apply {
        moveTo(start.x,start.y)
    }
    fun drawline(){
        path.lineTo(end.x,end.y)
    }
}