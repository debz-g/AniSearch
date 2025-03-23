package dev.redfox.anisearch.ui.favourite.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.redfox.anisearch.MainActivity
import dev.redfox.anisearch.databinding.FragmentFavouriteAnimeBinding

class FavouriteAnimeFragment : Fragment() {
    private val binding: FragmentFavouriteAnimeBinding by lazy {
        FragmentFavouriteAnimeBinding.inflate(layoutInflater)
    }

    private lateinit var mContext: Context
    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        parentActivity = mContext as MainActivity
    }

    companion object {
        fun getInstance() = FavouriteAnimeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}