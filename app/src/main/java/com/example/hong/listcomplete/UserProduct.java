package com.example.hong.listcomplete;

import android.graphics.drawable.Drawable;

public class UserProduct { // 냉장고 속 품목
    private int icon; // 이미지
    private String user_id; //  유저아이디
    private int type;
    private String title; // 품목명
    private String enrollDate; // 등록일
    private String date; // 유통기한
    private int amount; // 수량
    private String position; // 저장공간
    private String category; // 카테고리
    private String detail; // 상세정보
    private boolean ox = false; // 체크박스 변수

    public UserProduct(String date) {
        this.date = date;
        this.type = 0;
        this.title = "";
    }

    public UserProduct(int icon, String title) {
        this.icon = icon;
        this.type = 1;
        this.title = title;
    }

    public UserProduct() {
        this.type = 1;
    }

    public UserProduct(int icon, String user_id, String title, String enrollDate, String date, int amount, String position, String category, String detail, int type) {
        this.icon = icon;
        this.user_id = user_id;
        this.type = type;
        this.title = title;
        this.enrollDate = enrollDate;
        this.date = date;
        this.amount = amount;
        this.position = position;
        this.category = category;
        this.detail = detail;
        this.ox = false;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isOx() {
        return ox;
    }

    public void setOx(boolean ox) {
        this.ox = ox;
    }
}


