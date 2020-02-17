package com.arc.w;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.arc.w.model.MyContact;
import com.arc.w.service.UserService;
import com.arc.w.util.ContactTool;
import com.arc.w.util.UserListViewAdapter;

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
        testCrudContactAndShow();
    }

    private void testCrudContactAndShow() {
        // 写几个按钮，对于不同按钮分别绑定crud是事件
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
                Toast.makeText(MainActivity.this, "save", Toast.LENGTH_SHORT).show();
            }
        });

        //delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
            }
        });

        //update
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "update", Toast.LENGTH_SHORT).show();
            }
        });

        //getOne
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "get", Toast.LENGTH_SHORT).show();
            }
        });

        //listAll
        listAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "listAll", Toast.LENGTH_SHORT).show();

                String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};
                addPermissionsByPermissionList(MainActivity.this, permissions, PERMISSION_CONTACT);
            }
        });

    }

    //========================== 第2个测试 START================================
    private static final String TAG = "Contact_Test";
    //permission
    private static final int PERMISSION_CONTACT = 1;
    private Button onClick;

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

    private void showContacts() {
        List<MyContact> contacts = ContactTool.listAllContacts(MainActivity.this);
        outputText.setText(contacts.toString());
        Log.e(TAG, "contacts:" + contacts.toString());
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
                showContacts();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
            showContacts();
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
        BaseAdapter adapter = new UserListViewAdapter(UserService.listAllNumber(this), this);
        userListView.setAdapter(adapter);
    }

    //========================== 第一个测试 END================================

}
