package edu.ntu.cltk.android;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SimpleHttpClient implements HttpClient {

    /**
     * Send a post http request, and return the response of the request.
     * This operation needs to be run in a background thread, that is, it should be not run
     * in the main process to avoid of blocking GUI
     *
     * @param sUrl
     * @param messages
     * @return
     */
    public static String post(String sUrl, List<NameValuePair> messages) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(sUrl);
        try {
            post.setEntity(new UrlEncodedFormEntity(messages));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(String sUrl) {
        InputStream is = null;
        try {
            URL url = new URL(sUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(1000 * 60 * 5);
            urlConnection.connect();
            is = urlConnection.getInputStream();
            byte data[] = new byte[1024 * 4];
            int len = 0;
            long progress = 0;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(data)) != -1) {
                sb.append(new String(data, 0, len));
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }
            }
        }
        return null;
    }


}
