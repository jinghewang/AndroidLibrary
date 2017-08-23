package com.hbdworld.androidlibrary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.encoding.EncodingHandler;

public class MainActivity extends AppCompatActivity {


    //客户主扫

    private EditText resultTv;

    private EditText authCode;

    private EditText payCode;

    private WebView wb;

    private RadioGroup group_temo;
    private RadioButton checkRadioButton;

    private ImageView QrCode;

    private static final String tag=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //
        authCode = (EditText)findViewById(R.id.authCode);
        payCode = (EditText) findViewById(R.id.payCode);
        QrCode = (ImageView) findViewById(R.id.QrCode);
    }

    /**
     * 点击事件
     * @param view
     */
    public void eventScan(View view) {
        //扫描成功后调用
        Bundle bundle = new Bundle();
        Intent intent = new Intent(MainActivity.this,ScanActivity.class);
        startActivityForResult(intent,1,bundle);
    }

    /**
     * 生成二维码
     * @param view
     */
    public void eventCreate(View view) {
        //扫描成功后调用
        try {
            //获取输入的文本信息
            String str = payCode.getText().toString().trim();
            if(str != null && !"".equals(str.trim())){
                //根据输入的文本生成对应的二维码并且显示出来
                Bitmap mBitmap = EncodingHandler.createQRCode(str.toString(), 800);
                if(mBitmap != null){
                    Toast.makeText(this,"二维码生成成功！",Toast.LENGTH_SHORT).show();
                    QrCode.setImageBitmap(mBitmap);
                }
            }else{
                Toast.makeText(this,"文本信息不能为空！",Toast.LENGTH_SHORT).show();
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            Bundle bundle = data.getExtras();
            String s = bundle.getString("result");
            authCode.setText(s);
        }
    }
}
