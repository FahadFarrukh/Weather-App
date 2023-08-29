package com.example.weather

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class SnowView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val snowflakes = mutableListOf<Snowflake>()

    init {
        paint.color = Color.WHITE
        paint.strokeWidth = 5f
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (flake in snowflakes) {
            flake.draw(canvas)
        }


        if (snowflakes.size < 100) {
            snowflakes.add(Snowflake())
        }


        for (flake in snowflakes) {
            flake.update()
        }


        invalidate()
    }

    private inner class Snowflake {
        private var x: Float = Random.nextFloat() * width
        private var y: Float = -10f
        private var speed: Float = Random.nextFloat() * 5 + 2

        fun draw(canvas: Canvas) {
            canvas.drawCircle(x, y, 5f, paint)
        }

        fun update() {
            y += speed


            if (y > height) {
                y = -10f
                x = Random.nextFloat() * width
                speed = Random.nextFloat() * 5 + 2
            }
        }
    }
}
