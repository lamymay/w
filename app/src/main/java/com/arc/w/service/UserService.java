package com.arc.w.service;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.arc.w.model.User;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 叶超
 * @since 2020/2/17 15:31
 */
public class UserService {

    //获取手机号
    public static List<User> listAllNumber(Context context) {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//content://com.android.contacts/data/phones
        System.out.println(" 获取联系人电话 ContactsContract.CommonDataKinds.Phone.CONTENT_URI\n" + uri);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        String phoneNumber = null;
        String name = null;

        LinkedList<User> users = new LinkedList<>();

        while (cursor.moveToNext()) {
            System.out.println(cursor);

            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            //组装联系人数据
            User user = new User(name, phoneNumber);
            users.add(user);
        }

        //测试  print user bean
        System.out.println("联系人条数=" + users.size());
        for (User user : users) {
            System.out.println(user);
        }

        return users;
    }

}
