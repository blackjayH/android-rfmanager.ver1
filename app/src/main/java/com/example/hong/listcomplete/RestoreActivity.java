package com.example.hong.listcomplete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.hong.listcomplete.ShoppingListActivity.adapter;
import static com.example.hong.listcomplete.ShoppingListActivity.all;
import static com.example.hong.listcomplete.ShoppingListActivity.backupList;
import static com.example.hong.listcomplete.ShoppingListActivity.buyproductList;
import static com.example.hong.listcomplete.ShoppingListActivity.check;
// 백업 데이터 보여주기 위해 만든 임시클래스 >> Shopping 리스트 처럼 그대로 리스트뷰로 보여줌
public class RestoreActivity extends AppCompatActivity {
    private ListViewAdapter badapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoreview);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "11.db", null, 1);
        final ListView listView3 = (ListView) findViewById(R.id.listView3);

        badapter = new ListViewAdapter();
        for (BuyProduct bp : backupList)
            badapter.add(bp);
        listView3.setAdapter(badapter);
        // 되돌아가기
        Button btn_back = (Button) findViewById(R.id.back);
        btn_back.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        // 가장 최근 항목 하나 복원
        Button golist = (Button) findViewById(R.id.golist);
        golist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.insert("me", backupList.get(backupList.size() - 1).getItem(), backupList.get(backupList.size() - 1).getAmount(), backupList.get(backupList.size() - 1).getLive());
                buyproductList.add(backupList.get(backupList.size() - 1));
                adapter.add(backupList.get(backupList.size() - 1));
                Toast.makeText(getApplicationContext(), backupList.get(backupList.size() - 1).getItem() + "이 복원되었습니다", Toast.LENGTH_SHORT).show();
                dbHelper.returnbackup(backupList.get(backupList.size() - 1).getItem());
                backupList.remove(backupList.get(backupList.size() - 1));
                check.add(1);

                int tmp2 = 0;
                for (BuyProduct bp : buyproductList) {
                    if (bp.getLive() == 1)
                        tmp2++;
                }
                String str = buyproductList.size() + "";
                String temp = "ALL ( " + tmp2 + " / " + str + " )";
                all.setText(temp);
                badapter = new ListViewAdapter();
                for (BuyProduct bp : backupList)
                    badapter.add(bp);
                adapter.notifyDataSetChanged();
                badapter.notifyDataSetChanged();
                listView3.setAdapter(badapter);
            }
        });
    }
}
