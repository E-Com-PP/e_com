package com.fci.e_com;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

public class Top_50
{
    Map<String, String> deps = new HashMap<String, String>();
    public String[][] Top_50;
    MainActivity MainActv;

    Top_50(MainActivity c)
    {
        MainActv = c;
    }

    public void getTop_50(final int year, final String dep, GWebAppInterface webInterface)
    {
        deps.put("ALL", "3");
        deps.put("CS", "5");
        deps.put("IT", "7");
        deps.put("IS", "9");
        deps.put("DS", "11");

        MainActv.webViewer.loadUrl("https://my.fci-cu.edu.eg/content.php?pg=top50.php");
        MainActv.webViewer.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView web, String url)
            {
                web.loadUrl("javascript: document.getElementsByTagName('select')[0].childNodes[" + Integer.toString((year * 2) + 1) + "].selected = true;" +
                                        "document.getElementsByTagName('select')[1].childNodes[" + deps.get(dep) + "].selected = true;" +
                                        "document.studform.submit();");

                web.setWebViewClient(new WebViewClient()
                {
                    @Override
                    public void onPageFinished(WebView web, String url)
                    {
                        web.loadUrl("javascript: var tbs = '';" +
                                "var table = Array.prototype.slice.call(document.getElementsByTagName('tbody')[5].children).slice(2);" +
                                "for (var i = 0; i < table.length; i++)" +
                                "{" +
                                "for (var j = 0; j < 5; j++)" +
                                "{" +
                                "tbs += table[i].children[j].textContent.trim();" +
                                "tbs += '|';" +
                                "}" +
                                "}" +
                                "GInter.send(tbs, '" + year + "', '" + dep + "');");
                    }
                });
            }
        });
    }

    public void getTop_50(final int year, GWebAppInterface webInterface)
    {
        MainActv.webViewer.loadUrl("https://my.fci-cu.edu.eg/content.php?pg=top50.php");
        MainActv.webViewer.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView web, String url)
            {
                web.loadUrl("javascript: document.getElementsByTagName('select')[0].childNodes[" + Integer.toString((year * 2) + 1) + "].selected = true;" +
                        "document.studform.submit();");

                web.setWebViewClient(new WebViewClient()
                {
                    @Override
                    public void onPageFinished(WebView web, String url)
                    {
                        web.loadUrl("javascript: var tbs = '';" +
                                "var table = Array.prototype.slice.call(document.getElementsByTagName('tbody')[5].children).slice(2);" +
                                "for (var i = 0; i < table.length; i++)" +
                                "{" +
                                "for (var j = 0; j < 5; j++)" +
                                "{" +
                                "tbs += table[i].children[j].textContent.trim();" +
                                "tbs += '|';" +
                                "}" +
                                "}" +
                                "GInter.send(tbs, '" + year + "', 'ALL');");
                    }
                });
            }
        });
    }
}
