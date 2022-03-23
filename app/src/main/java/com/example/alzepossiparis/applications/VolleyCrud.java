package com.example.alzepossiparis.applications;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alzepossiparis.OptionsPos;
import com.example.alzepossiparis.models.ApiResult;
import com.example.alzepossiparis.models.VolleyCallback;
import com.example.alzepossiparis.tools.ToolProgressBar;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyCrud {

    RequestQueue mQueue;
    Context _context;
    ToolProgressBar toolProgressBar;
    ProgressDialog progressDialog;
    ObjectMapper mapper;

    public VolleyCrud(Context context){
        mQueue = Volley.newRequestQueue(context);
        _context=context;
        toolProgressBar = new ToolProgressBar(context);
        mapper = new ObjectMapper();
    }

    public JsonObjectRequest sendRequest(String apiUrl, String jsonParameters, VolleyCallback callback) throws JSONException {

        JSONObject object=new JSONObject();

        try {
              object = new JSONObject(jsonParameters);
        }catch (JSONException e){
            AlertDialog.Builder dialog = toolProgressBar.showAlertDialog("Hata",e.getMessage());
            dialog.show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,apiUrl, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            ApiResult apiResult = mapper.readValue(response.toString(),ApiResult.class);
                            if(apiResult.status)
                            {
                                callback.onSuccess(apiResult.result);
                            }else{
                                AlertDialog.Builder dialog = toolProgressBar.showAlertDialog("Hata",apiResult.message);
                                dialog.show();
                                callback.onError(apiResult.message);
                            }
                        } catch (JsonProcessingException e) {
                            AlertDialog.Builder dialog = toolProgressBar.showAlertDialog("Hata",e.getMessage());
                            dialog.show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder dialog = toolProgressBar.showAlertDialog("Hata",error.getMessage());
                dialog.show();
                callback.onError(error.getMessage());
                // System.out.println(error.getMessage());
                // resultTextView.setText("Error getting response");
            }
        });
        return  jsonObjectRequest;
    }
}
