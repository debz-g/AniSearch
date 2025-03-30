package dev.redfox.anisearch.ui.customViews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import dev.redfox.anisearch.R
import dev.redfox.anisearch.databinding.ViewInfoCardBinding

/**
 * A custom view that displays an information card with an image, primary text, and secondary text.
 * All three elements can be set via XML attributes or programmatically.
 *
 * This implementation uses view binding for improved type safety and null safety.
 */
class InfoCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewInfoCardBinding by lazy {
        ViewInfoCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        // Process custom attributes if available
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.InfoCardView)
            try {
                setAttributes(typedArray)
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun setAttributes(typedArray: TypedArray) {
        // Set image drawable
        val drawable = typedArray.getDrawable(R.styleable.InfoCardView_imageDrawable)
        drawable?.let { setImageDrawable(it) }

        // Set primary text
        val primaryText = typedArray.getString(R.styleable.InfoCardView_primaryText)
        primaryText?.let { setPrimaryText(it) }

        // Set secondary text
        val secondaryText = typedArray.getString(R.styleable.InfoCardView_secondaryText)
        secondaryText?.let { setSecondaryText(it) }
    }

    /**
     * Sets the image drawable for the view.
     *
     * @param drawable The drawable to set.
     */
    fun setImageDrawable(drawable: Drawable) {
        binding.infoCardImage.setImageDrawable(drawable)
    }

    /**
     * Sets the image resource for the view.
     *
     * @param resId The resource ID of the drawable to set.
     */
    fun setImageResource(@DrawableRes resId: Int) {
        binding.infoCardImage.setImageResource(resId)
    }

    /**
     * Sets the primary text.
     *
     * @param text The text to set.
     */
    fun setPrimaryText(text: String) {
        binding.infoCardPrimaryText.text = text
    }

    /**
     * Sets the primary text using a string resource.
     *
     * @param resId The resource ID of the string to set.
     */
    fun setPrimaryText(@StringRes resId: Int) {
        binding.infoCardPrimaryText.setText(resId)
    }

    /**
     * Sets the secondary text.
     *
     * @param text The text to set.
     */
    fun setSecondaryText(text: String) {
        binding.infoCardSecondaryText.text = text
    }

    /**
     * Sets the secondary text using a string resource.
     *
     * @param resId The resource ID of the string to set.
     */
    fun setSecondaryText(@StringRes resId: Int) {
        binding.infoCardSecondaryText.setText(resId)
    }

    /**
     * Gets the current image drawable.
     *
     * @return The current drawable or null if none is set.
     */
    fun getImageDrawable(): Drawable? = binding.infoCardImage.drawable

    /**
     * Gets the current primary text.
     *
     * @return The current primary text.
     */
    fun getPrimaryText(): CharSequence = binding.infoCardPrimaryText.text

    /**
     * Gets the current secondary text.
     *
     * @return The current secondary text.
     */
    fun getSecondaryText(): CharSequence = binding.infoCardSecondaryText.text
}