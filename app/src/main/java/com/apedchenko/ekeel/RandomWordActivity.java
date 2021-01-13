package com.apedchenko.ekeel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apedchenko.ekeel.utils.GeneralData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static com.apedchenko.ekeel.utils.GeneralData.requestUrlGlobalDictionary;

public class RandomWordActivity extends AppCompatActivity {

    private Button btnShow, btnCheck;
    private TextView result_rand_word, tvHideNimetav, tvHideOmastav, tvHideOsastav, tvHideTranslate, tvHideType, tvHideTheme;
    private EditText etThemeNumber;
    private LinearLayout randomResultLayout;
    private RadioButton rbOnlyEst, rbOnlyRus;
    private Spinner sGetWordLang, sThemeNumbers;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_word);

        btnShow = findViewById(R.id.b_show_translate);
        btnCheck = findViewById(R.id.b_check_randomWord);
        result_rand_word = findViewById(R.id.tv_result_random_word);
        progressBar = findViewById(R.id.pb_load_random_word);
//        etThemeNumber = findViewById(R.id.etThemeNumberFromRandomWord);
        randomResultLayout = findViewById(R.id.randomResultLayout);
//        rbOnlyEst = findViewById(R.id.rbOnlyEst);
//        rbOnlyRus = findViewById(R.id.rbOnlyRus);

        tvHideNimetav = findViewById(R.id.tvHideNimetavRandom);
        tvHideOmastav = findViewById(R.id.tvHideOmastavRandom);
        tvHideOsastav = findViewById(R.id.tvHideOsastavRandom);
        tvHideTranslate = findViewById(R.id.tvHideTranslateRandom);
        tvHideType = findViewById(R.id.tvHideTypeRandom);
        tvHideTheme = findViewById(R.id.tvHideThemeRandom);
        sThemeNumbers = findViewById(R.id.sThemeNumbers);
        sGetWordLang = findViewById(R.id.sGetWordLang);

//        String[] itemsTranslate = new String[]{"Все", "Только рус", "Только эст"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsTranslate);
//        sGetWordLang.setAdapter(adapter);

        // set data in spinner
        ArrayAdapter<CharSequence> randomWordLangAdapter = ArrayAdapter.createFromResource(this, R.array.randomWordLang, android.R.layout.simple_spinner_item);
        randomWordLangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGetWordLang.setAdapter(randomWordLangAdapter);

        setThemeBookNumbers();


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

    public void randomWord(View view) {
        progressBar.setVisibility(View.VISIBLE);
        randomResultLayout.removeAllViews();
        result_rand_word.setText("");
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        String url = requestUrlGlobalDictionary;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
//                        System.out.println(response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response.trim());
                            boolean errorStatus = Boolean.parseBoolean(jsonResponse.getString("error"));
                            if (errorStatus) {
                                result_rand_word.setText(jsonResponse.getString("error_msg"));
                                result_rand_word.setTextColor(Color.RED);
                            } else {
                                result_rand_word.setText("");
                                String tmp = "";
                                String nimetav = "";
                                String omastav = "";
                                String osastav = "";
                                String theme = "";
                                String translate = "";
                                String type;
                                tvHideNimetav.setText("");
                                tvHideOmastav.setText("");
                                tvHideOsastav.setText("");
                                tvHideTranslate.setText("");
                                tvHideType.setText("");
                                tvHideTheme.setText("");

//                                nimetav = "I: " + jsonResponse.getString("nimetav") + "\n";
//                                type = "Тип: " +  jsonResponse.getString("type") + "\n";
//                                translate = "Перевод: " + jsonResponse.getString("translate") + "\n";
                                nimetav = "I: " + jsonResponse.getString("nimetav");
                                type = jsonResponse.getString("type");
                                translate = jsonResponse.getString("translate");

                                tvHideNimetav.setText(nimetav);
                                tvHideTranslate.setText(translate);
                                tvHideType.setText(type);

                                if (!jsonResponse.getString("omastav").equals("null")
                                && !jsonResponse.getString("osastav").equals("null")) {
//                                    omastav = "II: " + jsonResponse.getString("omastav") + "\n";
//                                    osastav = "III: " + jsonResponse.getString("osastav") + "\n";
                                    omastav = "II: " + jsonResponse.getString("omastav");
                                    osastav = "III: " + jsonResponse.getString("osastav");
                                    tvHideOmastav.setText(omastav);
                                    tvHideOsastav.setText(osastav);
                                }

                                if (!jsonResponse.getString("theme").equals("null")) {
                                    theme = "Тема: " +  jsonResponse.getString("theme") + "\n";
                                    tvHideTheme.setText(theme);
                                }

/*                                tmp += nimetav
                                        + omastav
                                        + osastav
                                        + translate
                                        + "------------" + "\n"
                                        + type
                                        + theme;*/

//                                if (rbOnlyRus.isChecked()) {
//                                    randomResultLayout.removeAllViews();
//                                    btnCheck.setVisibility(View.VISIBLE);
//
//                                    displayOnlyTranslate(randomResultLayout, translate);
//                                    displayOther(randomResultLayout, type, theme);
//                                } else if (rbOnlyEst.isChecked()) {
//                                    randomResultLayout.removeAllViews();
//                                    btnCheck.setVisibility(View.VISIBLE);
//
//                                    displayOnlyEst(randomResultLayout, nimetav, omastav, osastav);
//                                    displayOther(randomResultLayout, type, theme);
//                                } else {
                                    randomResultLayout.removeAllViews();
                                    btnCheck.setVisibility(View.GONE);

                                    displayOnlyEst(randomResultLayout, nimetav, omastav, osastav);
                                    displayOnlyTranslate(randomResultLayout, translate);
                                    displayOther(randomResultLayout, type, theme);
//                                }

                                btnCheck.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        randomResultLayout.removeAllViews();
                                        btnCheck.setVisibility(View.GONE);



                                        displayOnlyEst(randomResultLayout, tvHideNimetav.getText().toString(), tvHideOmastav.getText().toString(), tvHideOsastav.getText().toString());
                                        displayOnlyTranslate(randomResultLayout, tvHideTranslate.getText().toString());
                                        displayOther(randomResultLayout, tvHideType.getText().toString(), tvHideTheme.getText().toString());
                                    }
                                });

