package com.simple.viewpagertransforms;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simple.transformslibrary.CardSlideTransformer;
import com.simple.transformslibrary.Flip3DTransform;
import com.simple.transformslibrary.FlipHorizontalTransformer;
import com.simple.transformslibrary.TransformUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<View> viewList = new ArrayList<>();
    private int[] colors = {Color.parseColor("#00BFFF"), Color.parseColor("#FF1493")
            , Color.parseColor("#8B0000"), Color.parseColor("#008B8B")
            , Color.parseColor("#8B008B")};
    private TextView mTextview;
    private String[] transformNames = {"卡片滑动", "水平翻转", "3D翻转", "4", "5", "6", "7"};
    private Class[] clazzArray = {CardSlideTransformer.class, FlipHorizontalTransformer.class
            , Flip3DTransform.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTextview = (TextView) findViewById(R.id.textview);

        for (int i = 0; i < 5; i++) {
            View rootView = View.inflate(MainActivity.this, R.layout.item_pager, null);
            rootView.setBackgroundColor(colors[i]);
            TextView textView = (TextView) rootView.findViewById(R.id.pager_tv);
            textView.setText(String.valueOf(i + 1));
            viewList.add(rootView);
        }

        int position = 0;
        try {
            TransformUtil.reverse(mViewPager,
                    (ViewPager.PageTransformer) clazzArray[position].newInstance());
            mTextview.setText(transformNames[position]);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mViewPager.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

    }
}
