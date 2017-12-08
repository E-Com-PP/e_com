package com.fci.e_com;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StarkTheGnr on 9/15/2017.
 */

public class UserSettings {
        public String Name = "";
        public int ID = 0;
        public float GPA = 0;
        public String Advisor = "";
        public String Visits = "";
        public int Year = 0;
        public String Status = "";
        public int Level = 0;
        public String MajorTrack = "";
        public String Minor = "";
        public List<Grade> Grades = new ArrayList<>();

        public UserSettings(String DataString)
        {
            if(DataString.contains("╖")) {
                String[] DataA = DataString.split("╖");
                Year = Integer.parseInt(DataA[0]);
                Status = DataA[1];
                Level = Integer.parseInt(DataA[2]);
                Minor = DataA[3];
                MajorTrack = DataA[4];
                Name = DataA[5];
                ID = Integer.parseInt(DataA[6]);
                GPA = Float.parseFloat(DataA[7]);
                Advisor = DataA[8];
                Visits = DataA[9];
            }
        }
        public UserSettings() {};
    }

