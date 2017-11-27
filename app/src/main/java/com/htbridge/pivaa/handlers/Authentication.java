package com.htbridge.pivaa.handlers;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.htbridge.pivaa.handlers.Encryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Authentication extends AppCompatActivity {

    public Authentication() {
    }


    /**
     * Save loggin info
     * @param context
     * @param username
     * @param password
     * @return
     */
    public boolean saveCache(Context context, String username, String password) {
        String content = username + ":" + password;

        Log.i( "info", "saveLoginInfo: username = " + username + " | password = " + password);

        try {
            File outputDir = context.getCacheDir(); // context being the Activity pointer
            File outputFile = File.createTempFile("cache", "", outputDir);

            content = Encryption.hash("MD5", content);

            Log.i( "saveLoginInfo", "Saving to file " + outputFile.getAbsoluteFile() + " md5 content = " + content);

            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content + "\n");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }



    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    /**
     * Creates lockfile
     * @return boolean
     */
    public boolean createLockFile(Context context, String text) {
        try {
            Log.i( "info", "createLockFile: start");
            FileOutputStream fos = context.openFileOutput("lockfile", context.MODE_WORLD_WRITEABLE);
            String string = text + "\n";
            fos.write(string.getBytes());
            fos.close();
            Log.i( "info", "createLockFile: done");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Save username and password to external file
     * @param context
     * @param username
     * @param password
     * @return boolean
     */
    public boolean saveLoginInfoExternalStorage(Context context, String username, String password) {
        if (isExternalStorageWritable()) {
            Log.i( "htbridge", "saveLoginInfoExternalStorage: writable, all ok!");
            Log.i( "htbridge", "getExternalStorageDirectory = " + Environment.getExternalStorageDirectory());
            Log.i( "htbridge", "getExternalStoragePublicDirectory = " + context.getExternalFilesDir(null) );



            String filename = context.getExternalFilesDir(null) + "/credentials.dat";

            Log.i( "htbridge", "saveLoginInfoExternalStorage: username = " + username + " | password = " + password);
            Log.i( "htbridge", "saveLoginInfoExternalStorage: opening for writing " + filename);

            File file = new File(context.getExternalFilesDir(null), "/credentials.dat");

            Log.i( "htbridge", "saveLoginInfoExternalStorage: opening for reading " + filename);

            try {
                String content = username + ":" + password + "\n";

                file.createNewFile();
                FileOutputStream fOut = new FileOutputStream(file);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

                myOutWriter.write(content);
                myOutWriter.close();
                fOut.flush();
                fOut.close();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }


            return true;
        } else {
            return false;
        }
    }

    /**
     * Read stored username/password
     * @param context
     * @return
     */
    public String loadLoginInfoExternalStorage(Context context) {
        if (isExternalStorageReadable()) {
            Log.i( "htbridge", "loadLoginInfoExternalStorage: readable, all ok!");
            Log.i( "htbridge", "getExternalStorageDirectory = " + Environment.getExternalStorageDirectory());
            Log.i( "htbridge", "getExternalStoragePublicDirectory = " + context.getExternalFilesDir(null) );


            String filename = context.getExternalFilesDir(null) + "/credentials.dat";
            File file = new File(context.getExternalFilesDir(null), "/credentials.dat");

            Log.i( "htbridge", "loadLoginInfoExternalStorage: opening for reading " + filename);

            try {
                FileInputStream inputStream = new FileInputStream(file);

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    Log.i("htbridge", line);
                    sb.append(line).append("\n");
                }
                reader.close();
                return sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

}
