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
        String Name = "";
        int ID = 0;
        float GPA = 0;
        String Advisor = "";
        String Visits = "";
        int Year = 0;
        String Status = "";
        int Level = 0;
        String MajorTrack = "";
        String Minor = "";
        public List<Grade> Grades = new ArrayList<>();

        public UserSettings(String DataString)
        {
            if(DataString.contains("╖")) {
                String[] DataA = DataString.split("╖");
                Year = Integer.parseInt(DataA[0]);
                Level = Integer.parseInt(DataA[2]);
                Status = DataA[1];
                MajorTrack = DataA[4];
                Minor = DataA[3];
                GPA = Float.parseFloat(DataA[7]);
                ID = Integer.parseInt(DataA[6]);
                Name = DataA[5];
                Advisor = DataA[8];
                Visits = DataA[9];
            }
        }
    }

class NewsObj{
        String Data, Date;

        public NewsObj(String data, String date)
        {
            Data = data;
            Date = date;
        }
    }
