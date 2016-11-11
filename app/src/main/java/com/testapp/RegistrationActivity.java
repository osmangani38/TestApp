package com.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.testapp.interfaces.IAppDataDownloader;
import com.testapp.utils.ApiCall;
import com.testapp.utils.RequestBuilder;
import com.testapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RegistrationActivity extends AppCompatActivity {
    ProgressDialog prgDialog;
    EditText userName, email, password, confirmpassword, phone, userAddress, userState,
            userCity, zipcode, storeName, storeContactName, storeAddress;
    LinearLayout signin;
    TextInputLayout txtLayoutStoreName, txtLayoutUser,txtLayoutStoreContactName,txtLayoutState,
            txtLayoutUserAddress, txtLayoutStoreAddress;
    private OkHttpClient client;
    Button btn_signup;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No title bar will be shown
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //This will show the screen in full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.activity_registration);
        String usertype = getIntent().getExtras().getString("usertype");
        prgDialog = new ProgressDialog(RegistrationActivity.this);
        prgDialog.setCancelable(false);
        userName = (EditText)findViewById(R.id.userName);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);
        phone = (EditText)findViewById(R.id.phone);
        userAddress = (EditText)findViewById(R.id.userAddress);
        userState = (EditText)findViewById(R.id.userState);
        userCity = (EditText)findViewById(R.id.userCity);
        zipcode = (EditText)findViewById(R.id.zipcode);
        storeContactName = (EditText)findViewById(R.id.storeContactName);
        storeName = (EditText)findViewById(R.id.storeName);
        storeAddress = (EditText)findViewById(R.id.storeAddress);
        signin = (LinearLayout) findViewById(R.id.signin);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        txtLayoutState = (TextInputLayout)findViewById(R.id.txtLayoutState);
        txtLayoutStoreName = (TextInputLayout)findViewById(R.id.txtLayoutStoreName);
        txtLayoutUser = (TextInputLayout)findViewById(R.id.txtLayoutUser);
        txtLayoutStoreContactName = (TextInputLayout)findViewById(R.id.txtLayoutStoreContactName);
        txtLayoutUserAddress = (TextInputLayout)findViewById(R.id.txtLayoutUserAddress);
        txtLayoutStoreAddress = (TextInputLayout)findViewById(R.id.txtLayoutStoreAddress);
        profileImage = (ImageView)findViewById(R.id.profileImage);
        client = new OkHttpClient();

        if(usertype.equals("user")){

        }else if(usertype.equals("store")){
            txtLayoutStoreName.setVisibility(View.VISIBLE);
            txtLayoutStoreContactName.setVisibility(View.VISIBLE);
            txtLayoutStoreAddress.setVisibility(View.VISIBLE);
            txtLayoutUser.setVisibility(View.GONE);
            txtLayoutState.setVisibility(View.GONE);
            txtLayoutUserAddress.setVisibility(View.GONE);
            profileImage.setImageResource(R.drawable.store_owner_profile);
        }


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeHTTPCall();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }


    public void makeHTTPCall() {
        prgDialog.setMessage("Loading");
        prgDialog.show();
        RequestBody requestBody= RequestBuilder.LoginBody(email.getText().toString(), password.getText().toString());
        ApiCall apiCall = new ApiCall();
        apiCall.POST(client, Utils.getUrl(), requestBody, new IAppDataDownloader() {
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
