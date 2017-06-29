package fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yls.newclient.NewDetailActivity;
import com.example.yls.newclient.R;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;
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
    private NewAdapter newAdapter;
    private SpringView springView;
    private View headerView;
    private int pageNo=1;
    private List<NewEntity.ResultBean> listDatas;
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
        newAdapter=new NewAdapter(null,getContext());
        listView.setAdapter(newAdapter);
        textView.setText(channelId);
        initSpringView();
    }

    private void initSpringView() {
        springView= (SpringView) mRoot.findViewById(R.id.spring_view);

        springView.setHeader(new MeituanHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer(true);
            }

            @Override
            public void onLoadmore() {
                getDataFromServer(false);

            }
        });
    }

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                NewEntity.ResultBean newBean= (NewEntity.ResultBean) parent.getItemAtPosition(position);
                int index=position;
                if(listView.getHeaderViewsCount()>0){
                    index=index-1;
                }
                NewEntity.ResultBean newBean=listDatas.get(index);
                Intent intent=new Intent(getActivity(), NewDetailActivity.class);
                intent.putExtra("news",newBean);
                startActivity(intent);

            }
        });

    }

    @Override
    public void initData() {
        getDataFromServer(true);
    }

    private void getDataFromServer(final boolean refresh) {
        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
        if(refresh){
            pageNo=1;
        }
        String url = URLManager.getUrl(channelId,pageNo);

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
                listDatas=newEntity.getResult();
                if(refresh){
                    showData(listDatas);
                }else {
                    newAdapter.appendDatas(listDatas);
                }
                pageNo++;
                // 显示数据到列表中
               springView.onFinishFreshAndLoad();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });
      }

    private void showData(List<NewEntity.ResultBean> listDatas) {
        if (listView.getHeaderViewsCount() > 0) {
            listView.removeHeaderView(headerView);
        }
        NewEntity.ResultBean firstNews = listDatas.get(0);
        if (firstNews.getAds() != null && firstNews.getAds().size() > 0) {
            headerView = LayoutInflater.from(getContext()).inflate(R.layout.list_header, listView, false);
            SliderLayout sliderLayout = (SliderLayout) headerView.findViewById(R.id.slider_layout);
            List<NewEntity.ResultBean.AdsBean> ads = firstNews.getAds();
            for (int i = 0; i < ads.size(); i++) {
                NewEntity.ResultBean.AdsBean adBean = ads.get(i);
                TextSliderView textSliderView = new TextSliderView(getContext());

                textSliderView.description(adBean.getTitle()).image(adBean.getImgsrc());
                sliderLayout.addSlider(textSliderView);
            }
            listView.addHeaderView(headerView);
        } else {

        }
        newAdapter.setDatas(listDatas);
    }
}
