package com.htbridge.pivaa.handlers;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;


public class LoadCode extends ClassLoader {
    Context context;

    public LoadCode(Context context) {
        this.context = context;
    }

    /**
     * Method for load and execute class
     * @param classname
     * @return
     */
    public String load(String classname) {
        StringBuilder sb = new StringBuilder();

        String path = classname;
        path = path.replace('.', '/');
        path = "assets/" + path + ".jar";


        String[] classnameExploded = classname.split("\\.");
        classname = classnameExploded[classnameExploded.length - 1];

        String resCopy = copyAssetsToFiles(path);
        sb.append(resCopy).append("\n").append("\n");

        String filePath = context.getFilesDir().getPath() + "/test.jar";

        Log.i("htbridge", "Loading class file " + filePath);
        sb.append("Loading class file " + filePath).append("\n").append("\n");


        /**
         * Print all assets
         */
        /*try {
            String[] res = this.context.getAssets().list("com/htbridge/raw");
            for (String s: res) {
                Log.i("htbridge", s);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }*/


        try {
            String dexDir = this.context.getDir("dex", 0).getAbsolutePath();
            ClassLoader parent = getClass().getClassLoader();
            DexClassLoader loader  = new DexClassLoader(filePath, dexDir, null, parent);
            Class c = loader.loadClass(classname);
            Object o = c.newInstance();
            Method m = c.getMethod("run");

            Object res = m.invoke(o);

            Log.i("htbridge", res.toString());
            sb.append("Output from executed code: \"" + res.toString() + "\"").append("\n").append("\n");

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        sb.append("Error in running").append("\n").append("\n");

        return sb.toString();
    }


    /**
     * Copy the DEX file from assets to folder for optimized DEX
     */
    public String copyAssetsToFiles(String sourceFile) {
        String fileDir = this.context.getFilesDir().getPath() + "/";
        File workingDir = new File(fileDir);
        if (!workingDir.exists()) {
            workingDir.mkdirs();
        }
        Log.i("htbridge", "Copying " + sourceFile);

        File outFileBin = new File(workingDir, "test.jar");
        if (!outFileBin.exists()) {
            copyFile(this.context, sourceFile, outFileBin);
            outFileBin.setExecutable(true, false);
        }

        return "Copying " + sourceFile + " to " + workingDir + "/test.jar";
    }

    /**
     * Copy file
     */
    private void copyFile(Context context, String sourceFileName, File targetFile) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = context.getAssets().open(sourceFileName);
            out = new FileOutputStream(targetFile);
            byte[] temp = new byte[1024];
            int count = 0;
            while ((count = in.read(temp)) > 0) {
                out.write(temp, 0, count);
            }

            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}