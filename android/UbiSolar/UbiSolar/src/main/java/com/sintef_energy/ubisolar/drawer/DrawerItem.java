package com.sintef_energy.ubisolar.drawer;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sintef_energy.ubisolar.R;
import com.sintef_energy.ubisolar.adapter.NavDrawerListAdapter;

/**
 * Created by Håvard on 02.04.14.
 */
public class DrawerItem implements Item {
    private String         str1;
    private String         str2;

    public DrawerItem(String text1, String text2) {
        this.str1 = text1;
        this.str2 = text2;
    }

    @Override
    public int getViewType() {
        return NavDrawerListAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.drawer_list_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text1 = (TextView) view.findViewById(R.id.title);
        TextView text2 = (TextView) view.findViewById(R.id.counter);
        text1.setText(str1);
        text2.setText(str2);

        return view;
    }

    public void setTitle(String str1) {
        this.str1 = str1;
    }
}
