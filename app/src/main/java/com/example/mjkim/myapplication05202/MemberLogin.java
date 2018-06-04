package com.example.mjkim.myapplication05202;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;



public class MemberLogin extends AppCompatActivity {

    private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON = "mjkim";
    private static final String TAG_ID = "id";
    private static final String TAG_PASSWORD = "password";
    private EditText mEditTextId;
    private EditText mEditTextPassword;


    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);
        try{
            mEditTextId = (EditText) findViewById(R.id.IdInsert);
            mEditTextPassword = (EditText) findViewById(R.id.PasswordInsert);
            Button buttonInsert = (Button) findViewById(R.id.Sign_in);
            buttonInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String aid = mEditTextId.getText().toString();

                    String apassword = mEditTextPassword.getText().toString();



                }
            });
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String mid = item.getString(TAG_ID);
                String mpassword = item.getString(TAG_PASSWORD);
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, mid);
                hashMap.put(TAG_PASSWORD, mpassword);

                mArrayList.add(hashMap);
            }



        } catch (JSONException e) {

        Log.d(TAG, "showResult : ", e);
    }




    class GetData extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog;
            String errorString = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(MemberLogin.this,
                        "Please Wait", null, true, true);
            }


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                progressDialog.dismiss();
                mTextViewResult.setText(result);
                Log.d(TAG, "response  - " + result);

                if (result == null) {

                    mTextViewResult.setText(errorString);
                } else {

                    mJsonString = result;

                }
            }


            @Override
            protected String doInBackground(String... params) {

                String serverURL = params[0];

                try {

                    URL url = new URL(serverURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.connect();


                    int responseStatusCode = httpURLConnection.getResponseCode();
                    Log.d(TAG, "response code - " + responseStatusCode);

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();
                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }


                    bufferedReader.close();


                    return sb.toString().trim();


                } catch (Exception e) {

                    Log.d(TAG, "InsertData: Error ", e);
                    errorString = e.toString();

                    return null;
                }

            }
        }



        }
    }
