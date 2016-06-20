package com.anxin.changbaishan.view.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.MyInfoEntity;
import com.anxin.changbaishan.entity.UploadPhotoEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.Base64Util;
import com.anxin.changbaishan.utils.GlideImageLoader;
import com.anxin.changbaishan.utils.ImageLoadUtil;
import com.anxin.changbaishan.utils.InputFilterMinMax;
import com.anxin.changbaishan.utils.PhoneUtils;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.AutographActivity;
import com.anxin.changbaishan.view.MainTabActivity;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.account.address.AddAddressActivity;
import com.anxin.changbaishan.view.account.address.ShippingAddressActivity;
import com.anxin.changbaishan.view.account.bonuspoints.BonusPointsActivity;
import com.anxin.changbaishan.view.account.deliver.DeliverWaterActivity;
import com.anxin.changbaishan.view.account.deliver.DeliveryListActivity;
import com.anxin.changbaishan.view.account.friend.SendFriendActivity;
import com.anxin.changbaishan.view.account.order.OrderActivity;
import com.anxin.changbaishan.widget.dragindicator.DragIndicatorView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.iv_left)
    ImageView mIvLeft;
    @Bind(R.id.ll_left)
    LinearLayout mLlLeft;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.btn_right)
    Button mBtnRight;
    @Bind(R.id.img_user_icon)
    ImageView mImgUserIcon;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;
    @Bind(R.id.btn_appointment)
    Button mBtnAppointment;
    @Bind(R.id.tv_distribution)
    TextView mTvDistribution;
    @Bind(R.id.tv_pending_payment)
    TextView mTvPendingPayment;
    @Bind(R.id.tv_points)
    TextView mTvPoints;
    @Bind(R.id.rl_all_order)
    RelativeLayout mRlAllOrder;
    @Bind(R.id.rl_address)
    RelativeLayout mRlAddress;
    @Bind(R.id.rl_account)
    RelativeLayout mRlAccount;
    @Bind(R.id.rl_gift)
    RelativeLayout mRlGift;
    @Bind(R.id.rl_shard)
    RelativeLayout mRlShard;
    @Bind(R.id.rl_more)
    RelativeLayout mRlMore;
    @Bind(R.id.btn_login)
    Button mbtnLogin;
    @Bind(R.id.ll_not_logged)
    LinearLayout mLlNotLogged;
    @Bind(R.id.sv_login)
    ScrollView mSvLogin;
    @Bind(R.id.indicator_pending_payment_num)
    DragIndicatorView mIndicatorPendingPaymentNum;
    @Bind(R.id.indicator_gift_num)
    DragIndicatorView mIndicatorGiftNum;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainTabActivity mActivity;
    private MyInfoEntity.DataBean mData;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (MainTabActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        init(view);

        Button loginBtn = (Button) view.findViewById(R.id.login_btn);
        final Context context = getContext();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                startActivity(intent);
            }
        });
        Button autograpBtn = (Button) view.findViewById(R.id.autograp_btn);
        autograpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AutographActivity.class);
                startActivity(intent);
            }
        });

        final EditText editText = (EditText) view.findViewById(R.id.et_maxmin);
        InputFilterMinMax.InterfaceInputError inputError = new InputFilterMinMax.InterfaceInputError() {
            @Override
            public void InputError(int value) {
                editText.setText(String.valueOf(value));
            }
        };
        editText.setFilters(new InputFilter[]{new InputFilterMinMax(1, 99, inputError)});
        return view;
    }

    private void init(View view) {
        mTvTitle.setText("个人中心");
        mBtnRight.setVisibility(View.VISIBLE);
        mIvLeft.setImageResource(R.drawable.setting_icon);
        mLlLeft.setVisibility(View.VISIBLE);

//        if ("".equals(mActivity.spUtil.get(SPUtil.ATOKEN, ""))) {
//            mLlNotLogged.setVisibility(View.VISIBLE);
//            mSvLogin.setVisibility(View.GONE);
//        } else {
//            mLlNotLogged.setVisibility(View.GONE);
//            mSvLogin.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ("".equals(mActivity.spUtil.get(SPUtil.ATOKEN, ""))) {
            mLlNotLogged.setVisibility(View.VISIBLE);
            mSvLogin.setVisibility(View.GONE);
        } else {
            mLlNotLogged.setVisibility(View.GONE);
            mSvLogin.setVisibility(View.VISIBLE);
            getMyInfo();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_appointment, R.id.tv_distribution, R.id.tv_pending_payment, R.id.tv_points,
            R.id.rl_all_order, R.id.rl_address, R.id.rl_account, R.id.rl_gift, R.id.rl_shard,
            R.id.rl_more, R.id.btn_login, R.id.btn_right, R.id.img_user_icon})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_appointment:
                mActivity.startAnimActivity(DeliverWaterActivity.class);
                break;
            case R.id.tv_distribution:
                mActivity.startAnimActivity(DeliveryListActivity.class);
                break;
            case R.id.tv_pending_payment:
                bundle.putInt("OrderFragment", 1);
                mActivity.startAnimActivity(OrderActivity.class, bundle);
                break;
            case R.id.tv_points:
                mActivity.startAnimActivity(BonusPointsActivity.class);
                break;
            case R.id.rl_all_order:
                mActivity.startAnimActivity(OrderActivity.class);
                break;
            case R.id.rl_address:
                mActivity.startAnimActivity(ShippingAddressActivity.class);
                break;
            case R.id.rl_account:
                bundle.putParcelable("MyInfo", mData);
                mActivity.startAnimActivity(AccountActivity.class, bundle);
                break;
            case R.id.rl_gift:
                mActivity.startAnimActivity(SendFriendActivity.class);
                break;
            case R.id.rl_shard:
                mActivity.startAnimActivity(ShareActivity.class);
                break;
            case R.id.rl_more:
                break;
            case R.id.btn_login:
                mActivity.startAnimActivity(RegisterActivity.class);
                break;
            case R.id.btn_right:
                PhoneUtils.callDial(mActivity, PhoneUtils.PHONE);
                break;
            case R.id.img_user_icon:
                uploadImage();
                break;
        }
    }

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;
    private void uploadImage() {
        ThemeConfig themeConfig = ThemeConfig.DEFAULT;
        final FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setEnableCamera(true)
                .setCropSquare(true)
                .setForceCrop(true)
                .setForceCropEdit(true)
                .build();
        ImageLoader imageLoader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(mActivity, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);

        final PopupWindow popupWindow = new PopupWindow(mActivity);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(LayoutInflater.from(mActivity).inflate(R.layout.layout_popup_window_list, null));
        popupWindow.setBackgroundDrawable(new ColorDrawable(0X00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(mActivity.findViewById(R.id.img_user_icon), Gravity.BOTTOM, 0, 0);

        Button btn1 = (Button) popupWindow.getContentView().findViewById(R.id.button);
        Button btn2 = (Button) popupWindow.getContentView().findViewById(R.id.button2);
        Button btn3 = (Button) popupWindow.getContentView().findViewById(R.id.button3);
        Button btn4 = (Button) popupWindow.getContentView().findViewById(R.id.button4);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mActivity.showShortToast("size=" + resultList.size());
                if (null != resultList && resultList.size() > 0) {
                    Bitmap bitmap = BitmapFactory.decodeFile(resultList.get(0).getPhotoPath());

                    uploadPhoto(Base64Util.bitmapToBase64(bitmap));

                    bitmap.recycle();
                }
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            mActivity.showShortToast("size=" + errorMsg);
        }
    };

    private void uploadPhoto(final String imageData) {
        VolleyRequest.uploadPhoto(imageData, this.getClass().getSimpleName() + "image", new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    UploadPhotoEntity entity =
                            mActivity.loadDataUtil.getJsonData(response, UploadPhotoEntity.class);
                    if (0 == entity.getErrorNo()) {
                        ImageLoadUtil.loadImage(mActivity, entity.getData().getUrl(), mImgUserIcon);
                    } else if (-99 == entity.getErrorNo()) {
                        mActivity.spUtil.put(SPUtil.TOKEN, "");
                        uploadPhoto(imageData);
                    } else if (-52 == entity.getErrorNo()) {
                        mActivity.spUtil.put(SPUtil.ATOKEN, "");
                        mActivity.startAnimActivity(RegisterActivity.class);
                    } else {
                        mActivity.showShortToast(entity.getMessage());
                    }

                } else {
                    mActivity.showShortToast(error);
                }
            }
        });
    }

    private void getMyInfo() {
        VolleyRequest.getMyInfo(this.getClass().getSimpleName(), new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            MyInfoEntity entity =
                                    mActivity.loadDataUtil.getJsonData(response, MyInfoEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mData = entity.getData();
                                mTvUserName.setText(
                                        (String)mActivity.spUtil.get(SPUtil.USER_MOBILE_PHONE, ""));
                                if (0 != entity.getData().getNeedToPay()) {
                                    mIndicatorPendingPaymentNum.setVisibility(View.VISIBLE);
                                    mIndicatorPendingPaymentNum.setText(
                                            String.valueOf(entity.getData().getNeedToPay()));
                                } else {
                                    mIndicatorPendingPaymentNum.setVisibility(View.GONE);
                                }
                                if (0 != entity.getData().getFriendGiveToMe()){
                                    mIndicatorGiftNum.setVisibility(View.VISIBLE);
                                    mIndicatorGiftNum.setText(
                                            String.valueOf(entity.getData().getFriendGiveToMe()));
                                } else {
                                    mIndicatorGiftNum.setVisibility(View.GONE);
                                }
                            } else if (-99 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.TOKEN, "");
                                getMyInfo();
                            } else if (-52 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.ATOKEN, "");
                                mActivity.startAnimActivity(RegisterActivity.class);
                            } else {
                                mActivity.showShortToast(entity.getMessage());
                            }
                        } else {
                            mActivity.showShortToast(error);
                        }
                    }
                });
    }

}
