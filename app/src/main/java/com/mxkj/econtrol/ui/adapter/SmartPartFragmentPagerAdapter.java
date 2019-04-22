package com.mxkj.econtrol.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.ui.fragment.AGFragment;
import com.mxkj.econtrol.ui.fragment.AirConditionerFragment;
import com.mxkj.econtrol.ui.fragment.BlowerFragment;
import com.mxkj.econtrol.ui.fragment.CurtainsFragment;
import com.mxkj.econtrol.ui.fragment.LightFragment;
import com.mxkj.econtrol.ui.fragment.LockFragment;
import com.mxkj.econtrol.utils.ResourceUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/3/31.
 *
 * @Description:
 */

public class SmartPartFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mFragmentTitle;
    private FragmentManager mFragmentManager;


    public SmartPartFragmentPagerAdapter(FragmentManager manager, List<Fragment> fragments,List<String> mFragmentTitle) {
        super(manager);
        mFragments = fragments;
        this.mFragmentTitle = mFragmentTitle;
        mFragmentManager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


    @Override
    public CharSequence getPageTitle(int position) {
       /* Fragment fragment = mFragments.get(position);
        String title = "";
        if (fragment instanceof LightFragment) {
            title = ResourceUtil.getString(R.string.light);
        } else if (fragment instanceof AirConditionerFragment) {
            title = ResourceUtil.getString(R.string.AirConditioner);
        } else if (fragment instanceof BlowerFragment) {
            title = ResourceUtil.getString(R.string.Blower);
        }else if (fragment instanceof CurtainsFragment) {
            title = ResourceUtil.getString(R.string.curtains);
        }else if (fragment instanceof LockFragment) {
            title = ResourceUtil.getString(R.string.lock);
        }else if (fragment instanceof AGFragment) {
            title ="玻璃";
        }
        return title;*/


        return  mFragmentTitle.get(position);
    }



}