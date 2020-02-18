package com.arc.w.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.arc.w.R;
import com.arc.w.model.User;

import java.util.LinkedList;
import java.util.List;

/**
 * 布局文件适配转换
 *
 * @author 叶超
 * @since 2020/2/17 19:49
 */
public class UserListViewAdapter extends BaseAdapter {

    private List<User> list = new LinkedList<>();
    private Context context;
    //布局    private LinearLayout layout;

    public UserListViewAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 当前要加载的子 view
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return fun1(position, convertView, parent); //测试第一种方案
        return fun2(position, convertView, parent);
    }

    /**
     * 不好的例子，改进前的例子
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    private View fun1(int position, View convertView, ViewGroup parent) {
        //加载view的权限
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = (LinearLayout) layoutInflater.inflate(R.layout.call, null);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);
        TextView numberTextView = (TextView) convertView.findViewById(R.id.number);

        User user = list.get(position);
        nameTextView.setText(user.getName());
        numberTextView.setText(user.getPhone());

        return convertView;
    }


    /**
     * 改进后的例子
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    private View fun2(int position, View convertView, ViewGroup parent) {
        CacheViewHolder cacheView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.call, null);
            TextView nameText = (TextView) convertView.findViewById(R.id.name);
            TextView phoneText = (TextView) convertView.findViewById(R.id.number);

            nameText.setText(list.get(position).getName());
            phoneText.setText(list.get(position).getPhone());

            convertView.setTag(new CacheViewHolder(nameText, phoneText));
        } else {
            cacheView = (CacheViewHolder) convertView.getTag();
            cacheView.nameTv.setText(list.get(position).getName());
            cacheView.phoneTv.setText(list.get(position).getPhone());
        }
        return convertView;
    }


    //    @Setter
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
    private static class CacheViewHolder {
        TextView nameTv;
        TextView phoneTv;

        public CacheViewHolder() {
        }

        public CacheViewHolder(TextView nameTv, TextView phoneTv) {
            this.nameTv = nameTv;
            this.phoneTv = phoneTv;
        }
    }

}

