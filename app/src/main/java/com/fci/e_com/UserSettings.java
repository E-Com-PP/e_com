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
            YWork = Integer.parseInt(courseData[3]);
            WExam = Integer.parseInt(courseData[4]);
            TotalMarks = Integer.parseInt(courseData[5]);
            Grade = courseData[6];
            LabAbs = Integer.parseInt(courseData[7]);
            SectionAbs = Integer.parseInt(courseData[8]);
            TotalAbs = Integer.parseInt(courseData[9]);
        }
}
