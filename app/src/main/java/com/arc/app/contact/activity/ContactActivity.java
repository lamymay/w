package com.arc.app.contact.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alibaba.fastjson.JSON;
import com.arc.app.contact.ContactDataProvider;
import com.arc.app.contact.ContactTool;
import com.arc.app.contact.MyContactListViewAdapter;
import com.arc.app.contact.R;
import com.arc.app.contact.model.AppContact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author may
 * todo 需要完成的问题
 * 1 一个人多个电话怎么插入?
 * 2 怎么发送post请求?
 * 3 怎么解析返回值
 * 4 怎么加快处理速度?
 * 5 怎么读取一个人的多个电话号码?
 */
public class ContactActivity extends AppCompatActivity {

    //输入框
    private EditText input1;

    //private static final String TAG = "Contact_Test";

    //permission
//    private static final int PERMISSION_CONTACT = 1;


    /**
     * 联系人获取服务
     */
    ContactDataProvider dataProvider = new ContactDataProvider();

    //数据输出展示的地方
    private TextView outputText;

    /**
     * 程序入口
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 第一个测试
        //listAllContactAndShowByTextViewExample1();

        // 第二个测试-- 测试对联系人 CRUD
        setContentView(R.layout.activity_contact);

        //初始化
        initContact();

        input1 = findViewById(R.id.input1);

    }


    Button syncButton = null;
    Button saveButton = null;
    Button deleteButton = null;
    Button updateButton = null;
    Button getButton = null;
    Button listAllButton = null;

    //init layout  with method
    private void initContact() {
        // 写几个按钮，对于不同按钮分别绑定crud是事件
        syncButton = findViewById(R.id.syncBtn);
        saveButton = findViewById(R.id.saveBtn);
        deleteButton = findViewById(R.id.deleteBtn);
        updateButton = findViewById(R.id.updateBtn);
        getButton = findViewById(R.id.getOneBtn);
        listAllButton = findViewById(R.id.listAllBtn);
        outputText = findViewById(R.id.outputText);

        //绑定事件

        /*
         * sync
         * 0、提示是同步方案
         * 1、远程覆盖本地
         * 2、远程被本地覆盖
         * 3、合并后处理      【暂时使用该方案，且有server完成】
         * 4、
         */
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sync();
                } catch (Exception e) {
                    e.printStackTrace();
                    //todo error
                }
                //MyContact
            }
        });

        //save
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};
//                addPermissByPermissionList(MainActivity.this, permissions, PERMISSION_CONTACT);

                List<AppContact> contacts = dataProvider.getContactList();
                if (contacts == null && contacts.size() == 0) {
                    Toast.makeText(ContactActivity.this, "无可以保存的数据", Toast.LENGTH_SHORT).show();
                }
                for (AppContact contact : contacts) {
                    boolean save = dataProvider.save(contact, ContactActivity.this);
                }
            }


        });

        //delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ContactTool.delete(ContactActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    //todo error
                }
            }
        });

        //update
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactTool.update(ContactActivity.this, null);
            }
        });

        //getOne
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     * 1、打开一个页面要求用户输入
                //     * 2、参数点击搜素后查询数据
                //     * 3、显示结果
                //todo 打开一个页面要求用户输入
                String name = input1.getText().toString();
                System.out.println(name);
                AppContact localContact = ContactTool.getContactByDisplayNameWithAllPhone(name, ContactActivity.this);
                //todo 显示结果
                String toJSONString = JSON.toJSONString(localContact);
                Toast.makeText(ContactActivity.this, "根据参数name\n" + name + "查询到的结果=" + toJSONString, Toast.LENGTH_SHORT).show();
                outputText.setText(toJSONString);
                System.out.println(localContact);
            }
        });

        //listAll
        listAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAll3();
            }
        });


    }
    //------------------------------


    private void checkRWContactsPermission() {
        //获取listview
        //适配器是为了将构造函数中把要适配的数据传入 当前提供的数据是字符串 所以泛型为String 第二个参数是子项的布局
        //判断用户是否已经授权给我们了 如果没有，调用下面方法向用户申请授权，之后系统就会弹出一个权限申请的对话框
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
//        else {
//            readContacts();
//        }
    }

