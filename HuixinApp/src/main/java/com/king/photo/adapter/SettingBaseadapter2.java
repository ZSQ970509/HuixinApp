package com.king.photo.adapter;

import java.util.ArrayList;

import com.hc.android.huixin.AboutActivity;
import com.hc.android.huixin.AppUpdateActivity;
import com.hc.android.huixin.PermissionsSettingsActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.SettingItem;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.activity.SettingActivity;
import com.king.photo.adapter.PagingBaseadapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingBaseadapter2 extends BaseAdapter {
    Handler mHandler = new Handler();
    Context context;
    ArrayList<SettingItem> list;

    public SettingBaseadapter2(Context context, ArrayList<SettingItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class ViewHolder {
        LinearLayout mLinearLayout;
        TextView mText;
        ImageView mImageView;
        ImageView mFootLine;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_setting_2, null);
            viewHolder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll);
            viewHolder.mText = (TextView) convertView.findViewById(R.id.text);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);

            viewHolder.mFootLine = (ImageView) convertView.findViewById(R.id.foot_line);
            viewHolder.mText.setText(list.get(position).name + "");
            viewHolder.mImageView.setBackgroundResource(list.get(position).imageId);
            if (list.get(position).imageId == 0) {
                viewHolder.mImageView.setVisibility(View.GONE);
            }
            if (position == getCount() - 1) {
                viewHolder.mFootLine.setVisibility(View.GONE);
            }

            viewHolder.mLinearLayout.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub

                    ImageView mFootLine = (ImageView) v.findViewById(R.id.foot_line);
                    // 第一个
                    if (position == 0) {
                        View vNext = parent.getChildAt(position + 1);

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:

                                mFootLine.setBackgroundResource(R.color.setting_color);
                                break;
                            case MotionEvent.ACTION_UP:
                                mFootLine.setBackgroundResource(R.drawable.setting_line);
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                mFootLine.setBackgroundResource(R.drawable.setting_line);
                                break;
                            default:

                                break;
                        }
                        // 最后一个
                    } else if (position == getCount() - 1) {
                        View vLast = parent.getChildAt(position - 1);
                        ImageView mHeadLineLast = (ImageView) vLast.findViewById(R.id.foot_line);
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                mFootLine.setBackgroundResource(R.color.setting_color);
                                mHeadLineLast.setBackgroundResource(R.color.setting_color);
                                break;
                            case MotionEvent.ACTION_UP:
                                mFootLine.setBackgroundResource(R.drawable.setting_line);
                                mHeadLineLast.setBackgroundResource(R.drawable.setting_line);
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                mFootLine.setBackgroundResource(R.drawable.setting_line);
                                mHeadLineLast.setBackgroundResource(R.drawable.setting_line);
                                break;
                            default:

                                break;
                        }
                    } else {
                        View vLast = parent.getChildAt(position - 1);
                        ImageView mHeadLineLast = (ImageView) vLast.findViewById(R.id.foot_line);
                        View vNext = parent.getChildAt(position + 1);
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                mFootLine.setBackgroundResource(R.color.setting_color);
                                mHeadLineLast.setBackgroundResource(R.color.setting_color);
                                break;
                            case MotionEvent.ACTION_UP:
                                mFootLine.setBackgroundResource(R.drawable.setting_line);
                                mHeadLineLast.setBackgroundResource(R.drawable.setting_line);
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                mFootLine.setBackgroundResource(R.drawable.setting_line);
                                mHeadLineLast.setBackgroundResource(R.drawable.setting_line);
                                break;
                            default:

                                break;
                        }
                    }

                    return false;
                }
            });

            viewHolder.mLinearLayout.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    // View v = parent.getChildAt(getCount() - 1 - position);
                    switch (list.get(position).id) {
                        case 1236:
                            jumpActivity(context, AppUpdateActivity.class, "在线更新");
                            break;
                        case 1237:
                            jumpActivity(context, AboutActivity.class, "关于");
                            break;
                        case 1238:
                            jumpActivity(context, PermissionsSettingsActivity.class, "权限设置");
                            break;
                        default:
                            break;
                    }
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;

    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;

    private void jumpActivity(Context context, Class<?> cls, String title) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

}
