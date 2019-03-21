package com.example.hong.listcomplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.hong.listcomplete.FridgeActivity.check2;
import static com.example.hong.listcomplete.FridgeActivity.mClick;

public class GridViewAdapter extends BaseAdapter {
    private static final int GRID_VIEW_TYPE_Date = 0;
    private static final int GRID_VIEW_TYPE_Product = 1;

    private ArrayList<UserProduct> userproductList = new ArrayList<>();

    @Override
    public int getCount() {
        return userproductList.size();
    }

    public GridViewAdapter() {
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    // position 위치의 아이템 타입 리턴.
    @Override
    public int getItemViewType(int position) {
        return userproductList.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index = position;
        final Context context = parent.getContext();
        int viewType = getItemViewType(position);

        GridViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new GridViewHolder();
            switch (viewType) {
                case GRID_VIEW_TYPE_Product:
                    convertView = inflater.inflate(R.layout.activity_userproductlist2,
                            parent, false);
                    holder.m_icon = (ImageView) convertView.findViewById(R.id.image);
                    holder.m_name = (TextView) convertView.findViewById(R.id.name);
                    holder.m_checkbox = (CheckBox) convertView.findViewById(R.id.checkb);
                    break;

                case GRID_VIEW_TYPE_Date:
                    convertView = inflater.inflate(R.layout.activity_userproductlist, parent, false);
                    holder.m_title = (TextView) convertView.findViewById(R.id.title);
                    break;

            }
        } else {
            holder = (GridViewHolder) convertView.getTag();
        }
        switch (viewType) {
            case GRID_VIEW_TYPE_Product:
                holder.m_icon.setImageResource(userproductList.get(index).getIcon());
                holder.m_name.setText(userproductList.get(index).getTitle());
                if (("trash".equals(userproductList.get(index).getTitle()))) {
                    holder.m_icon.setVisibility(View.INVISIBLE);
                    holder.m_name.setVisibility(View.INVISIBLE);
                    holder.m_checkbox.setVisibility(View.INVISIBLE);

                } else {
                    holder.m_icon.setVisibility(View.VISIBLE);
                    holder.m_name.setVisibility(View.VISIBLE);
                    if (mClick)
                        holder.m_checkbox.setVisibility(View.VISIBLE);
                    else
                        holder.m_checkbox.setVisibility(View.INVISIBLE);
                }

                if (!userproductList.get(index).isOx())
                    holder.m_checkbox.setChecked(false);
                else
                    holder.m_checkbox.setChecked(true
                    );
                holder.m_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!userproductList.get(index).isOx())
                            userproductList.get(index).setOx(true);
                        else
                            userproductList.get(index).setOx(false);

                        if (check2.get(index) == 1) {
                            check2.set(index, 0);
                        } else {
                            check2.set(index, 1);
                        }

                        Toast.makeText(context, index + "번째 체크박스 선택 ", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case GRID_VIEW_TYPE_Date:
                holder.m_title.setText(userproductList.get(index).getDate());
                break;
        }

        convertView.setTag(holder);
        return convertView;

    }

    public class GridViewHolder {
        public ImageView m_icon;
        public TextView m_name;
        public TextView m_title;
        public CheckBox m_checkbox;
    }

    @Override
    public long getItemId(int position) {
        return position;
    } // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현

    @Override
    public Object getItem(int position) {
        return userproductList.get(position);
    }

    public void add(UserProduct up) {
        userproductList.add(up);
    }

}

