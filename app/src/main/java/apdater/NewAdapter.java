package apdater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yls.newclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import url.NewEntity;

/**
 * Created by yls on 2017/6/27.
 */

public class NewAdapter extends BaseAdapter {
    private Context context;
    private List<NewEntity.ResultBean> listDatas;
    private static final int ITEM_TYPE_WITH_1_IMAGE = 0;
    private static final int ITEM_TYPE_WITH_3_IMAGE = 1;

    public NewAdapter(List<NewEntity.ResultBean> listDatas,Context context) {
        this.context = context;
        this.listDatas = listDatas;
    }

    @Override
    public int getCount() {
        return (listDatas == null) ? 0 : listDatas.size()-1;
    }

    @Override
    public NewEntity.ResultBean getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewEntity.ResultBean info = (NewEntity.ResultBean) getItem(position);
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_TYPE_WITH_1_IMAGE) {

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_news_1, null);
            }

            // 查找列表item中的子控件
            ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            TextView tvSource = (TextView) convertView.findViewById(R.id.tv_source);
            TextView tvComment = (TextView) convertView.findViewById(R.id.tv_comment);

            // 显示列表item中的子控件
            tvTitle.setText(info.getTitle());
            tvSource.setText(info.getSource());
            tvComment.setText(info.getReplyCount() + "跟帖");
            Picasso.with(context).load(info.getImgsrc()).into(ivIcon);

        } else if (itemViewType == ITEM_TYPE_WITH_3_IMAGE) {
            if(convertView==null){
                convertView=View.inflate(context,R.layout.item_news_2,null);
            }
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            TextView  tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
            ImageView iv01 = (ImageView) convertView.findViewById(R.id.iv_01);
            ImageView iv02 = (ImageView) convertView.findViewById(R.id.iv_02);
            ImageView iv03 = (ImageView) convertView.findViewById(R.id.iv_03);
            tvTitle.setText(info.getTitle());
            tvComment.setText(info.getReplyCount() + "跟帖");
            try {
                Picasso.with(context).load(info.getImgsrc()).into(iv01);
                Picasso.with(context).load(info.getImgextra().get(0).getImgsrc())
                        .into(iv02);
                Picasso.with(context).load(info.getImgextra().get(1).getImgsrc())
                        .into(iv03);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
            return convertView;
    }
    public int getItemViewType(int position) {
        NewEntity.ResultBean item = getItem(position);
        if (item.getImgextra() == null || item.getImgextra().size() == 0) {
            return ITEM_TYPE_WITH_1_IMAGE;
        } else {
            return ITEM_TYPE_WITH_3_IMAGE;
        }
    }
    public int getViewTypeCount(){
        return 2;
    }

    public void appendDatas(List<NewEntity.ResultBean> listDatas) {
        this.listDatas=listDatas;
        notifyDataSetChanged();
    }

    public void setDatas(List<NewEntity.ResultBean> listDatas) {
        this.listDatas=listDatas;
        notifyDataSetChanged();
    }
}
