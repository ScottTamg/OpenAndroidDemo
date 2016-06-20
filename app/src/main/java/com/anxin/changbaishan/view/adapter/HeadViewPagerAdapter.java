package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Txw on 2016/4/13.
 */
public class HeadViewPagerAdapter extends PagerAdapter{
    private Context mContext;
//    private ImageLoader mImageLoader;
//    private DisplayImageOptions mOptions;
    private ImageView mImageView;
    public List<ImageView> mImageViewList;
    private View mView;
    private LinearLayout.LayoutParams mParams;

    private static String imgUrl = "http://static.anxin.com/m/images/v2/pages/default2/slide-006.png";

    public HeadViewPagerAdapter(final Context context, LinearLayout pointGroup, List<DummyContent.DummyItem> items) {
        this.mContext = context;
//        mImageLoader = ImageLoader.getInstance();
//        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
//        mOptions = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.color.nocolor)
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .showImageForEmptyUri(R.color.nocolor)
//                .showImageOnFail(R.color.nocolor).cacheInMemory(true)
//                .cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(1))
//                .build();

        mImageViewList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setImageResource(R.drawable.home_foot);
//            mImageLoader.displayImage(imgUrl, mImageView, mOptions, null);
            mImageViewList.add(mImageView);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "图片被点击", Toast.LENGTH_SHORT).show();
                }
            });
            // 每循环一次需要向LinearLayout中添加一个点的view对象
            mView = new View(context);
            mView.setBackgroundResource(R.drawable.point_bg);
            mParams = new LinearLayout.LayoutParams(14, 14); //点的宽高
            if (i != 0) {
                //当前不是第一点，需要设置左边距
                mParams.leftMargin = 20;
            }
            mView.setLayoutParams(mParams);
            mView.setEnabled(false);
            pointGroup.addView(mView);
        }
    }

    @Override
    public int getCount() {
        return mImageViewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mImageViewList.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViewList.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
