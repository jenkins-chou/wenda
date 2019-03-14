package com.w.wenda;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.jaeger.library.StatusBarUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.base.MyBaseActivity;
import com.w.wenda.pojo.CommonData;
import com.w.wenda.util.AccountValidatorUtil;
import com.w.wenda.util.CodeUtils;
import com.w.wenda.util.MyToastUtil;
import com.w.wenda.util.UserClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserRegist extends MyBaseActivity {
    @OnClick(R.id.back)
    void back(){
        finish();
    }
    EditText username, password, nickname, mobile,yzms;
    ImageView head;//头像
    String names = "";
    String head_url = "";
    Button reg;
    Context con = UserRegist.this;
    ImageView yzm;

    @Override
    protected void initUI() {
        setContentView(R.layout.user_regist);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.black),0);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        nickname = (EditText) findViewById(R.id.nickname);
        mobile = (EditText) findViewById(R.id.mobile);
        reg = (Button) findViewById(R.id.reg);
        head = (ImageView) findViewById(R.id.head);
        yzm=(ImageView)findViewById(R.id.yzm);
        yzms=(EditText)findViewById(R.id.yzms);
        yzm.setImageBitmap(CodeUtils.getInstance().createBitmap());
        Log.i("dd",CodeUtils.code);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(UserRegist.this).setTitle("").setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleDateFormat df = new SimpleDateFormat(
                                "MMddHHmmssSSSS");
                        names = df.format(new Date());
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment
                                        .getExternalStorageDirectory(), names
                                        + ".jpg")));
                        startActivityForResult(intent, 2);
                    }
                }).setNeutralButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 1);
                    }
                }).show();

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AccountValidatorUtil.isMobile(mobile.getText().toString())) {
                    if(yzms.getText().toString().equals(CodeUtils.code)){
                        RequestParams ps = new RequestParams();
                        ps.add("username", username.getText().toString());
                        ps.add("pass", password.getText().toString());
                        ps.add("name", nickname.getText().toString());
                        ps.add("sjh", mobile.getText().toString());
                        ps.add("head", head_url);

                        UserClient.post("user/add", ps, new AsyncHttpResponseHandler() {


                            @Override
                            public void onSuccess(String content) {
                                super.onSuccess(content);
                                CommonData data = JSON.parseObject(content, CommonData.class);
                                if (data.getCode().equals("200")) {
                                    MyToastUtil.ShowToast(con, "注册成功");
                                    finish();
                                } else {
                                    MyToastUtil.ShowToast(con, data.getMsg());
                                }
                            }


                        });
                    }else{
                        MyToastUtil.ShowToast(UserRegist.this,"验证码不正确");
                    }

                }else{
                    MyToastUtil.ShowToast(UserRegist.this,"手机号不正确");
                }

            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (data != null) {
                    Uri uri = data.getData();
                    cropImage(uri, 500, 500, 4);
                }
                break;
            case 2:
                try {
                    if (resultCode != 0) {
                        Uri uris = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), names + ".jpg"));
                        cropImage(uris, 500, 500, 4);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;
            case 4:
                if (data != null) {
                    Bitmap photo = null;
                    Uri photoUri = data.getData();
                    if (photoUri != null) {
                        photo = BitmapFactory.decodeFile(photoUri.getPath());
                    }
                    if (photo == null) {
                        Bundle extra = data.getExtras();
                        if (extra != null) {
                            photo = (Bitmap) extra.get("data");
                        }
                    }
                    File f = new File(Environment.getExternalStorageDirectory(),
                            "pic.jpg");
                    if (f.exists()) {
                        f.delete();
                    }
                    try {
                        FileOutputStream out = new FileOutputStream(f);
                        photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                        out.flush();
                        out.close();
                        Log.i("image", "已经保存");
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    FileInputStream fis;
                    try {
                        fis = new FileInputStream(
                                Environment.getExternalStorageDirectory()
                                        + "/pic.jpg");

                        head.setImageBitmap(BitmapFactory.decodeStream(fis));
                        String picPath = Environment.getExternalStorageDirectory()
                                + "/pic.jpg";
                        RequestParams ps = new RequestParams();
                        ps.put("head", new File(picPath));
                        UserClient.post("common/mobileupload", ps, new AsyncHttpResponseHandler() {

                            @Override
                            public void onSuccess(String content) {
                                super.onSuccess(content);
                                CommonData msg = JSON.parseObject(content, CommonData.class);
                                if (msg.getCode().equals("200")) {
                                    head_url = msg.getData();
                                } else {
                                    MyToastUtil.ShowToast(con, msg.getMsg());
                                }
                            }


                        });
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    // 截取图片
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finish() {
        startActivity(new Intent(this,UserLogin.class));
        super.finish();
    }
}
