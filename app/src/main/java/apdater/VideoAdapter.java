package apdater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yls.newclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import url.VideoEntity;

/**
 * Created by yls on 2017/6/29.
 */

public class VideoAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VideoEntity.ResultBean> listDatas;

    public VideoAdapter(Context context, List<VideoEntity.ResultBean> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        // 列表项对应的数据
       final  VideoEntity.ResultBean video = listDatas.get(position);
       // 显示标题
        holder.tvVideoTitle.setText(listDatas.get(position).getTitle());
        // 显示视频播放时长
        System.out.println("-----duration: " + video.getLength());
        String durationStr = DateFormat.format("mm:ss", video.getLength() * 1000).toString();
        holder.tvVideoDuration.setText(durationStr);
        // 显示播放次数
        holder.tvPlayCount.setText(video.getPlayCount() + "");
        // 显示视频预览图片
        Picasso.with(context).load(video.getCover()).into(holder.ivVideoImage);
        // 点击列表项时，跳转进入视频播放详情界面
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDatas == null ? 0 : listDatas.size();
    }

   private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivVideoImage;
        private TextView tvVideoTitle;
        private TextView tvVideoDuration;
        private TextView tvPlayCount;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivVideoImage = (ImageView) itemView.findViewById(R.id.iv_video_image);
            tvVideoTitle = (TextView) itemView.findViewById(R.id.tv_video_title);
            tvVideoDuration = (TextView) itemView.findViewById(R.id.tv_video_duration);
            tvPlayCount = (TextView) itemView.findViewById(R.id.tv_play_count);
        }
    }
}