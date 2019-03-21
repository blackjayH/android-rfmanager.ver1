package com.example.hong.listcomplete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hong.listcomplete.ShoppingListActivity.buyproductList;
// ShoppingList 디테일 롱클릭시 실행
public class DetailActivity extends AppCompatActivity {
    TextView item; // 품목명
    EditText memodetail; // 수량

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buylistdetail);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "11.db", null, 1);
        // BACK 버튼 >> 되돌아가기 / 장바구니 리스트로 이동
        Button btn_back = (Button) findViewById(R.id.back);
        btn_back.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        // CLEAN 버튼 >> 수량 삭제
        Button memodelete = (Button) findViewById(R.id.memodelete);
        memodelete.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHelper.update(item.getText().toString(), 0);
                        for (BuyProduct bp : buyproductList) {
                            if (bp.getItem().equals(item.getText().toString()))
                                bp.setAmount(0);
                        }
                        Toast.makeText(getApplicationContext(), item.getText().toString() + "상품의 수량이 0으로 초기화되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
        // SAVE 버튼 >> 수량 저장
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int inttemp = Integer.parseInt(memodetail.getText().toString());
                        dbHelper.update(item.getText().toString(), inttemp);
                        for (BuyProduct bp : buyproductList) {
                            if (bp.getItem().equals(item.getText().toString()))
                                bp.setAmount(inttemp);
                        }
                        Toast.makeText(getApplicationContext(), item.getText().toString() + "상품에 대한 : "+inttemp + "수량이 추가되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );

        Intent intent = getIntent(); /** * 객체를 받아옵니다. */
        item = (TextView) findViewById(R.id.item);
        memodetail = (EditText) findViewById(R.id.memodetail);


        item.setText(intent.getStringExtra("item"));
        memodetail.setText(intent.getStringExtra("memodetail"));
    }
}
