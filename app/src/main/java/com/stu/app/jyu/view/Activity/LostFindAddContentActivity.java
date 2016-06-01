package com.stu.app.jyu.view.Activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stu.app.jyu.Adapter.BaseRecyclerViewAdapter;
import com.stu.app.jyu.Adapter.take_photo_adapter;
import com.stu.app.jyu.Domain.TakePhotoItem;
import com.stu.app.jyu.R;
import com.stu.app.jyu.Utils.IntentUtils;
import com.stu.app.jyu.Utils.constantsVAR;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

import static android.media.ThumbnailUtils.extractThumbnail;
import static com.stu.app.jyu.view.Activity.InnerAppFunctionActivity.App_Function;

public class LostFindAddContentActivity extends AppCompatActivity {
    private int TYPE;
    FloatingActionButton fab;
    PopupWindow popupWindow;
    CoordinatorLayout coo;
    Toolbar toolbar;
    List<TakePhotoItem> list;
    HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    RecyclerView rv;
    take_photo_adapter adapter;
    SimpleDraweeView simpleDraweeView;
    View view;
    BottomSheetBehavior behavior;
    View bottom_sheet;
    Button bt_take_photo_byCamera;
    Button bt_take_photo_byLocal;
    File dir;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
        bottom_sheet = coo.findViewById(R.id.take_photo_bottom_sheet);
        behavior = BottomSheetBehavior.from(bottom_sheet);
        bt_take_photo_byCamera = (Button) findViewById(R.id.bt_take_photo_byCamera);
        bt_take_photo_byLocal = (Button) findViewById(R.id.bt_take_photo_byLocal);
        view = View.inflate(this, R.layout.take_photo_item, null);
        simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_take_phone);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            TYPE = bundle.getInt(App_Function);
        }

        list = new ArrayList<TakePhotoItem>();

    }

    private void initEvent() {

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
                TakePhotoItem item = list.get(position);
                Log.i(constantsVAR.TAG, "list item::" + item.getImgPath() + ":::");
                startActivity(IntentUtils.getImageFileIntent(item.getImgPath()));
                Toast.makeText(getApplicationContext(), "open img", Toast.LENGTH_LONG).show();
            }
        });
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        rv.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        rv.setLayoutManager(new GridLayoutManager(this, 4));
        RecyclerViewUtils.setFooterView(rv, view);

        simpleDraweeView.setImageURI(Uri.parse("res:///" + R.mipmap.ic_addpic));
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
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
                                                    TakePhotoItem item = new TakePhotoItem();
                                                    item.setImgPath(fo.getPhotoPath());
                                                    item.setSmallIMG(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(fo.getPhotoPath()), 200, 200));
                                                    Log.i(constantsVAR.TAG, "str path::" + item.getImgPath());
                                                    list.add(item);
                                                    adapter.notifyDataSetChanged();
                                                }
                                                if ((list.size()) == 9) {
                                                    simpleDraweeView.setVisibility(View.GONE);
                                                } else {
                                                    simpleDraweeView.setVisibility(View.VISIBLE);
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
                                                    TakePhotoItem item = new TakePhotoItem();
                                                    item.setImgPath(fo.getPhotoPath());
                                                    item.setSmallIMG(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(fo.getPhotoPath()), 200, 200));
                                                    Log.i(constantsVAR.TAG, "str path::" + item.getImgPath());
                                                    list.add(item);
                                                    adapter.notifyDataSetChanged();
                                                }
                                                if ((9 - list.size()) == 0) {
                                                    simpleDraweeView.setVisibility(View.GONE);
                                                } else {
                                                    simpleDraweeView.setVisibility(View.VISIBLE);
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
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.i(constantsVAR.TAG, "NOW IS ::" + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i(constantsVAR.TAG, "NOW IS ::" + slideOffset);
            }
        });
        bt_take_photo_byCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LostFindAddContentActivity.this, "bt_take_photo_byCamera", Toast.LENGTH_LONG).show();
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                Intent take_photo_byCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Long time = Calendar.getInstance().getTimeInMillis();

                dir = new File(Environment.getExternalStorageDirectory(), "testPhoto");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                file = new File(dir, time + ".jpg");
                take_photo_byCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//                startActivityForResult(take_photo_byCamera, TAKE_PHOTO_BYCAMERA);
            }
        });
        bt_take_photo_byLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openGalleryMuti(0, 9, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        list.removeAll(list);
                        Log.i(constantsVAR.TAG, "list size::" + resultList.size());
                        for (PhotoInfo fo : resultList) {
                            TakePhotoItem item = new TakePhotoItem();
                            item.setImgPath(fo.getPhotoPath());

                            item.setSmallIMG(extractThumbnail(BitmapFactory.decodeFile(fo.getPhotoPath()), 200, 200));
                            Log.i(constantsVAR.TAG, "str path::" + item.getImgPath());
                            list.add(item);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        System.gc();
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(constantsVAR.TAG, "requestcode::" + requestCode + "::resultCode::" + resultCode);
        if (requestCode == TAKE_PHOTO_BYCAMERA && resultCode == RESULT_OK) {
            TakePhotoItem item = new TakePhotoItem();
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            item.setSmallIMG(extractThumbnail(bitmap, 200, 200));
            item.setImgPath(file.getAbsolutePath());
            list.add(item);
            //
            //
            //                    adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            //                        @Override
            //                        public void onItemClick(View view, int position) {
            //                            Log.i(constantsVAR.TAG,"list item:::::");
            //                            TakePhotoItem item =  list.get(position);
            //                            Log.i(constantsVAR.TAG,"list item::"+item.getImgPath()+":::");
            //                            startActivity(IntentUtils.getImageFileIntent(item.getImgPath()));
            //                            Toast.makeText(getApplicationContext(),"open img",Toast.LENGTH_LONG).show();
            //                        }
            //                    });
            adapter.notifyDataSetChanged();
            //            mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
            //            adapter.notifyItemChanged(list.size()-1);
            //            rv.notifyAll();


            //            Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
            //            FileOutputStream fos = null;
            //            try {
            //                //                ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //                fos = new FileOutputStream(new File(dir, "TEST.png"));
            //                if (fos != null) {
            //                    boolean flag = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            //                    fos.flush();
            //                    fos.close();
            //                    if (flag) {
            //                        Toast.makeText(getApplicationContext(), "success compress", Toast.LENGTH_LONG).show();
            //                    }
            //                }
            //
            //            } catch (FileNotFoundException e) {
            //                e.printStackTrace();
            //            } catch (IOException e) {
            //                e.printStackTrace();
            //            } finally {
            //            }
        }
    }
*/
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
            Toast.makeText(getApplicationContext(), "send!", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
