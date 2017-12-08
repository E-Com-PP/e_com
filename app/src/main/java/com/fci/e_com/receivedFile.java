package com.fci.e_com;

public class receivedFile
{
    public String date, from, fileDescription, to, downloadLink;
    MainActivity MainActv;

    public receivedFile(MainActivity MainActv, String date, String from, String fileDescription, String to, String downloadLink)
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