//                                TextView typeView = new TextView(getApplicationContext());
//                                typeView.setPadding(0, 20, 0, 10);
//                                typeView.setText(type);
//                                typeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
//                                typeView.setTextSize(24);
//                                randomResultLayout.addView(typeView);
//
//                                if (!TextUtils.isEmpty(theme)) {
//                                    TextView themeView = new TextView(getApplicationContext());
//                                    themeView.setPadding(0, 20, 0, 10);
//                                    themeView.setText(theme);
//                                    themeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
//                                    themeView.setTextSize(24);
//                                    randomResultLayout.addView(themeView);
//                                }
//                                result_rand_word.setText(tmp);
//                                result_rand_word.setTextColor(Color.BLACK);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        result_rand_word.setTextColor(Color.RED);
                        result_rand_word.setText("Error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("getGlobalRandom", "1");
//                if (!TextUtils.isEmpty(etThemeNumber.getText().toString())) {
//                    params.put("getThemeInBook", etThemeNumber.getText().toString());
//                }
                if (!sThemeNumbers.getSelectedItem().toString().equals("Номер темы")) {
                    params.put("getThemeInBook", sThemeNumbers.getSelectedItem().toString());
                }
//                System.out.println("selected: " + sThemeNumbers.getSelectedItem().toString());

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

        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        // specifying the object
        InputMethodManager service = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        // verify if the soft keyboard is open
        if (Objects.requireNonNull(service).isAcceptingText()) service.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
    }

    private void displayOnlyTranslate(LinearLayout randomResultLayout, String translate) {
        TextView translateView = new TextView(getApplicationContext());
        translateView.setPadding(0, 20, 0, 10);
        translateView.setText(translate);
        translateView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreen));
        translateView.setTextSize(24);
        randomResultLayout.addView(translateView);
    }

    private void displayOnlyEst(LinearLayout randomResultLayout, String nimetav, String omastav, String osastav) {
        TextView nimetavView = new TextView(getApplicationContext());
        nimetavView.setPadding(0, 20, 0, 10);
        nimetavView.setText(nimetav);
        nimetavView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        nimetavView.setTextSize(24);
        randomResultLayout.addView(nimetavView);


        if (!TextUtils.isEmpty(omastav) && !TextUtils.isEmpty(osastav)) {
            TextView omastavView = new TextView(getApplicationContext());
            omastavView.setPadding(0, 20, 0, 10);
            omastavView.setText(omastav);
            omastavView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            omastavView.setTextSize(24);
            randomResultLayout.addView(omastavView);

            TextView osastavView = new TextView(getApplicationContext());
            osastavView.setPadding(0, 20, 0, 10);
            osastavView.setText(osastav);
            osastavView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            osastavView.setTextSize(24);
            randomResultLayout.addView(osastavView);
        }
    }

    private void displayOther(LinearLayout randomResultLayout, String type, String theme) {
        TextView typeView = new TextView(getApplicationContext());
        typeView.setPadding(0, 20, 0, 10);
        typeView.setText(type);
        typeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        typeView.setTextSize(24);
        randomResultLayout.addView(typeView);

        if (!TextUtils.isEmpty(theme)) {
            TextView themeView = new TextView(getApplicationContext());
            themeView.setPadding(0, 20, 0, 10);
            themeView.setText(theme);
            themeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            themeView.setTextSize(24);
            randomResultLayout.addView(themeView);
        }
    }


    private void setThemeBookNumbers() {
        progressBar.setVisibility(View.VISIBLE);
        randomResultLayout.removeAllViews();
        result_rand_word.setText("");
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        String url = requestUrlGlobalDictionary;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
//                        System.out.println("themeNumber: " + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response.trim());
                            boolean errorStatus = Boolean.parseBoolean(jsonResponse.getString("error"));
                            if (errorStatus) {
                                result_rand_word.setText(jsonResponse.getString("error_msg"));
                                result_rand_word.setTextColor(Color.RED);
                            } else {
                                ArrayList<String> itemsTranslate = new ArrayList<>();
                                itemsTranslate.add("Номер темы");
                                    try {
                                        Iterator<String> temp = jsonResponse.keys();
                                        while (temp.hasNext()) {
                                            String key = temp.next();
                                            Object value = jsonResponse.get(key);
                                            if (!value.equals(false)) {
                                                itemsTranslate.add(value.toString());
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        itemsTranslate.add("");
                                    }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemsTranslate);
                                sThemeNumbers.setAdapter(adapter);

                                System.out.println("themeNumber: " + itemsTranslate);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        result_rand_word.setTextColor(Color.RED);
                        result_rand_word.setText("Error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("getThemeNumbers", "1");

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