package dev.redfox.anisearch.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.SystemClock
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import dev.redfox.anisearch.R
import androidx.core.net.toUri


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

fun View.setMargins(
    left: Int = this.marginLeft,
    top: Int = this.marginTop,
    right: Int = this.marginRight,
    bottom: Int = this.marginBottom,
) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(left, top, right, bottom)
    }
}

fun setImage(mContext: Context, drawable: Int?, imageView: ImageView) {
    try {
        Glide.with(mContext).load(drawable).into(imageView)
    } catch (ex: Exception) {
        ex.showLog()
    }
}

fun setImage(mContext: Context, drawable: Drawable?, imageView: ImageView) {
    try {
        Glide.with(mContext).load(drawable).into(imageView)
    } catch (ex: Exception) {
        ex.showLog()
    }
}

fun setImage(
    mContext: Context,
    imageUrl: String?,
    imageView: ImageView,
    isAnimated: Boolean = false
) {
    try {
        val builder = Glide.with(mContext)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
            ).load(imageUrl)
        if (isAnimated) {
            builder.transition(DrawableTransitionOptions.withCrossFade(200))
                .into(imageView)
        } else
            builder.into(imageView)
    } catch (ex: Exception) {
        ex.showLog()
    }
}

@Suppress("DEPRECATION")
fun String.setHtmlText(): Spanned {
    return if (SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun String.getWordCount(): Int {
    //Split String by Space
    val strArray = split(" ".toRegex()).toTypedArray() // Spilt String by Space
    var count = 0
    //iterate String array
    for (s in strArray) {
        if (s != "") {
            //Increase Word Counter
            count++
        }
    }
    return count
}

fun CharSequence.getDiscountedWords(noWords: Int): String {
    val strArray = split(" ".toRegex()).toTypedArray() // Spilt String by Space
    var count = 0
    val builder = StringBuilder()
    //iterate String array
    for (s in strArray) {
        if (s != "" && count < noWords) {
            //Increase Word Counter
            count++
            builder.append(s)
            if (count != noWords)
                builder.append(" ")
        } else
            break
    }
    return builder.toString()
}

fun Context.openUrlWithCustomTab(url: String) {
    try {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorBlack))
        builder.setShowTitle(true)

        // Animation for entering and exiting the Chrome Custom Tab
        builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
        builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, url.toUri())
    } catch (e: Exception) {
      // No Ops
    }
}
