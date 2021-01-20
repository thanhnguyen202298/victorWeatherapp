package com.victorthanh.utilslib.presentation.base.adapter

import android.util.Log
import android.view.ViewGroup
import com.victorthanh.utilslib.presentation.base.BaseFragment
import java.util.*

class FragmentPagerAdapter(fm: androidx.fragment.app.FragmentManager, vararg fragments: androidx.fragment.app.Fragment) : androidx.fragment.app.FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{

    //region Properties
    private val listFragment: MutableList<androidx.fragment.app.Fragment>

    init {
        listFragment = ArrayList(Arrays.asList(*fragments))
    }
    //endregion

    //region Getter - Setter
    fun getListFragment(): List<androidx.fragment.app.Fragment> {
        return listFragment
    }

    fun setListFragment(vararg fragments: androidx.fragment.app.Fragment) {
        this.listFragment.addAll(Arrays.asList(*fragments))
    }
    //endregion

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return this.listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return (listFragment[position] as BaseFragment).title
    }

    override fun finishUpdate(container: ViewGroup) {
        try {
            super.finishUpdate(container)
        } catch (nullPointerException: NullPointerException) {
            Log.e("<frag erroer","Catch the NullPointerException in FragmentPagerAdapter.finishUpdate")
        }

    }
}