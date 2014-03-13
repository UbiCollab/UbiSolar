package com.sintef_energy.ubisolar.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;

import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.sintef_energy.ubisolar.IView.IDeviceView;
import com.sintef_energy.ubisolar.R;
import com.sintef_energy.ubisolar.activities.AddDeviceEnergyActivity;
import com.sintef_energy.ubisolar.activities.DrawerActivity;
import com.sintef_energy.ubisolar.database.energy.DeviceModel;
import com.sintef_energy.ubisolar.database.energy.EnergyContract;
import com.sintef_energy.ubisolar.database.energy.EnergyDataSource;
import com.sintef_energy.ubisolar.database.energy.EnergyUsageModel;

import java.util.ArrayList;

/**
 * Created by perok on 2/11/14.
 */
public class DeviceFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, IDeviceView {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String TAG = DeviceFragment.class.getName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private Button addButton;
    private EditText nameField, descriptionField;
    private EnergyUsageModel usageField;
    private SimpleCursorAdapter adapter;
    private ArrayList<DeviceModel> devices;
    private ArrayList<EnergyUsageModel> usage;
    private View view;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DeviceFragment newInstance(int sectionNumber) {
        DeviceFragment fragment = new DeviceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public DeviceFragment() {
    }



    /**
     * The first call to a created fragment
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Callback to activity
        ((DrawerActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_device, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //There was some reason I could not create the list view outside of this method
        final ListView listView = (ListView) getActivity().findViewById(R.id.device_list);
        devices = new ArrayList<DeviceModel>();
        usage = new ArrayList<EnergyUsageModel>();

        adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
                R.layout.fragment_device_row,
                null,
                new String[]{DeviceModel.DeviceEntry.COLUMN_NAME, DeviceModel.DeviceEntry.COLUMN_DESCRIPTION},
                new int[]{R.id.row_header, R.id.row_description}, 0);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v(TAG, "Du klikka på listeItem nummer: " + i);
            }
        });

        nameField = (EditText) getActivity().findViewById(R.id.edit_name);
        descriptionField = (EditText) getActivity().findViewById(R.id.edit_description);
        //usageField = (EnergyUsageModel) getActivity().findViewById(R.id.edit_usage);
        addButton = (Button) getActivity().findViewById(R.id.add_button);

        //EnergyDataSource.deleteAll(getActivity().getContentResolver());

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                adapter = null;

                DeviceModel deviceModel = new DeviceModel();
                deviceModel.setUser_id(System.currentTimeMillis());
                deviceModel.setDevice_id(System.currentTimeMillis());
                deviceModel.setDescription(descriptionField.getText().toString());
                deviceModel.setName(nameField.getText().toString());
                devices.add(deviceModel);
                //devices.add(deviceModel);

                EnergyDataSource.insertDevice(getActivity().getContentResolver(), deviceModel);


                adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
                        R.layout.fragment_device_row,
                        null,
                        new String[]{DeviceModel.DeviceEntry.COLUMN_NAME, DeviceModel.DeviceEntry.COLUMN_DESCRIPTION},
                        new int[]{R.id.row_header, R.id.row_description}, 0);

                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        if (savedInstanceState != null) {
            // Restore last state for checked position.


        }

        getLoaderManager().initLoader(0, null, this);

    }

    /*End lifecycle*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), EnergyContract.Devices.CONTENT_URI,
                EnergyContract.Devices.PROJECTION_ALL, null, null,
                BaseColumns._ID + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        ((SimpleCursorAdapter) this.adapter).swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ((SimpleCursorAdapter)this.adapter).swapCursor(null);
    }

    //SPØRSMÅL : Bør disse være i presenteren? Eller her OG i presenteren?
    @Override
    public void addDevice(DeviceModel model) {

    }

    @Override
    public void deleteDevice(DeviceModel model) {

    }

    @Override
    public void changeDevice(DeviceModel model, String name, String description) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_device, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection OBS: copy paste
        /*switch (item.getItemId()) {
            case R.id.fragment_usage_menu_add:
                Intent intent = new Intent(this.getActivity(), AddDeviceEnergyActivity.class);
                startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/

        //TODO: Show the addusage acivity when clicked
    }
}