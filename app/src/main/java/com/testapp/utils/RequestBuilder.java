package com.testapp.utils;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by osman on 22-09-2016.
 */
public class RequestBuilder {

    //Login request body
    public static RequestBody LoginBody(String email, String password) {
        return new FormBody.Builder()
               .add("customer_email", email)
                .add("customer_password", password)
                .build();
    }

}
