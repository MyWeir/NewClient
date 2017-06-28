package fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yls.newclient.NewDetailActivity;
import com.example.yls.newclient.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import apdater.NewAdapter;
import url.NewEntity;
import url.URLManager;

/**
 * Created by yls on 2017/6/27.
 */

public class NewFragment extends  BaseFragment{
    private ListView listView;
   private String channelId;
    public void setChannelId(String channelId){
        this.channelId=channelId;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_new;
    }

    @Override
    public void initView() {
        TextView textView= (TextView) mRoot.findViewById(R.id.tv_01);
        listView= (ListView) mRoot.findViewById(R.id.list_view_new);
        textView.setText(channelId);
    }

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewEntity.ResultBean newBean= (NewEntity.ResultBean) parent.getItemAtPosition(position);
                Intent intent=new Intent(getActivity(), NewDetailActivity.class);
                intent.putExtra("news",newBean);
                startActivity(intent);

            }
        });

    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
        String url = URLManager.getUrl(channelId);

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                System.out.println("----服务器返回的json数据:" + json);

                json =  json.replace(channelId, "result");
                Gson gson = new Gson();
                NewEntity newEntity = gson.fromJson(json, NewEntity.class);
                System.out.println("----解析json:" + newEntity.getResult().size());

                // 显示数据到列表中
                showData(newEntity);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });
      }

    private void showData(NewEntity newEntity) {
        if (newEntity == null
                || newEntity.getResult() == null
                || newEntity.getResult().size() == 0) {
            System.out.println("----没有获取到服务器的新闻数据");
            return;
        }
        List<NewEntity.ResultBean.AdsBean> ads=newEntity.getResult().get(0).getAds();
        if(ads!=null&&ads.size()>0){
            View headerView=View.inflate(getActivity(), R.layout.list_header,null);
            SliderLayout sliderLayout= (SliderLayout) headerView.findViewById(R.id.slider_layout);
            for(int i=0;i<ads.size();i++){
                NewEntity.ResultBean.AdsBean adBean=ads.get(i);
                TextSliderView sliderView=new TextSliderView(getActivity());
                sliderView.description(adBean.getTitle()).image(adBean.getImgsrc());
                sliderLayout.addSlider(sliderView);
                sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        showToast(slider.getDescription());
                    }
                });
            }
            listView.addHeaderView(headerView);
        }
                NewAdapter newAdapter = new NewAdapter(
                      getActivity(), newEntity.getResult());
        listView.setAdapter(newAdapter);
    }
}
