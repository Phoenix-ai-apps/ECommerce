package com.demo.ecommerce.managers;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.helper.HelperInterface;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class CustomVolleyPostRequestWithTextPlain extends StringRequest implements HelperInterface {

    private static final String TAG = CustomVolleyPostRequestWithTextPlain.class.getSimpleName();
    private String jsonBody;

    public CustomVolleyPostRequestWithTextPlain(int method,
                                                String url,
                                                String jsonBody,
                                                Response.Listener<String> listener,
                                                Response.ErrorListener errorListener) {

        super(method, url, listener, errorListener);
        this.jsonBody = jsonBody;
        Log.i(TAG, "CustomVolleyPostRequestWithTextPlain: calling : " + url + " " + jsonBody.toString());
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        return headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        byte[] body = new byte[0];
        try {
            body = jsonBody.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Unable to gets bytes from JSON", e.fillInStackTrace());
        }
        return body;
    }

    @Override
    public String getBodyContentType() {
        return "text/plain";
    }

    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }
}
