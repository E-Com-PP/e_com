package com.fci.e_com;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.Uri;
import android.webkit.DownloadListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WebHandler {
    public MainActivity MainActv;
    Synchronizer synchro;
    public static List<String> YearOptions = new ArrayList<String>();


    public WebHandler(Context mainContxt) { MainActv = (MainActivity)mainContxt; }

    public void StartUp()
    {
        synchro = MainActv.Synchro;
        MainActv.webViewer = MainActv.findViewById(R.id.wv1);
        MainActv.webInterface = new WebAppInterface(MainActv);
        MainActv.GInterface = new GWebAppInterface(MainActv);

        MainActv.webViewer.addJavascriptInterface(MainActv.webInterface, "Android");
        MainActv.webViewer.addJavascriptInterface(MainActv.GInterface, "GInter");
        MainActv.webViewer.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");

        MainActv.webViewer.getSettings().setJavaScriptEnabled(true);

        MainActv.isInstantiated = true;
        //MainActv.webViewer.clearCache(true);
        //MainActv.webViewer.clearFormData();

        MainActv.webViewer.setWebViewClient(new WebViewClient() {
                                                @Override
                                                public void onPageFinished(WebView web, String url) {
                                                    CheckIfLoggedIn();
                                                }
                                            });

        MainActv.webViewer.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                MainActv.startActivity(i);
            }
        });

        MainActv.webViewer.loadUrl("https://my.fci-cu.edu.eg/");
    }

    public void CheckIfLoggedIn()
    {
        MainActv.webViewer.loadUrl("javascript:var result = document.getElementsByTagName('body')[0].innerHTML.includes('Academic Year:');Android.IsLoggedIn(result);");
    }

    public void LogOut(final String Username, final String Password)
    {
        if(MainActv.loggedIn == 1)
        {
            LoadJSOnPageFinish("https://my.fci-cu.edu.eg/logout.php", new Runnable() {
                @Override
                public void run() {
                    Login(Username, Password);
                }
            });
            MainActv.loggedIn = 0;
        }
    }
    public void LogOut()
    {
        if(MainActv.loggedIn == 1)
        {
            LoadUrl("https://my.fci-cu.edu.eg/logout.php");
            MainActv.loggedIn = 0;
        }
    }

    public void ValidInput()
    {
        MainActv.webViewer.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void Login(final String Username, final String Password)
    {
        if(MainActv.loggedIn == 0) {
            MainActv.webViewer.post(new Runnable() {
                @Override
                public void run() {
                    String url = "https://my.fci-cu.edu.eg/";
                    final String JS = "javascript:(function(){document.getElementsByTagName('ul')[1].childNodes[3].firstChild.value='" + Username + "';" +
                            "document.getElementsByTagName('ul')[1].childNodes[5].firstChild.value='" + Password + "';" +
                            "var x = document.getElementsByTagName('ul')[1].childNodes[7].childNodes[1];" +
                            "$(x).trigger('click');" +
                            "})()";

                    if(MainActv.webViewer.getUrl() == null || !MainActv.webViewer.getUrl().equals(url)) {
                        MainActv.webViewer.loadUrl(url);

                        MainActv.webViewer.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageFinished(WebView web, String url) {
                                // TODO Auto-generated method stub
                                ResetOnPageFinish();
                                MainActv.webViewer.loadUrl(JS);

                                MainActv.webViewer.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public void onPageFinished(WebView web, String url) {
                                        synchro.TaskDone();

                                        Toast.makeText(MainActv, "Logged in", Toast.LENGTH_LONG).show();
                                        synchro.AddTask(new NetTask() {
                                            @Override
                                            public void run() {
                                                GetUserData();
                                            }
                                        }, false);
                                        //MainActv.startActivity(new Intent(MainActv, ShowData.class));
                                    }});
                                //GetUserData();
                            }
                        });
                    }
                    else {
                        synchro.RunTask(new NetTask(){
                            @Override
                            public void run() {
                                ResetOnPageFinish();
                                MainActv.webViewer.loadUrl(JS);
                                MainActv.webViewer.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public void onPageFinished(WebView web, String url) {
                                        synchro.TaskDone();

                                        Toast.makeText(MainActv, "Logged in", Toast.LENGTH_LONG).show();
                                        synchro.AddTask(new NetTask() {
                                            @Override
                                            public void run() {
                                                GetUserData();
                                            }
                                        }, false);
                                        //MainActv.startActivity(new Intent(MainActv, ShowData.class));
                                    }
                                });
                            }});
                    }
                }
            });

        }
        else if(MainActv.loggedIn == 1)
        {
            synchro.AddTask(new NetTask(){
                @Override
                public void run()
                {
                    LogOut(Username, Password);
                }
            }, false);
        }
    }

    public void GetUserData()
    {
        //wb = (WebView)MainActivity.mainOB.findViewById(R.id.wv1);
        //wb.loadUrl("javascript:document.getElementsByTagName('body')[0].innerHTML = ''");
        //if(MainActv.loggedIn != 1) return;

        MainActv.webViewer.loadUrl("javascript:(function() {try{var result = '';" +
                "var tb = document.getElementsByClassName('tabledata')[0];" +
                "for(var n = 0; n < 2; n++)" +
                "{" +
                "for(var i = 0; i < 5; i++)" +
                "{" +
                "result += (tb.children[0].children[n].children[i].innerText.includes(': ')) ? tb.children[0].children[n].children[i].innerText.split(': ')[1] : 'None';" +
                "result += '╖';" +
                "}" +
                "}" +
                "Android.sendData(result); Android.UserDataShow(); Android.isValidLogin('true');} catch(error) { Android.isValidLogin('false'); }})()");
    }

    public void GetNews()
    {
        /*LoadJSOnPageFinish("https://my.fci-cu.edu.eg/", "javascript:var MainText = document.getElementsByClassName(\"items\")[0];" +
                "var result = \"\";" +
                "" +
                "for(var i = 0; i < MainText.childElementCount; i++)" +
                "{" +
                "var Child = MainText.children[i];" +
                "for(var n = 0; n < Child.childElementCount; n++)" +
                "{" +
                "var TextObj = Child.children[n].children[0].children[1].children[0];" +
                "TextObj = TextObj.innerHTML;" +
                "" +
                "TextObj = TextObj.replace(/<br>/g, \"\").replace(/&nbsp;/g, '').split('</a>');" +
                "for(var c = 0; c < TextObj.length; c++)" +
                "{" +
                "var includez = false;" +
                "if(TextObj[c].includes(\"<a hre\"))" +
                "{" +
                "TextObj[c] = TextObj[c].split('<a href=');" +
                "includez = true;" +
                "}" +
                "" +
                "result += (includez) ? TextObj[c][0] : \"<a href=\" + TextObj[c];" +
                "result += (includez) ? \"╖\" + TextObj[c][1] + \"╖\" : \"\";" +
                "}" +
                "result += \"±\";" +
                "}" +
                "}" +
                "Android.sendNews(result);");*/
        LoadJSOnPageFinish("https://my.fci-cu.edu.eg/", "javascript:var MainText = document.getElementsByClassName(\"items\")[0];" +
                "var result = \"\";" +
                "" +
                "for(var i = 0; i < MainText.childElementCount; i++)" +
                "{" +
                "var Child = MainText.children[i];" +
                "for(var n = 0; n < Child.childElementCount; n++)" +
                "{" +
                "var TextObj = Child.children[n].children[0].children[1].children[0];" +
                "TextObj = TextObj.innerHTML;" +
                "result += Child.children[n].children[0].childNodes[0].wholeText + \"╖\" + TextObj + \"±\";" +
                "}" +
                "}" +
                "Android.sendNews(result);");
    }

    public void GetGrades(int semester)
    {
        String url = "https://my.fci-cu.edu.eg/content.php?pg=studgroup_term" + semester + ".php";

        LoadJSOnPageFinish(url, "javascript:var Tb = document.getElementsByClassName('FormTable')[0].children[0].children;" +
                "var result = \"\";" +
                "" +
                "for(var i = 2; i < Tb.length; i++)" +
                "{" +
                "for(var n = 0; n < Tb[2].children.length; n++)" +
                "{" +
                "result += Tb[i].children[n].innerText.trim() + \"╖\";" +
                "}" +
                "result += \"±\";" +
                "}" +
                "Android.sendGrades(result);");
    }
    public void GetGrades(int semester, final String option)
    {
        if(YearOptions.size() != 0) {
            String url = "https://my.fci-cu.edu.eg/content.php?pg=studgroup_term" + semester + ".php";
            boolean loaded = LoadUrl(url);
            if(loaded) {
                MainActv.webViewer.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView web, String url) {
                        LoadUrl("javascript:var lst = document.getElementsByName(\"prev_years\")[0];\n" +
                                "lst.selectedIndex = " + GetOptionIndex(option) + ";\n" +
                                "var button = document.getElementsByClassName(\"TableHeader\")[1].children[0].children[0];\n" +
                                "button.click();");

                        MainActv.webViewer.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageFinished(WebView web, String url) {
                                LoadUrl("javascript:var Tb = document.getElementsByClassName('FormTable')[0].children[0].children;" +
                                        "var result = \"\";" +
                                        "" +
                                        "for(var i = 2; i < Tb.length; i++)" +
                                        "{" +
                                        "for(var n = 0; n < Tb[2].children.length; n++)" +
                                        "{" +
                                        "result += Tb[i].children[n].innerText.trim() + \"╖\";" +
                                        "}" +
                                        "result += \"±\";" +
                                        "}" +
                                        "Android.sendGrades(result);");
                            }
                        });
                    }
                });
            }
            else
            {
                LoadUrl("javascript:var lst = document.getElementsByName(\"prev_years\")[0];\n" +
                        "lst.selectedIndex = " + GetOptionIndex(option) + ";\n" +
                        "var button = document.getElementsByClassName(\"TableHeader\")[1].children[0].children[0];\n" +
                        "button.click();");

                MainActv.webViewer.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView web, String url) {
                        LoadUrl("javascript:var Tb = document.getElementsByClassName('FormTable')[0].children[0].children;" +
                                "var result = \"\";" +
                                "" +
                                "for(var i = 2; i < Tb.length; i++)" +
                                "{" +
                                "for(var n = 0; n < Tb[2].children.length; n++)" +
                                "{" +
                                "result += Tb[i].children[n].innerText.trim() + \"╖\";" +
                                "}" +
                                "result += \"±\";" +
                                "}" +
                                "Android.sendGrades(result);");
                    }
                });
            }
        }
        else
        {
            GetGradeYears(semester);
        }
    }

    public void GetGradeYears(int semester)
    {
        String url = "https://my.fci-cu.edu.eg/content.php?pg=studgroup_term" + semester + ".php";
        YearOptions.clear();

        LoadJSOnPageFinish(url, "javascript:var lst = document.getElementsByName(\"prev_years\")[0];\n" +
                "var options = lst.options;\n" +
                "\nvar res = '';" +
                "for(var i = 0; i < options.length; i++)\n" +
                "{\n" +
                "\tres += options[i].value + '╖'\n" +
                "}\nAndroid.AddOptions(res);");
    }


    public boolean LoadUrl(String url)
    {
        if(!MainActv.webViewer.getUrl().equals(url))
        {
            MainActv.webViewer.loadUrl(url);
            return true;
        }
        else
            return false;
    }

    public void LoadJSOnPageFinish(final String url, final String JS)
    {
        if(url.equals(MainActv.webViewer.getUrl()))
            MainActv.webViewer.loadUrl(JS);
        else
        {
            MainActv.webViewer.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView web, String url) {
                    MainActv.webViewer.loadUrl(JS);
                }
            });
            MainActv.webViewer.loadUrl(url);
        }

    }
    public void LoadJSOnPageFinish(final String JS, final Runnable toRun)
    {
        MainActv.webViewer.post(new Runnable() {
            @Override
            public void run() {
                MainActv.webViewer.loadUrl(JS);
                MainActv.webViewer.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView web, String url) {
                        toRun.run();
                    }
                });
            }
        });
    }

    public void ResetOnPageFinish()
    {
        MainActv.webViewer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView web, String url) {

            }
        });
    }

    int GetOptionIndex(String option)
    {
        for(int i = 0; i < YearOptions.size(); i++)
        {
            if(option.equals(YearOptions.get(i)))
                return i;
        }
        return -1;
    }
}
