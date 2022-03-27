package com.example.proxyhook;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 动态代理AOP
 * 1.定义业务功能通用接口
 * 2.创建接口实现类实现具体业务逻辑A
 * 3.创建A的动态代理对象，并将A传入
 * 4.在动态代理对象的InvocationHandler中实现AOP切面功能
 * done
 */
public class OtherActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_other);

    }

}