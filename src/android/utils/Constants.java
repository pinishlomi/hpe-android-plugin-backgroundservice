package android.utils;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by shlomi on 11/03/2015.
 */
public class Constants {
    public static final String SITESCOPE_TAG = "SiteScope";
    public static final List<String> AvailableLocaleLanguageCodes = asList(
            "en",
            "fr",
            "ja",
            "ko",
            "zh"
    );
    public static final String defaulLocaleLanguageCodes = "en";
    public static Context context = null;

    public static void appendLog(String text)
    {
        File logFile = new File("sdcard"+ File.separator+"hp_sitescope.txt");
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.flush();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
