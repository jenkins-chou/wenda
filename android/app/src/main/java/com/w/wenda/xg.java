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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.w.wenda.pojo.CommonData;
import com.w.wenda.pojo.User;
import com.w.wenda.util.MyApplication;
import com.w.wenda.util.MyToastUtil;
import com.w.wenda.util.Url;
import com.w.wenda.util.UserClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zuo.biao.library.base.BaseFragment;

public class xg extends BaseFragment {


    @OnClick(R.id.back)
    void back(){
        getActivity().finish();
    }

    TextView username;
    EditText password, nickname,tel;
    ImageView head;//头像
    String names = "";
    String head_url = "";
    Button reg;


    User u;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.user_info);

        //类相关初始化，必须使用>>>>>>>>>>>>>>>>
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        ButterKnife.bind(this,view);
        return view;
    }

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static xg createInstance() {
        xg fragment = new xg();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        username = findViewById(R.id.username);
        tel = findViewById(R.id.tel);
        password = findViewById(R.id.password);
        nickname = findViewById(R.id.nickname);
        reg = findViewById(R.id.reg);
        head = findViewById(R.id.head);
        u=MyApplication.getApp().getU();
        username.setText(u.getUsername());
        tel.setText(u.getSjh());
        password.setText(u.getPass());
        nickname.setText(u.getName());
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity()).setTitle("选择照片").setNegativeButton("拍照", new DialogInterface.OnClickListener() {
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
                }).setNeutralButton("相册", new DialogInterface.OnClickListener() {
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
        ImageLoader.getInstance().displayImage(Url.url()+"upload/"+u.getHead(),head);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams ps = new RequestParams();
                ps.add("username", username.getText().toString());
                ps.add("pass", password.getText().toString());
                ps.add("name", nickname.getText().toString());
                ps.add("head", head_url);
                ps.add("sjh",tel.getText().toString());
                ps.add("id",u.getId());
                UserClient.post("user/update", ps, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        CommonData data = JSON.parseObject(content, CommonData.class);
                        if (data.getCode().equals("200")) {
                            MyToastUtil.ShowToast(getActivity(), "成功");
                            getActivity().finish();
                        } else {
                            MyToastUtil.ShowToast(getActivity(), data.getMsg());
                        }
                    }
                });

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                                    MyToastUtil.ShowToast(getActivity(), msg.getMsg());
                                }
                            }

                            @Override
                            public void onFailure(Throwable error, String content) {
                                super.onFailure(error, content);
                                MyToastUtil.ShowToast(getActivity(), "失败");
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

}
