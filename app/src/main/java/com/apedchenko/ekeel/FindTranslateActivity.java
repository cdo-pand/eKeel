package com.apedchenko.ekeel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apedchenko.ekeel.utils.GeneralData;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static com.apedchenko.ekeel.utils.GeneralData.requestUrlGlobalDictionary;
import static com.apedchenko.ekeel.utils.NetworkUtils.generateURL;
import static com.apedchenko.ekeel.utils.NetworkUtils.getResponseFromURL;

public class FindTranslateActivity extends AppCompatActivity {

    //region Variable declaration
    private EditText searchField;
    private Button btnShow;
    private ProgressBar loading;
    private TextView result;
    private LinearLayout findTranslatePage;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_translate);

        searchField = findViewById(R.id.et_search_field);
        btnShow = findViewById(R.id.b_show_translate);
        loading = findViewById(R.id.pb_load_find_translate);
        result = findViewById(R.id.tv_result);
        findTranslatePage = findViewById(R.id.findTranslatePage);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent dictionary = new Intent(getApplicationContext(), DictionaryActivity.class);
        startActivity(dictionary);
    }


    public void showTranslate(View view) {
        loading.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        String url = requestUrlGlobalDictionary;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response.trim());
                            boolean errorStatus = Boolean.parseBoolean(jsonResponse.getString("error"));
                            if (errorStatus) {
                                result.setText(jsonResponse.getString("error_msg"));
                                result.setTextColor(Color.RED);
                            } else {
                                result.setText("");
                                Iterator<String> keys = jsonResponse.keys();

                                String tmp = "";
                                String nimetav;
                                String omastav;
                                String osastav;
                                String theme;
                                String translate = "";
                                String type;

                                while (keys.hasNext()) {
                                    String key = keys.next();

                                    if (jsonResponse.get(key) instanceof JSONObject) {
                                        omastav = "";
                                        osastav = "";
                                        theme = "";

                                        nimetav = "I: " + ((JSONObject) jsonResponse.get(key)).getString("nimetav") + "\n";
                                        type = "Тип: " + ((JSONObject) jsonResponse.get(key)).getString("type") + "\n\n\n";
                                        translate = "Перевод: " + ((JSONObject) jsonResponse.get(key)).getString("translate") + "\n";


                                        if (!((JSONObject) jsonResponse.get(key)).getString("omastav").equals("null")
                                        && !((JSONObject) jsonResponse.get(key)).getString("osastav").equals("null")) {
                                            omastav = "II: " + ((JSONObject) jsonResponse.get(key)).getString("omastav") + "\n";
                                            osastav = "III: " + ((JSONObject) jsonResponse.get(key)).getString("osastav") + "\n";
                                        }
                                        if (!((JSONObject) jsonResponse.get(key)).getString("theme").equals("null")) {
                                            theme = "Тема: " + ((JSONObject) jsonResponse.get(key)).getString("theme") + "\n";
                                        }

                                        tmp += nimetav
                                                + omastav
                                                + osastav
                                                + translate
                                                + "---" + "\n"
                                                + theme
                                                + type;
                                    }
                                }

                                result.setText(tmp);
                                result.setTextColor(Color.BLACK);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        loading.setVisibility(View.INVISIBLE);
                        GeneralData.hideSoftKeyBoard(FindTranslateActivity.this);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        result.setTextColor(Color.RED);
                        result.setText("Error");
                        GeneralData.hideSoftKeyBoard(FindTranslateActivity.this);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("findWord", searchField.getText().toString());

                return params;
            };

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "Mozilla/5.0");
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                return headers;
            }
        };
        queue.add(postRequest);
    }
}