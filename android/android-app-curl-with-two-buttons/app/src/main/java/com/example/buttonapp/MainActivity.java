package com.example.buttonapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    // 10.0.2.2 is how the Android Emulator sees your computer's localhost
    private final String SERVER_URL = "http://10.0.2.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOne = findViewById(R.id.btn_one);
        Button btnTwo = findViewById(R.id.btn_two);

        btnOne.setOnClickListener(v -> makeRequest("button1"));
        btnTwo.setOnClickListener(v -> makeRequest("button2"));
    }

    private void makeRequest(String path) {
        Request request = new Request.Builder()
                .url(SERVER_URL + path)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> 
                    Toast.makeText(MainActivity.this, "Network Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.isSuccessful() ? "Success!" : "Server Error: " + response.code();
                runOnUiThread(() -> 
                    Toast.makeText(MainActivity.this, responseData, Toast.LENGTH_SHORT).show());
            }
        });
    }
}