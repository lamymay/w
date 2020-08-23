package com.arc.test;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author 叶超
 * @since 2019/4/26 17:48
 */
public class FlashActivity extends Activity implements View.OnClickListener {

    private EditText lightInput;
    private Button lightBtn;
    private CameraManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

       lightInput = findViewById(R.id.lightInput);
        lightBtn = findViewById(R.id.lightBtn);
//        button.setOnClickListener(new SouDianTongView());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.b1) {
            Editable text = lightInput.getText();
            Toast.makeText(this, "=-=  \n" + text.toString(), Toast.LENGTH_SHORT).show();

//            int progress = processBar.getProgress();
//            progress += 10;
//            if (progress > 100) {
//                progress = 0;
//            }
        }
    }


    public void flashLight(boolean flag) {
        if (flag) {
            //open
            try {
                screenLight();
                if (manager == null) {
                    manager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
                }
                manager.setTorchMode("0", true);// "0"是主闪光灯
            } catch (Exception e) {
            }
        } else {
            //close
            try {
//                if (manager == null) {
//                    return;
//                }
                if (manager == null) {
                    manager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
                }
                manager.setTorchMode("0", false);
            } catch (Exception e) {
            }


        }
    }

    private void screenLight() {
        Window localWindow = this.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        params.screenBrightness = 1.0f;
        localWindow.setAttributes(params);
    }

//    class SouDianTongView implements View.OnClickListener {
//        //手电筒的开关控制代码，固定格式，基本上没什么可修改的。
//        @Override
//        public void onClick(View v) {
//
//
//        }
//    }
}
