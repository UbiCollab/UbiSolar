package com.sintef_energy.ubisolar.database.energy;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.sintef_energy.ubisolar.model.DeviceUsage;

import java.util.Date;

/**
 * Created by perok on 2/11/14.
 */
public class EnergyUsageModel extends DeviceUsage implements Parcelable, Comparable<EnergyUsageModel>{
    private static final String TAG = EnergyUsageModel.class.getName();

    @Override
    public int compareTo(EnergyUsageModel energyUsageModel) {
        long one = getDatetime().getTime();
        long two = energyUsageModel.getDatetime().getTime();

        if(one == two)
            return 0;
        else if(one > two)
            return 1;
        else
            return -1;
    }

    /* Column definitions*/
    public static interface EnergyUsageEntry extends BaseColumns {
        public static final String TABLE_NAME = "energy";
        public static final String COLUMN_DEVICE_ID = "fkdeviceid";
        public static final String COLUMN_DATETIME = "datetime";
        public static final String COLUMN_POWER = "power";
        public static final String COLUMN_IS_DELETED = "is_deleted";
        public static final String COLUMN_LAST_UPDATED = "lastUpdated";
    }

    public static final String[] projection = new String[]{
            EnergyUsageEntry._ID,
            EnergyUsageEntry.COLUMN_DEVICE_ID,
            EnergyUsageEntry.COLUMN_DATETIME,
            EnergyUsageEntry.COLUMN_POWER,
            EnergyUsageEntry.COLUMN_IS_DELETED,
            EnergyUsageEntry.COLUMN_LAST_UPDATED
    };

    /* SQL Statements*/
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EnergyUsageEntry.TABLE_NAME + " (" +
                    EnergyUsageEntry._ID + " INTEGER PRIMARY KEY," +
                    EnergyUsageEntry.COLUMN_DEVICE_ID + INTEGER_TYPE + COMMA_SEP +
                    EnergyUsageEntry.COLUMN_DATETIME + " DATE" + COMMA_SEP +
                    EnergyUsageEntry.COLUMN_POWER + REAL_TYPE + COMMA_SEP +
                    EnergyUsageEntry.COLUMN_IS_DELETED + INTEGER_TYPE + COMMA_SEP +
                    EnergyUsageEntry.COLUMN_LAST_UPDATED + INTEGER_TYPE + COMMA_SEP +
                    "FOREIGN KEY(" + EnergyUsageEntry.COLUMN_DEVICE_ID +
                        ") REFERENCES " + DeviceModel.DeviceEntry.TABLE_NAME +
                            "(" + DeviceModel.DeviceEntry._ID + ")" +

                    " )";
    //FOREIGN KEY(foreign_key_name) REFERENCES one_table_name(primary_key_name)

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + EnergyUsageEntry.TABLE_NAME;

    /* POJO */
    private int _id = 0;
    private int _deviceId = 1;
    private int _dateTime = 2;
    private int _power = 3;
    private int _is_deleted = 4;
    private int _lastUpdated = 5;


    /**
     * Create CalendarEventModel with default values. All relation ID's are '-1'
     */
    public EnergyUsageModel() {
        setId(-1);
        setDevice_id(-1);
        setDatetime(new Date(-1));
        setPower_usage(-1);
        setDeleted(false);
        setLastUpdated(-1);
    }

    /* Parcable */
    public EnergyUsageModel(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<EnergyUsageModel> CREATOR = new Creator<EnergyUsageModel>() {

        public EnergyUsageModel createFromParcel(Parcel in) {
            return new EnergyUsageModel(in);
        }

        public EnergyUsageModel[] newArray(int size) {
            return new EnergyUsageModel[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(getId());
        out.writeLong(getDevice_id());
        out.writeFloat(getDatetime().getTime());
        out.writeDouble(getPower_usage());
        out.writeInt((isDeleted() ? 1 : 0));
        out.writeLong(getLastUpdated());
    }

    private void readFromParcel(Parcel in) {
        setId(in.readLong());
        setDevice_id(in.readLong());
        setDatetime(new Date(in.readLong()));
        setPower_usage(in.readFloat());
        setDeleted(in.readInt() != 0);
        setLastUpdated(in.readLong());
    }

    /**
     * Get all the values in ContentValues class.
     * @return
     */
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(EnergyUsageEntry._ID, getId());
        values.put(EnergyUsageEntry.COLUMN_DEVICE_ID, getDevice_id());
        values.put(EnergyUsageEntry.COLUMN_DATETIME, getDatetime().getTime() / 1000L);
        values.put(EnergyUsageEntry.COLUMN_POWER, getPower_usage());
        values.put(EnergyUsageEntry.COLUMN_IS_DELETED, (isDeleted() ? 1 : 0));
        values.put(EnergyUsageEntry.COLUMN_LAST_UPDATED, getLastUpdated());
        return values;
    }

    /**
     * Create CalendarEventModel from cursor
     *
     * Short data is used in loaderManager. Else expect the entire projection,
     * @param cursor
     */
    public EnergyUsageModel(Cursor cursor, boolean isShortData) {
        setId(cursor.getLong(_id));
        setDevice_id(cursor.getLong(_deviceId));
        setDatetime(new Date(cursor.getLong(_dateTime) * 1000));
        setPower_usage(cursor.getDouble(_power));
        if(!isShortData){
            setDeleted(cursor.getInt(_is_deleted) != 0);
            setLastUpdated(cursor.getLong(_lastUpdated));
        }
    }

    public EnergyUsageModel(long id, long device_id, Date datetime, double power_usage) {
        super(id, device_id, datetime, power_usage);
    }

    @Override
    public String toString(){
        String info = "EnergyUsageModel:";
        info += "\n\t-> Id: " + getId();
        info += "\n\t-> Device id: " + getDevice_id();
        info += "\n\t-> Datetime: " + getDatetime();
        info += "\n\t-> Power usage: " + getPower_usage();
        info += "\n\t-> isDeleted: " + isDeleted();

        return info;
    }
}
