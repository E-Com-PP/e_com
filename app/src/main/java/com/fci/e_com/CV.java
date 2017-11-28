package com.fci.e_com;

/**
 * Created by Mano on 9/18/2017.
 */
import android.webkit.WebViewClient;
import android.webkit.WebView;

public class CV {

    String EnglishName, CompanyName,JobTitle, PhoneNo, MobileNo, Nationality, CountryOfBirth, NationalityE, CountryOfBirthE, NationalID,SpecialTemporaryCertificate, DateOfBirth, Fax, Email, Address, SchoolPercentageGrade, Flanguage, Slanguage, Tlanguage, TechnicalSkills
            , Certificates, Hobbies, SportsMedals,RBParty,RBHightschool,RBSex, FlanguageMark, MathMark;

    public void SaveOldValues( String EnglishName,String CompanyName,String JobTitle,String PhoneNo,String MobileNo,String Nationality,String CountryOfBirth,String NationalityE,String CountryOfBirthE,String NationalID, String SpecialTemporaryCertificate,String DateOfBirth,String Fax,String Email,String Address,
                 String SchoolPercentageGrade,String Flanguage,String Slanguage,String Tlanguage,String TechnicalSkills,String Certificates,String Hobbies,String SportsMedals,String RBParty ,String RBHightschool,String RBSex
            ,String FlanguageMark,String MathMark)
    {
        this.EnglishName=EnglishName;
        this.CompanyName=CompanyName;
        this.JobTitle=JobTitle;
        this.PhoneNo=PhoneNo;
        this.MobileNo=MobileNo;
        this.Nationality=Nationality;
        this.CountryOfBirth=CountryOfBirth;
        this.NationalityE =NationalityE;
        this.CountryOfBirthE =CountryOfBirthE;
        this.NationalID=NationalID;
        this.SpecialTemporaryCertificate=SpecialTemporaryCertificate;
        this.DateOfBirth=DateOfBirth;
        this.Fax=Fax;
        this.Email=Email;
        this.Address=Address;
        this.SchoolPercentageGrade =SchoolPercentageGrade;
        this.Flanguage=Flanguage;
        this.Slanguage=Slanguage;
        this.Tlanguage=Tlanguage;
        this.TechnicalSkills=TechnicalSkills;
        this.Certificates=Certificates;
        this.Hobbies=Hobbies;
        this.SportsMedals=SportsMedals;
        this.RBParty=RBParty;
        this.RBHightschool=RBHightschool;
        this.RBSex=RBSex;
        this.FlanguageMark =FlanguageMark;
        this.MathMark =MathMark;
    }
    public void GetOldValues()
    {
        MainActivity.webViewer.loadUrl("https://my.fci-cu.edu.eg/content.php?pg=mycontact.php");
        MainActivity.webViewer.setWebViewClient(new WebViewClient()
                                         {
                                             @Override
                                             public void onPageFinished(WebView web, String url)
                                             {
                                                 web.loadUrl("javascript:function RB(){for (var x=0;x<=8;x+=2){if ((document.getElementsByClassName('FormTable')[0].rows[18].children[1].children[x].checked)) return x;}}" +
                                                         " Android.SaveOldValues("+"document.getElementsByClassName('FormTable')[0].rows[2].children[1].children[0].value,"+
                                                         "document.getElementsByClassName('FormTable')[0].rows[4].children[1].children[0].value," +
                                                         "document.getElementsByClassName('FormTable')[0].rows[5].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[6].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[7].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[8].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[9].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[10].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[11].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[12].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[13].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[14].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[15].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[16].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[17].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[20].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[21].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[23].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[24].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[26].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[27].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[28].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[29].children[1].children[0].value" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[3].children[1].children[0].checked" +
                                                         "RB();" +
                                                         "document.getElementsByClassName('FormTable')[0].rows[19].children[1].children[0].checked"+
                                                         "document.getElementsByClassName('FormTable')[0].rows[22].children[1].children[0].selectedOptions[0].innerText"+
                                                         "document.getElementsByClassName('FormTable')[0].rows[25].children[1].children[0].selectedOptions[0].innerText"+")")
                                                         ;
                                             }
                                         }

        );

    }
    public void ChangeValues(String EnglishName,String CompanyName,String JobTitle,String PhoneNo,String MobileNo,String Nationality,String CountryOfBirth,String NationalityE,String CountryOfBirthE,String NationalID, String SpecialTemporaryCertificate,String DateOfBirth,String Fax,String Email,String Address,
                             String SchoolPercentageGrade,String Flanguage,String Slanguage,String Tlanguage,String TechnicalSkills,String Certificates,String Hobbies,String SportsMedals,String RBParty ,String RBHightschool,String RBSex
            ,String FlanguageMark,String MathMark)
    {
        MainActivity.webViewer.loadUrl("javascript:("+"document.getElementsByClassName(FormTable')[0].rows[2].children[1].children[0].value=EnglishName"+
                "document.getElementsByClassName('FormTable')[0].rows[4].children[1].children[0].value=CompanyName" +
                        "document.getElementsByClassName('FormTable')[0].rows[5].children[1].children[0].value=JobTitle" +
                        "document.getElementsByClassName('FormTable')[0].rows[6].children[1].children[0].value=PhoneNo" +
                        "document.getElementsByClassName('FormTable')[0].rows[7].children[1].children[0].value=MobileNo" +
                        "document.getElementsByClassName('FormTable')[0].rows[8].children[1].children[0].value=Nationality" +
                        "document.getElementsByClassName('FormTable')[0].rows[9].children[1].children[0].value=CountryOfBirth" +
                        "document.getElementsByClassName('FormTable')[0].rows[10].children[1].children[0].value=NationalityE" +
                        "document.getElementsByClassName('FormTable')[0].rows[11].children[1].children[0].value=CountryOfBirthE" +
                        "document.getElementsByClassName('FormTable')[0].rows[12].children[1].children[0].value=NationalID" +
                        "document.getElementsByClassName('FormTable')[0].rows[13].children[1].children[0].value=SpecialTemporaryCertificate" +
                        "document.getElementsByClassName('FormTable')[0].rows[14].children[1].children[0].value=DateOfBirth" +
                        "document.getElementsByClassName('FormTable')[0].rows[15].children[1].children[0].value=Fax" +
                        "document.getElementsByClassName('FormTable')[0].rows[16].children[1].children[0].value=Email" +
                        "document.getElementsByClassName('FormTable')[0].rows[17].children[1].children[0].value=Address" +
                        "document.getElementsByClassName('FormTable')[0].rows[20].children[1].children[0].value=SchoolPercentageGrade" +
                        "document.getElementsByClassName('FormTable')[0].rows[21].children[1].children[0].value=Flanguage" +
                        "document.getElementsByClassName('FormTable')[0].rows[23].children[1].children[0].value=Slanguage" +
                        "document.getElementsByClassName('FormTable')[0].rows[24].children[1].children[0].value=Tlanguage" +
                        "document.getElementsByClassName('FormTable')[0].rows[26].children[1].children[0].value=TechnicalSkills" +
                        "document.getElementsByClassName('FormTable')[0].rows[27].children[1].children[0].value=Certificates" +
                        "document.getElementsByClassName('FormTable')[0].rows[28].children[1].children[0].value=Hobbies" +
                        "document.getElementsByClassName('FormTable')[0].rows[29].children[1].children[0].value=SportsMedals" +
                        "document.getElementsByClassName('FormTable')[0].rows[3].children[1].children[RBParty].checked=true" +
                        "document.getElementsByClassName('FormTable')[0].rows[18].children[1].children[RBHightschool].checked=true" +
                        "document.getElementsByClassName('FormTable')[0].rows[19].children[1].children[RBSex].checked=true"+
                        "document.getElementsByClassName('FormTable')[0].rows[22].children[1].children[0].children[FlanguageMark].selected=true"+
                        "document.getElementsByClassName('FormTable')[0].rows[25].children[1].children[0].children[MathMark].selected=true");

    }
    public void ChangeValues2(final String Newdata,final String NewdataPlace, final String Type)
    {
        MainActivity.webViewer.loadUrl("javascript:document.getElementsByClassName('FormTable')[0]." + NewdataPlace +"."+ Type +"=" + Newdata);
//        .trigger('click');
    };
    public void Upadre()
    {
        MainActivity.webViewer.loadUrl("javascript:("+"$('[name=\"add\"]').click()");

    }


}
