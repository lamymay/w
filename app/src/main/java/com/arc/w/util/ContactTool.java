package com.arc.w.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.arc.w.model.AppContact;

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
        Map<String, AppContact> map = getMapByListAllContacts(context);
        if (map == null) {
            return null;
        }
        List<AppContact> result = new ArrayList<>(map.size());
        for (AppContact value : map.values()) {
            result.add(value);
        }
        return result;
    }

    public static Map<String, AppContact> getMapByListAllContacts(Context context) {
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
     * @param name
     * @param context
     * @return
     */
    public static AppContact getContactByDisplayName(String name, Context context) {
        if (name == null) {
            return null;
        }

        AppContact resultBean = null;

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

    /**
     * 输出显示一个bean
     */
    public static AppContact getOne(Context context, AppContact user) {
        String name = user.getDisplayName();
        //查询并组装
        AppContact localContact = null;
        if (name != null && name.trim().length() != 0) {
            localContact = ContactTool.getContactByDisplayName(name, context);
            System.out.println("##############################################");
            System.out.println("根据名称=" + name + ",查询出的电话号码是\n" + localContact);
            System.out.println("##############################################");

        }
        //输出
        return localContact;
    }


    /**
     * update
     *
     * @param context
     * @param user
     * @return
     */
    public static AppContact update(Context context, AppContact user) {
        //数据处理
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
}
