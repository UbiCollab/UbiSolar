/*
 * Licensed to UbiCollab.org under one or more contributor
 * license agreements.  See the NOTICE file distributed
 * with this work for additional information regarding
 * copyright ownership. UbiCollab.org licenses this file
 * to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.sintef_energy.ubisolar.model;

import java.util.Date;

/**
 * Created by thb on 19.02.14.
 */
public class TotalUsage
{
    private double power_usage;
    private Date datetime;
    private long user_id;

    private TotalUsage()
    {

    }

    public TotalUsage(int user_id, long datetime, double power_usage) {
        this(user_id, new Date(datetime), power_usage);
    }

    public TotalUsage(long user_id, Date datetime, double power_usage) {
        this.power_usage = power_usage;
        this.datetime = datetime;
        this.user_id = user_id;
    }


    public double getPower_usage() {
        return power_usage;
    }

    public Date getDatetime() {
        return datetime;
    }

    public long getUser_id() {
        return user_id;
    }

//    public String getFormatedDate()
//    {
//        System.out.println(datetime);
//        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
//        return ft.format(datetime);
//    }
}
