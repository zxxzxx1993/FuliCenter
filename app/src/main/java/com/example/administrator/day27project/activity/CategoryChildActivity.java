package com.example.administrator.day27project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.fragment.CategoryChildFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryChildActivity extends AppCompatActivity {


    CategoryChildFragment mCategoryFragment;
    int catId;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.childName)
    TextView childName;
    @Bind(R.id.line_goods)
    LinearLayout lineGoods;
    @Bind(R.id.childprice)
    Button childprice;
    @Bind(R.id.childTime)
    Button childTime;
    @Bind(R.id.fragment_child)
    RelativeLayout fragmentChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mCategoryFragment = new CategoryChildFragment();
        catId = getIntent().getIntExtra(I.Boutique.CAT_ID, 0);
        initFragment();
    }

    private void initFragment() {
        Log.e("initFragment", "回火");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_child, mCategoryFragment)
                .show(mCategoryFragment)
                .commit();
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }
}
