package mubble.estaoagrometeorolgica.presenter.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Criado por Igor em 28/01/2018.
 */

public class AbasAdapter extends FragmentPagerAdapter {

    private String[] abas;
    private List<Fragment> fragments;

    public AbasAdapter(FragmentManager fm, List<Fragment> fragments, String[] abas) {
        super(fm);
        this.fragments = fragments;
        this.abas = abas;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return abas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return abas[position];
    }

}
