package com.example.a72button;

import androidx.annotation.NonNull;

import org.w3c.dom.NodeList;

public class automaton {    String id;
    String name;
    String city;
    String address;
    String country;
    String availability;

    public automaton(String id, String name, String city, String address, String country, String availability) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.country = country;
        this.availability = availability;
    }


    @NonNull
    @Override
    public String toString() {
        String spinnerText = city + " " +  address;
        return spinnerText;
    }


}