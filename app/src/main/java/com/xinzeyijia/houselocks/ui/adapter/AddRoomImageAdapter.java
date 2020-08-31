package com.xinzeyijia.houselocks.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.xinzeyijia.houselocks.R;
import com.xinzeyijia.houselocks.util.ImageUtil;

import java.io.File;

import static com.xinzeyijia.houselocks.util.chicun.DensityUtil.getScreenWidth;
import static com.xinzeyijia.houselocks.utils.DensityUtil.dp2px;


/**
 * author：luck
 * project：PictureSelector
 * email：893855882@qq.com
 * data：仿微信发朋友圈 选择照片功能 Adapter
 */
public class AddRoomImageAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {
    private static final int TYPE_CAMERA = 1;//相机
    private static final int TYPE_PICTURE = 2;//相册

    private int width;
    private int height;
    private int selectMax = 9;
    private Context mContext;

    /**
     * 点击添加图片跳转
     */
    public AddRoomImageAdapter(Context context) {
        super(R.layout.add_img_item);
        mContext = context;

        if (width == 0) {
            width = getScreenWidth(mContext);
            height = width / 3 - dp2px(30);
        }
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }


    @Override
    public int getItemCount() {
        if (getData().size() < selectMax) {
            return getData().size() + 1;
        } else {
            return getData().size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, LocalMedia item) {
        ImageView img = helper.getView(R.id.fiv_img);
//        img.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        LinearLayout linearLayout = helper.addOnClickListener(R.id.ll_del).getView(R.id.ll_del);
        TextView tvDuration = helper.getView(R.id.tv_duration);


        //少于8张，显示继续添加的图标
        if (getItemViewType(helper.getLayoutPosition()) == TYPE_CAMERA) {
            img.setImageResource(R.mipmap.add_room_img);
            img.setPadding(dp2px(10), dp2px(10), dp2px(10), dp2px(10));
            linearLayout.setVisibility(View.INVISIBLE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);

            LocalMedia media = getData().get(helper.getLayoutPosition());

            int pictureType = PictureMimeType.isPictureType(media.getPictureType());
            int mimeType = media.getMimeType();
            String path = "";
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
            // 图片
            if (media.isCompressed()) {
                Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
                Log.i("压缩地址::", media.getCompressPath());
            }

            Log.i("原图地址::", media.getPath());
            if (media.isCut()) {
                Log.i("裁剪地址::", media.getCutPath());
            }
            long duration = media.getDuration();
            tvDuration.setVisibility(pictureType == PictureConfig.TYPE_VIDEO
                    ? View.VISIBLE : View.GONE);

            if (mimeType == PictureMimeType.ofAudio()) {
                tvDuration.setVisibility(View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.picture_audio);
                StringUtils.modifyTextViewDrawable(tvDuration, drawable, 0);
            } else {
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.video_icon);
                StringUtils.modifyTextViewDrawable(tvDuration, drawable, 0);
            }
            tvDuration.setText(DateUtils.timeParse(duration));
            if (mimeType == PictureMimeType.ofAudio()) {
                img.setImageResource(R.drawable.audio_placeholder);
            } else {
                ImageUtil.getInstance().getRoundRadiusImg(mContext, path, img, 0, 0, 12, false);
            }

        }
    }

    private boolean isShowAddItem(int position) {
        int size = getData().size();
        return position == size;
    }


}
