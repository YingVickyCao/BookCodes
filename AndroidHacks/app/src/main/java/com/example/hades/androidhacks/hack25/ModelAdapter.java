/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack25;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hades.androidhacks.R;

import java.util.List;

public class ModelAdapter extends ArrayAdapter<Model> {

    private LayoutInflater mInflater;

    public ModelAdapter(Context context, int textViewResourceId, List<Model> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        // 如果 convertView 为 null，就填充视图
        if (convertView == null) {
            // 创建视图
            convertView = mInflater.inflate(R.layout.activity_hack_25_item_view, parent, false);

            viewHolder = new ViewHolder();
            // 获取视图中各个控件的引用，并存入viewHolder中
            viewHolder.imageView = convertView.findViewById(R.id.image);
            viewHolder.text1 = convertView.findViewById(R.id.text1);
            viewHolder.text2 = convertView.findViewById(R.id.text2);

            // 把ViewHolder存入标记中
            convertView.setTag(viewHolder);

        } else {
            // 如果convertView 非null， 则回收利用它。
            // convertView的标记中获取viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 根据视图的索引位置，获取 model 对象
        Model model = getItem(position);

        // 填充视图
        // 使用 model 中的数据填充该视图
        viewHolder.imageView.setImageResource(model.getImage());
        viewHolder.text1.setText(model.getText1());
        viewHolder.text2.setText(model.getText2());

        return convertView;
    }

    // ViewHolder 类
    // ViewHolder中变量存储所有UI控件的引用
    private static class ViewHolder {
        ImageView imageView;
        TextView text1;
        TextView text2;
    }
}
