package edu.ntu.cltk.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SimpleHttpClient implements HttpClient{

	public static String post(String sUrl, String message) {
		throw new UnsupportedOperationException();
	}

	public static String get(String sUrl) {
		InputStream is = null;
		try {
			URL url = new URL(sUrl);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setConnectTimeout(1000 * 60 * 5);
			urlConnection.connect();
			is = urlConnection.getInputStream();
			byte data[] = new byte[1024 * 4];
			int len = 0;
			long progress = 0;
			StringBuilder sb = new StringBuilder();
			while ((len = is.read(data)) != -1){
				sb.append(new String(data, 0, len));
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} finally{
			if (is != null){
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
