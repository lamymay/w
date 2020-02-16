package com.arc.w;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

//
//import android.Manifest;
//import android.content.ContentResolver;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
public class MainActivity extends AppCompatActivity {

    private ListView sortListView;
    private TextView dialog;

    private String mobile;     //被授权人电话号码
    private String name;    //被授权人姓名
    private Uri contactsUrl = ContactsContract.Contacts.CONTENT_URI;   //查询通讯录名字
    private ContentResolver resolver;
    private List<ContactsBean> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

//    private SideLetterUtil sideLetterUtil;
//    private SortAdapter adapter;
//    private ClearEditText mClearEditText;


//汉字转换成拼音的类
//        private CharacterParser characterParser;
//        private List<ContactsBean> SourceDateList;
//
//        /**
//         * 根据拼音来排列ListView里面的数据类
//         */
//        private ContactsPinyinComparator pinyinComparator;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        getPersimmionInfo();
//    }

//        private void getPersimmionInfo() {
//            if (Build.VERSION.SDK_INT >= 23) {
//                //1. 检测是否添加权限   PERMISSION_GRANTED  表示已经授权并可以使用
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                    //手机为Android6.0的版本,权限未授权去i请授权
//                    //2. 申请请求授权权限
//                    //1. Activity
//                    // 2. 申请的权限名称
//                    // 3. 申请权限的 请求码
//                    ActivityCompat.requestPermissions(this, new String[]
//                            {Manifest.permission.READ_CONTACTS//通话录
//                            }, 1005);
//                } else {//手机为Android6.0的版本,权限已授权可以使用
//                    // 执行下一步
//                    initContacts();
//                }
//
//            } else {//手机为Android6.0以前的版本，可以使用
//                initContacts();
//            }
//
//        }
//
//        //  重写方法    当权限申请后     执行 接收结果的作用
//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//            // 先判断请求码 与 请求时的  是否一致
//            if (requestCode == 1005) {
//                //  判断请求结果长度     且  结果 为  允许访问  则 进行使用;第一次授权成功后
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // 可以使用
//                    initContacts();
//                } else {//未授权不可以使用
//                    // 摄像机或读写内存卡权限授权将不能使用以下功能。
//                    Toast.makeText(MainActivity.this, "未授权不可以使用", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//        private void initContacts() {
//            //实例化汉字转拼音类
//            characterParser = CharacterParser.getInstance();
//            pinyinComparator = new ContactsPinyinComparator();
//            //右侧26个字母和#
//            sideLetterUtil = (SideLetterUtil) findViewById(R.id.sidrbar);
//            //中间显示字母
//            dialog = (TextView) findViewById(R.id.dialog);
//            sideLetterUtil.setTextView(dialog);
//            //设置右侧触摸监听
//            sideLetterUtil.setOnTouchingLetterChangedListener(new SideLetterUtil.OnTouchingLetterChangedListener() {
//
//                @Override
//                public void onTouchingLetterChanged(String s) {
//                    //该字母首次出现的位置
//                    int position = adapter.getPositionForSection(s.charAt(0));
//                    if (position != -1) {
//                        sortListView.setSelection(position);
//                    }
//
//                }
//            });
//
//            sortListView = (ListView) findViewById(R.id.lv_contacts_item);
//            /**
//             * 查询通讯录名字
//             */
//            nameList = new ArrayList<>();
//            resolver = getContentResolver();
//            //用于查询电话号码的URI
//            Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//            // 查询的字段
//            String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,//Id
//                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,//通讯录姓名
//                    ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",//通讯录手机号
//                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,//通讯录Id
//                    ContactsContract.CommonDataKinds.Phone.PHOTO_ID,//手机号Id
//                    ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY};
//            Cursor cursor = resolver.query(phoneUri, projection, null, null, null);
//            while ((cursor.moveToNext())) {
//                String name = cursor.getString(1);
//                String phone = cursor.getString(2);
//                nameList.add(new ContactsBean(name, phone));
//            }
//            SourceDateList = filledData(nameList);
//            // 根据a-z进行排序源数据
//            Collections.sort(SourceDateList, pinyinComparator);
//            adapter = new SortAdapter(this, SourceDateList);
//            sortListView.setAdapter(adapter);
//            adapter.setOnCliker(new SortAdapter.OnClicker() {
//                @Override
//                public void onClik(String name1, String phone1) {
//                    //这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                    mobile = phone1;
//                    if (MobileUtil.isMobileNO(mobile)) {
//                        //无通讯姓名则为StrConfig.CONTACTS_NAME_NULL
//                        name = (TextUtils.equals(name1, "未备注联系人"))
//                                ? "null" : name1;
//                        Toast.makeText(MainActivity.this,
//                                name + mobile, Toast.LENGTH_SHORT).show();
//                    } else {
//                        //该用户无手机号，或手机号格式不正确
//                        Toast.makeText(MainActivity.this,
//                                "该用户无手机号，或手机号格式不正确", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            mClearEditText = findViewById(R.id.filter_edit);
//            //根据输入框输入值的改变来过滤搜索
//            mClearEditText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                    filterData(s.toString());
//                }
//
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count,
//                                              int after) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
//        }
//
//        /**
//         * 为ListView填充数据
//         */
//        private List<ContactsBean> filledData(List<ContactsBean> date) {
//            List<ContactsBean> mSortList = new ArrayList<ContactsBean>();
//            for (int i = 0; i < date.size(); i++) {
//                ContactsBean contactsBean = new ContactsBean();
//                contactsBean.setName(date.get(i).getName());
//                contactsBean.setPhone(date.get(i).getPhone());
//                //汉字转换成拼音
//                String pinyin = characterParser.getSelling(date.get(i).getName());
//                String sortString = pinyin.substring(0, 1).toUpperCase();
//
//                // 正则表达式，判断首字母是否是英文字母
//                if (sortString.matches("[A-Z]")) {
//                    contactsBean.setSortLetters(sortString.toUpperCase());
//                } else {
//                    contactsBean.setSortLetters("#");
//                }
//                mSortList.add(contactsBean);
//            }
//            return mSortList;
//
//        }
//
//        /**
//         * 根据输入框中的值来过滤数据并更新ListView
//         *
//         * @param filterStr
//         */
//        private void filterData(String filterStr) {
//            List<ContactsBean> filterDateList = new ArrayList<ContactsBean>();
//
//            if (TextUtils.isEmpty(filterStr)) {
//                filterDateList = SourceDateList;
//            } else {
//                filterDateList.clear();
//                for (ContactsBean contactsBean : SourceDateList) {
//                    String name = contactsBean.getName();
//                    if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
//                        filterDateList.add(contactsBean);
//                    }
//                }
//            }
//            // 根据a-z进行排序
//            Collections.sort(filterDateList, pinyinComparator);
//            adapter.updateListView(filterDateList);
//        }
//    }
