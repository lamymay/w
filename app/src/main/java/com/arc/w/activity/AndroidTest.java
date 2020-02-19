//package com.arc.w.activity;
//
//import android.app.Activity;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import com.arc.w.R;
//
///**
// * @author 叶超
// * @since 2020/2/18 20:19
// */
//public class AndroidTest extends Activity {
//
//    private static final String TAG = "AndroidTest";
//    private TextView m_TextView;
//    private EditText m_EditText;
//
//    private String mNumber;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        m_TextView = (TextView) findViewById(R.id.TextView01);
//        m_EditText = (EditText) findViewById(R.id.EditText01);
//
//        m_TextView.setTextSize(20);
//        /**
//         * 设置当m_EditText中为空时提示的内容
//         * 在XML中同样可以实现：android:hint="请输入账号"
//         */
//        m_EditText.setHint("请输入账号");
//
//        /* 设置EditText事件监听 */
//        m_EditText.setOnKeyListener(new EditText.OnKeyListener() {
//            @Override
//            public boolean onKey(View arg0, int arg1, KeyEvent arg2)            {
//                // TODO Auto-generated method stub
//                // 得到文字，将其显示到TextView中
////          m_TextView.setText("文本框中内容是：" + m_EditText.getText().toString());
//                return false;
//            }
//        });
//
//        m_EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                mNumber = ((EditText)v).getText().toString();
//                Log.d(TAG, "mNumber = " + mNumber);
//                getPeople();
//            }
//
//        });
//
//        m_EditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,                                      int count) {
//                // TODO Auto-generated method stub
//                // 得到文字，将其显示到TextView中
//                m_TextView.setText("文本框中内容是：" + m_EditText.getText().toString());
//            }
//
//        });
//    }
//
//    /*
//     * 根据电话号码取得联系人姓名
//     */
//    public void getPeople() {
//        String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME,                ContactsContract.CommonDataKinds.Phone.NUMBER};
//        Log.d(TAG, "getPeople ---------");
//
//        // 将自己添加到 msPeers 中
//        Cursor cursor = this.getContentResolver().query(
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                projection,    // Which columns to return.
//                ContactsContract.CommonDataKinds.Phone.NUMBER + " = '" + mNumber + "'", // WHERE clause.
//                null,          // WHERE clause value substitution
//                null);   // Sort order.
//
//        if( cursor == null ) {
//            Log.d(TAG, "getPeople null");
//            return;
//        }
//        Log.d(TAG, "getPeople cursor.getCount() = " + cursor.getCount());
//        for( int i = 0; i < cursor.getCount(); i++ )        {
//            cursor.moveToPosition(i);
//            // 取得联系人名字
//            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
//            String name = cursor.getString(nameFieldColumnIndex);
//            Log.i("Contacts", "" + name + " .... " + nameFieldColumnIndex); // 这里提示 force close
//            m_TextView.setText("联系人姓名：" + name);
//        }
//    }
//
//}
