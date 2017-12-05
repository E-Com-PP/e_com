package com.fci.e_com;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class GWebAppInterface
{
    private MainActivity MainActv;

    GWebAppInterface(MainActivity context)
    {
        MainActv = context;
    }

    @JavascriptInterface
    public void sendPeople(String people)
    {
        Map<String, String> op = new HashMap<String, String>();

        String[] inBetween = people.split("\\|");

        for (int i = 0; i < inBetween.length; i += 2)
        {
            op.put(inBetween[i], inBetween[i + 1]);
        }

        MainActv.allMails.options = op;
    }

    @JavascriptInterface
    public void sendE_mails(String allMails)
    {
        String[] inBetween = allMails.split("\\|");

        int varId = (MainActv.allMails.e_mailsPageNumber - 1) * 50;

        for (int i = 0; i < inBetween.length; i += 5)
        {
            MainActv.allMails.e_mails.add(new E_mail(inBetween[i], inBetween[i + 1], inBetween[i + 2], inBetween[i + 3], inBetween[i + 4], MainActv, varId));
            varId++;
        }
        Toast.makeText(MainActv, "sendE_mails", Toast.LENGTH_SHORT).show();
        MainActv.fillFragment(0, 4);
    }

    @JavascriptInterface
    public void makeToast()
    {
        Toast.makeText(MainActv, "TOAST", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void send(String s)
    {
        int counter = 0;
        String[] inBetween = s.split("\\|");

        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                MainActv.top.Top_50[i][j] = inBetween[counter];
                counter++;
            }
        }
        MainActv.fillFragment(0,3);

    }

    @JavascriptInterface
    public void sendAdditionalInfo(String delete, String reply, String message, String e_mailId)
    {
        int intId = Integer.parseInt(e_mailId);
        MainActv.allMails.e_mails.get(intId).deleteURL = delete;
        MainActv.allMails.e_mails.get(intId).replyURL = reply;
        MainActv.allMails.e_mails.get(intId).message = message;
    }

    @JavascriptInterface
    public void sendFilesData(String filesData)
    {
        String[] inBetween = filesData.split("\\|");

        for (int i = 0; i < inBetween.length; i += 5)
        {
            MainActv.allMails.recievedFile.add(new recievedFile(MainActv, inBetween[i], inBetween[i + 1], inBetween[i + 2], inBetween[i + 3], inBetween[i + 4]));
        }
    }
}
