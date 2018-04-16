package com.javacodegeeks.androidhttppostgetexample;

import java.io.IOException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AndroidHttpPostGetActivity extends Activity {
    OkHttpClient client;
    MediaType JSON;
    public static final MediaType MEDIA_TYPE =
            MediaType.parse("application/json");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        client = new OkHttpClient();
        JSON = MediaType.parse("application/json; charset=utf-8");
    }

    public void makeGetRequest(View v) throws IOException {
        GetTask task = new GetTask();
        task.execute();
    }

    public class GetTask extends AsyncTask<String, Void, String> {
        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
               // String getResponse = get("https://publicobject.com/helloworld.txt");
                String getResponse = get("http://studycollab.com/mobile/api/index.php/Dashboard/searchQuestion");
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(String getResponse) {
            System.out.println(getResponse);
        }

        public String get(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJVME0wTURReE5qSXdNVGhmTVRBd05USTMiLCJpYXQiOjE1MjM4Njk1MjcsIm5iZiI6MTUyMzg2OTUyNywiaXNzIjoic2MubG9jYWxob3N0IiwidXNlciI6eyJpZCI6IjQiLCJ1c2VyX2lkIjoiYmFsYSIsImZpcnN0TmFtZSI6ImJhbGEiLCJsYXN0TmFtZSI6InNlbHZhcmFzdSIsInJldHVybmluZ1VzZXIiOiIwIiwiaW1hZ2VwYXRoIjoidXBsb2FkSW1hZ2VcL2JhbGEyMDE4XzAyXzA2XzA5XzIzXzE3LmpwZyIsInN0YXR1cyI6IkFjdGl2ZSJ9fQ.VuE_srdzeOCynABqVluxb38-Q5BG6fUMN_MQZ8RTQqUwU_AEyzHtOF4LKk1hUG4GmOvB0m25nkVXLJRLUMgzyg")
                    .build();

            Response response = client.newCall(request).execute();
            Log.d("REsss",response.toString());
            return response.body().string();
        }
    }

    public void makePostRequest(View v) throws IOException {
      //  PostTask task = new PostTask();
     //   task.execute();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("userName", "bala");
            postdata.put("password", "123");
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());

        final Request request = new Request.Builder()
                .url("http://studycollab.com/mobile/api/index.php/Account/login")
                .post(body)
                //.addHeader("Content-Type", "application/json")//add header suppose if you want header here apart from login
                //.addHeader("Authorization", "Your Token")//add header suppose if you want header here apart from login
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {

                String mMessage = response.body().string();
                if (response.isSuccessful()){

                    Log.d("RESPOST",mMessage);


                }
            }
        });

    }

}