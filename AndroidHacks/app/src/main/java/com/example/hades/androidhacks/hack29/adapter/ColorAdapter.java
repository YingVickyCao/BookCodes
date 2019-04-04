/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack29.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hades.androidhacks.hack29.fragment.ColorFragment;

public class ColorAdapter extends FragmentStatePagerAdapter {
    public static final int[] COLORS = {Color.MAGENTA, Color.BLUE,
            Color.CYAN, Color.GREEN, Color.RED, Color.WHITE, Color.MAGENTA};

    public ColorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ColorFragment.newInstance(COLORS[position], "" + position, (position % 2) == 0);
    }

    @Override
    public int getCount() {
        return COLORS.length;
    }

    public boolean usesLandscape(int position) {
        return (position % 2) == 0;
    }
}
