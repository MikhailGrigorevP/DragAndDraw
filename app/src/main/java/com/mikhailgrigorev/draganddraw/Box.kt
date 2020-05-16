package com.mikhailgrigorev.draganddraw

import android.graphics.PointF


class Box(val origin: PointF) {
    var current: PointF

    init {
        current = origin
    }
}
