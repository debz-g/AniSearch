package dev.redfox.anisearch.ui.characters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dev.redfox.anisearch.R
import dev.redfox.anisearch.databinding.ItemCharactersBinding
import dev.redfox.anisearch.models.CharacterData
import dev.redfox.anisearch.utils.CharactersDiffCallback

class CharactersAdapter(private val mContext: Context) :
    ListAdapter<CharacterData, CharactersAdapter.CharacterViewHolder>(CharactersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharactersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharacterViewHolder(private val binding: ItemCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharacterData) {
            binding.apply {
                tvCharactersName.text =
                    mContext.getString(R.string.character_name, item.character.name)
                tvCharactersRole.text = mContext.getString(R.string.character_role, item.role)

                Glide.with(mContext)
                    .load(item.character.images.jpg.imageUrl)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.user_placeholder)
                            .error(R.drawable.user_placeholder)
                    )
                    .into(ivCharactersImage)
            }
        }
    }
}