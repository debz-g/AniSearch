package dev.redfox.anisearch.viewmodel

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.redfox.anisearch.R
import dev.redfox.anisearch.models.AnimeChildData
import dev.redfox.anisearch.models.OverviewData
import dev.redfox.anisearch.network.AnimeRepository
import dev.redfox.anisearch.network.RetrofitClient
import dev.redfox.anisearch.utils.DESCRIPTION_WORD_LIMIT
import dev.redfox.anisearch.utils.PARAM_DATA
import dev.redfox.anisearch.utils.PARAM_DATA_ALT
import dev.redfox.anisearch.utils.getColorCompat
import dev.redfox.anisearch.utils.getDiscountedWords
import dev.redfox.anisearch.utils.getWordCount
import dev.redfox.anisearch.utils.parcelable
import dev.redfox.anisearch.utils.setHtmlText
import dev.redfox.anisearch.utils.showLog
import kotlin.Boolean

class AnimeDetailsViewModel : ViewModel() {

    private val animeRepository: AnimeRepository by lazy {
        AnimeRepository(RetrofitClient.apiService)
    }

    private var animeChildData: AnimeChildData? = null
    private var overviewData: OverviewData? = null

    fun handleExtras(extras: Bundle?) {
        extras?.let { args ->
            args.parcelable<AnimeChildData>(PARAM_DATA)?.let { data ->
                animeChildData = data
            }
            args.parcelable<OverviewData>(PARAM_DATA_ALT)?.let { data ->
                overviewData = data
            }
        }
    }

    fun getAnimeChildData() = animeChildData
    fun getOverviewData() = overviewData
    fun getMalId() = animeChildData?.malId

    fun getMalLink() = animeChildData?.linkMAL
    fun getTrailerLink() = animeChildData?.linkTrailer

    fun getTabList(mContext: Context): Array<String> =
        mContext.resources.getStringArray(R.array.anime_details_series_tabs)


    val descriptionSpanClickObserver: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    fun getFullAbout(mContext: Context): SpannableString? {
        return animeChildData?.let {
            it.description?.let { description ->
                val descriptionString =
                    description.setHtmlText()
                val stringBuilder = StringBuilder()
                    .append(descriptionString)
                    .append("\n${mContext.getString(R.string.read_less)}")
                val spannable = SpannableString(stringBuilder)
                val indexOfRead = stringBuilder.lastIndexOf("Read")
                spannable.setSpan(object : ClickableSpan() {
                    override fun onClick(p0: View) {
                        showLog("READ LESS CLICKED", "READ")
                        descriptionSpanClickObserver.value = false
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = mContext.getColorCompat(R.color.colorWhite)
                        ds.isUnderlineText = false
                    }
                }, indexOfRead, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable
            }
        }
    }

    private fun isDescriptionLong() =
        ((animeChildData?.description?.getWordCount() ?: 0) > DESCRIPTION_WORD_LIMIT)

    fun getTrimmedAbout(mContext: Context): SpannableString? {
        return animeChildData?.let {
            it.description?.let { description ->
                val descriptionString =
                    description.setHtmlText()
                if (isDescriptionLong()) {
                    val aboutString = StringBuilder()
                        .append(descriptionString.getDiscountedWords(DESCRIPTION_WORD_LIMIT))
                        .append(" ... ${mContext.getString(R.string.read_more)}")
                        .toString()
                    val spannable = SpannableString(aboutString)
                    val indexOfRead = aboutString.lastIndexOf("Read")
                    spannable.setSpan(object : ClickableSpan() {
                        override fun onClick(p0: View) {
                            showLog("READ MORE CLICKED", "READ")
                            descriptionSpanClickObserver.value = true
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.color = mContext.getColorCompat(R.color.colorWhite)
                            ds.isUnderlineText = false
                        }
                    }, indexOfRead, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannable
                } else {
                    SpannableString(descriptionString)
                }
            }
        }
    }

}