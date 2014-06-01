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


/**
 * Created by Håvard on 05.03.14.
 */
public class Tip {
    private int id;
    private String name;
    private String description;
    private int averageRating;
    private int numberOfRatings;

    public Tip(int id, String name, String description, int averageRating, int numberOfRatings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.averageRating = averageRating;
        this.numberOfRatings = numberOfRatings;
    }

    public Tip() {
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return this.name + ": " + this.description + " | " + this.averageRating + ":"
                + this.numberOfRatings;
    }
}
