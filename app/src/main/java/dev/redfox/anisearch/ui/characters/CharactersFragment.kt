package dev.redfox.anisearch.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import dev.redfox.anisearch.R
import dev.redfox.anisearch.databinding.FragmentCharactersBinding
import dev.redfox.anisearch.ui.characters.adapter.CharactersAdapter
import dev.redfox.anisearch.ui.episodes.EpisodesFragment
import dev.redfox.anisearch.utils.PARAM_ID
import dev.redfox.anisearch.utils.getDimensionPxSize
import dev.redfox.anisearch.utils.getModelView
import dev.redfox.anisearch.utils.setMargins
import dev.redfox.anisearch.utils.showToast
import dev.redfox.anisearch.viewmodel.CharactersViewModel

class CharactersFragment : Fragment() {
    private val binding: FragmentCharactersBinding by lazy {
        FragmentCharactersBinding.inflate(layoutInflater)
    }
    private lateinit var mContext: Context
    private lateinit var charactersVm: CharactersViewModel
    private lateinit var charactersAdapter: CharactersAdapter

    companion object {
        fun getInstance(malId: Int): CharactersFragment {
            val fragment = CharactersFragment()
            fragment.arguments = Bundle().apply {
                putInt(PARAM_ID, malId)
            }
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        charactersVm = getModelView(CharactersViewModel()) as CharactersViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        charactersVm.handleExtras(arguments)
    }

    private fun setupRecyclerView() {
        charactersAdapter = CharactersAdapter(mContext)

        binding.rvCharacters.apply {
            setHasFixedSize(false)
            val horizontalMargin = mContext.getDimensionPxSize(R.dimen.dimen16)
            setMargins(left = horizontalMargin, right = horizontalMargin)
            layoutManager = LinearLayoutManager(mContext)
            isNestedScrollingEnabled = false
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = charactersAdapter
        }
    }

    private fun observeViewModel() {
        charactersVm.characters.observe(viewLifecycleOwner) { characters ->
            charactersAdapter.submitList(characters)
        }

        charactersVm.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.rvCharacters.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        charactersVm.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                mContext.showToast(it)
                charactersVm.clearError()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        charactersVm.getMalId()?.let {
            if (charactersAdapter.currentList.isEmpty()) {
                charactersVm.fetchCharacters()
            }
        }
    }
}