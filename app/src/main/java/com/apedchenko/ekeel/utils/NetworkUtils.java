package com.apedchenko.ekeel.utils;

import android.net.Uri;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NetworkUtils {
    private static final String API_BASE_URL = "https://a-capoeira.com/ekeel/api";
    private static final String API_GET = "/global_dictionary.php";

    public static URL generateURL(String stringUrl) {
        Uri builtUri = Uri.parse(API_BASE_URL + API_GET)
                .buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromURL(URL url, Map<String, String> keys) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        OutputStream os = null;
        byte[] out = keys.toString().getBytes();
        System.out.println(keys.toString());

        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            String urlParams = "getGlobalRandom=1";

            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            urlConnection.connect();

            try {
//                os = urlConnection.getOutputStream();
                DataOutputStream dStream = new DataOutputStream(urlConnection.getOutputStream());
//                os.write(out);
                dStream.writeBytes(urlParams);
//                dStream.writeBytes(keys.toString());
                dStream.flush();
                dStream.close();

            } catch (Exception e) {
                System.err.print(e.getMessage());
            }

//            if (HttpURLConnection.HTTP_OK == urlConnection.getResponseCode()) {}


            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); // all row

            boolean hasInput = scanner.hasNext();



            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (UnknownHostException e) {
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }

}
