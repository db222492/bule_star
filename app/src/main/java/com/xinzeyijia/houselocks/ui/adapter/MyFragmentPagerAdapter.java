package com.xinzeyijia.houselocks.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private List<String> titles;

    public void setFragmentsAndTitle(List<Fragment> fragments,List<String> titles) {
        this.mList = fragments;
        this.titles = titles;
        notifyDataSetChanged();
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        this.mList = list;
        this.titles = titles;

    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.titles == null ? null : this.titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return this.mList == null ? null : this.mList.get(position);
    }

    @Override
    public int getCount() {
        return this.mList == null ? 0 : this.mList.size();
    }
}