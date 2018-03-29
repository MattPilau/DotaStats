package com.app.dotastats.dotastats.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.app.dotastats.dotastats.FavoritePlayerLastGameService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class UtilsHttp {

    // performs the actual request to the API
    private static String request(URL url) throws IOException{

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.connect();

        int status = connection.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK)
            Log.i("error",Integer.toString(status));

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return content.toString();
    }

    // sets up the http request and returns the received JSON
    public static String getInfoFromAPI(String request){

        URL url = null;
        try {
            url = new URL(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String dataCleaned = null;
        try {
            // executes the http request, connects to the API
            dataCleaned = UtilsHttp.request(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataCleaned;
    }

    // simple request called when we have the URL of a picture and wants it as a Bitmap
    public static Bitmap getPicture(String url) throws IOException {
        URL url2 = new URL(url);

        HttpURLConnection connection  = (HttpURLConnection) url2.openConnection();
        InputStream is = connection.getInputStream();
        return BitmapFactory.decodeStream(is);
    }

    // same principle as the one but the image of the hero needs to be handled specifically because of their URL
    public static Bitmap getHeroImage(String url) throws IOException{

        String newUrl = "http://cdn.dota2.com" + url;
        return getPicture(newUrl);
    }

    // begins to check if a favorite player finished a game
    public static void startRepetitiveRequest(Context context){
        Intent intentService = new Intent(context, FavoritePlayerLastGameService.class);
        context.startService(intentService);
    }

}