//    //调用并获取联系人信息
//    private void readContacts() {
//        Cursor cursor = null;
//        try {
//
//            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    null, null, null, null);
//            if (cursor != null) {
//                while (cursor.moveToNext()) {
//                    String displayName = cursor.getString(cursor.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                    String number = cursor.getString(cursor.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    contactsList.add(displayName + "\n" + number);
//                }
//                //刷新
//                adapter.notifyDataSetChanged();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }


    //------------------------------


    /**
     * 合并后处理      【暂时使用该方案，且有server完成】
     */
    private void sync() {
        //1、listAll
        //2、send data to server and wait the server response
        //3、get the server response success than remove local database
        //2、save batch data to local database
        //2、render result success


        List<AppContact> contacts = dataProvider.getContactList();
        if (contacts == null && contacts.size() == 0) {
            Toast.makeText(ContactActivity.this, "无可以保存的数据", Toast.LENGTH_SHORT).show();
        }

        int insertTotal = 0;
        for (AppContact contact : contacts) {
            boolean save = dataProvider.save(contact, ContactActivity.this);
            if (save) {
                insertTotal = insertTotal + 1;
            }
        }

        Toast.makeText(ContactActivity.this, "保存成功" + insertTotal, Toast.LENGTH_SHORT).show();


//        //1、list
//        List<AppContact> contacts = listAll3();
//
//        //                UNION_SET(1, "取并集"),
//        //                ONLY_ACCEPT_SERVER(2, "仅取服务器的数据"),
//        //                ONLY_ACCEPT_CLIENT(3, "仅取客户端数据"),
//        String inputType = input1.getText().toString();
//        Integer synStrategyType = 3;
//        if (inputType != null) {
//            synStrategyType = Integer.valueOf(inputType.trim());
//        }
//
//        System.out.println("String inputType=" + inputType);
//        System.out.println("Integer synStrategyType=" + synStrategyType);
//        System.out.println();
//
//        AppContactRequest requestData = new AppContactRequest("18674192462", contacts, synStrategyType);
//        //2、send data
//        post(requestData);
//
//    }
//
//    public final static String uriForPostListAll = "http://192.168.2.243:8001/zero/contacts/sync";
//
//    private void post(final AppContactRequest requestData) {
//        Map<String, Object> resultMap = new HashMap<>();
//
//        System.out.println("########################################");
//        //开启线程，发送请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                HttpURLConnection connection = null;
//                BufferedReader reader = null;
//                try {
//                    URL url = new URL(uriForPostListAll);
//                    connection = (HttpURLConnection) url.openConnection();
//                    //设置请求方法
//                    connection.setRequestMethod("POST");
//                    //设置连接超时时间（毫秒）
//                    connection.setConnectTimeout(9000);
//                    //设置读取超时时间（毫秒）
//                    connection.setReadTimeout(9000);
//
//                    // 设置输入可用
//                    connection.setDoInput(true);
//                    // 设置输出可用
//                    connection.setDoOutput(true);
//                    // 设置不使用缓存
//                    connection.setUseCaches(false);
//
//                    // 设置HTTP请求属性 - 连接方式：保持
//                    connection.setRequestProperty("Connection", "Keep-Alive");
//                    // 设置HTTP请求属性 - 字符集：UTF-8
//                    connection.setRequestProperty("Charset", "UTF-8");
//                    // 设置HTTP请求属性 - 传输内容的类型 - 简单表单
////                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////                    connection.setRequestProperty("Content-Type", "application/json");
//
//                    //设置参数类型是json格式
//                    connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//
//
//                    // 发送参数 ，采用字符流发送数据
//                    //                    PrintWriter pw = new PrintWriter(connection.getOutputStream());
//                    //                    pw.write(params);
//                    //                    pw.flush();
//                    //                    pw.close();
//
//                    //                    String body = "[{},{}]";
//
//                    //contacts
//
//
//                    String params = JSON.toJSONString(requestData);
//                    String body = params;
//                    System.out.println("#################################################################");
//                    System.out.println(params);
//                    System.out.println(body);
//                    System.out.println("#################################################################");
//
//                    // 设置HTTP请求属性 - 传输内容的长度
////                    connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
//
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
//                    writer.write(body);
//                    writer.close();
//
//                    //返回输入流
//                    InputStream in = connection.getInputStream();
//
//                    //读取输入流
//                    reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//                    String resultString = result.toString();
//                    show(resultString);
//                    resultMap.put("response", resultString);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (ProtocolException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (connection != null) {//关闭连接
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
//
//        System.out.println(resultMap.size());
//        System.out.println(resultMap.get("response"));
//        System.out.println("################## END ######################");
//        System.out.println("################## END ######################");
    }

    /**
     * 展示
     *
     * @param result
     */
    private void show(final String result) {
        //todo 返回数据的处理
        System.out.println(result);

        //因为在 Android 中不允许在子线程中执行 UI 操作，所以我们通过 runOnUiThread 方法，切换为主线程，然后再更新 UI 元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outputText.setText(result);
            }
        });
    }

    //========================== 第2个测试 START================================


    /**
     * 请转换为此项
     *
     * @return
     */
    private List<AppContact> listAll3() {
        Toast.makeText(ContactActivity.this, "listAll3", Toast.LENGTH_SHORT).show();
        List<AppContact> rows = new ArrayList<>();
        //1、访问raw_contacts表 uri = content://com.android.contacts/contacts
//        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        //Uri uri = ContactsContract.Contacts.CONTENT_URI;

        ContentResolver resolver = ContactActivity.this.getContentResolver();

        //2、获得_id属性
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.Data._ID},
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            //========================================================

            //获得id并且在data中寻找数据
            int contactId = cursor.getInt(0);
            AppContact contact = new AppContact();
            List<String> phoneNumbers = new ArrayList<>();
            List<String> emails = new ArrayList<>();

            contact.setContactId(contactId);

