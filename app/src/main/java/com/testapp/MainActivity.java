package com.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    ProgressDialog prgDialog;
    EditText email,password;
    private OkHttpClient client;
    Button btn_signup;
    public static String SERVER_URL  = "http://internetofdigitalthings.com/test/registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No title bar will be shown
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //This will show the screen in full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_main);
        prgDialog = new ProgressDialog(MainActivity.this);
        prgDialog.setCancelable(false);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        client = new OkHttpClient();
        btn_signup = (Button)findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeHTTPCall();
            }
        });
    }


    public void makeHTTPCall() {
        prgDialog.setMessage("Loading");
        prgDialog.show();
        RequestBody requestBody= RequestBuilder.LoginBody(email.getText().toString(), password.getText().toString());
        ApiCall apiCall = new ApiCall();
        apiCall.POST(client, SERVER_URL, requestBody, new IAppDataDownloader() {
            @Override
            public void downloaderStatus(int status, final Object data) {
                prgDialog.dismiss();
                if (status == 200) {
                    if (data != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run(){
                                try {

                                    JSONObject jArray = new JSONObject((String)data);
                                    System.out.println(jArray);
                                    Toast.makeText(getApplicationContext(),jArray.getString("message"),Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block

                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }else {
                    Log.e("Error","occurred");
                }
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
