package com.stu.app.jyu.view.Activity;

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

import com.baoyz.actionsheet.ActionSheet;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stu.app.jyu.Adapter.BaseRecyclerViewAdapter;
import com.stu.app.jyu.Adapter.take_photo_adapter;
import com.stu.app.jyu.Domain.JyuUser;
import com.stu.app.jyu.Domain.baseAPPUI;
import com.stu.app.jyu.R;
import com.stu.app.jyu.Utils.IntentUtils;
import com.stu.app.jyu.Utils.constantsVAR;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

import static com.stu.app.jyu.Domain.baseAPPUI.Contact_Type.PHONE;
import static com.stu.app.jyu.Domain.baseAPPUI.Contact_Type.QQ;
import static com.stu.app.jyu.Domain.baseAPPUI.Contact_Type.WEICHAT;
import static com.stu.app.jyu.view.Activity.InnerAppFunctionActivity.App_Function;
import static com.stu.app.jyu.view.Activity.InnerAppFunctionActivity.LostFind;

public class LostFindAddContentActivity extends AppCompatActivity {
    private int TYPE;
    //    FloatingActionButton fab;
    //    PopupWindow popupWindow;
    CoordinatorLayout coo;
    Toolbar toolbar;
//    List<TakePhotoItem> list;
    List<String> list;
    HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    RecyclerView rv;
    take_photo_adapter adapter;
    SimpleDraweeView simpleDraweeView;
    View view;
    TextInputLayout til_eventinfo_desc;
    ImageView iv_take_photo;
    TextInputLayout til_eventinfo_contact;
    private EditText et_eventinfo_desc;
    private EditText et_eventinfo_contact;
    private baseAPPUI.Contact_Type CT= PHONE;

    //    EditText et_eventinfo_input;
    //    BottomSheetBehavior behavior;
    //    View bottom_sheet;
    Button bt_take_photo_byCamera;
    //    Button bt_take_photo_byLocal;
    //    File dir;
    //    File file;
    Button bt_recover;
    Spinner sp_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_lost_find_add);
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
//        simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_take_phone);

        iv_take_photo = (ImageView) view.findViewById(R.id.iv_take_photo);
        til_eventinfo_desc = (TextInputLayout) findViewById(R.id.til_eventinfo_desc);
        til_eventinfo_contact = (TextInputLayout) findViewById(R.id.til_eventinfo_contact);
        et_eventinfo_contact = (EditText) findViewById(R.id.et_eventinfo_contact);
        et_eventinfo_desc = (EditText) findViewById(R.id.et_eventinfo_desc);

        sp_contact = (Spinner) findViewById(R.id.sp_contact);
        //        et_eventinfo_input = (EditText) findViewById(et_eventinfo_input);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            TYPE = bundle.getInt(App_Function);
        }
//        list = new ArrayList<TakePhotoItem>();
        list = new ArrayList<String>();
    }


    private void initEvent() {
        sp_contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        CT= PHONE;
                        break;
                    case 1:
                        CT= QQ;
                        break;
                    case 2:
                        CT= WEICHAT;
                        break;
                }
                String[] contact = getResources().getStringArray(R.array.contact);

                Log.i("20160601","position+::"+position);
                Toast.makeText(LostFindAddContentActivity.this,"your select is "+contact[position],Toast.LENGTH_LONG).show();
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
//                    if (count>=6){
//                        til_eventinfo_contact.setErrorEnabled(false);
//                    }
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
                if (list.size() != 0) {
                    list.removeAll(list);
                    adapter.notifyDataSetChanged();
                    iv_take_photo.setVisibility(View.VISIBLE);
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(constantsVAR.TAG, "ENTER NAV ONCLICK");
                finish();
            }
        });
        adapter = new take_photo_adapter(this, list, R.layout.take_photo_item);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(constantsVAR.TAG, "list item:::::");
//                TakePhotoItem item = list.get(position);
                String item = list.get(position);
                Log.i(constantsVAR.TAG, "list item::" + item + ":::");
                startActivity(IntentUtils.getImageFileIntent(item));
                Toast.makeText(getApplicationContext(), "open img", Toast.LENGTH_LONG).show();
            }
        });
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        rv.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        rv.setLayoutManager(new GridLayoutManager(this, 4));
        RecyclerViewUtils.setFooterView(rv, view);

