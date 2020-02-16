//package com.arc.w;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.RelativeLayout;
//import android.widget.SectionIndexer;
//import android.widget.TextView;
//
//import java.util.List;
//
///**
// * @author 叶超
// * @since 2020/2/16 20:44
// */
//public class SortAdapter extends BaseAdapter implements SectionIndexer {
//    private List<ContactsBean> list = null;
//    private Context mContext;
//    private OnClicker onClicker;
//
//    public SortAdapter(Context mContext, List<ContactsBean> list) {
//        this.mContext = mContext;
//        this.list = list;
//    }
//
//    /**
//     * 当ListView数据发生变化时,调用此方法来更新ListView
//     *
//     * @param list
//     */
//    public void updateListView(List<ContactsBean> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }
//    @Override
//    public int getCount() {
//        return this.list.size();
//    }
//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//    @Override
//    public View getView(final int position, View view, ViewGroup arg2) {
//        ViewHolder viewHolder = null;
//        final ContactsBean mContent = list.get(position);
//        if (view == null) {
//            viewHolder = new ViewHolder();
//            view = LayoutInflater.from(mContext).inflate(R.layout.contacts_item, null);
//            viewHolder.rlContacts = view.findViewById(R.id.rl_contacts);
//            viewHolder.tvTitle = view.findViewById(R.id.tv_contacts_item_title);
//            viewHolder.tvLetter = view.findViewById(R.id.tv_contacts_item_catalog);
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
//        //根据position获取分类的首字母的Char ascii值
//        int section = getSectionForPosition(position);
//        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
//
//        if (position == getPositionForSection(section)) {
//            viewHolder.tvLetter.setVisibility(View.VISIBLE);
//            viewHolder.tvLetter.setText(mContent.getSortLetters());
//        } else {
//            viewHolder.tvLetter.setVisibility(View.GONE);
//        }
//        final String mobile = this.list.get(position).getPhone()
//                .replaceAll(" ", "")
//                .replaceAll("\\+", "")
//                .replaceAll("-", "");
//        viewHolder.tvTitle.setText(this.list.get(position).getName());
//        viewHolder.rlContacts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClicker.onClik(list.get(position).getName(), mobile);
//            }
//        });
//        return view;
//
//    }
//
//
//    final static class ViewHolder {
//        private TextView tvLetter,//首字母
//                tvTitle;//名字
//        private RelativeLayout rlContacts;
//        //tvIsImpower;//是否授权
//    }
//
//
//    /**
//     * 根据ListView的当前位置获取分类的首字母的Char ascii值
//     */
//    @Override public int getSectionForPosition(int position) {
//        return list.get(position).getSortLetters().charAt(0);
//    }
//
//    /**
//     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
//     */
//    @Override
//    public int getPositionForSection(int section) {
//        for (int i = 0; i < getCount(); i++) {
//            String sortStr = list.get(i).getSortLetters();
//            char firstChar = sortStr.toUpperCase().charAt(0);
//            if (firstChar == section) {
//                return i;
//            }
//        }
//
//        return -1;
//    }
//
//    public void setOnCliker(OnClicker onClicker) {
//        this.onClicker = onClicker;
//    }
//
//    /**
//     * 提取英文的首字母，非英文字母用#代替。
//     *
//     * @param str
//     * @return
//     */
//    private String getAlpha(String str) {
//        String sortStr = str.trim().substring(0, 1).toUpperCase();
//        // 正则表达式，判断首字母是否是英文字母
//        if (sortStr.matches("[A-Z]")) {
//            return sortStr;
//        } else {
//            return "#";
//        }
//    }
//
//    @Override
//    public Object[] getSections() {
//        return null;
//    }
//
//    public interface OnClicker {
//        void onClik(String name, String phone);
//    }
//}
