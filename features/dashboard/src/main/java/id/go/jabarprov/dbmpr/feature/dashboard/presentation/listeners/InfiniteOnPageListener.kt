package id.go.jabarprov.dbmpr.feature.dashboard.presentation.listeners

import androidx.viewpager2.widget.ViewPager2

class InfiniteOnPageListener(listSize: Int) : ViewPager2.OnPageChangeCallback() {

    private val listSize = listSize + 2

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
    }
}