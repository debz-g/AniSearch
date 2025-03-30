package dev.redfox.anisearch.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 * This function includes animation
 */
fun Fragment.replaceChildFragment(
    fragment: Fragment, @IdRes frameId: Int,
    addBackStack: String? = null
) {
    try {
        childFragmentManager.beginTransaction().apply {
            if (addBackStack != null) {
                addToBackStack(addBackStack)
            }
            replace(frameId, fragment)
            commitAllowingStateLoss()
        }
    } catch (ex: Exception) {
        ex.showLog()
    }
}