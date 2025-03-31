package dev.redfox.anisearch.ui.overview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dev.redfox.anisearch.databinding.ItemTagGenresBinding
import dev.redfox.anisearch.utils.StringListDiffCallback

class GenresAdapter(
    var mContext: Context
) : RecyclerView.Adapter<ViewHolder>() {

    private val tagsList: ArrayList<String> by lazy {
        ArrayList()
    }

    private inner class TagsViewHolder(private val binding: ItemTagGenresBinding) :
        ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.tvTagItemText.text = tagsList[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TagsViewHolder(
            ItemTagGenresBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = tagsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as TagsViewHolder).bind(position)
    }

    fun updateData(newList: List<String>) {
        val diffCallback = StringListDiffCallback(tagsList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        tagsList.clear()
        tagsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}