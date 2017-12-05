package com.fci.e_com;

import android.webkit.WebViewClient;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class E_Mails
{
    List<recievedFile> recievedFile = new ArrayList<recievedFile>();
    Map<String, String> options = new HashMap<String, String>();
    List<E_mail> e_mails = new ArrayList<E_mail>();

    public int e_mailsPageNumber = 0;
    public int filesPageNumber = 0;

    MainActivity MainActv;

    E_Mails(MainActivity c) {
        MainActv = c;
    }

    public void loadPage() {
        e_mailsPageNumber++;
        MainActv.webViewer.loadUrl("https://my.fci-cu.edu.eg/content.php?pg=fromadmin.php&mt=" + Integer.toString(e_mailsPageNumber));

        MainActv.webViewer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView web, String url) {
                web.loadUrl(
                        "javascript: var table = Array.prototype.slice.call(document.getElementsByTagName('tbody')[5].childNodes).slice(4);" +
                        "var tbs = '';" +
                        "for(var i = 0; i < table.length; i++)" +
                        "tbs += table[i].childNodes[1].textContent + '|' + table[i].childNodes[3].firstChild.textContent + '|' + table[i].childNodes[5].textContent + '|' + table[i].childNodes[7].textContent + '|'" +
                        "+ table[i].childNodes[3].firstChild.href + '|';" +
                        "GInter.sendE_mails(tbs);");
            }
        });
    }

    public void getComposeOptions() {
        MainActv.webViewer.loadUrl("https://my.fci-cu.edu.eg/Send_Msg.ecom");
        MainActv.webViewer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView web, String url) {
                web.loadUrl("javascript: var tbs = '';" +
                        "table = $('[name = \"to\"]').children;" +
                        "for (var i = 0; i < table.length; i++)" +
                        "tbs += table[i].textContent + '|' + table[i].value + '|';" +
                        "GInter.sendPeople(tbs);");
            }
        });
    }

    public void sendEmail(final String to, final String msg) {
        MainActv.webViewer.loadUrl("https://my.fci-cu.edu.eg/Send_Msg.ecom");
        MainActv.webViewer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView web, String url) {
                MainActv.webViewer.loadUrl("javascript: document.getElementsByTagName('textarea')[0].innerText=" + msg +
                        ";$('[value=" + options.get(to) + "]').selected = true" +
                        "$(\"[type='submit']\").trigger('click');");
            }
        });
    }

    public void sendFiles()
    {
        filesPageNumber++;
        MainActv.webViewer.loadUrl("https://my.fci-cu.edu.eg/content.php?pg=filesfromadmin.php&mt=" + Integer.toString(filesPageNumber));
        MainActv.webViewer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView web, String url) {
                MainActv.webViewer.loadUrl("javascript: tbs = '';" +
                        "var table = document.getElementsByTagName('tbody')[4].children;" +
                        "for (var i = 2; i < table.length - 2; i++)" +
                        "{" +
                        "tbs += " +
                        "table[i].children[0].innerText.trim() + '|' +" +
                        "table[i].children[1].innerText.trim() + '|' +" +
                        "table[i].children[2].innerText.trim() + '|' +" +
                        "table[i].children[3].innerText.trim() + '|' +" +
                        "table[i].children[4].children[0].href + '|' ;" +
                        "}" +
                        "GInter.sendFilesData(tbs);");
            }
        });
    }
}

class E_mail
{
    String date, msg, from, to, openURL, deleteURL, replyURL, message;
    int id;
    MainActivity MainActv;

    E_mail(String date, String msg, String from, String to, String openURL, MainActivity c, int id)
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

class recievedFile
{
    String date, from, fileDescription, to, downloadLink;
    MainActivity MainActv;

    recievedFile(MainActivity MainActv, String date, String from, String fileDescription, String to, String downloadLink)
    {
        this.date = date;
        this.from = from;
        this.fileDescription = fileDescription;
        this.to = to;
        this.downloadLink = downloadLink;
        this.MainActv = MainActv;
    }

    public void download()
    {
        MainActv.webViewer.loadUrl(downloadLink);
    }
}
