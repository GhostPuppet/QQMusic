package com.example.androiddemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoginActivity extends AppCompatActivity{
    EditText uname;
    EditText upwd;
    CheckBox rememberpwd;
    CheckBox showpwd;
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setActionBar();
        init();
    }
    public void init(){
        uname= (EditText) findViewById(R.id.uname);
        upwd= (EditText) findViewById(R.id.upassword);
        rememberpwd= (CheckBox) findViewById(R.id.rpassword);
        showpwd= (CheckBox) findViewById(R.id.showpassword);
        login= (Button) findViewById(R.id.login_btn);
        register= (Button) findViewById(R.id.register_btn);

        SharedPreferences loginStatus = getSharedPreferences("loginStatus", MODE_PRIVATE);
        String temp_uname=loginStatus.getString("uname","");
        String temp_upwd=loginStatus.getString("upwd","");
        int temp_status=loginStatus.getInt("status",0);
        uname.setText(temp_uname);
        upwd.setText(temp_upwd);
        if(temp_status==1)
            rememberpwd.setChecked(true);
        else
            rememberpwd.setChecked(false);

        showpwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    upwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    upwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_uname = uname.getText().toString();
                String str_upwd = upwd.getText().toString();

                if(str_uname.length()==0){
                    Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                return;}
                if(str_upwd.length()==0){
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                return;}
                if(str_upwd.length()<6){
                    Toast.makeText(LoginActivity.this,"密码长度不能低于6位",Toast.LENGTH_SHORT).show();
                return;}
                if(str_uname.equals("admin")&&str_upwd.equals("123456")){
                    //记住密码功能,数据存储
                    SharedPreferences loginStatus = getSharedPreferences("loginStatus", MODE_PRIVATE);//得到一个共享的引用
                    SharedPreferences.Editor edit = loginStatus.edit();
                    if(rememberpwd.isChecked()){
                        edit.putString("uname",str_uname);
                        edit.putString("upwd",str_upwd);
                        edit.putInt("status",1);
                        edit.putInt("login_status",1);
                    }else{
                        edit.clear();
                    }
                    edit.commit();//提交
                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(LoginActivity.this,"登录失败，请重试！",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" QQ音乐");
        actionBar.setSubtitle(" 登录");
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setBackgroundDrawable(getDrawable(R.mipmap.title_bg));
        actionBar.setIcon(getDrawable(R.mipmap.qq_logo));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getMenuInflater().inflate(R.menu.login_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //后续完成
        if(item.getItemId()==R.id.menu_exit){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("是否真的要退出系统？");
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.qq_logo);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        if(item.getItemId()==R.id.menu_register){
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
        return true;
    }
}

