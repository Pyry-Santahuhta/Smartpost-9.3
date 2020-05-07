package com.example.a72button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static java.lang.String.format;


public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    Button timePickerButton;
    Spinner automatSpinner, locationSpinner, weekDaySpinner;
    ArrayList<automaton> automatonList = new ArrayList<>();
    ArrayList<automaton> sortedAutomatonList = new ArrayList<>();
    ArrayList<String> countryList = new ArrayList<>(Arrays.asList("Kaikki", "Suomi", "Eesti"));
    ArrayAdapter<automaton> listAdapter;
    ArrayAdapter<String> stringArrayAdapter;
    ArrayAdapter<weekdayAdapter.weekDay> weekDayArrayAdapter;
    Context context = null;
    TextView weekDayText, console;
    private weekdayAdapter w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        timePickerButton = findViewById(R.id.timePickerButton);
        timePickerButton.setText("12:00");
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment TimePicker = new TimePickerFragment();
                TimePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        weekDaySpinner = findViewById(R.id.weekDaySpinner);
        automatSpinner = findViewById(R.id.automatsSpinner);
        locationSpinner = findViewById(R.id.locationSpinner);
        weekDayText = findViewById(R.id.weekday);
        console = findViewById(R.id.console);
        w = new weekdayAdapter();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortedAutomatonList);
        listAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryList);
        stringArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        weekDayArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, w.weekDays);
        weekDayArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        context = MainActivity.this;
        automatSpinner.setAdapter(listAdapter);
        locationSpinner.setAdapter(stringArrayAdapter);
        weekDaySpinner.setAdapter(weekDayArrayAdapter);
        AsyncTaskRunner readXml = new AsyncTaskRunner();
        readXml.doInBackground();
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.clear();
                if(locationSpinner.getSelectedItem().equals("Suomi")){
                    for (automaton a : automatonList){
                        if ((a.country.equals("FI") && (a.availability.contains(weekDaySpinner.getSelectedItem().toString() )))){
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                        else if ((a.country.equals("FI") &&weekDaySpinner.getSelectedItem().toString().equals("Kaikki"))){
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }
                else if(locationSpinner.getSelectedItem().equals("Eesti")){
                    listAdapter.clear();
                    for (automaton a : automatonList){
                        if ((a.country.equals("EE") && (a.availability.contains(weekDaySpinner.getSelectedItem().toString())))){ /* insert availability check */
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                        else if ((a.country.equals("EE") &&weekDaySpinner.getSelectedItem().toString().equals("Kaikki"))){
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }
                else if(locationSpinner.getSelectedItem().equals("Kaikki")){
                    for (automaton a : automatonList){
                         if(a.availability.contains(weekDaySpinner.getSelectedItem().toString())){
                             listAdapter.add(a);
                             listAdapter.notifyDataSetChanged();
                         }
                         else if ((weekDaySpinner.getSelectedItem().toString().equals("Kaikki"))){
                             listAdapter.add(a);
                             listAdapter.notifyDataSetChanged();
                         }

                    }
                 }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        weekDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.clear();
                if(locationSpinner.getSelectedItem().equals("Suomi")){
                    for (automaton a : automatonList){
                        if ((a.country.equals("FI")) && (a.availability.contains(weekDaySpinner.getSelectedItem().toString()))){
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                        else if ((a.country.equals("FI") &&weekDaySpinner.getSelectedItem().toString().equals("Kaikki"))){
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }
                else if(locationSpinner.getSelectedItem().equals("Eesti")){
                    listAdapter.clear();
                    for (automaton a : automatonList){
                        if ((a.country.equals("EE")) && (a.availability.contains(weekDaySpinner.getSelectedItem().toString()))){
                            System.out.println(weekDaySpinner.getSelectedItem().toString());
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                        else if ((a.country.equals("EE") &&weekDaySpinner.getSelectedItem().toString().equals("Kaikki"))){
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }
                else if(locationSpinner.getSelectedItem().equals("Kaikki")){
                    for (automaton a : automatonList){
                        if(a.availability.contains(weekDaySpinner.getSelectedItem().toString())){
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                        else if (weekDaySpinner.getSelectedItem().toString().equals("Kaikki")){
                            listAdapter.add(a);
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        automatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (automaton a : automatonList) {
                    if ((a.city + " " + a.address).equals(automatSpinner.getSelectedItem().toString())){
                        console.setText("Name: " + a.name + "\n" + "City: " + a.city + "\n"+ "Address: " + a.address + "\n" + "Country: " + a.country);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String s = format("%02d:%02d", hourOfDay, minute);
        timePickerButton.setText(s);
        listAdapter.clear();

        if(locationSpinner.getSelectedItem().equals("Eesti")){
            for (automaton a : automatonList){
                System.out.println(a.country);
                if ((a.country.equals("EE")) && (a.availability.contains(weekDaySpinner.getSelectedItem().toString()))){
                    String[] splitAvailability = a.availability.split(";");
                    if (splitAvailability.length > 1) {
                        switch (weekDaySpinner.getSelectedItem().toString()) {
                            case ("ma"):
                                listAdapter.add(a);
                                listAdapter.notifyDataSetChanged();
                                break;
                            case ("ti"):
                                if (splitAvailability[1].equals(s)) {
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("ke"):
                                if (splitAvailability[2].equals(s)) {
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("to"):
                                if (splitAvailability[3].equals(s)) {
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("pe"):
                                if (splitAvailability[4].equals(s)) {
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("la"):
                                if (splitAvailability[5].equals(s)) {
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("su"):
                                if (splitAvailability[6].equals(s)) {
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                            case ("kaikki"):
                                listAdapter.add(a);
                                listAdapter.notifyDataSetChanged();
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + weekDaySpinner.getSelectedItem().toString());
                        }
                    }
                }
            }
        }
        else if(locationSpinner.getSelectedItem().equals("Kaikki ")){
            for (automaton a : automatonList){
                if ((a.country.equals("EE")) && (a.availability.contains(weekDaySpinner.getSelectedItem().toString()))){
                    String[] splitAvailability = a.availability.split(";");
                    if (splitAvailability.length > 1){
                        switch (weekDaySpinner.getSelectedItem().toString()){
                            case ("ma"):
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                break;
                            case ("ti"):
                                if (splitAvailability[1].equals(s)){
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("ke"):
                                if (splitAvailability[2].equals(s)){
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("to"):
                                if (splitAvailability[3].equals(s)){
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("pe"):
                                if (splitAvailability[4].equals(s)){
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("la"):
                                if (splitAvailability[5].equals(s)){
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case ("su"):
                                if (splitAvailability[6].equals(s)){
                                    listAdapter.add(a);
                                    listAdapter.notifyDataSetChanged();
                                }
                            case ("kaikki"):
                                listAdapter.add(a);
                                listAdapter.notifyDataSetChanged();
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + weekDaySpinner.getSelectedItem().toString());
                        }
                    }
                }
            }
        }

    }

    public class AsyncTaskRunner extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                readXML();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
    }
        public void readXML() throws ParserConfigurationException {
            try {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                String urlEst = "http://iseteenindus.smartpost.ee/api/?request=destinations&country=EE&type=APT";
                String urlFin = "http://iseteenindus.smartpost.ee/api/?request=destinations&country=FI&type=APT";
                String urlEstTime = "http://iseteenindus.smartpost.ee/api/?request=courier&type=express";
                Document estDocument = documentBuilder.parse(urlEst);
                Document finDocument = documentBuilder.parse(urlFin);
                Document estDocumentTime = documentBuilder.parse(urlEstTime);
                estDocument.getDocumentElement().normalize();
                finDocument.getDocumentElement().normalize();
                estDocumentTime.getDocumentElement().normalize();
                NodeList estNodeList = estDocument.getDocumentElement().getElementsByTagName("item");
                NodeList finNodeList = finDocument.getDocumentElement().getElementsByTagName("item");
                NodeList estTimeNodeList = estDocumentTime.getDocumentElement().getElementsByTagName("item");
                for (int i = 0; i < estNodeList.getLength(); i++) {
                    Node node = estNodeList.item(i);
                    if (node.getNodeType() == node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        automaton a = new automaton(element.getElementsByTagName("place_id").item(0).getTextContent(),
                                element.getElementsByTagName("name").item(0).getTextContent(),
                                element.getElementsByTagName("city").item(0).getTextContent(),
                                element.getElementsByTagName("address").item(0).getTextContent(),
                                element.getElementsByTagName("country").item(0).getTextContent(),
                                element.getElementsByTagName("availability").item(0).getTextContent());
                        if (a.availability.contains("E-P") || (a.availability.contains("E-L") && (a.availability.contains("P"))) || (a.availability.contains("E-R") && (a.availability.contains("L-P")))){
                            a.availability = "ma-ti-ke-to-pe-la-su";
                        }
                        else if (a.availability.contains("E-N") && (a.availability.contains("R-L"))){
                            a.availability = "ma-ti-ke-to-pe-la";
                        }
                        else if (a.availability.contains("E-R") && (a.availability.contains("L - P -"))){
                            a.availability = "ma-ti-ke-to-pe";
                        }
                        else{
                            System.out.println(a.availability); /* Prints unusual availability pattern */
                        }
                        automatonList.add(a);
                    }
                }
                for (int i = 0; i < finNodeList.getLength(); i++) {
                    Node node = finNodeList.item(i);
                    if (node.getNodeType() == node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        automaton a = new automaton(element.getElementsByTagName("place_id").item(0).getTextContent(),
                                element.getElementsByTagName("name").item(0).getTextContent(),
                                element.getElementsByTagName("city").item(0).getTextContent(),
                                element.getElementsByTagName("address").item(0).getTextContent(),
                                element.getElementsByTagName("country").item(0).getTextContent(),
                                element.getElementsByTagName("availability").item(0).getTextContent());
                        if ((a.availability.contains("ma-pe") && a.availability.contains("la") && (a.availability.contains("su"))) ||
                                ((a.availability.contains("ma-la")) && (a.availability.contains("su"))) ||
                                (a.availability.contains("24h")) ||
                                (a.availability.contains("ma-su")) ||
                                ((a.availability.contains("ma-to")) && (a.availability.contains("pe")) && (a.availability.contains("la")) && (a.availability.contains("su"))) ||
                                ((a.availability.contains("ma-to")) && (a.availability.contains("pe-su")))){
                            a.availability = "ma-ti-ke-to-pe-la-su";
                        }
                        else if((a.availability.contains("ma-pe") && (a.availability.contains("la"))) ||
                                (a.availability.contains("ma-la")) ||
                                ((a.availability.contains("ma")) && (a.availability.contains("ti-to")) && (a.availability.contains("pe")) && (a.availability.contains("la"))) ||
                                ((a.availability.contains("ma-to")) && (a.availability.contains("pe")) && (a.availability.contains("la"))) ||
                                ((a.availability.contains("ma")) && (a.availability.contains("ti-ke")) && (a.availability.contains("to")) && (a.availability.contains("pe")) && (a.availability.contains("la"))) ||
                                ((a.availability.contains("ma")) && (a.availability.contains("ti")) && (a.availability.contains("ke-to")) && (a.availability.contains("pe")) && (a.availability.contains("la")))){
                            a.availability = "ma-ti-ke-to-pe-la";
                        }
                        else if((a.availability.contains("ma-pe")) || ((a.availability.contains("ma-to")) && (a.availability.contains("pe")))){
                            a.availability = "ma-ti-ke-to-pe";
                        }
                        else if((a.availability.contains("ma-to") && (a.availability.contains("la")))){
                            a.availability = "ma-ti-ke-to-la";
                        }
                        else if((a.availability.contains("pe") && (a.availability.contains("la")) && (a.availability.contains("su")))){
                            a.availability = "pe-la-su";
                        }
                        else if ((a.availability.contains("ma-ti") && (a.availability.contains("to-su")))){
                            a.availability = "ma-ti-to-pe-la-su";
                        }
                        else if (a.availability.contains("ma-to")){
                            a.availability = "ma-ti-ke-to";
                        }
                        else{
                            System.out.println(a.availability); /* Prints unusual availability pattern */
                        }
                        automatonList.add(a);
                    }
                }
                for (int i = 0; i < estTimeNodeList.getLength(); i++){
                    Node node = estTimeNodeList.item(i);
                    if(node.getNodeType() == node.ELEMENT_NODE){
                        Element element = (Element) node;
                        for (automaton a : automatonList){
                            if (a.id.equals(element.getElementsByTagName("place_id").item(0).getTextContent())){
                                a.availability = a.availability + ";" + element.getElementsByTagName("express_out").item(0).getTextContent();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }

        public static class TimePickerFragment extends DialogFragment{
            @NonNull
            @Override
            public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
                return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), 12,00, DateFormat.is24HourFormat(getActivity()));
            }
        }
}



