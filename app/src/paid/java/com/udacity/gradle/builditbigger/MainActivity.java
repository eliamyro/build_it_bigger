package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.androidjoke.AndroidJokeActivity;
import com.udacity.gradle.builditbigger.backend.jokeBeanApi.JokeBeanApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        // STEP 1
        // Create an instance of Jokes from Java library
        // Jokes joke = new Jokes();
        // Show a Toast when button is clicked.
        // Toast.makeText(this, joke.getJoke(), Toast.LENGTH_LONG).show();

        // STEP 2
        // Send a joke created from Jokes to AndroidJokeActivity.
        // Intent androidIntent = new Intent(this, AndroidJokeActivity.class);
        // androidIntent.putExtra(AndroidJokeActivity.JOKE_KEY, joke.getJoke());
        // startActivity(androidIntent);

        new EndpointsAsyncTask(this, mProgressBar).execute();
    }
}

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static JokeBeanApi myApiService = null;
    private Context mContext;
    private ProgressBar mProgressBar;

    private EndpointsAsyncTaskListener mListener = null;
    private Exception mError = null;

    public EndpointsAsyncTask(Context context, ProgressBar progressBar){
        mContext = context;
        mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            JokeBeanApi.Builder builder = new JokeBeanApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - turn off compression when running against local devappserver

                    // localhost's IP address in Android emulator
                    // .setRootUrl("http://10.0.2.2:8080/_ah/api/")

                    // localhost's IP address in Genymotion
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.showJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public EndpointsAsyncTask setListener(EndpointsAsyncTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected void onPostExecute(String result) {
        // Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        if (this.mListener != null)
            this.mListener.onComplete(result, mError);

        // Retrieve joke from GCE and launch the activity from the Android Library to display it.
        Intent androidIntent = new Intent(mContext, AndroidJokeActivity.class);
        androidIntent.putExtra(AndroidJokeActivity.JOKE_KEY, result);
        mContext.startActivity(androidIntent);

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onCancelled() {
        if (this.mListener != null) {
            mError = new InterruptedException("AsyncTask cancelled");
            this.mListener.onComplete(null, mError);
        }
    }

    public static interface EndpointsAsyncTaskListener {
        public void onComplete(String message, Exception e);
    }
}
