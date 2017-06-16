package com.example.nick.countrypedia.model.restprovider;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpClient extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... strings) {
        InputStream inputStream = null;
        BufferedReader reader = null;

        String response = "";
        try {
            HttpURLConnection connection = ((HttpURLConnection) (new URL(strings[0])).openConnection());
            connection.setRequestMethod("GET");
            inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            response = readAll(reader);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private String readAll(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

}