package com.example.androiddemo;


import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText uname;
    EditText upwd;
    EditText reupwd;
    EditText uemail;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setActionBar();
        init();
    }

    public void init() {
        uname = (EditText) findViewById(R.id.uname);
        upwd = (EditText) findViewById(R.id.upassword);
        reupwd = (EditText) findViewById(R.id.reupwd);
        uemail = (EditText) findViewById(R.id.uemail);
        finish = (Button) findViewById(R.id.finish_btn);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_uname = uname.getText().toString();
                String str_upwd = upwd.getText().toString();
                String str_reupwd = reupwd.getText().toString();
                String str_uemail = uemail.getText().toString();

                if (str_uname.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (str_upwd.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (str_upwd.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "密码长度不能低于6位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (str_upwd != str_reupwd) {
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (str_uemail.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "邮箱地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //记住信息功能,数据存储
                SharedPreferences RegisterStatus = getSharedPreferences("RegisterStatus", MODE_PRIVATE);//得到一个共享的引用
                SharedPreferences.Editor edit = RegisterStatus.edit();
                edit.putString("uname", str_uname);
                edit.putString("upwd", str_upwd);
                edit.putString("uemail",str_uemail);
                edit.commit();//提交
                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
        }
    });
}

    public void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("QQ音乐");
        actionBar.setSubtitle(" 注册");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setBackgroundDrawable(getDrawable(R.mipmap.title_bg));
        //actionBar.setIcon(getDrawable(R.mipmap.qq_logo));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //后续完成
        if (item.getItemId() == android.R.id.home) {//系统自带
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }
}

