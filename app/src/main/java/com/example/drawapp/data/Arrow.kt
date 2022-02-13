package com.example.drawapp.data

import android.graphics.Path

class Arrow(val startX:Float,val startY : Float,val color: Int) {
    var endX = startX
    var endY = startY
    var path: Path = Path()
}