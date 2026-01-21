package com.example.buttonapp;

import android.os.Bundle; 
import android.view.ViewGroup; 
import android.widget.Button; 
import android.widget.LinearLayout; 
import androidx.appcompat.app.AlertDialog; 
import androidx.appcompat.app.AppCompatActivity; 
import com.google.gson.Gson; 
import com.google.gson.annotations.SerializedName; 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.nio.charset.StandardCharsets; 
import java.util.HashMap; 
import java.util.Map; 
import okhttp3.Call; 
import okhttp3.Callback; 
import okhttp3.Headers; 
import okhttp3.MediaType; 
import okhttp3.OkHttpClient; 
import okhttp3.Request; 
import okhttp3.RequestBody; 
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    private LinearLayout buttonContainer;
    private final Gson gson = new Gson();

    private static class ButtonSpec {
        @SerializedName("title")
        String title;
        @SerializedName("request")
        String request;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonContainer = findViewById(R.id.buttonContainer);

        String buttonsJson = readAssetToString("buttons.json");
        ButtonSpec[] specs = gson.fromJson(buttonsJson, ButtonSpec[].class);
        if (specs != null) {
            for (ButtonSpec s : specs) {
                Button b = new Button(this);
                b.setText(s.title);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 16, 0, 0);
                b.setLayoutParams(lp);
                b.setOnClickListener(v -> onButtonClick(s.request));
                buttonContainer.addView(b);
            }
        }
    }

    private String readAssetToString(String name) {
        try (InputStream is = getAssets().open(name);
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void onButtonClick(String requestHeading) {
        new Thread(() -> {
            String httpText = readAssetToString("requests.http");
            Map<String, String> vars = parseHttpFileVariables(httpText);
            String block = extractRequestBlock(httpText, requestHeading);
            if (block == null) {
                runOnUiThread(() -> showAlert("Error", "Request not found: " + requestHeading));
                return;
            }
            Request okReq = buildOkHttpRequestFromBlock(block, vars);
            if (okReq == null) {
                runOnUiThread(() -> showAlert("Error", "Failed to build request"));
                return;
            }
            client.newCall(okReq).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> showAlert("Network Error", e.getMessage()));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String body = response.body() != null ? response.body().string() : "";
                    runOnUiThread(() -> showAlert("Response: " + response.code(), body));
                }
            });
        }).start();
    }

    private Map<String, String> parseHttpFileVariables(String text) {
        Map<String, String> vars = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new java.io.ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("@")) {
                    int eq = line.indexOf('=');
                    if (eq > 1) {
                        String name = line.substring(1, eq).trim();
                        String val = line.substring(eq + 1).trim();
                        vars.put(name, val);
                    }
                }
            }
        } catch (IOException ignored) { }
        return vars;
    }

    private String extractRequestBlock(String text, String heading) {
        String[] lines = text.split("\\r?\\n");
        StringBuilder sb = null;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().equals(heading)) {
                sb = new StringBuilder();
                for (int j = i + 1; j < lines.length; j++) {
                    if (lines[j].trim().startsWith("### ")) break;
                    sb.append(lines[j]).append('\n');
                }
                break;
            }
        }
        return sb == null ? null : sb.toString().trim();
    }

    private Request buildOkHttpRequestFromBlock(String block, Map<String, String> vars) {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new java.io.ByteArrayInputStream(block.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8));
        try {
            String methodAndUrl;
            do {
                methodAndUrl = br.readLine();
                if (methodAndUrl == null) return null;
            } while (methodAndUrl.trim().isEmpty());

            String[] parts = methodAndUrl.trim().split("\\s+", 2);
            String method = parts[0].toUpperCase();
            String url = parts.length > 1 ? substituteVariables(parts[1].trim(), vars) : "";

            Headers.Builder headersBuilder = new Headers.Builder();
            String line;
            StringBuilder bodySb = new StringBuilder();
            boolean inBody = false;
            while ((line = br.readLine()) != null) {
                if (!inBody) {
                    if (line.trim().isEmpty()) {
                        inBody = true;
                        continue;
                    }
                    int colon = line.indexOf(':');
                    if (colon > 0) {
                        String name = line.substring(0, colon).trim();
                        String val = line.substring(colon + 1).trim();
                        val = substituteVariables(val, vars);
                        headersBuilder.add(name, val);
                    }
                } else {
                    bodySb.append(line).append('\n');
                }
            }

            RequestBody requestBody = null;
            if (!method.equals("GET") && !method.equals("HEAD")) {
                String bodyStr = bodySb.toString().trim();
                if (!bodyStr.isEmpty()) {
                    String contentType = headersBuilder.build().get("Content-Type");
                    MediaType mt = contentType != null ? MediaType.parse(contentType) : MediaType.parse("application/json; charset=utf-8");
                    requestBody = RequestBody.create(bodyStr, mt);
                } else {
                    requestBody = RequestBody.create(new byte[0], null);
                }
            }

            Request.Builder reqB = new Request.Builder().url(url).headers(headersBuilder.build());
            switch (method) {
                case "GET": reqB.get(); break;
                case "POST": reqB.post(requestBody); break;
                case "PUT": reqB.put(requestBody); break;
                case "DELETE":
                    if (requestBody != null) reqB.delete(requestBody);
                    else reqB.delete();
                    break;
                case "PATCH": reqB.patch(requestBody); break;
                default: return null;
            }
            return reqB.build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String substituteVariables(String s, Map<String, String> vars) {
        if (s == null) return "";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\{\\{([^}]+)\\}\\}");
        java.util.regex.Matcher m = p.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String name = m.group(1).trim();
            String val = vars.getOrDefault(name, "");
            m.appendReplacement(sb, java.util.regex.Matcher.quoteReplacement(val));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

}