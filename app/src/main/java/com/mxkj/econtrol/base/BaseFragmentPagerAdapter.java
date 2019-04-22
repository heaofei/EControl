package com.mxkj.econtrol.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.ui.fragment.AirConditionerFragment;
import com.mxkj.econtrol.ui.fragment.BlowerFragment;
import com.mxkj.econtrol.ui.fragment.LightFragment;
import com.mxkj.econtrol.utils.ResourceUtil;
import com.mxkj.econtrol.view.fragment.UseNumberFragment;
import com.mxkj.econtrol.view.fragment.UseTimeFragment;

import java.util.List;

/**
 * Created by chanjun on 8/10/2016.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private String tabTitles[];

    public BaseFragmentPagerAdapter(FragmentManager fm , List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fragment = fragmentList.get(position);
        String title = "";
        if (fragment instanceof UseTimeFragment) {
            title = ResourceUtil.getString(R.string.fragment_statistics_usetime);
        } else if (fragment instanceof UseNumberFragment) {
            title = ResourceUtil.getString(R.string.fragment_statistics_usenumber);
        }
        return title;
    }
}
