package com.arc.util;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.arc.model.AppContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人 CRUD 服务
 *
 * @author z
 */
public class ContactTool {


    /**
     * 获取全部联系人
     *
     * @param context
     * @return
     */
    public static List<AppContact> listAllContacts(Context context) {
        Map<String, AppContact> map = listAllMapContacts(context);
        if (map == null) {
            return null;
        }

        List<AppContact> result = new ArrayList<>(map.size());
        for (AppContact value : map.values()) {
            result.add(value);
        }
        return result;
    }


    public static Map<String, AppContact> listAllMapContacts(Context context) {
        Map<String, AppContact> map = new HashMap<>();
        Map<String, AppContact> nameMap = new HashMap<>();
        Cursor cursor = null;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//content://com.android.contacts/data/phones
        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            while (cursor.moveToNext()) {
                String idSting = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String display_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String has_phone_number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                String phonetic_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHONETIC_NAME));
                // String content_item_type = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTENT_ITEM_TYPE));
                //String count = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._COUNT));
                String name_raw_contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
                String contact_status = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS));


                System.out.println("########################" + System.currentTimeMillis() + "##############################");
                System.out.println("id=" + idSting);
                System.out.println("display_name=" + display_name);
                System.out.println("has_phone_number=" + has_phone_number);
                System.out.println("phonetic_name=" + phonetic_name);
                System.out.println("name_raw_contact_id=" + name_raw_contact_id);
                System.out.println("contact_status=" + contact_status);
                System.out.println("######################################################");

                phoneNumber = phoneNumber.replaceAll("-", "");
                phoneNumber = phoneNumber.replaceAll(" ", "");
                //todo 组装联系人数据
                AppContact user = new AppContact();
                Integer id = Integer.valueOf(idSting);
                user.setContactId(id);
                user.setDisplayName(display_name);
                user.setCellphone(phoneNumber);
                user.setHasPhoneNumber(has_phone_number);

                nameMap.put(user.getDisplayName(), user);
                map.put("" + id, user);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        System.out.println(map.size());
        System.out.println(nameMap.size());
        System.out.println(nameMap);

        return map;
    }

    /**
     * 根据联系人名称获取该联系人的全部数据
     * 包含：
     * 手机号
     *
     * @param displayName
     * @param context
     * @return
     */
    public static AppContact getContactByDisplayName(String displayName, Context context) {
        if (displayName == null) {
            return null;
        }

        AppContact resultBean = null;
        //1 get one by name//        if (name != null && name.trim().length() != 0) {

        //全部数据集
        Cursor cursor = null;
        //全部联系人 暂时存储起来
        Map<String, AppContact> map = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();

        try {
            //调用查询，获取全部联系人
            cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            if (cursor == null) {
                throw new RuntimeException("查询联系人为空");
            }

            while (cursor != null && cursor.moveToNext()) {
                //获取联系人 id   key= _id
                String idSting = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Integer id = Integer.valueOf(idSting);
                String display_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phonetic_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHONETIC_NAME));
                String content_item_type = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTENT_ITEM_TYPE));
                String name_raw_contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
                String contact_status = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS));
                String count = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._COUNT));
                String has_phone_number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

                System.out.println(columnIndex);
                temp.put("" + id, id);
                temp.put(display_name, display_name);
                temp.put(count, count);
                temp.put(content_item_type, content_item_type);
                temp.put(name_raw_contact_id, name_raw_contact_id);
                temp.put(contact_status, contact_status);
                temp.put(phonetic_name, phonetic_name);
                temp.put(has_phone_number, has_phone_number);
                //todo 组装数据
                AppContact user = new AppContact();
                user.setContactId(id);
                user.setDisplayName(display_name);
                System.out.println("联系人 id=" + id + user);
                map.put("" + id, user);
            }

        } catch (Exception e) {
            // todo           Log.;
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        System.out.println("根据名称displayName=" + displayName + ",查询出的电话号码是\n" + resultBean);
        return resultBean;
    }

    /**
     * 根据电话号码查询姓名
     *
     * @param phoneNumber
     * @return
     */
    private String getContactNameByPhoneNumber(final String phoneNumber, Context context) {
        //根据电话号码查询姓名（在一个电话打过来时，如果此电话在通讯录中，则显示姓名）
        //uri=  content://com.android.contacts/data/phones/filter/#
        final Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + phoneNumber);
        ContentResolver resolver = context.getContentResolver();
        //从raw_contact表中返回display_name
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data.DISPLAY_NAME}, null, null, null);
        StringBuffer buffer = new StringBuffer("");
        if (cursor.moveToFirst()) {
            buffer.append(cursor.getString(0));
            //todo Log.i("Contacts", "name=" + cursor.getString(0));
        }
        //输出显示
        Toast.makeText(context, "根据电话号码查询姓名", Toast.LENGTH_SHORT).show();
        return buffer.toString();
    }

    /**
     * 查询指定联系人的所有号码
     *
     * @param name
     * @param context
     * @return
     */
    public static AppContact getContactByDisplayNameWithAllPhone(String name, Context context) {
        if (name == null) {
            return null;
        }

        AppContact appContact2 = new AppContact();
        List<String> phoneNumbers = new ArrayList<>();
        Cursor cursor = null;
        Cursor phoneCursor = null;
        try {
            //使用ContentResolver查找联系人数据 content://com.android.contacts/contacts
            System.out.println(ContactsContract.Contacts.CONTENT_URI.toString());
            cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            //遍历查询结果，找到所需号码
            while (cursor.moveToNext()) {
                //获取联系人ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //获取联系人的名字
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                if (name.equalsIgnoreCase(contactName)) {
                    // 查看联系人有多少个号码，如果没有号码，返回0
                    int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    if (phoneCount > 0) {
                        // 获得联系人的电话号码列表
                        Uri contactDataUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        String sqlSelectPhoneCursor = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId;
                        phoneCursor = context.getContentResolver().query(contactDataUri, null, sqlSelectPhoneCursor, null, null);
                        if (phoneCursor.moveToFirst()) {
                            do {
                                //遍历所有的联系人下面所有的电话号码
                                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                phoneNumbers.add(phoneNumber);
                            } while (phoneCursor.moveToNext());
                        }
                    }
                    //使用ContentResolver查找联系人的电话号码
                    //                Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    //                if (phone.moveToNext()) {
                    //                    String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //                    phoneNumbers.add(phoneNumber);
                    //                }
                }
            }
            cursor.close();
        } catch (Exception e) {
            // todo           Log.;
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (phoneCursor != null) {
                phoneCursor.close();
                phoneCursor = null;
            }
        }
        appContact2.setPhones(phoneNumbers);
        return appContact2;
    }


    /**
     * update
     *
     * @param context
     * @param user
     * @return
     */
    public static AppContact update(Context context, AppContact user) {
        // 1、打开一个页面要求用户输入
        // 2、输入参数后，根据变化的数据来更新
        // 3、显示结果


        //数据处理
        if (user == null) {
            System.out.println(" ERROR throw new RuntimeException(\"null update\");");
            throw new RuntimeException("null update");
        }
        int id = user.getId();
        String phone = user.getCellphone();

        Uri uri = Uri.parse("content://com.android.contacts/data");//对data表的所有数据操作
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("data1", phone);
        resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/phone_v2", id + ""});

        //成功标志
        user.setState(1);
        return user;
    }

    @Deprecated
    public static ArrayList<AppContact> getAllContacts(Context context) {
        ArrayList<AppContact> contacts = new ArrayList<AppContact>();

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //新建一个联系人实例
            AppContact temp = new AppContact();
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            temp.setName(name);

            //获取联系人电话号码
            Cursor phoneCursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null,
                    null);
            while (phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
                temp.setCellphone(phone);
            }

            //获取联系人备注信息
            Cursor noteCursor = context.getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Nickname.NAME},
                    ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE + "'",
                    new String[]{contactId},
                    null);
            if (noteCursor.moveToFirst()) {
                do {
                    String note = noteCursor.getString(noteCursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                    temp.note = note;
                    Log.i("note:", note);
                } while (noteCursor.moveToNext());
            }
            contacts.add(temp);
            //记得要把cursor给close掉
            phoneCursor.close();
            noteCursor.close();
        }
        cursor.close();
        return contacts;
    }


    /**
     * 测试
     * insert
     * 注意：对某个联系人插入姓名、电话等记录时必须要插入Data.MIMETYPE（或者是"mimetype"）属性,而不是插入"mimetype_id"!
     * 比如：values.put(Data.MIMETYPE,"vnd.android.cursor.item/phone_v2")
     */
    public static void saveOne(Context context, TextView outputText) {
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
        Toast.makeText(context, "saveOne", Toast.LENGTH_SHORT).show();
        outputText.setText("insert");
        System.out.println("########################saveOne######################");
    }

    /**
     * 测试批量保存by写死数据
     *
     * @param context
     * @param outputText
     * @throws Exception
     */
    public static void saveBatchForTest(Context context, TextView outputText) throws Exception {
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
        Toast.makeText(context, "saveBatch", Toast.LENGTH_SHORT).show();
        outputText.setText("saveBatch");
        System.out.println("##################### saveBatch #########################");
    }


    /**
     * 批量添加数据
     * 核心代码：
     * <p>
     * (1)ContentProviderOperation operation = ContentProviderOperation.newInsert(uri).withValue("key","value").build();
     * <p>
     * (2)resolver.applyBatch("authorities",operations);//批量提交
     */
    public static void saveBatch(Context context, TextView outputText, List<AppContact> contacts) throws Exception {
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
        Toast.makeText(context, "saveBatch", Toast.LENGTH_SHORT).show();
        outputText.setText("saveBatch");
        System.out.println("##################### saveBatch #########################");
    }

    /**
     * delete
     * 核心思想：
     * (1)先在raw_contacts表根据姓名(此处的姓名为name记录的data2的数据而不是data1的数据)查出id；
     * (2)在data表中只要raw_contact_id匹配的都删除；
     * 复制代码
     */
    public static void delete(Context context) throws Exception {
        System.out.println("##################### DELETE START #########################");
        long t1 = System.currentTimeMillis();
        Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
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



}
