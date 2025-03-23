package dev.redfox.anisearch

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import dev.redfox.anisearch.databinding.ActivityMainBinding
import dev.redfox.anisearch.databinding.ItemBottomNavigationBinding
import dev.redfox.anisearch.models.TabIconPair
import dev.redfox.anisearch.ui.favourite.fragment.FavouriteAnimeFragment
import dev.redfox.anisearch.ui.news.fragment.NewsAnimeFragment
import dev.redfox.anisearch.ui.schedule.fragment.ScheduleAnimeFragment
import dev.redfox.anisearch.ui.topAnime.fragment.TopAnimeFragment
import dev.redfox.anisearch.utils.changeBackgroundDrawableColor
import dev.redfox.anisearch.utils.getColorCompat
import dev.redfox.anisearch.utils.hide
import dev.redfox.anisearch.utils.showLog

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var currentSelectedTabPosition = 0
    private val fragmentMap = mutableMapOf<Int, Fragment>()
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setNavigationBarInsets()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupTabLayout()
        if (savedInstanceState == null) {
            showLog("Initialize Fragment", "FragTransaction")
            initializeFragments()
            showFragment(0) // Show first tab by default
        } else {
            showLog("Restore Fragment", "FragTransaction")
            restoreFragments()
        }
        setupBackPressHandler()
    }

    private fun setNavigationBarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val navHeight = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            if (navHeight != 0) {
                binding.mainNavigationPlaceholder.apply {
                    layoutParams = layoutParams.apply { height = navHeight }
                    changeBackgroundDrawableColor(getColorCompat(R.color.colorBlack))
                }
            } else
                binding.mainNavigationPlaceholder.hide()
            windowInsets
        }
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.tabLayout.selectedTabPosition != 0) {
                    // Switch to home tab if not already there
                    binding.tabLayout.getTabAt(0)?.select()
                } else {
                    // Finish the activity when on home tab
                    this@MainActivity.finish()
                }
            }
        })
    }

    private fun setupTabLayout() {
        showLog("Initialize Tab layout", "FragTransaction")
        binding.tabLayout.removeAllTabs()
        addTab(0, getString(R.string.explore), R.drawable.ic_explore_inactive, R.drawable.ic_explore_active)
        addTab(1, getString(R.string.schedule), R.drawable.ic_schedule_inactive, R.drawable.ic_schedule_active)
        addTab(2, getString(R.string.news), R.drawable.ic_news_inactive, R.drawable.ic_news_active)
        addTab(3, getString(R.string.favourites), R.drawable.ic_favourite_inactive, R.drawable.ic_favourite_active)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                currentSelectedTabPosition = tab.position
                showFragment(tab.position)
                updateTabAppearance(tab, isSelected = true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                updateTabAppearance(tab, isSelected = false)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                if (currentSelectedTabPosition == 0) {
                    (fragmentMap[currentSelectedTabPosition] as TopAnimeFragment).scrollToTop()
                }
            }
        })
        binding.tabLayout.post {
            binding.tabLayout.getTabAt(0)?.select()
        }
    }

    private fun addTab(position: Int, title: String, inactiveIconRes: Int, activeIconRes: Int) {
        val tab = binding.tabLayout.newTab()
        val bindingTab = ItemBottomNavigationBinding.inflate(LayoutInflater.from(this))

        val iconPair = TabIconPair(inactiveIconRes, activeIconRes)
        tab.tag = iconPair
        bindingTab.tabIcon.setImageResource(inactiveIconRes)
        bindingTab.tabTitle.text = title
        tab.customView = bindingTab.root
        binding.tabLayout.addTab(tab)
        if (position == 0) {
            binding.tabLayout.post {
                updateTabAppearance(tab, isSelected = true)
            }
        }
    }

    private fun updateTabAppearance(tab: TabLayout.Tab, isSelected: Boolean) {
        tab.customView?.let { view ->
            val binding = ItemBottomNavigationBinding.bind(view)
            val iconPair = tab.tag as? TabIconPair

            iconPair?.let { (inactiveIcon, activeIcon) ->
                if (isSelected) {
                    binding.tabIcon.setImageResource(activeIcon)
                    binding.tabTitle.setTextColor(getColorCompat(R.color.colorWhite))
                } else {
                    binding.tabIcon.setImageResource(inactiveIcon)
                    binding.tabTitle.setTextColor(getColorCompat(R.color.colorGray400))
                }
            }
        }
    }

    private fun initializeFragments() {
        fragmentMap[0] = TopAnimeFragment.getInstance()
        fragmentMap[1] = ScheduleAnimeFragment.getInstance()
        fragmentMap[2] = NewsAnimeFragment.getInstance()
        fragmentMap[3] = FavouriteAnimeFragment.getInstance()
    }

    private fun restoreFragments() {
        supportFragmentManager.fragments.forEach { fragment ->
            when (fragment) {
                is TopAnimeFragment -> fragmentMap[0] = fragment
                is ScheduleAnimeFragment -> fragmentMap[1] = fragment
                is NewsAnimeFragment -> fragmentMap[2] = fragment
                is FavouriteAnimeFragment -> fragmentMap[3] = fragment
            }
        }
        currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main_container)
    }

    private fun showFragment(position: Int) {
        val fragment = fragmentMap[position] ?: return
        val transaction = supportFragmentManager.beginTransaction()
        showLog("Fragment Map : $fragmentMap", "FragTransaction")

        // Hide current fragment if exists
        currentFragment?.let {
            transaction.hide(it)
        }

        // Special handling for ScheduleAnimeFragment (position 1)
        if (position == 1) {
            // Remove existing ScheduleAnimeFragment if it exists
            fragmentMap[1]?.let { oldFragment ->
                if (oldFragment.isAdded) {
                    transaction.remove(oldFragment)
                }
            }
            // Create a new instance
            val newFragment = ScheduleAnimeFragment.getInstance()
            fragmentMap[1] = newFragment
            showLog("Add Fresh MyListFragment :$newFragment", "FragTransaction")
            transaction.add(
                R.id.fcv_main_container,
                newFragment,
                "fragment_$position"
            )
            currentFragment = newFragment
        } else {
            // Original logic for other fragments
            if (fragment.isAdded) {
                showLog("Show Fragment :$fragment", "FragTransaction")
                transaction.show(fragment)
            } else {
                showLog("Add Fragment :$fragment", "FragTransaction")
                transaction.add(
                    R.id.fcv_main_container,
                    fragment,
                    "fragment_$position"
                )
            }
            currentFragment = fragment
        }
        transaction.commitAllowingStateLoss()
    }
}