package com.example.hong.listcomplete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
// 메인으로 가기전에 이미지 출력 화면
public class LoadingActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(3000); // 3초간 이미지 출력
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
