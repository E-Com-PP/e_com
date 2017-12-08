package com.fci.e_com;

public class Grade{
    public String Code, CName, Group, Grade;
    public int YWork, WExam, TotalMarks, LabAbs, SectionAbs, TotalAbs;

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
