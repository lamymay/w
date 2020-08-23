//            //获取联系人ID
//            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//            //获取联系人的名字
//            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

        DELETE FROM   t_app_contact
        WHERE  user_id = '#{userId}'






1=并集 insert data by received data and analysis the common
2=仅保留server端的
3=仅保留client端的

UNION_SET(1, "取并集"),
ONLY_ACCEPT_SERVER(2, "仅取服务器的数据"),
ONLY_ACCEPT_CLIENT(3, "仅取客户端数据"),

Android EditText 赋值与取值
   //取值
   String strSmsPhone=m_txtSmsPhone.getText().toString();
   //赋值
   m_txtSmsPhone.setText("你好");


$(lsof -i:8001 |awk '{print $2}' | tail -n 2)
#cd  /data/soft/jar/

nohup java -jar -Xmx128m  /data/project/java/server.jar --spring.profiles.active=local >/data/project/java/server.out 2>&1 &
tail -100f /data/project/java/server.out 