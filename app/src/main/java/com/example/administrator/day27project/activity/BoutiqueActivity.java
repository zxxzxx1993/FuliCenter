package com.example.administrator.day27project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.fragment.BoutiqueChildFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BoutiqueActivity extends AppCompatActivity {
    @Bind(R.id.goods_name)
    TextView goodsName;
    int catId;
    BoutiqueActivity context;
    BoutiqueChildFragment  mBoutiqueChildFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        ButterKnife.bind(this);
        catId= getIntent().getIntExtra(I.Boutique.CAT_ID, 0);
        initFragment();
    }

    private void initFragment() {
        mBoutiqueChildFragment = new BoutiqueChildFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_boutique,mBoutiqueChildFragment)
                .show(mBoutiqueChildFragment)
                .commit();
    }


    @OnClick(R.id.back)
    public void onClick() {
         finish();
    }
}
