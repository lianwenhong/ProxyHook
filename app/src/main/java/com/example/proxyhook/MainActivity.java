package com.example.proxyhook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.id_btn_jump).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        find(this.getClass());
        Intent intent = new Intent(this, OtherActivity.class);
        /**
         * 照理说Activity的startActivity和Context的startActivity的调用栈是不一样的
         * Context.startActivity:执行的是ActivityThread中的mInstrumentation这个变量的execStartActivity方法
         * Activity.startActivity:执行的是Activity自身的mInstrumentation的execStartActivity方法
         *
         * 但是为什么这里调用的是Activity.startActivity也能hook住呢？
         * 原因在于我们的hook流程是在Application中走的，这时候比Activity的启动流程早，
         * 而Activity.mInstrumentation变量是在attach中传过来的，所以其实这个变量已经被我们改成我们自己的HookInstrumentation类了
         * 通过find()方法可以证明
         */
        startActivity(intent);
    }

    public void find(Class clz) {
        try {
            Field field = clz.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) field.get(this);
            Log.e("lianwenhong", " >>> instrumentation:" + instrumentation);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            if (clz.getSuperclass() == Object.class) {
                return;
            }
            find(clz.getSuperclass());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}