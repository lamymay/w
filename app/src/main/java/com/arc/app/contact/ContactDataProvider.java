package com.arc.app.contact;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;
import com.arc.app.contact.activity.ContactActivity;
import com.arc.app.contact.model.AppContact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 联系人数据获取服务
 *
 * @author may
 * @date 2020/08/23
 */
public class ContactDataProvider {

    /**
     * 提供测试数据
     *
     * @return 返回联系人数据列表
     */
    public List<AppContact> getContactList() {
        ArrayList<AppContact> contacts = new ArrayList<>(16);
        AppContact contact1 = new AppContact();
        contact1.setDisplayName("王五");
        contact1.setLastname("王");
        contact1.setGivenName("五");

        List<String> phones = Arrays.asList("18642124", "021-1564");
        contact1.setPhones(phones);
        contact1.setEmail("lamymay@outlook.com");


        AppContact contact2 = new AppContact();
        contact2.setDisplayName("刘亦菲");
        contact2.setLastname("刘");
        contact2.setGivenName("亦菲");
        List<String> phones2 = Arrays.asList("177421277", "021-15645454");
        contact2.setEmail("235615645@qq.com");
        contact2.setPhones(phones2);

        contacts.add(contact1);
        contacts.add(contact2);
        return contacts;
    }

    public boolean save(AppContact appContact, ContactActivity context) {
        try {
            ContentResolver resolver = context.getContentResolver();

            // 创建一个空的ContentValues
            ContentValues contentValues = new ContentValues();

            // 向RawContacts.CONTENT_URI空值插入，
            // 先获取Android系统返回的rawContactId
            // 后面要基于此id插入值
            Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
            long contact_id = ContentUris.parseId(rawContactUri);


            //实际上是四个属性的
            //1 raw_contact_id 申请的
            //2 mimetype  固定值
            //3 类型 固定值
            //4 data1 数据本身
            //-------------------------------------------------------------------------
            contentValues.clear();

            // 内容类型             contentValues.put("mimetype", "vnd.android.cursor.item/name");
            contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

            // 联系人名字
            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, appContact.getGivenName());
            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, appContact.getLastname());
//            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, appContact.getDisplayName());

            //contentValues.put("raw_contact_id", contact_id);
            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);

            // 向联系人URI添加联系人名字
            resolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
            contentValues.clear();


            //-------------------------------------------------------------------------
            contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            // 电话类型
            contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);

            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);

            // 联系人的电话号码
             contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, appContact.getPhones().get(0));

//            List<String> phones = appContact.getPhones();
//            if (phones != null && phones.size() > 0) {
//                int len = phones.size();
//                for (int i = 1; i <= len; i++) {
//                    contentValues.put("data" + i, phones.get(i - 1));   //手机号码
//                }
//            }

            // 向联系人电话号码URI添加电话号码
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
            contentValues.clear();


            //---------------- EMAIL
            contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
            // 电子邮件的类型
            contentValues.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);

            //email  data.put("mimetype", "vnd.android.cursor.item/email_v2");


            // 联系人的Email地址
            contentValues.put(ContactsContract.CommonDataKinds.Email.ADDRESS, appContact.getEmail());

            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);

            // 向联系人Email URI添加Email数据
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);

            Toast.makeText(context, "save"+appContact.toString(), Toast.LENGTH_SHORT).show();
            System.out.println("########################saveOne######################");

            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

    }

    //获取手机号
    public static List<AppContact> listAllNumber(Context context) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//content://com.android.contacts/data/phones
        System.out.println(" 获取联系人电话 ContactsContract.CommonDataKinds.Phone.CONTENT_URI\n" + uri);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        String phoneNumber = null;
        String name = null;

        LinkedList<AppContact> users = new LinkedList<>();
        while (cursor.moveToNext()) {
            System.out.println(cursor);

            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            //组装联系人数据
            AppContact user = new AppContact(name, phoneNumber);
            users.add(user);
        }

        //测试  print user bean
        System.out.println("联系人条数=" + users.size());
        for (AppContact user : users) {
            System.out.println(user);
        }

        return users;
    }
}

/*

dataX 对应的 类型
            public static final int TYPE_HOME = 1;
            public static final int TYPE_MOBILE = 2;
            public static final int TYPE_WORK = 3;
            public static final int TYPE_FAX_WORK = 4;
            public static final int TYPE_FAX_HOME = 5;
            public static final int TYPE_PAGER = 6;
            public static final int TYPE_OTHER = 7;
            public static final int TYPE_CALLBACK = 8;
            public static final int TYPE_CAR = 9;
            public static final int TYPE_COMPANY_MAIN = 10;
            public static final int TYPE_ISDN = 11;
            public static final int TYPE_MAIN = 12;
            public static final int TYPE_OTHER_FAX = 13;
            public static final int TYPE_RADIO = 14;
            public static final int TYPE_TELEX = 15;
            public static final int TYPE_TTY_TDD = 16;
            public static final int TYPE_WORK_MOBILE = 17;
            public static final int TYPE_WORK_PAGER = 18;
            public static final int TYPE_ASSISTANT = 19;
            public static final int TYPE_MMS = 20;
**/
