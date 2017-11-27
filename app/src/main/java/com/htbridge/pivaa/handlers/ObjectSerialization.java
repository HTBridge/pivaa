package com.htbridge.pivaa.handlers;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectSerialization implements Serializable {
    private String proof = ":D";
    private String location;

    public ObjectSerialization(String location) {
        this.location = location;
    }

    /**
     * Method for setting value of proof
     * @param p
     */
    public void setProof(String p) {
        this.proof = p;
    }

    /**
     * Method for getting value of proof
     * @return
     */
    public String getProof() {
        return this.proof;
    }

    /**
     * Saving object
     */
    public void saveObject() {
        try {
            File f = new File(this.location);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();

            Log.i("htbridge", "[ SAVE ] Serialization SUCCESS!");
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("htbridge", "[ SAVE ] Serialization FAIL!");
    }

    /**
     * Loading object
     */
    public void loadObject() {
        try {
            File f = new File(this.location);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream is = new ObjectInputStream(fis);
            ObjectSerialization des = (ObjectSerialization) is.readObject();
            is.close();
            fis.close();

            this.setProof(des.getProof());

            Log.i("htbridge", "[ LOAD ] Serialization SUCCESS!");
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.i("htbridge", "[ LOAD ] Serialization FAIL!");
    }
}
