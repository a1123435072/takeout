package cn.zzu.takeout.ui.fragment;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import javax.inject.Inject;

import cn.zzu.takeout.R;

import cn.zzu.takeout.dagger.conponent.Fragment.DaggerHomeFragmentCoponent;
import cn.zzu.takeout.dagger.module.fragment.HomeFragmetModule;
import cn.zzu.takeout.presenter.fragment.HomeFragmentPresenter;
import cn.zzu.takeout.ui.activity.SelectLocationActivity;
import cn.zzu.takeout.ui.adapter.HomeAdapter;
import cn.zzu.takeout.utils.UIUtils;

/**
 * Created by yangg on 2017/7/21.
 * <p>
 * 1,布局
 * 2,投容器的处理
 * 1,侵入是装贪婪
 * 3,recycleView de 数据加载
 * 1,简单数据的加载
 * 2,复杂数据加载
 */

public class HomeFragment extends BaseFragment {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Inject
    HomeFragmentPresenter presenter;

    private RecyclerView recyclerView;


    private LinearLayout llTitleContainer;
    public HomeAdapter homeAdapter;
    private TextView tvHomeAddress;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homefragment, null);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llTitleContainer = view.findViewById(R.id.ll_title_container);

        tvHomeAddress = view.findViewById(R.id.home_tv_address);
        tvHomeAddress.setOnClickListener(addressListener);
        // presenter =new HomeFragmentPresenter(homeAdapter);
        DaggerHomeFragmentCoponent coponent = (DaggerHomeFragmentCoponent) DaggerHomeFragmentCoponent
                .builder()
                .homeFragmetModule(new HomeFragmetModule(this))
                .build();
        coponent.in(this);

        recyclerView = view.findViewById(R.id.rv_home);

        recyclerView.setLayoutManager(new LinearLayoutManager(
                UIUtils.getContext(),
                LinearLayoutManager.VERTICAL, false));
        homeAdapter = new HomeAdapter();

        recyclerView.setAdapter(homeAdapter);

        //rv设置滑动监听
        recyclerView.addOnScrollListener(rvListener);
        //加载数据
        presenter.getData();
    }

    /**
     * 监听RV的滚动
     * 计算滑动距离的总和  占用最大距离的百分比
     * 使用ArgbEvaluator 计算百分比对应的透明度的颜色值
     * 设置头布局北京色
     */
    private int sumY = 0;
    private float scope = 150;
    private ArgbEvaluator evaluator = new ArgbEvaluator();
    private RecyclerView.OnScrollListener rvListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            System.out.println("dx-->" + dx + "dy-->" + dy);
            sumY += dy;
            int color = 0X553190E8;
            if (sumY > 0) {
                float percent = sumY > scope ? 1 : sumY / scope;
                color = (Integer) evaluator.evaluate(percent, 0X553190E8, 0XFF3190E8);
            }

            llTitleContainer.setBackgroundColor(color);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData();

    }

    public void filed(String msg) {

    }

    public void success() {

    }

    public HomeAdapter getAdapter() {

        return homeAdapter;

    }

    private View.OnClickListener addressListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(UIUtils.getContext(), SelectLocationActivity.class);
            startActivityForResult(intent, 200);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //在Fragment内部接收不到数据,需要通过activity在中间传递数据

    }
}
