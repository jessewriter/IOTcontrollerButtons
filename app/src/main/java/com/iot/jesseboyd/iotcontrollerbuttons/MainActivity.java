package com.iot.jesseboyd.iotcontrollerbuttons;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    TextView etResponse;
    TextView tvIsConnected;
    TextView nameTv, durationTv;
    String controllerId;
    LedControllerBean ledController;
    RadioButton blueButton, redButton;
    ToggleButton isOn;
    Button updateJSONButton;
    SeekBar frequencySeekBar;  // slider
    RequestQueue queue;
    RadioGroup radio;
    String ledColor = "white";  // default color
    int frequency = -1;
    private String tag = "JESSE";

    final String restUrl = "https://rest-app-2.herokuapp.com/LedControllers/58df2ca3ca80a50011ecd71a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        radio = (RadioGroup) findViewById(R.id.colorButtons);
        frequencySeekBar = (SeekBar) findViewById(R.id.frequencySeekBar);
        frequencySeekBar.setOnSeekBarChangeListener(sliderControl);
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        //update button
        final Button button = (Button) findViewById(R.id.updateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  updateJSON(v);
                updateButtonAction();
            }
        });

        //change button
        final Button change = (Button) findViewById(R.id.changeButton);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  updateJSON(v);
                PUTrequest();
                Log.d(tag, "put request placed");
            }
        });

        // get reference to the views
        etResponse = (TextView) findViewById(R.id.restResponse);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        nameTv = (TextView) findViewById(R.id.endPointName);
        blueButton = (RadioButton) findViewById(R.id.color_blue);
        redButton = (RadioButton) findViewById(R.id.color_Red);
        durationTv = (TextView) findViewById(R.id.durationTv);
        frequencySeekBar = (SeekBar) findViewById(R.id.frequencySeekBar);
        isOn = (ToggleButton) findViewById(R.id.isOnButton);
        updateJSONButton = (Button) findViewById(R.id.updateButton);
/////////////////////
        // check if you are connected or not
        if (isConnected()) {
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are conncted");
        } else {
            tvIsConnected.setText("You are NOT conncted");
        }
        // call AsynTask to perform network operation on separate thread
        // new HttpAsyncTask().execute(restUrl);


        colorRadioButtons(); // check for color change
//////////////////
        // fab button action takes user to https://www.linkedin.com/in/mrjesseboyd
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "(will open https://www.linkedin.com/in/mrjesseboyd)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //delay the intent
                Handler mHandler = new Handler();
                mHandler.postDelayed(mUpdateTimeTask, 1000);
            }

            Runnable mUpdateTimeTask = new Runnable() {
                public void run() {
                    // do what you need to do here after the delay
                    //on click of the envelope icon open my LinkedIn page
                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.linkedin.com/in/mrjesseboyd"));
                    startActivity(intent);
                }
            };
        });

        ////////// end Fab
    }

    private void updateButtonAction() {
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, restUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String addLineBreaks = response.replace(",", System.getProperty("line.separator"));
                        etResponse.setText("Response is: \n" + addLineBreaks);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                etResponse.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else
            return false;
    }

// PUT request
    public void PUTrequest() {
        StringRequest putRequest = new StringRequest(Request.Method.PUT, restUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d(tag, "PUT Response" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(tag, "PUT Error.Response " + error.networkResponse.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
               Map<String, String> params = new HashMap<>();
                params.clear();
                params.put("isOn", isOn.getText().toString());
                if (nameTv.getText().length() > 0) {
                    Log.d(tag, "name text length =" + nameTv.getText().length() + nameTv.getText().toString());
                    params.put("name", nameTv.getText().toString());
                }
                params.put("color", ledColor);
                if (frequency != (-1)) {
                    params.put("frequency", String.valueOf(frequency));
                }
                if (durationTv.getText().length() > 0) {
                    params.put("duration", durationTv.getText().toString());
                }

                Log.d(tag, "param size in getParams b " + params.size());
                return params;
            }

        };
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                Toast.makeText(getApplicationContext(), "Request Finished :" + request.hasHadResponseDelivered(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(putRequest);
    }


    private void colorRadioButtons() {

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button
                        ledColor = "blue";
                        Toast.makeText(getApplicationContext(), "Selected button blue " + index, Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // secondbutton
                        ledColor = "red";
                        Toast.makeText(getApplicationContext(), "Selected button red " + index, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private SeekBar.OnSeekBarChangeListener sliderControl = new
            SeekBar.OnSeekBarChangeListener() {
                int freqProgress =0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    freqProgress = progress;  // set frequency

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Log.d(tag, "on start tracking touch = " + frequency);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    frequency = freqProgress;
                    Toast.makeText(getApplicationContext(), "slider value:  " + frequency, Toast.LENGTH_SHORT).show();
                    Log.d(tag, "on stop tracking touch = " + frequency);
                }
            };


}
