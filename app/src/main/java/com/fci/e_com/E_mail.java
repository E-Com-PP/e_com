package com.fci.e_com;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class E_mail
{
    public String date, msg, from, to, openURL, deleteURL, replyURL, message;
    public int id;
    MainActivity MainActv;

    public E_mail(String date, String msg, String from, String to, String openURL, MainActivity c, int id)
    {
        this.date = date;
        this.msg = msg;
        this.from = from;
        this.to = to;
        this.openURL = openURL;
        MainActv = c;
        this.id = id;
    }

    public void openE_mail()
    {
        MainActv.webViewer.loadUrl(openURL);
        MainActv.webViewer.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView web, String url)
            {
                web.loadUrl("javascript: GInter.sendAdditionalInfo(" +
                        "document.getElementsByTagName('tbody')[4].childNodes[4].childNodes[1].childNodes[1].href," +
                        "document.getElementsByTagName('tbody')[4].childNodes[4].childNodes[3].firstChild.href," +
                        "document.getElementsByTagName('tbody')[4].childNodes[2].childNodes[3].textContent," +
                        "'" + Integer.toString(id) + "');");
            }
        });
    }

    public void deleteMsg(WebView w)
    {
        w.loadUrl(deleteURL);
    }

    public void reply(final String userReply)
    {
        MainActv.webViewer.loadUrl(replyURL);
        MainActv.webViewer.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView web, String url)
            {
                web.loadUrl("javascript: document.getElementsByTagName('tbody')[4].childNodes[4].childNodes[1].firstChild.value='" +
                        userReply + "';" +
                        "var x = document.getElementsByTagName('tbody')[4].childNodes[6].childNodes[1].childNodes[3];" +
                        "$(x).trigger('click');");
            }
        });
    }
}
