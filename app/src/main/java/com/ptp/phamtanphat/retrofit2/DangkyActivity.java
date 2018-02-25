package com.ptp.phamtanphat.retrofit2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangkyActivity extends AppCompatActivity {

    Button btncamera,btngallery,btnhuy,btnxacnhan;
    EditText edtuser,edtpassword;
    ImageView imgdangky;
    int Request_Code_Camera = 123;
    int Request_Code_Gallery = 234;
    String path = "";
    Uri uri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        anhxa();
        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,Request_Code_Camera);
            }
        });
        btngallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Request_Code_Gallery);
            }
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
                Gson gson = new GsonBuilder().setLenient().create();
                Retrofit.Builder builder = new Retrofit.Builder().
                        baseUrl("http://192.168.1.120/Quanlyhocsinh/")
                        .addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit = builder.client(okhttp.build()).build();
                DataClient dataClient = retrofit.create(DataClient.class);

                File file = new File(path);
                String file_path = file.getAbsolutePath();
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);


                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);
                Call<String> callback = dataClient.uploadphoto(body);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                       Log.d("EEE",response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("BBB",t.getMessage() + "");
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Request_Code_Camera == requestCode && resultCode == RESULT_OK && data!= null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgdangky.setImageBitmap(bitmap);
        }
        if (Request_Code_Gallery == requestCode && resultCode == RESULT_OK && data!= null){

            Uri uri = data.getData();
            path = getRealPathFromURI(DangkyActivity.this,uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgdangky.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhxa() {
        btncamera = findViewById(R.id.buttoncamera);
        btngallery = findViewById(R.id.buttongallery);
        btnhuy = findViewById(R.id.buttonhuy);
        btnxacnhan = findViewById(R.id.buttonxacnhan);
        edtpassword = findViewById(R.id.edittextdangkypassword);
        edtuser  = findViewById(R.id.edittextdangkyuser);
        imgdangky = findViewById(R.id.imageviewthongtin);
    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
