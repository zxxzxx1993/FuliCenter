package com.example.administrator.day27project.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.bean.CategoryChildBean;
import com.example.administrator.day27project.fragment.CategoryChildFragment;
import com.example.administrator.day27project.view.CatChildFilterButton;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryChildActivity extends AppCompatActivity {


    CategoryChildFragment mCategoryFragment;
    int catId;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.line_goods)
    LinearLayout lineGoods;
    @Bind(R.id.childprice)
    Button childprice;
    @Bind(R.id.childTime)
    Button childTime;
    @Bind(R.id.fragment_child)
    RelativeLayout fragmentChild;
    boolean addtimeDesc = false;
    boolean priceDesc = false;
    int sortby = I.SORT_BY_PRICE_ASC;
    String name;
    ArrayList<CategoryChildBean> list;
    @Bind(R.id.btnCatChildFilter)
    CatChildFilterButton btnCatChildFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mCategoryFragment = new CategoryChildFragment();
        catId = getIntent().getIntExtra(I.Boutique.CAT_ID, 0);
        name = getIntent().getStringExtra("name");
        list = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra("list");
        initFragment();
        btnCatChildFilter.setText(name);
        btnCatChildFilter.setOnCatFilterClickListener(name,list);
    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_child, mCategoryFragment)
                .show(mCategoryFragment)
                .commit();
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick({R.id.childprice, R.id.childTime})
    public void onClick(View view) {
        Drawable right;
        switch (view.getId()) {
            case R.id.childprice:
                if (priceDesc) {
                    sortby = I.SORT_BY_PRICE_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                } else {
                    sortby = I.SORT_BY_PRICE_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                }
                childprice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                priceDesc = !priceDesc;
                break;
            case R.id.childTime:
                if (addtimeDesc) {
                    sortby = I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                } else {
                    sortby = I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                }
                childTime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                addtimeDesc = !addtimeDesc;
                break;
        }
        mCategoryFragment.getmGoodsAdapter().setSortby(sortby);
    }
}
