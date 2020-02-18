package com.example.lunares;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Communicator {
    private String server;
    private Context context;
    private Converter converter;

    public Communicator(Context context){
        this.server = MainActivity.server;
        this.context = context;
        converter = new Converter();
    }

    public void doPost(Bitmap image, final TextView textView) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);

        final JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, server+"subirFoto", new JSONObject("{\'image\' : \'" + converter.encode(image) + "\'}"),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText(R.string.received);
                        try {
                            JSONObject obj = response.getJSONObject("metadata");
                            MainActivity.database.insertKey(obj.getString("id_foto"),obj.getString("foto_password"));
                            MainActivity.list.clear();
                            MainActivity.list = MainActivity.database.selectAll();
                            MainActivity.adapter.clear();
                            MainActivity.adapter.addAll(MainActivity.list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "POST METHOD ERROR!", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            //here I want to post data to sever
        };
        queue.add(jsonobj);
    }

    public void getImage(Img img, final ImageView imageView){
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, server+"obtenerFoto?foto_password="+img.getPassword()+"&foto_id="+img.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Bitmap bm = converter.decode(response);
                            imageView.setImageBitmap(bm);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.e("asd","TIME OUT OR NOT CONNECTION");
                        } else if (error instanceof AuthFailureError) {
                            Log.e("asd","AUTH FAILURE");
                        } else if (error instanceof ServerError) {
                            Log.e("asd","SERVER ERROR");
                        } else if (error instanceof NetworkError) {
                            Log.e("asd","NETWORK ERROR");
                        } else if (error instanceof ParseError) {
                            Log.e("asd","PARSE ERROR");
                        }
                        Log.e("asd","ERROR");
                        Toast.makeText(context, "SERVER NO RESPONSE", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            //here I want to post data to sever
        };
        queue.add(stringRequest);
    }
}
