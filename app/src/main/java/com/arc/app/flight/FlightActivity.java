//package com.arc.app.flight;
//
//import android.app.Activity;
//import android.hardware.Camera;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import com.arc.app.contact.R;
//
//
///**
// * @author 叶超
// * @since 2019/4/26 17:48
// */
//public class FlightActivity extends Activity {
//
//    private Button button;
//    Camera camera = Camera.open();
//    boolean kaiguanzhuangtai;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
////        button = (Button) findViewById(R.id.button2);
//        button.setOnClickListener(new SouDianTongView());
//
//    }
//
//    class SouDianTongView implements View.OnClickListener {
//        //手电筒的开关控制代码，固定格式，基本上没什么可修改的。
//        @Override
//        public void onClick(View v) {
//            if (!kaiguanzhuangtai) {
//                Camera.Parameters myParameters = camera.getParameters();
//                myParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//                camera.setParameters(myParameters);
//                kaiguanzhuangtai = true;
//            } else {
//                Camera.Parameters myParameters = camera.getParameters();
//                myParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//                camera.setParameters(myParameters);
//                kaiguanzhuangtai = false;
//            }
//        }
//    }
//
//}
