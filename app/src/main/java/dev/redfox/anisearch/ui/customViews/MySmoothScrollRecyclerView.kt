package dev.redfox.anisearch.ui.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class MySmoothScrollRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        // keep it less than 1.0 to slowdown
        val newVelocityY: Int = velocityY.times(0.65F).toInt()
        return super.fling(velocityX, newVelocityY)
    }
}