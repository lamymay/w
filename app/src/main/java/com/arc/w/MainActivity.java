package com.arc.w;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.arc.w.service.UserService;
import com.arc.w.util.UserListViewAdapter;

/**
 * @author arc
 */
public class MainActivity extends AppCompatActivity {

    private UserListViewAdapter adapter;
    private ListView userListView;

    /**
     * 程序入口
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载渲染主图
        setContentView(R.layout.activity_main);

        //测试方法
        //UserService.listAllNumber(this);
        userListView = (ListView) findViewById(R.id.listView1);
        BaseAdapter adapter=      new UserListViewAdapter(UserService.listAllNumber(this), this);

        userListView.setAdapter(adapter);


    }

}
