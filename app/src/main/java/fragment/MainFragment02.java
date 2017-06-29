package fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yls.newclient.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import apdater.VideoAdapter;
import url.URLManager;
import url.VideoEntity;

/**
 * Created by yls on 2017/6/27.
 */

public class MainFragment02 extends BaseFragment{
    private RecyclerView mRecyclerView;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main_02;
    }

    @Override
    public void initView() {
        mRecyclerView= (RecyclerView) mRoot.findViewById(R.id.recycler_view);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        initVideoDatas();

    }

    private void initVideoDatas() {
        // http://c.m.163.com/nc/video/list/V9LG4B3A0/y/0-20.html
        new HttpUtils().send(HttpRequest.HttpMethod.GET, URLManager.VideoURL,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String json = responseInfo.result;
                        System.out.println("-----视频数据：" + json);
                        json=json.replace("V9LG4B3A0","result");
                        Gson gson=new Gson();
                        VideoEntity entity = gson.fromJson(json, VideoEntity.class);
                        List<VideoEntity.ResultBean> listDatas=entity.getResult();
;
                        VideoEntity newsDatas=gson.fromJson(json,VideoEntity.class);
                        System.out.println("----解析视频json:"
                                + newsDatas.getResult().size());
                        showRecyclerView(listDatas);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        error.printStackTrace();
                    }
                });
    }
    private void showRecyclerView(List<VideoEntity.ResultBean> listDatas) {
        // 设置列表布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        VideoAdapter videoAdapter = new VideoAdapter(getContext(), listDatas);
        mRecyclerView.setAdapter(videoAdapter);
    }
    }
