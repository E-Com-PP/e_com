package com.fci.e_com;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

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
        List<Grade> Grades;

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

    class Grade{
        String Code, CName, Group, Grade;
        int YWork, WExam, TotalMarks, LabAbs, SectionAbs, TotalAbs;

        public Grade(String Data)
        {
            String[] courseData = Data.split("╖");
            Code = courseData[0];
            CName = courseData[1];
            Group = courseData[2];
            YWork = (courseData[3].isEmpty()) ? 0 : Integer.parseInt(courseData[3]);
            WExam = (courseData[4].isEmpty()) ? 0 :Integer.parseInt(courseData[4]);
            TotalMarks = (courseData[5].isEmpty()) ? 0 :Integer.parseInt(courseData[5]);
            Grade = courseData[6];
            LabAbs = (courseData[7].isEmpty()) ? 0 :Integer.parseInt(courseData[7]);
            SectionAbs = (courseData[8].isEmpty()) ? 0 :Integer.parseInt(courseData[8]);
            TotalAbs = (courseData[9].isEmpty()) ? 0 :Integer.parseInt(courseData[9]);
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
