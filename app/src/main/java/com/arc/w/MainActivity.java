package com.arc.w;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alibaba.fastjson.JSON;
import com.arc.w.model.AppContact;
import com.arc.w.service.AppContactService;
import com.arc.w.util.ContactTool;
import com.arc.w.util.MyContactListViewAdapter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author arc
 */
public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        try {
            testCrudContactAndShow();
        } catch (Exception e) {
            e.printStackTrace();
            //todo error
        }
    }

    //init layout  with method
    private void testCrudContactAndShow() {
        // 写几个按钮，对于不同按钮分别绑定crud是事件
        Button syncButton = findViewById(R.id.syncBtn);
        Button saveButton = findViewById(R.id.saveBtn);
        Button deleteButton = findViewById(R.id.deleteBtn);
        Button updateButton = findViewById(R.id.updateBtn);
        Button getButton = findViewById(R.id.getOneBtn);
        Button listAllButton = findViewById(R.id.listAllBtn);
        outputText = findViewById(R.id.outputText);

        //绑定事件-- save
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};
//                addPermissByPermissionList(MainActivity.this, permissions, PERMISSION_CONTACT);
                save();
            }
        });

        //delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    delete();
                } catch (Exception e) {
                    e.printStackTrace();
                    //todo error
                }
            }
        });

        /**
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

        //update
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        //getOne
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOne();
            }

        });

        //listAll
        listAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAll2();
            }


        });

    }

    /**
     * 更新数据
     */
    private void update() {
        // 1、打开一个页面要求用户输入
        // 2、输入参数后，根据变化的数据来更新
        // 3、显示结果

        AppContact contact = new AppContact();

        AppContact localContact = ContactTool.update(MainActivity.this, contact);

        Toast.makeText(MainActivity.this, "update" + localContact, Toast.LENGTH_SHORT).show();

    }

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

        //1、list
        List<AppContact> contacts = listAll3();
