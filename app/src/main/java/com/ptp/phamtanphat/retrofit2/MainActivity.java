package com.ptp.phamtanphat.retrofit2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edtuser,edtpassword;
    Button btndangnhap,btndangky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DangkyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        edtuser = findViewById(R.id.edittextuser);
        edtpassword = findViewById(R.id.edittextpassword);
        btndangky = findViewById(R.id.buttondangky);
        btndangnhap = findViewById(R.id.buttondangnhap);
    }
}
