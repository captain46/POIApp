package at.fhj.mad.poiapp.service;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Simone on 30.03.2017.
 */

public class HttpHelper extends AsyncTask<String, Void, String> {

    private ICallback callback;

    public HttpHelper(ICallback callback) {
        setCallback(callback);
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder sb = new StringBuilder();

        try {
            //get the address
            URL url = new URL(params[0]);

            // create URL Connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

            // read input stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            Log.i("INTERNET", sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.handleResult(s);
    }

    public ICallback getCallback() {
        return callback;
    }

    public void setCallback(ICallback callback) {
        this.callback = callback;
    }
}