//        String contacts = listAll3();

        //2、send data
        post(contacts);

    }


    public final static String uriForPostListAll = "http://192.168.2.195:8001/zero/contacts/sync";


    private void post(final List<AppContact> contacts) {
        System.out.println("########################################");
        //开启线程，发送请求
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(uriForPostListAll);
                    connection = (HttpURLConnection) url.openConnection();
                    //设置请求方法
                    connection.setRequestMethod("POST");
                    //设置连接超时时间（毫秒）
                    connection.setConnectTimeout(9000);
                    //设置读取超时时间（毫秒）
                    connection.setReadTimeout(9000);

                    // 设置输入可用
                    connection.setDoInput(true);
                    // 设置输出可用
                    connection.setDoOutput(true);
                    // 设置不使用缓存
                    connection.setUseCaches(false);

                    // 设置HTTP请求属性 - 连接方式：保持
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    // 设置HTTP请求属性 - 字符集：UTF-8
                    connection.setRequestProperty("Charset", "UTF-8");
                    // 设置HTTP请求属性 - 传输内容的类型 - 简单表单
//                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                    connection.setRequestProperty("Content-Type", "application/json");

                    //设置参数类型是json格式
                    connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");


                    // 发送参数 ，采用字符流发送数据
                    //                    PrintWriter pw = new PrintWriter(connection.getOutputStream());
                    //                    pw.write(params);
                    //                    pw.flush();
                    //                    pw.close();

                    //                    String body = "[{},{}]";

                    //contacts

                    if (contacts != null) {
                        for (AppContact contact : contacts) {
                            System.out.println("contact="+contact);
                        }
                    }

                    System.out.println("contacts="+contacts);

                    String params = JSON.toJSONString(contacts);
                    String body = params;
                    System.out.println("#################################################################");
                    System.out.println(params);
                    System.out.println(body);
                    System.out.println("#################################################################");

                    // 设置HTTP请求属性 - 传输内容的长度
//                    connection.setRequestProperty("Content-Length", String.valueOf(body.length()));

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    //返回输入流
                    InputStream in = connection.getInputStream();

                    //读取输入流
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    show(result.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {//关闭连接
                        connection.disconnect();
                    }
                }
            }
        }).start();

        System.out.println("################## END ######################");
        System.out.println("################## END ######################");

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
     * 1、打开一个页面要求用户输入
     * 2、参数点击搜素后查询数据
     * 3、显示结果
     */
    private void getOne() {
        //todo 打开一个页面要求用户输入
        AppContact contact = new AppContact();
        AppContact localContact = ContactTool.getOne(MainActivity.this, contact);
        //todo 显示结果
        Toast.makeText(MainActivity.this, "参数\n" + contact + " 结果=" + localContact, Toast.LENGTH_SHORT).show();
        outputText.setText(localContact.toString());
        System.out.println(localContact);
    }

    Context context = MainActivity.this;

    /**
     * Delete
     * <p>
     * 核心思想：
     * (1)先在raw_contacts表根据姓名(此处的姓名为name记录的data2的数据而不是data1的数据)查出id；
     * (2)在data表中只要raw_contact_id匹配的都删除；
     * 复制代码
     */
    private void delete() throws Exception {
        System.out.println("##################### DELETE START #########################");
        long t1 = System.currentTimeMillis();
        Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
        String name = "123";
        //根据姓名求id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, "display_name=?", new String[]{name}, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, "display_name=?", new String[]{name});
            uri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});
        }
        System.out.println("##################### DELETE END " + (System.currentTimeMillis() - t1) + "ms #########################");

    }

    /**
     * save
     */
    private void save() {
        System.out.println("##################### SAVE START #########################");
        long t1 = System.currentTimeMillis();
        try {

            saveOne();
            //saveBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("##################### SAVE END " + (System.currentTimeMillis() - t1) + "ms #########################");
    }

    /**
     * 测试
     * insert
     * 注意：对某个联系人插入姓名、电话等记录时必须要插入Data.MIMETYPE（或者是"mimetype"）属性,而不是插入"mimetype_id"!
     * 比如：values.put(Data.MIMETYPE,"vnd.android.cursor.item/phone_v2")
     */
    private void saveOne() {
        //插入raw_contacts表，并获取_id属性
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        long contact_id = ContentUris.parseId(resolver.insert(uri, values));
        //插入data表
        uri = Uri.parse("content://com.android.contacts/data");
        //add name
        values.put("raw_contact_id", contact_id);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/name");
        values.put("data2", "zdong");
        values.put("data1", "xzdong");
        resolver.insert(uri, values);
        values.clear();
        //add phone
        values.put("raw_contact_id", contact_id);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
        values.put("data2", "2");   //手机
        values.put("data1", "87654321");
        resolver.insert(uri, values);
        values.clear();
        //add email
        values.put("raw_contact_id", contact_id);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/email_v2");
        values.put("data2", "2");   //单位
        values.put("data1", "xzdong@xzdong.com");
        resolver.insert(uri, values);

        //方便测试这里使用一个文本框做输出显示
        Toast.makeText(MainActivity.this, "saveOne", Toast.LENGTH_SHORT).show();
        outputText.setText("insert");
        System.out.println("########################saveOne######################");
    }

    /**
     * 批量添加数据
     * 核心代码：
     * <p>
     * (1)ContentProviderOperation operation = ContentProviderOperation.newInsert(uri).withValue("key","value").build();
     * <p>
     * (2)resolver.applyBatch("authorities",operations);//批量提交
     */
    private void saveBatch(List<AppContact> contacts) throws Exception {
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();

        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
        // 向raw_contact表添加一条记录
        //此处.withValue("account_name", null)一定要加，不然会抛NullPointerException
        ContentProviderOperation operation1 = ContentProviderOperation.newInsert(uri).withValue("account_name", null).build();

        operations.add(operation1);

        // 向data添加数据
        uri = Uri.parse("content://com.android.contacts/data");
        //添加姓名
        ContentProviderOperation operation2 = ContentProviderOperation.newInsert(uri).withValueBackReference("raw_contact_id", 0)
                //withValueBackReference的第二个参数表示引用operations[0]的操作的返回id作为此值
                .withValue("mimetype", "vnd.android.cursor.item/name")
                .withValue("data2", "xzdong").build();
        operations.add(operation2);


        //添加手机数据
        ContentProviderOperation operation3 = ContentProviderOperation
                .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                .withValue("data2", "2").withValue("data1", "0000000").build();
        operations.add(operation3);

        //集合数据提交
        resolver.applyBatch("com.android.contacts", operations);


        //方便测试这里使用一个文本框做输出显示
        Toast.makeText(MainActivity.this, "saveBatch", Toast.LENGTH_SHORT).show();
        outputText.setText("saveBatch");
        System.out.println("##################### saveBatch #########################");
    }

    private void saveBatchForTest() throws Exception {
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
        // 向raw_contact表添加一条记录
        //此处.withValue("account_name", null)一定要加，不然会抛NullPointerException
        ContentProviderOperation operation1 = ContentProviderOperation
                .newInsert(uri).withValue("account_name", null).build();
        operations.add(operation1);
        // 向data添加数据
        uri = Uri.parse("content://com.android.contacts/data");
        //添加姓名
        ContentProviderOperation operation2 = ContentProviderOperation
                .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                //withValueBackReference的第二个参数表示引用operations[0]的操作的返回id作为此值
                .withValue("mimetype", "vnd.android.cursor.item/name")
                .withValue("data2", "xzdong").build();
        operations.add(operation2);
        //添加手机数据
        ContentProviderOperation operation3 = ContentProviderOperation
                .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                .withValue("data2", "2").withValue("data1", "0000000").build();
        operations.add(operation3);
        resolver.applyBatch("com.android.contacts", operations);

        //方便测试这里使用一个文本框做输出显示
        Toast.makeText(MainActivity.this, "saveBatch", Toast.LENGTH_SHORT).show();
        outputText.setText("saveBatch");
        System.out.println("##################### saveBatch #########################");
    }

    /**
     * 根据电话号码查询姓名
     *
     * @param phoneNumber
     * @return
     */
    private String getContactNameByPhoneNumber(final String phoneNumber) {
        //根据电话号码查询姓名（在一个电话打过来时，如果此电话在通讯录中，则显示姓名）
        //uri=  content://com.android.contacts/data/phones/filter/#
        final Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + phoneNumber);
        ContentResolver resolver = MainActivity.this.getContentResolver();
        //从raw_contact表中返回display_name
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data.DISPLAY_NAME}, null, null, null);
        StringBuffer buffer = new StringBuffer("");
        if (cursor.moveToFirst()) {
            buffer.append(cursor.getString(0));
            //todo Log.i("Contacts", "name=" + cursor.getString(0));
        }

        //输出显示
        Toast.makeText(MainActivity.this, "根据电话号码查询姓名", Toast.LENGTH_SHORT).show();
        outputText.setText(buffer.toString());
        return buffer.toString();
    }


    @Deprecated
    private void listAll() {
        Toast.makeText(MainActivity.this, "listAll", Toast.LENGTH_SHORT).show();
        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};
        addPermissionsByPermissionList(MainActivity.this, permissions, PERMISSION_CONTACT);
    }


    private void listAll2() {
        Toast.makeText(MainActivity.this, "listAll2", Toast.LENGTH_SHORT).show();

        Toast.makeText(MainActivity.this, "测试获取单条", Toast.LENGTH_SHORT).show();
//        MyContact contact = ContactTool.getContactByDisplayName(name, this);
        System.out.println("##############################################");

        //1、访问raw_contacts表 uri = content://com.android.contacts/contacts
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        ContentResolver resolver = MainActivity.this.getContentResolver();

        //2、获得_id属性
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Contacts.Data._ID}, null, null, null);
        StringBuilder buf = new StringBuilder();
        while (cursor.moveToNext()) {
            //获得id并且在data中寻找数据
            int id = cursor.getInt(0);
            buf.append("id=" + id);
            uri = Uri.parse("content://com.android.contacts/contacts/" + id + "/data");
            //data1存储各个记录的总数据，MIMETYPE 存放记录的类型，如电话、email等
            Cursor cursor2 = resolver.query(uri, new String[]{ContactsContract.Contacts.Data.DATA1, ContactsContract.Contacts.Data.MIMETYPE}, null, null, null);
            while (cursor2.moveToNext()) {
                String data = cursor2.getString(cursor2.getColumnIndex("data1"));
                if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/name")) {       //如果是名字
                    buf.append(",name=" + data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/nickname")) {  //如果是昵称
                    buf.append(",nickname=" + data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")) {  //如果是电话
                    buf.append(",phone=" + data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")) {  //如果是email
                    buf.append(",email=" + data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")) { //如果是地址
                    buf.append(",address=" + data);
                }
//                else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")) {  //如果是组织
//                    buf.append(",organization=" + data);
//                }else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/photo")) {  //如果是照片
//                    buf.append(",photo=" + data);
//                }else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/group_membership")) {  //如果是组织关系
//                    buf.append(",group_membership=" + data);
//                }else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/im")) {  //如果是即时通讯IM
//                    buf.append(",im=" + data);
//                }
            }
            String str = buf.toString();
            Log.i("Contacts", str);
        }

        System.out.println("##############################################");

        //输出显示
        outputText.setText(buf.toString());

    }


    /**
     * 请转换为此项
     *
     * @return
     */
    private List<AppContact> listAll3() {
        Toast.makeText(MainActivity.this, "listAll3", Toast.LENGTH_SHORT).show();
        List<AppContact> rows = new ArrayList<>();
        //1、访问raw_contacts表 uri = content://com.android.contacts/contacts
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        ContentResolver resolver = MainActivity.this.getContentResolver();

        //2、获得_id属性
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Contacts.Data._ID}, null, null, null);
        while (cursor.moveToNext()) {
            //获得id并且在data中寻找数据
            AppContact appContact = new AppContact();

            int id = cursor.getInt(0);
            appContact.setContactId(id);

            uri = Uri.parse("content://com.android.contacts/contacts/" + id + "/data");
            //data1存储各个记录的总数据，MIMETYPE 存放记录的类型，如电话、email等
            Cursor cursor2 = resolver.query(uri, new String[]{ContactsContract.Contacts.Data.DATA1, ContactsContract.Contacts.Data.MIMETYPE}, null, null, null);
            while (cursor2.moveToNext()) {
                String data = cursor2.getString(cursor2.getColumnIndex("data1"));
                if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/name")) {       //如果是名字
                    appContact.setDisplayName(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/nickname")) {  //如果是昵称
                    appContact.setNickname(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")) {  //如果是电话
                    appContact.setCellphone(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")) {  //如果是email
                    appContact.setEmail(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")) { //如果是地址
                    appContact.setPostalAddress(data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")) {  //如果是组织
                    appContact.setOrganization(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/photo")) {  //如果是照片
                    //appContact.setPhoto(data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/group_membership")) {  //如果是组织关系
                    appContact.setGroupMembership(data);

                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/im")) {  //如果是即时通讯IM
                    appContact.setIm(data);
                }
            }
            //            Log.i("Contacts", str);
            rows.add(appContact);
        }
        //输出显示
        String string = JSON.toJSONString(rows);
        outputText.setText(string);
        return rows;
    }

    //-----
    private static final String TAG = "Contact_Test";
    //permission
    private static final int PERMISSION_CONTACT = 1;

    //数据输出展示的地方
    private TextView outputText;

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
     * 动态权限
     */
    public void addPermissionsByPermissionList(Activity activity, String[] permissions, int request) {
        //ro.build.version.sdk  == 26?  [Build.VERSION_CODES.M即26]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //Android 6.0开始的动态权限，这里进行版本判断
            List<String> mPermissionList = new LinkedList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(activity, permissions[i])
                        != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }

            //非初次进入App且已授权
            if (mPermissionList.isEmpty()) {
                List<AppContact> contacts = ContactTool.listAllContacts(MainActivity.this);
                showContacts(contacts.toString());
                Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show();
            } else {
                //请求权限方法
                String[] permissionsNew = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(activity, permissionsNew, request); //这个触发下面onRequestPermissionsResult这个回调
            }
        }
    }

    /**
     * requestPermissions的回调
     * 一个或多个权限请求结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        //判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                break;
            }
        }
        if (hasAllGranted) { //同意权限做的处理,开启服务提交通讯录
            List<AppContact> contacts = ContactTool.listAllContacts(MainActivity.this);
            showContacts(contacts.toString());
            Toast.makeText(this, "同意授权", Toast.LENGTH_SHORT).show();
        } else {    //拒绝授权做的处理，弹出弹框提示用户授权
            dealwithPermiss(MainActivity.this, permissions[0]);
        }

    }

    public void dealwithPermiss(final Activity context, String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                            Toast.makeText(MainActivity.this, "取消操作", Toast.LENGTH_SHORT).show();
                        }
                    }).show();

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
        BaseAdapter adapter = new MyContactListViewAdapter(AppContactService.listAllNumber(this), this);
        userListView.setAdapter(adapter);
    }

    //========================== 第一个测试 END================================

}
