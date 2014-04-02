package com.sintef_energy.ubisolar.model;

/**
 * Created by thb on 19.02.14.
 */
public abstract class Device
{
    protected long device_id;
    protected long user_id;
    protected String name;
    protected String description;
    protected int category;

    public Device()
    {

    }

    public Device(long device_id, String name, String description, long user_id, int category) {
        this.device_id = device_id;
        this.name = name;
        this.description = description;
        this.user_id = user_id;
        this.category = category;
    }

    public long getDevice_id() {
        return device_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getUser_id() {
        return user_id;
    }

    public int getCategory() { return category; }

    public String toString() { return name; }

    public void setDevice_id(long device_id) {
        this.device_id = device_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
