package fr.univtln.tbezenger.r2d2translator.Utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requete extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... params) {
        String returnValue = "";
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (params[1] == "GET") {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } else if (params[1] == "PUT" || params[1] == "POST") {
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod(params[1]);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                BufferedOutputStream output = new BufferedOutputStream(urlConnection.getOutputStream());
                output.write(params[2].getBytes());
                output.flush();
                returnValue = urlConnection.getResponseMessage();
            } else if (params[1] == "DEL") {
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("DELETE");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
        return returnValue;
    }
}