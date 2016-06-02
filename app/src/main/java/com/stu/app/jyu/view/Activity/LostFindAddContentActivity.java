package com.stu.app.jyu.view.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.stu.app.jyu.Adapter.BaseRecyclerViewAdapter;
import com.stu.app.jyu.Adapter.take_photo_adapter;
import com.stu.app.jyu.Domain.JyuUser;
import com.stu.app.jyu.Domain.UpLoadImg;
import com.stu.app.jyu.Domain.baseAPPUI;
import com.stu.app.jyu.R;
import com.stu.app.jyu.Utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import me.nereo.multi_image_selector.MultiImageSelector;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static com.stu.app.jyu.Domain.baseAPPUI.Contact_Type.PHONE;
import static com.stu.app.jyu.Domain.baseAPPUI.Contact_Type.QQ;
import static com.stu.app.jyu.Domain.baseAPPUI.Contact_Type.WEICHAT;
import static com.stu.app.jyu.view.Activity.InnerAppFunctionActivity.App_Function;
import static com.stu.app.jyu.view.Activity.InnerAppFunctionActivity.LostFind;

public class LostFindAddContentActivity extends AppCompatActivity {
    private int TYPE;
    private CoordinatorLayout coo;
    private Toolbar toolbar;
    //    List<TakePhotoItem> ImgListDataSource;
    private ArrayList<String> ImgListDataSource;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private RecyclerView rv;
    private take_photo_adapter adapter;
    //    private  SimpleDraweeView simpleDraweeView;
    private View view;
    private TextInputLayout til_eventinfo_desc;
    private ImageView iv_take_photo;
    private TextInputLayout til_eventinfo_contact;
    private EditText et_eventinfo_desc;
    private EditText et_eventinfo_contact;
    private baseAPPUI.Contact_Type CT = PHONE;
    private Button bt_take_photo_byCamera;
    private Button bt_recover;
    private Spinner sp_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_lost_find_add);
        EventBus.getDefault().register(this);
        initData();
        initView();
        initEvent();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("失物认领");
        toolbar.setNavigationIcon(R.mipmap.ic_goback_green);
        setSupportActionBar(toolbar);
        bindView();
    }

    private void bindView() {
        rv = (RecyclerView) findViewById(R.id.rv_lost_find_photo);
        coo = (CoordinatorLayout) findViewById(R.id.coo_lost_find_add);
        view = View.inflate(this, R.layout.take_photo_item, null);
        bt_recover = (Button) findViewById(R.id.bt_recover);
        iv_take_photo = (ImageView) view.findViewById(R.id.iv_take_photo);
        til_eventinfo_desc = (TextInputLayout) findViewById(R.id.til_eventinfo_desc);
        til_eventinfo_contact = (TextInputLayout) findViewById(R.id.til_eventinfo_contact);
        et_eventinfo_contact = (EditText) findViewById(R.id.et_eventinfo_contact);
        et_eventinfo_desc = (EditText) findViewById(R.id.et_eventinfo_desc);
        sp_contact = (Spinner) findViewById(R.id.sp_contact);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            TYPE = bundle.getInt(App_Function);
        }
        ImgListDataSource = new ArrayList<String>();
    }


    private void initEvent() {
        sp_contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        CT = PHONE;
                        et_eventinfo_contact.setInputType(TYPE_CLASS_NUMBER);
                        break;
                    case 1:
                        CT = QQ;
                        et_eventinfo_contact.setInputType(TYPE_CLASS_NUMBER);
                        break;
                    case 2:
                        CT = WEICHAT;
                        et_eventinfo_contact.setInputType(TYPE_CLASS_TEXT);
                        break;
                }
                String[] contact = getResources().getStringArray(R.array.contact);

                Toast.makeText(LostFindAddContentActivity.this, "your select is " + contact[position], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_eventinfo_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_eventinfo_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count >= 6) {
                    til_eventinfo_desc.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bt_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImgListDataSource.size() != 0) {
                    ImgListDataSource.removeAll(ImgListDataSource);
                    adapter.notifyDataSetChanged();
                    iv_take_photo.setVisibility(View.VISIBLE);
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new take_photo_adapter(this, ImgListDataSource, R.layout.take_photo_item);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String item = ImgListDataSource.get(position);
                startActivity(IntentUtils.getImageFileIntent(item));
                Toast.makeText(getApplicationContext(), "open img", Toast.LENGTH_LONG).show();
            }
        });
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        rv.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        rv.setLayoutManager(new GridLayoutManager(this, 4));
        RecyclerViewUtils.setFooterView(rv, view);
        iv_take_photo.setImageResource(R.mipmap.ic_addpic);
        iv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector.create(LostFindAddContentActivity.this)
                        .showCamera(true)
                        .origin(ImgListDataSource)
                        .count(9)
                        .multi()
                        .start(LostFindAddContentActivity.this, LostFind);
            }
        });

    }

    private ArrayList<String> mSelectPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LostFind) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                //list需要去重复
                ImgListDataSource.clear();
                ImgListDataSource.addAll(mSelectPath);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private int SendFlag = 0x00;
    private baseAPPUI LostFindFunction;

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void BmobUploadFile(final UpLoadImg upLoadImg) {
        final int[] testsize = {upLoadImg.getPaths().length};
        Log.i("20160602", "enter main bus");
        Log.i("20160602", "upLoadImg.path size::" + upLoadImg.getPaths().length);
        ArrayList<String> CacheList = new ArrayList();
        File dir = null;
        dir = new File(getCacheDir().getAbsolutePath() + "/JYUtmp");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //先压缩
        String[] paths = upLoadImg.getPaths();
        Bitmap bitmap = null;
        boolean COVflag = false;
        FileOutputStream fos = null;
        for (String str : paths) {
            try {
                bitmap = BitmapFactory.decodeFile(str);
                File fi = new File(str);
                File newCacheFile = new File(dir.getAbsolutePath() + "/" + fi.getName());
                fos = new FileOutputStream(newCacheFile);
                CacheList.add(newCacheFile.getAbsolutePath());
                COVflag = bitmap.compress(Bitmap.CompressFormat.JPEG, 40, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.flush();
                    fos.close();
                    if (COVflag && bitmap != null) {
                        bitmap.recycle();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        String[] uploadPath = CacheList.toArray(new String[CacheList.size()]);
        //再将压缩源上传
        BmobFile.uploadBatch(LostFindAddContentActivity.this, uploadPath, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                //1、list-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、list1-上传文件的完整url地址
                if (list1.size() == list.size()) {//如果数量相等，则代表文件全部上传完成
                    //实际上，测试表明，它这里是一个for循环。。
                    // 实际上还是一个个上传，上传多少个东西，这里就循环多少次，list.size单调递增+1
                    //所以。。自己再传变量再判断
                    if (testsize[0] == list.size()) {
                        //do something
                        baseAPPUI appui = new baseAPPUI(LostFind);
                        appui.setImageUrl(list1);
                        appui.update(getApplicationContext(), upLoadImg.getId(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_LONG).show();
                                //                            FileUtils.delete(dir);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(getApplicationContext(), "update fail" + s, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {
            }

            @Override
            public void onError(int i, String s) {
            }
        });


    }

    private JyuUser jyuUser;

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_SEND) {
            LostFindFunction = new baseAPPUI(LostFind);

            jyuUser = BmobUser.getCurrentUser(LostFindAddContentActivity.this, JyuUser.class);
            List<Map<baseAPPUI.Contact_Type, String>> contact_list = new ArrayList<>();
            final String[] paths = ImgListDataSource.toArray(new String[ImgListDataSource.size()]);

            if (jyuUser != null) {
                SendFlag |= (0x01 << 1);
                LostFindFunction.setJyuUser(jyuUser);
            } else {
                SendFlag &= (~(0x01 << 1));
                Toast.makeText(LostFindAddContentActivity.this, "请先登录", Toast.LENGTH_LONG).show();
            }
            LostFindFunction.setDate(new BmobDate(new Date(System.currentTimeMillis())));
            Toast.makeText(getApplicationContext(), "send!", Toast.LENGTH_LONG).show();
            String desc = til_eventinfo_desc.getEditText().getText().toString();
            String contact = til_eventinfo_contact.getEditText().getText().toString();
            if (TextUtils.isEmpty(desc)) {
                SendFlag &= (~(0x01 << 2));
                til_eventinfo_desc.setError("请输入至少6个字符的物品描述信息");
            } else {
                til_eventinfo_desc.setErrorEnabled(false);
                SendFlag |= (0x01 << 2);
            }
            if (TextUtils.isEmpty(contact)) {
                SendFlag &= (~(0x01 << 3));
                til_eventinfo_desc.setError("请选择其中一种联系方式");
            } else {
                til_eventinfo_desc.setErrorEnabled(false);
                SendFlag |= (0x01 << 3);
            }
            switch (CT) {
                case QQ:
                    HashMap QQhashMap = new HashMap<baseAPPUI.Contact_Type, String>();
                    QQhashMap.put(QQ, contact);
                    contact_list.add(QQhashMap);
                    break;
                case WEICHAT:
                    HashMap WEICHAThashMap = new HashMap<baseAPPUI.Contact_Type, String>();
                    WEICHAThashMap.put(WEICHAT, contact);
                    contact_list.add(WEICHAThashMap);
                    break;
                case PHONE:
                    HashMap PHONEhashMap = new HashMap<baseAPPUI.Contact_Type, String>();
                    PHONEhashMap.put(PHONE, contact);
                    contact_list.add(PHONEhashMap);
                    break;

            }
            if (contact_list != null) {

                SendFlag |= (0x01 << 4);
                LostFindFunction.setContact(contact_list);
            } else {
                SendFlag &= (~(0x01 << 4));
            }
            if (paths != null) {
                //                LostFindFunction.setImageUrl(ImgListDataSource);这是错的
                SendFlag |= (0x01 << 5);
            } else {
                SendFlag &= (~(0x01 << 5));
            }

            if (SendFlag == 0x3e) {
                LostFindFunction.save(LostFindAddContentActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {

                        EventBus.getDefault().post(new UpLoadImg(LostFindFunction.getObjectId(), paths));
                        LostFindAddContentActivity.this.finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i("20160602", "fail:::" + s);
                        Toast.makeText(LostFindAddContentActivity.this, "消息发送失败", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                SendFlag = 0x00;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
