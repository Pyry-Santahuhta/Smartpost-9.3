package com.example.a72button;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

class weekdayAdapter {
    ArrayList<weekDay> weekDays;
    public static class weekDay{
        int idNum;
        String Name;
        boolean selected;
        weekDay(int idnum, String name ) {
            idNum = idnum;
            Name = name;
            selected = false;
        }

        @NonNull
        @Override
        public String toString() {
            return Name;
        }

        public int getIdNum() {
            return idNum;
        }
    }
    weekdayAdapter(){
        weekDay all = new weekDay(0, "Kaikki");
        weekDay mon = new weekDay(0, "ma");
        weekDay tue = new weekDay(1, "ti");
        weekDay wed = new weekDay(2, "ke");
        weekDay thu = new weekDay(3, "to");
        weekDay fri = new weekDay(4, "pe");
        weekDay sat = new weekDay(5, "la");
        weekDay sun = new weekDay(6, "su");
        weekDays = new ArrayList<>(Arrays.asList(all, mon, tue, wed, thu, fri, sat, sun));
    }
}


