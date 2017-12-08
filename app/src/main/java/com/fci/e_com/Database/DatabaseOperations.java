package com.fci.e_com.Database;

import com.fci.e_com.Grade;
import com.fci.e_com.NewsObj;
import com.fci.e_com.MainActivity;
import com.fci.e_com.UserSettings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StarkTheGnr on 12/7/2017.
 */

public class DatabaseOperations extends SQLiteOpenHelper {
    public static final String[] GradeColumnNames = new String[] {"Username", "Semester", "Level", "Code", "CName", "CGroup", "Grade", "YWork", "WExam", "TotalMarks", "LabAbs", "SectionAbs", "TotalAbs"};
    public static final String[] NewsColumnNames = new String[] {"Body", "Date"};
    public static final String[] UserColumnNames = new String[] {"Username", "Year", "Status", "Level", "Minor", "MajorTrack", "Name", "ID", "GPA", "Advisor", "Visits"};

    public DatabaseOperations(Context context, String DBName) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0)
    {

    }

    public boolean CreateTable(String TName, String[] ColumnNames, String[] ColumnTypes)
    {
        if(ColumnNames.length != ColumnTypes.length)
            throw new StringIndexOutOfBoundsException();

        try {
            SQLiteDatabase sdb = getWritableDatabase();
            String CREATE_QUERY = "CREATE TABLE " + TName + "( ";
            for (int i = 0; i < ColumnNames.length; i++) {
                CREATE_QUERY += ColumnNames[i] + " " + ColumnTypes[i] + ((i == ColumnNames.length - 1) ? "" : ", ");
            }
            CREATE_QUERY += " );";

            sdb.execSQL(CREATE_QUERY);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public void Insert(String TName, String[] ColumnNames, String[] Values)
    {
        if(ColumnNames.length != Values.length)
            throw new StringIndexOutOfBoundsException();

        SQLiteDatabase sdb = getWritableDatabase();
        ContentValues cntnt = new ContentValues();
        for(int i = 0; i < ColumnNames.length; i++)
            cntnt.put(ColumnNames[i], Values[i]);

        long success = sdb.insert(TName, "", cntnt);
    }

    public Cursor Query(String TName, String[] ColumnNames)
    {
        SQLiteDatabase sdb = getReadableDatabase();
        Cursor cur = sdb.query(TName, ColumnNames, null, null, null, null, null);
        return cur;
    }
    public Cursor Select(String TName, String[] ColumnNames, String[] colSelect, String[] valToSelect)
    {
        SQLiteDatabase sdb = getReadableDatabase();
        String selectQuery = "";
        for(int i = 0; i < valToSelect.length; i++)
        {
            selectQuery += colSelect[i] + " = ?" + ((i == valToSelect.length - 1) ? "" : " AND ");
        }
        Cursor cur = sdb.query(TName, ColumnNames, selectQuery, valToSelect, null, null, null);
        return cur;
    }

    public void Update(String TName, String[] colSelect, String[] valToSelect, String[] ColumnNames, String[] Vals, boolean insertOnFail)
    {
        SQLiteDatabase sdb = getWritableDatabase();

        try {
            String selectQuery = "";
            for(int i = 0; i < valToSelect.length; i++)
            {
                selectQuery += colSelect[i] + " LIKE ?" + ((i == valToSelect.length - 1) ? "" : " AND ");
            }

            ContentValues cont = new ContentValues();
            for (int i = 0; i < Vals.length; i++) {
                cont.put(ColumnNames[i], Vals[i]);
            }

            int rowsaffected = sdb.update(TName, cont, selectQuery, valToSelect);

            if(insertOnFail && rowsaffected == 0)
            {
                Insert(TName, ColumnNames, Vals);
            }
        }
        catch (Exception ex)
        {

        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {

    }

    public void createGradesTable(String Username, String Semester, String Level, List<Grade> grades)
    {
        String[] Types = new String[] {"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT"};
        CreateTable("Grade", GradeColumnNames, Types);

        for(int i = 0; i < grades.size(); i++)
        {
            Grade g = grades.get(i);
            String[] Vals = new String[]{Username, Semester, Level, g.Code, g.CName, g.Group, g.Grade, Integer.toString(g.YWork), Integer.toString(g.WExam)
                    , Integer.toString(g.TotalMarks), Integer.toString(g.LabAbs), Integer.toString(g.SectionAbs), Integer.toString(g.TotalAbs)};

            Update("Grade", new String[]{"Username", "Code"}, new String[]{Username, Vals[3]}, GradeColumnNames, Vals, true);
        }
    }
    public void createNewsTable(List<NewsObj> news)
    {
        String[] Types = new String[] {"TEXT", "TEXT"};
        CreateTable("News", NewsColumnNames, Types);

        for(int i = 0; i < news.size(); i++)
        {
            String[] Vals = new String[]{news.get(i).Data, news.get(i).Date};

            Update("News", new String[]{"Body"}, new String[]{Vals[0]}, NewsColumnNames, Vals, true);
        }
    }
    public void createUserTable(String Username, UserSettings user)
    {
        String[] Types = new String[] {"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT"};
        CreateTable("User", UserColumnNames, Types);

        String[] Vals = new String[]{Username, Integer.toString(user.Year), user.Status, Integer.toString(user.Level), user.Minor, user.MajorTrack, user.Name
                , Integer.toString(user.ID), Float.toString(user.GPA), user.Advisor, user.Visits};

        Update("User", new String[]{"Username"}, new String[]{Vals[0]}, UserColumnNames, Vals, true);
    }

    public boolean LoadExistingData(MainActivity ma, int caseType, int arg0)
    {
        String un = ma.Name;

        try {
            switch (caseType) {
                //Grades
                case 0: {
                    Cursor cur = Select("Grade", GradeColumnNames, new String[]{"Username", "Semester", "Level"}, new String[]{un, "1", ma.handler.YearOptions.get(arg0)});
                    if (cur.getCount() == 0)
                        return false;

                    List<Grade> finalGrades = new ArrayList<>();

                    cur.moveToFirst();
                    do {
                        String res = "";
                        for (int i = 3; i < cur.getColumnCount(); i++) {
                            res += cur.getString(cur.getColumnIndex(Grade.inpOrder[i - 3])) + "╖";
                        }
                        finalGrades.add(new Grade(res));
                    }
                    while (cur.moveToNext());

                    ma.user.Grades = finalGrades;
                    return true;
                }
                //News
                case 1: {
                    Cursor cur = Query("News", NewsColumnNames);
                    if (cur.getCount() == 0)
                        return false;

                    List<NewsObj> finalNews = new ArrayList<>();

                    cur.moveToFirst();
                    do {
                        finalNews.add(new NewsObj(cur.getString(0), cur.getString(1)));
                    }
                    while (cur.moveToNext());

                    ma.News = finalNews;
                    return true;
                }
                //User
                case 2: {
                    Cursor cur = Select("User", UserColumnNames, new String[] {"Username"}, new String[]{un});
                    if (cur.getCount() == 0)
                        return false;

                    UserSettings finalUser = new UserSettings();

                    cur.moveToFirst();

                    finalUser.Year = Integer.parseInt(cur.getString(1));
                    finalUser.Status = cur.getString(2);
                    finalUser.Level = Integer.parseInt(cur.getString(3));
                    finalUser.Minor = cur.getString(4);
                    finalUser.MajorTrack = cur.getString(5);
                    finalUser.Name = cur.getString(6);
                    finalUser.ID = Integer.parseInt(cur.getString(7));
                    finalUser.GPA = Float.parseFloat(cur.getString(8));
                    finalUser.Advisor = cur.getString(9);
                    finalUser.Visits = cur.getString(10);
                    if(ma.user != null && ma.user.Grades != null)
                        finalUser.Grades = ma.user.Grades;

                    ma.user = finalUser;
                    return true;
                }
            }
        }
        catch(Exception ex) {}

        return false;
    }
}
