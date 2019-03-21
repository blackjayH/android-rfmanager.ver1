package com.example.hong.listcomplete;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.hong.listcomplete.ShoppingListActivity.check;


public class ListViewAdapter extends BaseAdapter {
    private ArrayList<BuyProduct> buyproductList = new ArrayList<>();
    EditText inputproduct;

    @Override
    public int getCount() {
        return buyproductList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index = position;
        final Context context = parent.getContext();

        CustomViewHolder holder;
        if (convertView == null) { // Item Cell에 Layout을 적용시킬 Inflater 객체를 생성한다.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_buylist, null);
            holder = new CustomViewHolder();

            holder.m_item = (TextView) convertView.findViewById(R.id.item);
            holder.m_amount = (TextView) convertView.findViewById(R.id.tp);
            holder.m_checkbox = (CheckBox) convertView.findViewById(R.id.checkBox);

        } else {
            holder = (CustomViewHolder) convertView.getTag();

        }
        if (buyproductList.get(index).getAmount() == 0) { // 수량 0이면 리스트뷰에서 안보이게 설정
            holder.m_amount.setVisibility(View.INVISIBLE);
        } else
            holder.m_amount.setVisibility(View.VISIBLE);
        if (buyproductList.get(index).getLive() == 0) {
            holder.m_item.setTextColor(Color.parseColor("#FF1111")); // 빨간색으로 설정
            holder.m_item.setPaintFlags(holder.m_item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); // 줄긋기
        } else {
            holder.m_item.setTextColor(Color.argb(100, 0, 0, 0));

        }
        if (!buyproductList.get(index).isTf()) // 체크박스 체크 / 미체크 확인 변수
            holder.m_checkbox.setChecked(false);
        else
            holder.m_checkbox.setChecked(true);
        /*
        holder.m_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyproductList.get(index).getLive() == 0) {
                    Toast.makeText(context, index + "번째 선택 ", Toast.LENGTH_SHORT).show();
                    buyproductList.get(index).setLive(1);
                }
                else{
                    Toast.makeText(context, index + "번째 선택 ", Toast.LENGTH_SHORT).show();
                buyproductList.get(index).setLive(0);

                }
                notifyDataSetChanged();

            }


        });
*/
        holder.m_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buyproductList.get(index).isTf())
                    buyproductList.get(index).setTf(true);
                else
                    buyproductList.get(index).setTf(false);

                if (check.get(index) == 1) {
                    check.set(index, 0);
                } else {
                    check.set(index, 1);
                }
                Toast.makeText(context, index + 1 + "번째 체크박스 선택 ", Toast.LENGTH_SHORT).show();
            }
        });
        holder.m_item.setText(buyproductList.get(index).getItem());
        holder.m_amount.setText(buyproductList.get(index).getAmount() + "");

        convertView.setTag(holder);
        return convertView;
    }

    public class CustomViewHolder {
        public TextView m_item;
        public TextView m_amount;
        public CheckBox m_checkbox;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return buyproductList.get(position);
    }

    public void add(BuyProduct np) {
        buyproductList.add(np);
    }

}



