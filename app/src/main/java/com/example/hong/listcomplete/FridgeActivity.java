package com.example.hong.listcomplete;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.hong.listcomplete.ShoppingListActivity.adapter;
import static com.example.hong.listcomplete.ShoppingListActivity.all;
import static com.example.hong.listcomplete.ShoppingListActivity.buyproductList;
import static com.example.hong.listcomplete.ShoppingListActivity.check;

public class FridgeActivity extends AppCompatActivity {
    public static ArrayList<Integer> check2 = new ArrayList<>();
    public static ArrayList<String> gridList = new ArrayList<>();
    public static ArrayList<UserProduct> tempList = new ArrayList<>();
    public static ArrayList<UserProduct> userproductList = new ArrayList<>();
    public static GridViewAdapter gadapter;
    public static boolean mClick = false;

    EditText inputproduct, searchinput, editrecipe;
    TextView result;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tab1).setVisibility(View.VISIBLE);
        findViewById(R.id.tab2).setVisibility(View.GONE);
        findViewById(R.id.tab3).setVisibility(View.GONE);

        gadapter = new GridViewAdapter();
        editrecipe = (EditText) findViewById(R.id.editrecipe);
        result = (TextView) findViewById(R.id.result);
        final GridView gridView = (GridView) findViewById(R.id.gridView);
        //final ListView listView = (ListView) findViewById(R.id.listView);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "11.db", null, 1);

        for (UserProduct ap : tempList) {
            if (!gridList.contains(ap.getDate()))
                gridList.add(ap.getDate());
        }
        int x = 0;
        for (String st : gridList) {
            UserProduct uptemp1 = new UserProduct(st);
            UserProduct uptemp2 = new UserProduct("");
            UserProduct uptemp3 = new UserProduct(R.drawable.trash, "trash");
            userproductList.add(uptemp1);
            userproductList.add(uptemp2);
            //gadapter.add(uptemp1);
            // gadapter.add(uptemp2);
            for (UserProduct up : tempList) {
                if (up.getDate().equals(st)) {
                    userproductList.add(up);
                    x++;
                }
            }
            if (x % 2 == 1) {
                //tempList.add(userproductList.get(0));
                userproductList.add(uptemp3);
            }
            x = 0;
        }        tempList = dbHelper.getUserProduct();
        //userproductList = dbHelper.getUserProduct();

        for (UserProduct up : userproductList) {
            gadapter.add(up);
        }
        for (int i = 0; i < userproductList.size(); i++) {
            check2.add(1);

            //      else
            //        listView2.setEnabledset
            // listView2.Clickable(false);

        }
        gridView.setAdapter(gadapter);
        // 품목 장바구니에 넣기
        Button enrolllist = (Button) findViewById(R.id.enrolllist);
        enrolllist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("해당항목 장바구니 넣기");
                alertDialogBuilder.setMessage("장바구니에 담으시겠습니까?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("담기",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                for (int i = userproductList.size() - 1; i >= 0; i--) {
                                    if (check2.get(i) == 0) {
                                        dbHelper.insert("me", userproductList.get(i).getTitle(), 0, 1);
                                        BuyProduct tm;
                                        tm = new BuyProduct("me", userproductList.get(i).getTitle(), 0, 1, 1);
                                        buyproductList.add(tm);
                                        check.add(1);
                                        userproductList.get(i).setOx(false);
                                        check2.set(i, 1);
                                    }
                                }
                                result.setText(dbHelper.getResult());
                                gadapter.notifyDataSetChanged();
                                gridView.setAdapter(gadapter);

                            }

                        });
                alertDialogBuilder.setNegativeButton("취소",
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
        // 품목 선택 삭제
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
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
                                        for (int i = userproductList.size() - 1; i >= 0; i--) {
                                            if (check2.get(i) == 0) {
                                                dbHelper.delete2(userproductList.get(i).getTitle());
                                                userproductList.get(i).setOx(false);
                                                check2.remove(i);
                                            }
                                        }
                                        result.setText(dbHelper.getResult());
                                        userproductList.clear();
                                        tempList.clear();
                                        gridList.clear();
                                        tempList = dbHelper.getUserProduct();
                                        for (UserProduct ap : tempList) {
                                            if (!gridList.contains(ap.getDate()))
                                                gridList.add(ap.getDate());
                                        }
                                        gadapter = new GridViewAdapter();
                                        int x = 0;
                                        for (String str : gridList) {
                                            UserProduct uptemp1 = new UserProduct(str);
                                            UserProduct uptemp2 = new UserProduct("");
                                            UserProduct uptemp3 = new UserProduct(R.drawable.trash, "trash");
                                            userproductList.add(uptemp1);
                                            userproductList.add(uptemp2);
                                            for (UserProduct up : tempList) {
                                                if (up.getDate().equals(str)) {
                                                    userproductList.add(up);
                                                    x++;
                                                }
                                            }
                                            if (x % 2 == 1) {
                                                //tempList.add(userproductList.get(0));
                                                userproductList.add(uptemp3);
                                            }
                                            x = 0;
                                        }
                                        for (UserProduct up : userproductList) {
                                            gadapter.add(up);
                                        }
                                        gridView.setAdapter(gadapter);

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
        // DB 확인용 텍스트 제거 예정
       // result.setText(dbHelper.getResult());
        // 레시피 찾아볼 품목 선택
        Button recipescan = (Button) findViewById(R.id.recipescan);
        recipescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";
                String t = "";
                for (int i = userproductList.size() - 1; i >= 0; i--) {
                    if (check2.get(i) == 0) {
                        s += userproductList.get(i).getTitle() + "+";
                        userproductList.get(i).setOx(false);
                        check2.set(i, 1);
                    }
                }
                int m = s.length();
                t = s.substring(0, m - 1);
                editrecipe.setText(t);


            }
        });
        // 툴바 edit를 검색어로 레시피 검색
        Button btn_recipe = (Button) findViewById(R.id.btn_recipe);
        btn_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss = editrecipe.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.10000recipe.com/recipe/list.html?q=" + ss));
                startActivity(intent);
                userproductList.clear();
                tempList.clear();
                gridList.clear();
                tempList = dbHelper.getUserProduct();
                for (UserProduct ap : tempList) {
                    if (!gridList.contains(ap.getDate()))
                        gridList.add(ap.getDate());
                }
                gadapter = new GridViewAdapter();
                int x = 0;
                for (String str : gridList) {
                    UserProduct uptemp1 = new UserProduct(str);
                    UserProduct uptemp2 = new UserProduct("");
                    UserProduct uptemp3 = new UserProduct(R.drawable.trash, "trash");
                    userproductList.add(uptemp1);
                    userproductList.add(uptemp2);
                    for (UserProduct up : tempList) {
                        if (up.getDate().equals(str)) {
                            userproductList.add(up);
                            x++;
                        }
                    }
                    if (x % 2 == 1) {
                        userproductList.add(uptemp3);
                    }
                    x = 0;
                }
                for (UserProduct up : userproductList) {
                    gadapter.add(up);
                }
                gridView.setAdapter(gadapter);
            }
        });
        // 상품 등록
        Button input = (Button) findViewById(R.id.input);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserProductInput.class);
                startActivity(intent);
                userproductList.clear();
                tempList.clear();
                gridList.clear();
                tempList = dbHelper.getUserProduct();
                for (UserProduct ap : tempList) {
                    if (!gridList.contains(ap.getDate()))
                        gridList.add(ap.getDate());
                }
                gadapter = new GridViewAdapter();
                int x = 0;
                for (String str : gridList) {
                    UserProduct uptemp1 = new UserProduct(str);
                    UserProduct uptemp2 = new UserProduct("");
                    UserProduct uptemp3 = new UserProduct(R.drawable.trash, "trash");
                    userproductList.add(uptemp1);
                    userproductList.add(uptemp2);
                    for (UserProduct up : tempList) {
                        if (up.getDate().equals(str)) {
                            userproductList.add(up);
                            x++;
                        }
                    }
                    if (x % 2 == 1) {
                        //tempList.add(userproductList.get(0));
                        userproductList.add(uptemp3);
                    }
                    x = 0;
                }

                for (UserProduct up : userproductList) {
                    gadapter.add(up);
                }

                gridView.setAdapter(gadapter);

            }
        });
        // 리스트뷰 롱클릭시 상품 상세정보
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                mClick = true;
                Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();

                if ("trash".equals(userproductList.get(position).getTitle()) || "".equals(userproductList.get(position).getTitle())) {
                    // listView2.setEnabled(false);
                    return false;
                } else {
                    Intent intent = new Intent(view.getContext(), DetailProductActivity.class);
                    intent.putExtra("title", userproductList.get(position).getTitle());
                    intent.putExtra("amount", userproductList.get(position).getAmount());
                    intent.putExtra("category", userproductList.get(position).getCategory());
                    intent.putExtra("position", userproductList.get(position).getPosition());
                    intent.putExtra("detail", userproductList.get(position).getDetail());
                    intent.putExtra("date", userproductList.get(position).getDate());
                    intent.putExtra("enrollDate", userproductList.get(position).getEnrollDate());

                    startActivity(intent);
                }
                gadapter.notifyDataSetChanged();
                gridView.setAdapter(gadapter);
                return true;
            }

        });
    }



}
