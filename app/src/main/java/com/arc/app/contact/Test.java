//    /*查询*/
//    private void select() {
//            ContentResolver cr = getContentResolver();
//            /*第一个参数为Uri，第二个参数为查询哪些列，第三个参数为查询条件,第五个参数为排序方式*/
//            /*查询id和姓名*/
//            Cursor c = cr.query(Contacts.CONTENT_URI, new String[]{Contacts._ID, Contacts.DISPLAY_NAME}, null, null, null);
//            if (c != null) {
//            while (c.moveToNext()) {
//            int id = c.getInt(c.getColumnIndex("_ID"));
//            String name = c.getString(c.getColumnIndex("DISPLAY_NAME"));
//            Log.i("MainActivity", "_ID " + id);
//            Log.i("MainActivity", "DISPLAY_NAME " + name);
//            /*根据id查询联系人的电话号码*/
//            Cursor everyName = cr.query(Phone.CONTENT_URI, new String[]{Phone.NUMBER, Phone.TYPE}, Phone.CONTACT_ID + "=" + id, null, null);
//            if (everyName != null) {
//            while (everyName.moveToNext()) {
//            /*查询电话号码类型，type为0表示座机电话，type为1表示移动电话*/
//            int type = everyName.getInt(everyName.getColumnIndex(Phone.TYPE));
//            if (type == Phone.TYPE_HOME) {
//            Log.i("MainActivity", "座机电话" + everyName.getString(everyName.getColumnIndex(Phone.NUMBER)));
//            } else if (type == Phone.TYPE_MOBILE) {
//            Log.i("MainActivity", "移动电话" + everyName.getString(everyName.getColumnIndex(Phone.NUMBER)));
//            }
//            }
//            everyName.close();
//            }
//            /*根据id查询联系人的邮箱地址*/
//            Cursor everyEmail = cr.query(Email.CONTENT_URI, new String[]{Email.DATA, Email.TYPE}, Email.CONTACT_ID + "=" + id, null, null);
//            if (everyEmail != null) {
//            while (everyEmail.moveToNext()) {
//            int type = everyEmail.getInt(everyEmail.getColumnIndex(Email.TYPE));
//            if (type == Email.TYPE_WORK) {
//            Log.i("MainActivity", "工作邮箱" + everyEmail.getString(everyEmail.getColumnIndex(Email.DATA)));
//            }
//            }
//            everyEmail.close();
//            }
//            }
//            c.close();
//            }
//            }
