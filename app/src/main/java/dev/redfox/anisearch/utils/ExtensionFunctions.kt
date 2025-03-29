package dev.redfox.anisearch.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.SystemClock
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment


fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun View.disappear() {
    visibility = INVISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)
fun Fragment.getColorCompat(color: Int) = ContextCompat.getColor(requireContext(), color)

fun View.changeBackgroundDrawableColor(color: Int) {
    val drawable = this.background

    if (drawable is GradientDrawable) {
        drawable.setColor(color)
    } else {
        this.setBackgroundColor(color)
    }
}

fun Activity.changeSystemBarsIconsAppearance(isLight: Boolean) {
    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !isLight
    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = !isLight
}

fun Activity.isColorDark(color: Int): Boolean {
    val darkness =
        1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return darkness >= 0.5
}

fun Activity.updateSystemBarIconsColor(color: Int){
    if (isColorDark(color))
        changeSystemBarsIconsAppearance(isLight = true)
    else
        changeSystemBarsIconsAppearance(isLight = false)
}

class OnSingleClickListener(private val block: () -> Unit) : OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(view: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 600) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
        block()
    }
}

fun View.setOnSingleClickListener(block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(block))
}
