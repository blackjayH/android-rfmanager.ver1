package com.example.hong.listcomplete;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {
    public static ArrayList<Integer> check = new ArrayList<>(); // 체크박스 상태로 1,0 만 삽입 1 : 체크X / 0 체크O
    public static ArrayList<BuyProduct> buyproductList = new ArrayList<>(); // 장바구니 리스트
    public static ArrayList<BuyProduct> backupList = new ArrayList<>(); // 백업리스트 : 장바구니에서 지웠던 품목들로 모아 놓은것
    public static ListViewAdapter adapter;
    public static TextView all; // ALL ( / ) 텍스트뷰

    CheckBox allcheckBox;
    EditText inputproduct, searchinput;
    final Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tab1).setVisibility(View.GONE);
        findViewById(R.id.tab2).setVisibility(View.VISIBLE);
        findViewById(R.id.tab3).setVisibility(View.GONE);
        adapter = new ListViewAdapter();
        allcheckBox = (CheckBox) findViewById(R.id.allcheckBox);
        inputproduct = (EditText) findViewById(R.id.inputproduct);
        searchinput = (EditText) findViewById(R.id.searchinput);
        all = (TextView) findViewById(R.id.all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        final ListView listView = (ListView) findViewById(R.id.listView);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "11.db", null, 1);
        buyproductList = dbHelper.getBuyProduct();
        backupList = dbHelper.getBackUpBuyProduct();
        int tmp = 0;
        for (BuyProduct bp : buyproductList) {
            adapter.add(bp);
            if (bp.getLive() == 1)
                tmp++;
        }
        String str = buyproductList.size() + "";
        String temp = "ALL ( " + tmp + " / " + str + " )";
        all.setText(temp);
        for (int i = 0; i < buyproductList.size(); i++)
            check.add(1);

        listView.setAdapter(adapter);


        // ALL( / )
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setEnabled(true);
                inputproduct.setText("");
                listView.setAdapter(adapter);
            }
        });
        // 체크박스 전체선택
        allcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = buyproductList.size() - 1; i >= 0; i--) {
                    if (check.get(i) == 1)
                        check.set(i, 0);
                    else
                        check.set(i, 1);
                }
            }
        });
        // 장바구니리스트에 등록
        Button enrollproduct = (Button) findViewById(R.id.enrollproduct);
        enrollproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = "me";
                String item = inputproduct.getText().toString();
                int amount = 0;//"메모 입력하려면 클릭하시오.";
                int live = 1;
                int withdraw = 1;

                BuyProduct tmp = null;
                dbHelper.insert(user_id, item, amount, live);
                tmp = new BuyProduct(user_id, item, amount, live, withdraw);
                Toast.makeText(getApplicationContext(), tmp.getWithdraw() + "", Toast.LENGTH_SHORT).show();

                buyproductList.add(tmp);
                check.add(1);
                adapter.add(tmp);
                // adapter.notifyDataSetChanged();
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
                inputproduct.setText("");
            }
        });
        // 장바구니리스트에서 삭제
        Button deleteproduct = (Button) findViewById(R.id.deleteproduct);
        deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("해당항목 삭제");
                alertDialogBuilder
                        .setMessage("삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("삭제",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        for (int i = buyproductList.size() - 1; i >= 0; i--) {
                                            if (check.get(i) == 0) {
                                                dbHelper.backUp("me", buyproductList.get(buyproductList.size() - 1).getItem(), buyproductList.get(buyproductList.size() - 1).getAmount(), buyproductList.get(buyproductList.size() - 1).getLive());
                                                buyproductList.get(i).setTf(false);
                                                backupList.add(buyproductList.get(i));
                                                dbHelper.delete(buyproductList.get(i).getItem());
                                                buyproductList.remove(buyproductList.get(i));
                                                check.remove(i);
                                            }
                                        }
                                        int tmp2 = 0;
                                        for (BuyProduct bp : buyproductList) {
                                            if (bp.getLive() == 1)
                                                tmp2++;
                                        }
                                        String str = buyproductList.size() + "";
                                        String temp = "ALL ( " + tmp2 + " / " + str + " )";
                                        all.setText(temp);

                                        //adapter = new ListViewAdapter();
                                        allcheckBox.setChecked(false);
                                        //listView.clearChoices();
                                        adapter = new ListViewAdapter();
                                        for (BuyProduct bp : buyproductList)
                                            adapter.add(bp);
                                        //  adapter.notifyDataSetChanged();
                                        listView.setAdapter(adapter);
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        // 장바구니에서 삭제한 품목 복원
        Button backup = (Button) findViewById(R.id.backup);
        backup.setOnClickListener(new View.OnClickListener() {
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

                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        });
        // 툴바 장바구니 리스트에서 품목 검색창
        Button searchbutton = (Button) findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setEnabled(false);
                String str = searchinput.getText().toString();
                ListViewAdapter tempadapter = new ListViewAdapter();
                ArrayList<BuyProduct> templist = new ArrayList<>();
                for (BuyProduct bp : buyproductList)
                    if (bp.getItem().contains(str)) {
                        templist.add(bp);
                    }
                for (BuyProduct bp : templist)
                    tempadapter.add(bp);
                listView.setAdapter(tempadapter);
                InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(searchinput.getWindowToken(), 0);

            }
        });
        // 복원 리스트로 이동 보류
        Button findproduct = (Button) findViewById(R.id.findproduct);
        findproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RestoreActivity.class);
                startActivity(intent);

                //   listView.setEnabled(false);
                String str = inputproduct.getText().toString();
                ListViewAdapter tempadapter = new ListViewAdapter();
                ArrayList<BuyProduct> templist = new ArrayList<>();
                for (BuyProduct bp : buyproductList)
                    if (bp.getItem().contains(str)) {
                        templist.add(bp);
                    }
                for (BuyProduct bp : templist)
                    tempadapter.add(bp);
                listView.setAdapter(tempadapter);

            }
        });
        // 리스트뷰 클릭 빨간색 긋기 (장보고 확인하는 절차)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                BuyProduct tp = buyproductList.get(position);
                if (tp.getLive() == 1)
                    tp.setLive(0);
                else if (tp.getLive() == 0)
                    tp.setLive(1);
                dbHelper.update(tp.getUser_id(), tp.getItem(), tp.getAmount(), tp.getLive(), tp.getWithdraw());
                int tmp2 = 0;
                for (BuyProduct bp : buyproductList) {
                    if (bp.getLive() == 1)
                        tmp2++;
                }
                String str = buyproductList.size() + "";
                String temp = "ALL ( " + tmp2 + " / " + str + " )";
                all.setText(temp);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        });
        // 리스트뷰 롱클릭 수량 입력 변경 가능
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("item", buyproductList.get(position).getItem());
                intent.putExtra("memodetail", buyproductList.get(position).getAmount());

                startActivity(intent);
                return true;
            }

        });

    }

}