//        simpleDraweeView.setImageURI(Uri.parse("res:///" + R.mipmap.ic_addpic));
        //        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
        iv_take_photo.setImageResource(R.mipmap.ic_addpic);
        iv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionSheet.createBuilder(LostFindAddContentActivity.this, getSupportFragmentManager())

                        .setCancelButtonTitle("取消(Cancel)")
                        .setOtherButtonTitles("拍照(Camera)", "打开相册(Open Gallery)")
                        .setCancelableOnTouchOutside(true)
                        .setListener(new ActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                            }

                            @Override
                            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                                Log.i(constantsVAR.TAG, "index::" + index);
                                switch (index) {
                                    case 0:
                                        GalleryFinal.openCamera(0, new GalleryFinal.OnHanlderResultCallback() {
                                            @Override
                                            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                                for (PhotoInfo fo : resultList) {
//                                                    TakePhotoItem item = new TakePhotoItem();
//                                                    item.setImgPath(fo.getPhotoPath());
//                                                    item.setSmallIMG(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(fo.getPhotoPath()), 200, 200));
//                                                    Log.i(constantsVAR.TAG, "str path::" + item.getImgPath());
//                                                    list.add(item);
                                                    list.add(fo.getPhotoPath());
                                                    adapter.notifyDataSetChanged();
                                                }
                                                if ((list.size()) == 9) {
//                                                    simpleDraweeView.setVisibility(View.GONE);
                                                    iv_take_photo.setVisibility(View.GONE);
                                                } else {
//                                                    simpleDraweeView.setVisibility(View.VISIBLE);
                                                    iv_take_photo.setVisibility(View.VISIBLE);

                                                }
                                            }

                                            @Override
                                            public void onHanlderFailure(int requestCode, String errorMsg) {
                                            }
                                        });

                                        break;
                                    case 1:
                                        int item_num = 9 - list.size();

                                        GalleryFinal.openGalleryMuti(0, item_num, new GalleryFinal.OnHanlderResultCallback() {
                                            @Override
                                            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                                Log.i(constantsVAR.TAG, "本地 item_num = " + (9 - list.size()) + "res size=" + resultList.size());
                                                Log.i(constantsVAR.TAG, "list size::" + resultList.size());
                                                for (PhotoInfo fo : resultList) {
//                                                    TakePhotoItem item = new TakePhotoItem();
//                                                    item.setImgPath(fo.getPhotoPath());
////                                                    item.setSmallIMG(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(fo.getPhotoPath()), 200, 200));
//                                                    Log.i(constantsVAR.TAG, "str path::" + item.getImgPath());
//                                                    list.add(item);
                                                    list.add(fo.getPhotoPath());
                                                    adapter.notifyDataSetChanged();
                                                }
                                                if ((9 - list.size()) == 0) {
//                                                    simpleDraweeView.setVisibility(View.GONE);
                                                    iv_take_photo.setVisibility(View.GONE);
                                                } else {
//                                                    simpleDraweeView.setVisibility(View.VISIBLE);
                                                    iv_take_photo.setVisibility(View.VISIBLE);
                                                }
                                            }

                                            @Override
                                            public void onHanlderFailure(int requestCode, String errorMsg) {

                                            }
                                        });
                                        break;
                                }
                            }
                        }).show();
            }
        });

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

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_SEND) {
            int SendFlag = 0x00;
            List<Map<baseAPPUI.Contact_Type,String>> typelist = new ArrayList<>();
//            List<String> applist = new ArrayList<>();
//            for (TakePhotoItem takephotoitem :list){
//                applist.add(takephotoitem.getImgPath());
//            }
String [] path =  list.toArray(new String[list.size()]);
            BmobFile.uploadBatch(LostFindAddContentActivity.this, path, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    if(list1.size()==list.size()){//如果数量相等，则代表文件全部上传完成
                        //do something
                        for(int i=0;i<list1.size();i++){
                            Log.i("20160601","file is ::"+list.get(i).getFileUrl(LostFindAddContentActivity.this)+"  url::"+list1.get(i));
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

            baseAPPUI app = new baseAPPUI(LostFind);

//            app.setImageUrl(applist);
            app.setJyuUser(BmobUser.getCurrentUser(LostFindAddContentActivity.this, JyuUser.class));
            app.setDate( new BmobDate(new Date(System.currentTimeMillis())));
            Toast.makeText(getApplicationContext(), "send!", Toast.LENGTH_LONG).show();
            String desc = til_eventinfo_desc.getEditText().getText().toString();
            String contact = til_eventinfo_contact.getEditText().getText().toString();
            if (TextUtils.isEmpty(desc)) {
                til_eventinfo_desc.setError("请输入至少6个字符的物品描述信息");
            } else {
                til_eventinfo_desc.setErrorEnabled(false);
            }
            switch (CT){
                case QQ:
                   HashMap QQhashMap = new HashMap<baseAPPUI.Contact_Type,String>();
                    QQhashMap.put(QQ,contact);
                    typelist.add(QQhashMap);
                    break;
                case WEICHAT:
                    HashMap WEICHAThashMap = new HashMap<baseAPPUI.Contact_Type,String>();
                    WEICHAThashMap.put(WEICHAT,contact);
                    typelist.add(WEICHAThashMap);
                    break;
                case PHONE:
                    HashMap PHONEhashMap = new HashMap<baseAPPUI.Contact_Type,String>();
                    PHONEhashMap.put(PHONE,contact);
                    typelist.add(PHONEhashMap);
                    break;

            }
            app.setContact(typelist);
            app.save(LostFindAddContentActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Log.i("20160601","saving success");
                }

                @Override
                public void onFailure(int i, String s) {
                    Log.i("20160601","saving fail"+s);
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
