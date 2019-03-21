package com.example.hong.listcomplete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
// 유저냉장고 품목 클릭시 세부 내용
public class DetailProductActivity extends AppCompatActivity {
    TextView title, amount, category, position, detail;
    TextView enrollDate, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userproductdetail);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "11.db", null, 1);

        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );


        Intent intent = getIntent(); /** * 객체를 받아옵니다. */
        title = (TextView) findViewById(R.id.title);
        amount = (TextView) findViewById(R.id.amount);
        category = (TextView) findViewById(R.id.category);
        position = (TextView) findViewById(R.id.position);
        detail = (TextView) findViewById(R.id.detail);
        date = (TextView) findViewById(R.id.date);
        enrollDate = (TextView) findViewById(R.id.enrollDate);

        title.setText(intent.getStringExtra("title"));
        amount.setText(intent.getStringExtra("amount"));
        category.setText(intent.getStringExtra("category"));
        position.setText(intent.getStringExtra("position"));
        detail.setText(intent.getStringExtra("detail"));
        date.setText(intent.getStringExtra("date"));
        enrollDate.setText(intent.getStringExtra("enrollDate"));
    }
}
