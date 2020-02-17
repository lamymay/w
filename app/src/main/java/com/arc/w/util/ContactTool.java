package com.arc.w.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.arc.w.model.MyContact;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 联系人 CRUD 服务
 *
 * @author z
 */
public class ContactTool {

    /**
     * 查询全部的联系人
     *
     * @param context
     * @return
     */
    public static ArrayList<MyContact> listAllContacts(Context context) {
        ArrayList<MyContact> contacts = new ArrayList<MyContact>();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();

        //listAll
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        while (cursor.moveToNext()) {
            //新建一个联系人实例
            MyContact temp = new MyContact();
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人姓名
            temp.contactId = contactId;
            temp.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            //获取联系人电话号码
            Uri contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String sql = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId;

            Cursor phoneCursor = contentResolver.query(contentUri, null, sql, null, null);
            while (phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
                temp.phone = phone;
            }

//            //获取联系人备注信息
//            Cursor noteCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Nickname.NAME},
//                    ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "='"
//                            + ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE + "'",
//                    new String[]{contactId}, null);
//            if (noteCursor.moveToFirst()) {
//                do {
//                    String note = noteCursor.getString(noteCursor
//                            .getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
////                    temp.note = note;
//                    Log.i("note:", note);
//                } while (noteCursor.moveToNext());
//            }

            contacts.add(temp);
            //记得要把cursor给close掉
            phoneCursor.close();
//            noteCursor.close();
        }

        cursor.close();
        return contacts;
    }


    //获取手机号
    public static List<MyContact> listAllNumber(Context context) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//content://com.android.contacts/data/phones
        System.out.println(" 获取联系人电话 ContactsContract.CommonDataKinds.Phone.CONTENT_URI\n" + uri);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        String phoneNumber = null;
        String name = null;

        LinkedList<MyContact> users = new LinkedList<>();
        while (cursor.moveToNext()) {
            System.out.println(cursor);

            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            //组装联系人数据
            MyContact user = new MyContact(name, phoneNumber);
            users.add(user);
        }

        //测试  print user bean
        System.out.println("联系人条数=" + users.size());
        for (MyContact user : users) {
            System.out.println(user);
        }

        return users;
    }

}
