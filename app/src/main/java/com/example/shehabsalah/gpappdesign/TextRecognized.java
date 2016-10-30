package com.example.shehabsalah.gpappdesign;

import android.util.Log;

/**
 * Created by hayyan2001 on 6/2/16.
 */
public class TextRecognized {
    public String fetchText(int id){
        Log.i("recogRunTime","lookup table");
        Log.i("recogRunTime",(id-1)+"");
        /*String[] text= {"عام","خوف","حقيقه","حقا","حاله","الحب","حرير","حسنا","ابيض","احمر","الاسكندريه",
                        "ألم","امان","اسد","الاسماعليه", "استطيع", "اسوان","اطباق","اول","ايام","ايضا",
                        "ازرق","بين","بورسعيد","دواء","ضوضاء","اسبوع","جابر","جميع","جميل","الليل","المنصوره",
                        "النزهه","ام","انظر","رقم","ربما","سعيد","سعيده","صحيح","سفاجا","شهر","شكرا","سمنة","سيوه"
                        ,"تحت","التجمع","زجاجة"};*/

        String[] text= {"أسد","الأسكندرية","الأسماعيلية","الاول","البحر" ,"التجمع","الجديدة","الحقيقة","الحي","الخامس" ,"السابع" ,
                "العبور","أيضاً","أسف","أم","أين","حسناً","حقاً","رقم","سعيد","شارع",
                "شهر","عام","عيد","مدينة","مصر" , "نصر","يوجد",""};
        if(id-1 >= 0 && id-1 < text.length)
            return text[id-1<text.length?id-1:id]+" ";
        else return "";
    }
}
