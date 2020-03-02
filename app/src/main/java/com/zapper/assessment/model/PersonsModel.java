package com.zapper.assessment.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zapper.assessment.utils.AppConstants;

import java.util.List;


public class PersonsModel {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("persons")
    @Expose
    private List<Person> persons = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Entity(tableName = AppConstants.TABLE_PERSON)
    public static class Person {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("id")
        @Expose
        @PrimaryKey(autoGenerate = true)
        public int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Person(String name) {
            this.name = name;
        }

        public Person(){}
    }
}