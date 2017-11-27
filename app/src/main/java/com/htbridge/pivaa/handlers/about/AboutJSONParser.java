package com.htbridge.pivaa.handlers.about;


import android.content.Context;
import android.util.JsonReader;

import com.htbridge.pivaa.handlers.about.AboutRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AboutJSONParser {
    String path = "";
    Context context;

    public AboutJSONParser(String path, Context context) {
        this.path = path;
        this.context = context;
    }


    public ArrayList<AboutRecord> parse() {
        try {
            InputStream in = this.context.getAssets().open(path);
            return readJsonStream(in);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<AboutRecord> list = new ArrayList<AboutRecord>();
        return list;
    }

    /**
     * Reading JSON stream from InputStream
     * @param in
     * @return
     * @throws IOException
     */
    private ArrayList<AboutRecord> readJsonStream(InputStream in) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            try {
                return readMessagesArray(reader);
            } finally {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<AboutRecord> list = new ArrayList<AboutRecord>();
        return list;
    }

    /**
     * Iterating objects
     * @param reader
     * @return
     * @throws IOException
     */
    private ArrayList<AboutRecord> readMessagesArray(JsonReader reader) throws IOException {
        ArrayList<AboutRecord> messages = new ArrayList<AboutRecord>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    /**
     * Paring object
     * @param reader
     * @return
     * @throws IOException
     */
    private AboutRecord readMessage(JsonReader reader) throws IOException {
        String name = "";
        String description = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String key = reader.nextName();

            if (key.equals("name")) {
                name = reader.nextString();

            } else if (key.equals("description")) {
                description = reader.nextString();

            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new AboutRecord(name, description);
    }

}
