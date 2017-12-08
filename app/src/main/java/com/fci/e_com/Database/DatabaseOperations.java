package com.fci.e_com.Database;

import com.fci.e_com.Grade;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by StarkTheGnr on 12/7/2017.
 */

public class DatabaseOperations extends SQLiteOpenHelper {
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
            String CREATE_QUERY = "CREATE TABLE " + TName + " (";
            for (int i = 0; i < ColumnNames.length; i++) {
                CREATE_QUERY += ColumnNames[i] + " " + ColumnTypes[i] + ((i == ColumnNames.length - 1) ? "" : ",");
            }
            CREATE_QUERY += ");";

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
    public Cursor Select(String TName, String[] ColumnNames, String colSelect, String valToSelect)
    {
        SQLiteDatabase sdb = getReadableDatabase();
        Cursor cur = sdb.query(TName, ColumnNames, colSelect + " LIKE ?", new String[] {valToSelect}, null, null, null);
        return cur;
    }

    public void Update(String TName, String colSelect, String valToSelect, String[] ColumnNames, String[] Vals, boolean insertOnFail)
    {
        SQLiteDatabase sdb = getWritableDatabase();

        try {
            String selection = colSelect + " LIKE ?";

            ContentValues cont = new ContentValues();
            for (int i = 0; i < Vals.length; i++) {
                cont.put(ColumnNames[i], Vals[i]);
            }

            sdb.update(TName, cont, selection, new String[]{valToSelect});
        }
        catch (Exception ex)
        {
            if(insertOnFail)
            {
                Insert(TName, ColumnNames, Vals);
            }
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {

    }

    public void createGradesTable(String Username, String Level, List<Grade> grades)
    {
        String[] ColumnNames = new String[] {"Username", "Level", "Code", "CName", "Group", "Grade", "YWork", "WExam", "TotalMarks", "LabAbs", "SectionAbs", "TotalAbs"};
        String[] Types = new String[] {"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT"};
        CreateTable("Grade", ColumnNames, Types);

        for(int i = 0; i < grades.size(); i++)
        {
            Grade g = grades.get(i);
            String[] Vals = new String[]{Username, Level, g.Code, g.CName, g.Group, g.Grade, Integer.toString(g.YWork), Integer.toString(g.WExam)
                    , Integer.toString(g.TotalMarks), Integer.toString(g.LabAbs), Integer.toString(g.SectionAbs), Integer.toString(g.TotalAbs)};

            Update("Grade", "Username", Username, ColumnNames, Vals, true);
        }
    }
}
