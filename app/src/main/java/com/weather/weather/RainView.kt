package com.example.weather

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class RainView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val raindrops = mutableListOf<Raindrop>()

    init {
        paint.color = Color.argb(60, 0, 0, 185)
        paint.strokeWidth = 4f
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (drop in raindrops) {
            drop.draw(canvas)
        }


        if (raindrops.size < 100) {
            raindrops.add(Raindrop())
        }


        for (drop in raindrops) {
            drop.update()
        }


        invalidate()
    }

    private inner class Raindrop {
        var x: Float = Random.nextFloat() * width
        var y: Float = 0f
        var speed: Float = Random.nextFloat() * 20 + 5
        val dropLength: Int = 20

        fun draw(canvas: Canvas) {

            val alpha = (1 - (height - y) / height) * 255
            paint.alpha = alpha.toInt()

            canvas.drawLine(x, y, x, y + dropLength, paint)


            paint.alpha = 255
        }

        fun update() {
            y += speed

            if (y > height) {
                y = 0f
                x = Random.nextFloat() * width
                speed = Random.nextFloat() * 10 + 5
            }
        }
    }
}
