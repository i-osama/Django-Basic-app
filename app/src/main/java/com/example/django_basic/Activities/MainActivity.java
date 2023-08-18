package com.example.django_basic.Activities;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.django_basic.databinding.ActivityMainBinding;
//import com.example.phpserverapplication.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    RequestQueue requestQueue;
    String title, district, full_location, description, category;
//    String url = "https://zeeshan-has-this.000webhostapp.com/PHP/assistant.php";
//    String url = "http://127.0.0.1:8000/place_list/";
    String url = "http://192.168.1.1:8000/place_list/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestQueue = Volley.newRequestQueue(this);

//        handling insert btn
        binding.insertBtn.setOnClickListener(view -> {

            binding.insertBtn.setEnabled(false);
            binding.progressbar.setVisibility(View.VISIBLE);

            title = binding.title.getText().toString().trim();
            district = binding.district.getText().toString().trim();
            full_location = binding.fullLocation.getText().toString().trim();
            category = binding.category.getText().toString().trim();
            description = binding.description.getText().toString().trim();

            if(title.isEmpty()){
                binding.title.setError("Empty!!");
            } else if (district.isEmpty() || full_location.isEmpty() || description.isEmpty()) {
                binding.district.setError("Field empty!");
            }
            else {
                saveDataToDataBase(title, district, full_location, description, category);
            }
        });

//        TODO:
//        binding.getBtn.setOnClickListener(view -> {
//            startActivity(new Intent(MainActivity.this, GettingDataActivity.class));
//        });


//        INSERT INTO `user` (`SNo`, `Name`, `Age`, `Gender`, `Details`, `Date`)
//        VALUES ('1', 'Osama Bin Hashim', '21', 'Male', 'Hello, this is Osama Bin Hashim. This is the test database of mine. ', current_timestamp());

    }

    private void saveDataToDataBase(String title, String district, String full_location, String description, String category) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", "onResponse: " + response);
//                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        binding.insertBtn.setEnabled(true);
                        binding.progressbar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG", "onErrorResponse: "+error.getLocalizedMessage());
//                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                binding.insertBtn.setEnabled(true);
                binding.progressbar.setVisibility(View.GONE);
            }
        })

//            Sending JSON
//
//        {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("title", title);
//                    jsonObject.put("district", district);
//                    jsonObject.put("full_location", full_location);
//                    jsonObject.put("description", description);
//                    jsonObject.put("category", category);
//                    return jsonObject.toString().getBytes("utf-8");
//                } catch (JSONException e) {
//                    return null;
//                } catch (UnsupportedEncodingException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//


//            Post Main Part
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

//                {
//                    "id": 1,
//                        "title": "Dhaka",
//                        "description": "Dhaka is the capital of Bangladesh.",
//                        "district": "Dhaka",
//                        "full_location": "Bangladesh, Dhaka",
//                        "category": "City, Capital"
//                }

                Map<String, String> params = new HashMap<>();
                params.put("title",title);
                params.put("district", district);
                params.put("full_location", full_location);
                params.put("description", description);
                params.put("category", category);
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }
}