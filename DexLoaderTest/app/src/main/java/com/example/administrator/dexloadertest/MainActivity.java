package com.example.administrator.dexloadertest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;

    Class mClz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadClass();
            }
        });


        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Class clz = mClz;
                    Method method = clz.getMethod("goodNight");
                    Object instance = clz.newInstance();
                    method.invoke(instance);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    String PatchFileName = "classes.dex";

    private void loadClass (){
        try{
            //报备到其他路径
            File dexInternalStoragePath = new File(getDir("dex", Context.MODE_PRIVATE),
                    PatchFileName);
            System.out.println("-- 拷贝到的路径 -->>>"+dexInternalStoragePath.getAbsolutePath());
            BufferedInputStream bis = null;
            OutputStream dexWriter = null;

            final int BUF_SIZE = 8 * 1024;
            try {
                bis = new BufferedInputStream(getAssets().open(PatchFileName));
                dexWriter = new BufferedOutputStream(
                        new FileOutputStream(dexInternalStoragePath));
                byte[] buf = new byte[BUF_SIZE];
                int len;
                while((len = bis.read(buf, 0, BUF_SIZE)) > 0) {
                    dexWriter.write(buf, 0, len);
                }
                dexWriter.close();
                bis.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            PathClassLoader pathClassLoader = new PathClassLoader(dexInternalStoragePath.getAbsolutePath(), getClassLoader());
            Class clz = pathClassLoader.loadClass("Hello");
            Method method = clz.getMethod("sayHello");
            method.invoke(null);
            mClz = clz;
            System.out.println("------ loaded Class ------->>>"+clz.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
