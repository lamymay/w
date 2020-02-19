package com.arc.w.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.arc.w.model.MyContact;

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
    public static List<MyContact> listAllContacts(Context context) {
        Map<String, MyContact> map = getMapByListAllContacts(context);
        if (map == null) {
            return null;
        }
        List<MyContact> result = new ArrayList<>(map.size());
        for (MyContact value : map.values()) {
            result.add(value);
        }
        return result;
    }

    public static Map<String, MyContact> getMapByListAllContacts(Context context) {
        Map<String, MyContact> map = new HashMap<>();
        Map<String, MyContact> nameMap = new HashMap<>();
        Cursor cursor = null;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//content://com.android.contacts/data/phones
        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String display_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String has_phone_number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                String phonetic_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHONETIC_NAME));
                // String content_item_type = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTENT_ITEM_TYPE));
                //String count = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._COUNT));
                String name_raw_contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
                String contact_status = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS));


                System.out.println("########################" + System.currentTimeMillis() + "##############################");
                System.out.println("id=" + id);
                System.out.println("display_name=" + display_name);
                System.out.println("has_phone_number=" + has_phone_number);
                System.out.println("phonetic_name=" + phonetic_name);
                System.out.println("name_raw_contact_id=" + name_raw_contact_id);
                System.out.println("contact_status=" + contact_status);
                System.out.println("######################################################");

                phoneNumber = phoneNumber.replaceAll("-", "");
                phoneNumber = phoneNumber.replaceAll(" ", "");
                //todo 组装联系人数据
                MyContact user = new MyContact();
                user.setId(id);
                user.setDisplayName(display_name);
                user.setPhone(phoneNumber);
                user.setHasPhoneNumber(has_phone_number);

                nameMap.put(user.getDisplayName(), user);
                map.put(id, user);
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
     * @param name
     * @param context
     * @return
     */
    public static MyContact getContactByDisplayName(String name, Context context) {
        if (name == null) {
            return null;
        }

        MyContact resultBean = null;

        //全部数据集
        Cursor cursor = null;
        //全部联系人 暂时存储起来
        Map<String, MyContact> map = new HashMap<>();
        Map<String, Object> temp = new HashMap<>();

        try {
            //调用查询，获取全部联系人
            cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            if (cursor == null) {
                throw new RuntimeException("查询联系人为空");
            }

            while (cursor != null && cursor.moveToNext()) {
                //获取联系人 id   key= _id
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String display_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phonetic_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHONETIC_NAME));
                String content_item_type = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTENT_ITEM_TYPE));
                String name_raw_contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
                String contact_status = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS));
                String count = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._COUNT));
                String has_phone_number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

                System.out.println(columnIndex);
                temp.put(id, id);
                temp.put(display_name, display_name);
                temp.put(count, count);
                temp.put(content_item_type, content_item_type);
                temp.put(name_raw_contact_id, name_raw_contact_id);
                temp.put(contact_status, contact_status);
                temp.put(phonetic_name, phonetic_name);
                temp.put(has_phone_number, has_phone_number);
                //todo 组装数据
                MyContact user = new MyContact();
                user.setId(id);
                user.setDisplayName(display_name);
                System.out.println("联系人 id=" + id + user);
                map.put(id, user);
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
        return resultBean;
    }


    /**
     * 查询指定联系人的所有号码
     *
     * @param name
     * @param context
     * @return
     */
    public static List<String> queryByNumber(String name, Context context) {
        if (name == null) {
            return null;
        }
        List<String> numbers = new ArrayList<>();
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
                        System.out.println("获得联系人的电话号码列表URI=" + contactDataUri);
                        System.out.println("获得联系人的电话号码列表SQL=" + sqlSelectPhoneCursor);
                        phoneCursor = context.getContentResolver().query(contactDataUri, null, sqlSelectPhoneCursor, null, null);
                        if (phoneCursor.moveToFirst()) {
                            do {
                                //遍历所有的联系人下面所有的电话号码
                                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                numbers.add(phoneNumber);
                            } while (phoneCursor.moveToNext());
                        }
                    }
                    //使用ContentResolver查找联系人的电话号码
                    //                Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    //                if (phone.moveToNext()) {
                    //                    String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //                    numbers.add(phoneNumber);
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
        return numbers;
    }


}