//            int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            //data1存储各个记录的总数据，MIMETYPE 存放记录的类型，如电话、email等
            Cursor cursor2 = resolver.query(
                    Uri.parse("content://com.android.contacts/contacts/" + contactId + "/data"),
                    new String[]{ContactsContract.Contacts.Data.DATA1, ContactsContract.Contacts.Data.MIMETYPE},
                    null,
                    null,
                    null);
            while (cursor2.moveToNext()) {
                String data = cursor2.getString(cursor2.getColumnIndex("data1"));
                if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/name")) {       //如果是名字
                    contact.setDisplayName(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/nickname")) {  //如果是昵称
                    contact.setNickname(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")) {  //如果是电话
                    data = data.replaceAll("//s", "");
                    data = data.replaceAll("-", "");

                    phoneNumbers.add(data);
                    contact.setCellphone(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")) {  //如果是email
                    emails.add(data);
                    contact.setEmail(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")) { //如果是地址
                    contact.setPostalAddress(data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")) {  //如果是组织
                    contact.setOrganization(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/photo")) {  //如果是照片
                    //appContact.setPhoto(data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/group_membership")) {  //如果是组织关系
                    contact.setGroupMembership(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/im")) {  //如果是即时通讯IM
                    contact.setIm(data);
                }
                contact.setPhones(phoneNumbers);
                contact.setEmails(emails);
            }
            //            Log.i("Contacts", str);
            rows.add(contact);
        }
        //输出显示
        String string = JSON.toJSONString(rows);
        outputText.setText(string);
        return rows;
    }


//    private void listAll() {
//        onClick = findViewById(R.id.button);
//        showContact = findViewById(R.id.text);
//        onClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String[] permissList = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};
//                addPermissByPermissionList(MainActivity.this, permissList, PERMISSION_CONTACT);
//            }
//        });
//    }

    private void showContacts(String msg) {
        outputText.setText(msg);
        //Log.e(TAG, "contacts:" + msg);
    }

    /**
     * requestPermissions的回调
     * 一个或多个权限请求结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;

        //判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int grantResult : grantResults) {
            if (PackageManager.PERMISSION_DENIED == grantResult) {
                hasAllGranted = false;
                break;
            }
        }

        //同意权限做的处理,开启服务提交通讯录
        if (hasAllGranted) {
            List<AppContact> contacts = ContactTool.listAllContacts(ContactActivity.this);
            showContacts(contacts.toString());
            Toast.makeText(this, "APP拥有的确认授权" + (Arrays.toString(grantResults)), Toast.LENGTH_SHORT).show();
        } else {
            //拒绝授权做的处理，弹出弹框提示用户授权
            dealWithDeniedPermission(ContactActivity.this, permissions[0]);
        }

//        switch (requestCode) {
//             其实就是1,1就是允许
//            case PackageManager.COMPONENT_ENABLED_STATE_ENABLED:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    readContacts();
//                } else {
//                    Toast.makeText(this, "获取联系人权限失败", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                Toast.makeText(this, "联系人权限异常了走了 default 分支", Toast.LENGTH_SHORT).show();
//
//        }


    }

    public void dealWithDeniedPermission(final Activity context, String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ContactActivity.this);
            builder.setTitle("操作提示")
                    .setMessage("注意：当前缺少必要权限！\n请点击“设置”-“权限”-打开所需权限\n最后点击两次后退按钮，即可返回")
                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ContactActivity.this, "取消操作", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        }
    }

    /**
     * 动态权限
     */
    public void addPermissonByPermissionList(Activity activity, String[] permissions, int request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //Android 6.0开始的动态权限，这里进行版本判断
            ArrayList<String> mPermissionList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(activity, permissions[i])
                        != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            if (mPermissionList.isEmpty()) {  //非初次进入App且已授权
                //   showContacts();
                Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show();
            } else {
                //请求权限方法
                String[] permissionsNew = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(activity, permissionsNew, request); //这个触发下面onRequestPermissionsResult这个回调
            }
        }
    }

    //动态权限
    private void listAll() {
        Toast.makeText(ContactActivity.this, "listAll", Toast.LENGTH_SHORT).show();
        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};

        //addPermissionsByPermissionList(MainActivity.this, permissions, PERMISSION_CONTACT);
        //动态权限     public void addPermissionsByPermissionList(Activity activity, String[] permissions, int request) {
        //ro.build.version.sdk  == 26?  [Build.VERSION_CODES.M即26]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //Android 6.0开始的动态权限，这里进行版本判断
            List<String> mPermissionList = new LinkedList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(ContactActivity.this, permissions[i])
                        != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }

            //非初次进入App且已授权
            if (mPermissionList.isEmpty()) {
                List<AppContact> contacts = ContactTool.listAllContacts(ContactActivity.this);
                showContacts(contacts.toString());
                Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show();
            } else {
                //请求权限方法
                String[] permissionsNew = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(ContactActivity.this, permissionsNew, 1); //这个触发下面onRequestPermissionsResult这个回调
            }
        }
    }

    //========================== 第2个测试 END================================


    //========================== 第一个测试 START================================

    /**
     * 第一个测试，渲染一个
     */
    private void listAllContactAndShowByTextViewExample1() {
        //加载渲染主图
        setContentView(R.layout.activity_test_textview);
        //测试方法
        //UserService.listAllNumber(this);
        ListView userListView = (ListView) findViewById(R.id.listView1);
        BaseAdapter adapter = new MyContactListViewAdapter(ContactDataProvider.listAllNumber(this), this);
        userListView.setAdapter(adapter);
    }

    //========================== 第一个测试 END================================

}



