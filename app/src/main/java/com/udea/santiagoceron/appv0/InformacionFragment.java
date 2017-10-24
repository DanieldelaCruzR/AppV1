package com.udea.santiagoceron.appv0;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class InformacionFragment extends Fragment {


    public InformacionFragment() {
        // Required empty public constructor
    }

    private AppBarLayout appBar;
    private TabLayout tabs;
    private android.support.v4.view.ViewPager ViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_informacion, container, false);

        View contenedor= (View)container.getParent();


        ViewPager=(ViewPager)view.findViewById(R.id.pagerTabs);
        InformacionFragment.ViewPagerAdapter pagerAdapater= new InformacionFragment.ViewPagerAdapter(getFragmentManager());
        ViewPager.setAdapter(pagerAdapater);


        return view;
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        String[] tituloTabs={"Domiti", "Creadores"};

        @Override
        public Fragment getItem(int position){
            switch (position){
                case 0: return new InfoUnoFragment();
                case 1: return new InfoDosFragment();

            }
            return null;
        }
        @Override
        public int getCount(){
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position){
            return tituloTabs[position];
        }

    }

}
