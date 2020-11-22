package edu.pucmm.isc581.parcial2isc581.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import edu.pucmm.isc581.parcial2isc581.R;
import edu.pucmm.isc581.parcial2isc581.fragments.ListCategoriesFragment;
import edu.pucmm.isc581.parcial2isc581.fragments.ListProductsFragment;

public class TabsLayoutAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = {R.string.productos, R.string.categorias};
    private final Context mContext;

    public TabsLayoutAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ListProductsFragment.newInstance();
            case 1:
                return ListCategoriesFragment.newInstance();
            default:
                return null;
        }
    }

  @Override
  public CharSequence getPageTitle(int position) {
    return mContext.getResources().getString(TAB_TITLES[position]);
  }
  @Override
  public int getCount() {
    return 2;
  }

}
