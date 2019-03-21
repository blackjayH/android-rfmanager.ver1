package com.example.hong.listcomplete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.hong.listcomplete.FridgeActivity.check2;

public class UserProductInput extends AppCompatActivity {
    EditText title, amount, category, position, detail;
    TextView enrollDate, date, position2;
    CalendarView calendarView;
    CheckBox checkBox, checkBox2, checkBox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userproductinput);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        title = (EditText) findViewById(R.id.title);
        amount = (EditText) findViewById(R.id.amount);
        category = (EditText) findViewById(R.id.category);

        position2 = (TextView) findViewById(R.id.position2);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);

        //position = (EditText) findViewById(R.id.position);
        detail = (EditText) findViewById(R.id.detail);
        date = (TextView) findViewById(R.id.date);
        enrollDate = (TextView) findViewById(R.id.enrollDate);
        checkBox.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "11.db", null, 1);
        final GridView listView2 = (GridView) findViewById(R.id.gridView);
        /*
        private int icon; // 이미지
    private int type;
    private String title; // 품목명
    private String enrollDate; // 등록일
    private String date; // 유통기한
    private int amount; // 수량
    private String position; // 저장공간
    private String category;
    private String detail;
         */
        long now = System.currentTimeMillis();
        Date inputdate = new Date(now);
        // 출력 포맷 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/dd");
        enrollDate.setText(simpleDateFormat.format(inputdate));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(UserProductInput.this, year + "/" + (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                date.setText(year + "/" + (month + 1) + "/" + dayOfMonth);

            }

        });

        Button addproduct = (Button) findViewById(R.id.addproduct);
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id = "me";
                int stype = 1;
                String stitle = title.getText().toString();
                int samount = Integer.parseInt(amount.getText().toString());
                String scategory = category.getText().toString();
                String sposition = position2.getText().toString();
                String sdetail = detail.getText().toString();
                String sdate = date.getText().toString();
                String senrollDate = enrollDate.getText().toString();
                if (checkBox.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked()){
                    position2.setText("냉동실");
                    dbHelper.insertuser(user_id, stitle, senrollDate, sdate, samount, sposition, scategory, sdetail, stype);
                   // gadapter.notifyDataSetChanged();
                    //listView2.setAdapter(gadapter);
                    check2.add(1);
                    finish();

                }

                else if (!checkBox.isChecked() && checkBox2.isChecked() && !checkBox3.isChecked()){
                    position2.setText("냉장실");
                    dbHelper.insertuser(user_id, stitle, senrollDate, sdate, samount, sposition, scategory, sdetail, stype);
                   // gadapter.notifyDataSetChanged();
                    check2.add(1);
                    finish();
                }

                else if (!checkBox.isChecked() && !checkBox2.isChecked() && checkBox3.isChecked()) {
                    position2.setText("상온 보관");
                    dbHelper.insertuser(user_id, stitle, senrollDate, sdate, samount, sposition, scategory, sdetail, stype);
                  //  gadapter.notifyDataSetChanged();
                    check2.add(1);
                    finish();
                }

                else
                    Toast.makeText(UserProductInput.this,"다시입력해주세요", Toast.LENGTH_SHORT).show();



            }
        });

    }





























                /*tmp = new UserProduct(user_id, stitle, senrollDate, sdate, samount, sposition, scategory, sdetail, stype);
db.execSQL("CREATE TABLE USERPRODUCTLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, title TEXT, enrollDate TEXT, " +
     "date String, amount INTEGER, position TEXT, category TEXT, detail TEXT, type INTEGER );");


                BuyProduct tmp = null;
                dbHelper.insert(user_id, item, memo, live);
                tmp = new BuyProduct(user_id, item, memo, live, withdraw);
                Toast.makeText(getApplicationContext(), tmp.getWithdraw() + "", Toast.LENGTH_SHORT).show();

                buyproductList.add(tmp);
                check.add(1);
                adapter.add(tmp);
                int tmp2 = 0;
                for (BuyProduct bp : buyproductList) {
                    if (bp.getLive() == 1)
                        tmp2++;
                }
                String str = buyproductList.size() + "";
                String temp = "ALL ( " + tmp2 + " / " + str + " )";
                all.setText(temp);
                //Toast.makeText(getApplicationContext(), item + "이 추가되었습니다", Toast.LENGTH_SHORT).show();
                listView.setAdapter(adapter);
                inputproduct.setText("");*/


}
