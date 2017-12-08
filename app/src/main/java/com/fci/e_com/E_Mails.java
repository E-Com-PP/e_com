package com.fci.e_com;

import android.webkit.WebViewClient;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class E_Mails
{
    public List<receivedFile> recievedFile = new ArrayList<receivedFile>();
    Map<String, String> options = new HashMap<String, String>();
    public List<E_mail> e_mails = new ArrayList<E_mail>();

    public int e_mailsPageNumber = 0;
    public int filesPageNumber = 0;

    MainActivity MainActv;

    E_Mails(MainActivity c) {
        MainActv = c;
    }

    public void loadPage(int pageNumber) {
        e_mailsPageNumber = pageNumber;
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

