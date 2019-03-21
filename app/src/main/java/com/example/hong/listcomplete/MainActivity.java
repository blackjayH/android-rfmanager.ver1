package com.example.hong.listcomplete;


import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;


public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }
    // 메뉴 아이템 미구현
    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem) {
        switch (menuitem.getItemId()) {
            case R.id.login:
                break;
            case R.id.logout:
                break;
            case R.id.dbreset:
                break;
            case R.id.settingreset:
                break;
            case R.id.userguide:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent;
        tabHost = (TabHost) findViewById(R.id.tabhost1);
        LocalActivityManager activityMgr = new LocalActivityManager(this, false);
        activityMgr.dispatchCreate(savedInstanceState);
        tabHost.setup(activityMgr);

        intent = new Intent(this, FridgeActivity.class);
        TabHost.TabSpec tab1 = tabHost.newTabSpec("A").setContent(R.id.tab1).setIndicator("냉장고").setContent(intent);
        tabHost.addTab(tab1); //1번탭 생성

        intent = new Intent(this, ShoppingListActivity.class);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("B").setContent(R.id.tab2).setIndicator("장보기 리스트").setContent(intent);
        tabHost.addTab(tab2); //2번탭 생성

        //intent = new Intent(this, ShoppingListActivity.class);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("C").setContent(R.id.tab3).setIndicator("상품 등록하러 가기");//.setContent(intent);
        tabHost.addTab(tab3); //3번탭 생성

    }
}






















