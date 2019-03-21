package com.example.hong.listcomplete;

public class BuyProduct { // 장바구니 품목
    private String user_id; // 유저 아이디
    private String item; // 품목명
    private int amount; // 수량
    private int live; // // 장바구니에서 빨간색 줄로 긋는 변수(1,0) 2개만 사용  1 : 일반 / 0 : 빨간색 + 밑줄
    private int withdraw; // buylist : 1 //////// backuplist : 0 2개만 사용
    private boolean tf = false; // 체크박스 선택 관련 변수

    public BuyProduct(String user_id, String item, int amount, int live, int withdraw) {
        this.user_id = user_id;
        this.item = item;
        this.amount = amount;
        this.live = live;
        this.withdraw = withdraw;
        this.tf = false;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public int getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(int withdraw) {
        this.withdraw = withdraw;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }
}
