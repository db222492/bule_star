package com.xinzeyijia.houselocks.ui.view.datepickerlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinzeyijia.houselocks.R;
import com.xinzeyijia.houselocks.util.Tools;
import com.xinzeyijia.houselocks.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.xinzeyijia.houselocks.util.LogUtils.loge;
import static com.xinzeyijia.houselocks.util.LogUtils.logi;
import static com.xinzeyijia.houselocks.util.date_util.TimeUtils.DEFAULT_SDF;
import static com.xinzeyijia.houselocks.util.date_util.TimeUtils.HOUR_OPEN_YMD;
import static com.xinzeyijia.houselocks.util.date_util.TimeUtils.HOUR_SDF_D;
import static com.xinzeyijia.houselocks.util.date_util.TimeUtils.milliseconds2String;
import static com.xinzeyijia.houselocks.util.date_util.TimeUtils.string2Milliseconds;
import static com.xinzeyijia.houselocks.util.date_util.TimeUtils.string2string;

/**
 * Created by atuan on 2018/9/1.
 */
public class DatePopupWindow2 extends PopupWindow {

    private View rootView;
    private RecyclerView rv;
    private TextView tvStartDate;
    private TextView tvStartWeek;
    private TextView tvEndDate;
    private TextView tvEndWeek;
    private TextView tvTime;
    private TextView tvHintText;
    private TextView tvCustom;
    private ImageView btnClose;
    private TextView btnClear;
    private TextView tvOk;
    private boolean dayFalg;
    private Activity activity;
    private Date mSetDate;
    private String currentDate;
    private String startDesc;
    private String endDesc;
    private String endDate = "";
    private String day = "";
    private String customDate = "";
    private String begDate = "";
    private int startGroupPosition = -1;
    private int endGroupPosition = -1;
    private int startChildPosition = -1;
    int mstartChildPosition = -1;
    int mstartGroupPosition = -1;
    private int endChildPosition = -1;
    private int c_stratChildPosition = -1;//当天在列表中的子索引
    private DateAdapter mDateAdapter;
    private List<DateInfo> mList;
    private DateOnClickListener mOnClickListener = null;
    private List<String> inTimeList;
    private List<String> outTimeList;

    private DatePopupWindow2(Builder builder) {

        this.activity = builder.context;
        this.currentDate = builder.date;
        this.startDesc = builder.startDesc;
        this.endDesc = builder.endDesc;
        this.dayFalg = builder.dayFalg;
        this.startGroupPosition = builder.startGroupPosition;
        this.startChildPosition = builder.startChildPosition;
        this.endGroupPosition = builder.endGroupPosition;
        this.endChildPosition = builder.endChildPosition;
        this.mOnClickListener = builder.mOnClickListener;
        this.inTimeList = builder.inTimeList;
        this.outTimeList = builder.outTimeList;
        this.begDate = builder.begDate;
        this.endDate = builder.endDate;
        this.day = builder.day;
        this.customDate = builder.customDate;

        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.popupwindow_hotel_date2, null);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.dialogWindowAnim);
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOnDismissListener(new ShareDismissListener());
        backgroundAlpha(activity, 0.5f);

        initView();
        setInitSelect();
        create(builder.parentView);
    }

    private void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }

    private void initView() {
        tvOk = rootView.findViewById(R.id.tv_ok);
        btnClose = rootView.findViewById(R.id.btn_close);
        btnClear = rootView.findViewById(R.id.btn_clear);
        tvStartDate = rootView.findViewById(R.id.tv_startDate);
        tvStartWeek = rootView.findViewById(R.id.tv_startWeek);
        tvEndDate = rootView.findViewById(R.id.tv_endDate);
        tvEndWeek = rootView.findViewById(R.id.tv_endWeek);
        tvTime = rootView.findViewById(R.id.tv_time);
        tvHintText = rootView.findViewById(R.id.tv_hintText);
        tvCustom = rootView.findViewById(R.id.tv_custom);
        rv = rootView.findViewById(R.id.rv);
        TextView tvStartDateDesc = rootView.findViewById(R.id.tv_startDateDesc);
        TextView tvEndDateDesc = rootView.findViewById(R.id.tv_endDateDesc);
        tvStartDateDesc.setText(startDesc + "日期");
        tvEndDateDesc.setText(endDesc + "日期");
        tvStartDate.setText(string2string(begDate, DEFAULT_SDF, HOUR_SDF_D));
        tvEndDate.setText(string2string(endDate, DEFAULT_SDF, HOUR_SDF_D));
        tvTime.setText(day + "晚");
        tvOk.setOnClickListener(v -> {
            if (mOnClickListener != null && startGroupPosition != -1 || endGroupPosition != -1) {
                String startDate = mList.get(startGroupPosition)
                        .getList()
                        .get(startChildPosition)
                        .getDate();
                String endDate = mList.get(endGroupPosition)
                        .getList()
                        .get(endChildPosition)
                        .getDate();
                int days = Integer.parseInt(CalendarUtil.getTwoDay(endDate, startDate));
                String startDate2 = string2string(startDate, HOUR_SDF_D, HOUR_OPEN_YMD);
                String endDate2 = string2string(endDate, HOUR_SDF_D, HOUR_OPEN_YMD);

                mOnClickListener.getDate(startDate2, endDate2, days, startGroupPosition, startChildPosition, endGroupPosition, endChildPosition);
            } else {
                ToastUtil.show("请选择预定时间");
                return;
            }
            dismiss();
        });
        btnClose.setOnClickListener(v -> dismiss());
        btnClear.setOnClickListener(v -> {
            //重置操作
            initView();//重置当前view
            setDefaultSelect();//选中初始状态值
        });

        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        mList = new ArrayList<>();
        mDateAdapter = new DateAdapter(mList);
        rv.setAdapter(mDateAdapter);
        rv.setItemViewCacheSize(200);
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);
        initData();
    }

    @SuppressLint("SimpleDateFormat")
    private void initData() {
        SimpleDateFormat ymd_sdf = new SimpleDateFormat("yyyy-MM-dd");//当前日期转date
        try {
            if (currentDate == null) {
                new Throwable("please set one start time");
                return;
            }
            mSetDate = ymd_sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //初始化日期
        Calendar c = Calendar.getInstance();
        c.setTime(mSetDate);
        int firstM = c.get(Calendar.MONTH) + 1;//获取月份 月份是从0开始
        int days = c.get(Calendar.DATE);//日期
        int week = c.get(Calendar.DAY_OF_WEEK);//周几
        //获取当前这个月最大天数
        int maxDys = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        DateInfo info = new DateInfo();
        List<DayInfo> dayList = new ArrayList<>();//当月集合
        info.setDate(c.get(Calendar.YEAR) + "年" + firstM + "月");
        //当小于当前日期时，是不可选，setEnable(false)
        //当前月第一天是周几
        int w = CalendarUtil.getWeekNoFormat(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-01") - 1;
        //根据该月的第一天，星期几，填充上个月的空白日期
        for (int t = 0; t < w; t++) {
            DayInfo dayInfo = new DayInfo();
            dayInfo.setName("");
            dayInfo.setEnable(false);
            dayInfo.setDate("");
            dayList.add(dayInfo);
        }
        //计算当前月的天数
        for (int i = 1; i <= maxDys; i++) {
            DayInfo dayInfo = new DayInfo();
            dayInfo.setName(i + "");
            dayInfo.setDate(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + i);
            int c_year = Integer.parseInt(currentDate.split("-")[0]);
            int c_month = Integer.parseInt(currentDate.split("-")[1]);
            int c_day = Integer.parseInt(currentDate.split("-")[2]);
            if (c_year == c.get(Calendar.YEAR) && c_month == (c.get(Calendar.MONTH) + 1) && c_day == i) {
                c_stratChildPosition = dayList.size();
            }
            if (i < days) {
                dayInfo.setEnable(false);
            } else {
                dayInfo.setEnable(true);
            }

            myRemoveDay(dayInfo);//当前月份  判断符合条件的天就设置不可点击

            dayList.add(dayInfo);
        }
        info.setList(dayList);
        mList.add(info);
        //获取剩余的月的数据
        for (int i = 1; i <= 5; i++) {
            //当前月份循环加1
            c.add(Calendar.MONTH, 01);
            DateInfo nextInfo = new DateInfo();
            List<DayInfo> nextdayList = new ArrayList<>();
            int maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            nextInfo.setDate(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月");
            //周几
            int weeks = CalendarUtil.getWeekNoFormat(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-01") - 1;
            //根据该月的第一天，星期几，填充上个月的空白日期
            for (int t = 0; t < weeks; t++) {
                DayInfo dayInfo = new DayInfo();
                dayInfo.setName("");
                dayInfo.setEnable(false);
                dayInfo.setDate("");
                nextdayList.add(dayInfo);
            }
            //该月的所有日期
            for (int j = 0; j < maxDays; j++) {
                DayInfo dayInfo = new DayInfo();
                dayInfo.setName((j + 1) + "");
                dayInfo.setEnable(true);
                dayInfo.setDate(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + (j + 1));
                myRemoveDay(dayInfo);//出当前月份剩余月份  判断符合条件的天就设置不可点击
                nextdayList.add(dayInfo);
            }
            nextInfo.setList(nextdayList);
            mList.add(nextInfo);
        }
        mDateAdapter.updateData();
    }

    /**
     * 判断符合条件的天就设置不可点击
     *
     * @param dayInfo
     */
    private void myRemoveDay(DayInfo dayInfo) {
        long tempTime = 0;

        for (int j = 0; j < inTimeList.size(); j++) {
            long strTime = string2Milliseconds(string2string(inTimeList.get(j), DEFAULT_SDF, HOUR_SDF_D), HOUR_SDF_D);//已预订的开始时间  先抹掉小时和分钟，再转化成毫秒值
            long endTime = string2Milliseconds(string2string(outTimeList.get(j), DEFAULT_SDF, HOUR_SDF_D), HOUR_SDF_D);//已预订的结束时间
            //已预订的结束时间
            long dayTime = string2Milliseconds(dayInfo.getDate(), HOUR_SDF_D);//当前时间
            if (dayTime >= strTime && dayTime <= endTime) {
                dayInfo.setSelect(false);
                dayInfo.setEnable(false);
                dayInfo.setStatus(3);
            } else {
                if (Tools.NotNull(customDate)) {
                    long mendTime = string2Milliseconds(string2string(customDate, DEFAULT_SDF, HOUR_SDF_D), HOUR_SDF_D);//已预订的结束时间
                    if (dayTime <= mendTime) {
                        dayInfo.setSelect(false);
                        dayInfo.setEnable(false);
                        if (dayInfo.getStatus() != 3)
                            dayInfo.setStatus(0);
                    }
                }
            }

//            int parseInt = Integer.parseInt(milliseconds2String(string2Milliseconds(outTimeList.get(j), DEFAULT_SDF), HH));
//            if (milliseconds2String(dayTime, HOUR_SDF_D).equals(milliseconds2String(endTime, HOUR_SDF_D)) && parseInt < 12) {
////                dayInfo.setSelect(true);
//                dayInfo.setEnable(true);
//                dayInfo.setStatus(3);
//            }
            //拿到当前预定的离店时间，找到当前离店时间之后的下一个入住时间，将下一个入住时间之后的日期全部置灰
            long mstrTime = string2Milliseconds(inTimeList.get(j), DEFAULT_SDF);
            long atTime = string2Milliseconds(string2string(endDate, DEFAULT_SDF, DEFAULT_SDF), DEFAULT_SDF);//当前的离店时间
            if (atTime <= mstrTime) {
                tempTime = string2Milliseconds(string2string(inTimeList.get(j), DEFAULT_SDF, HOUR_SDF_D), HOUR_SDF_D);
                break;
            }
        }
        long dayTime = string2Milliseconds(dayInfo.getDate(), HOUR_SDF_D);//已预订的结束时间
        if (tempTime != 0 && dayTime >= tempTime) {
            dayInfo.setSelect(false);
            dayInfo.setEnable(false);
            dayInfo.setStatus(0);
        }
    }

    /**
     * 判断如果选择的范围里是否包含有已预订的时间，提示此时间段包含已预订的日期
     *
     * @param mList
     * @param startGroupPosition
     * @param startChildPosition
     * @param endGroupPosition
     * @param endChildPosition
     */
    private boolean myCheckRemoveDay(List<DateInfo> mList, int startGroupPosition, int startChildPosition, int endGroupPosition, int endChildPosition) {
        String date1 = mList.get(startGroupPosition).getList().get(startChildPosition).getDate();
        String date2 = mList.get(endGroupPosition).getList().get(endChildPosition).getDate();
        long startTime = string2Milliseconds(date1, HOUR_SDF_D);//选中的开始时间点
        long endTime = string2Milliseconds(date2, HOUR_SDF_D);//选中的结束时间点
        for (int i = startGroupPosition; i <= endGroupPosition; i++) {
            for (DayInfo dayInfo : mList.get(i).getList()) {
                String date = dayInfo.getDate();
                logi("all_data" + date);
                if (!date.isEmpty()) {
                    long time = string2Milliseconds(date, HOUR_SDF_D);//选中的每一个时间点
                    if (startTime < time && endTime > time) {
                        logi("data" + date);
                        if (endCheck(time)) return true;//拿到选中时间段的时间，放入已预订的时间段做表如果包含就返回true
                    }

                }
            }
        }
        return false;
    }

    //拿到选中时间段的时间，放入已预订的时间段做表如果包含就返回true
    private boolean endCheck(long time) {
        for (int j = 0; j < inTimeList.size(); j++) {
            long strETime = string2Milliseconds(string2string(inTimeList.get(j), DEFAULT_SDF, HOUR_SDF_D), HOUR_SDF_D);//已预订的开始时间  先抹掉小时和分钟，再转化成毫秒值
            long endETime = string2Milliseconds(string2string(outTimeList.get(j), DEFAULT_SDF, HOUR_SDF_D), HOUR_SDF_D);//已预订的结束时间
            if (time >= strETime && time <= endETime) {
                if (milliseconds2String(time, HOUR_SDF_D).equals(milliseconds2String(strETime, HOUR_SDF_D)) || milliseconds2String(time, HOUR_SDF_D).equals(milliseconds2String(endETime, HOUR_SDF_D))) {
                    return true;
                }
            }
        }
        return false;
    }


    private void setInitSelect() {
        if (0 <= this.startGroupPosition && this.startGroupPosition < mList.size() && 0 <= this.endGroupPosition && this.endGroupPosition < mList.size()) {
            int maxEndChild = mList.get(this.endGroupPosition).getList().size();
            int maxStartChild = mList.get(this.startGroupPosition).getList().size();
            if (0 <= this.startChildPosition && this.startChildPosition < maxStartChild && 0 <= this.endChildPosition && this.endChildPosition < maxEndChild) {
                setInit();
            } else {
                setDefaultSelect();//设置根据mDate设定今天和明天日期
            }
        } else {
            setDefaultSelect();//设置根据mDate设定今天和明天日期
        }
    }

    private void setInit() {
        mList.get(this.startGroupPosition).getList().get(this.startChildPosition).setStatus(1);
        mList.get(this.endGroupPosition).getList().get(this.endChildPosition).setStatus(2);
        mDateAdapter.notifyDataSetChanged();
        getoffsetDate(mList.get(startGroupPosition).getList().get(startChildPosition).getDate(), mList.get(endGroupPosition).getList().get(endChildPosition).getDate(), true);
        rv.scrollToPosition(this.startGroupPosition);
    }

    //设置日历标明当前日期的状态
    @SuppressLint("SimpleDateFormat")
    private void setDefaultSelect() {
//        if (c_stratChildPosition == -1) return;
//        String date = mList.get(0).getList().get(c_stratChildPosition).getDate();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date curDate = null;
//        try {
//            curDate = sdf.parse(FormatDate(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (curDate == null) return;
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(curDate);
//        calendar.add(Calendar.DATE, 1);
//
//        int year = Integer.parseInt(date.split("-")[0]);
//        int month = Integer.parseInt(date.split("-")[1]);
//        if (year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH) + 1
//                && c_stratChildPosition < mList.get(0).getList().size() - 1) {
//            this.startGroupPosition = 0;
//            this.startChildPosition = c_stratChildPosition;
//            this.endGroupPosition = 0;
//            this.endChildPosition = c_stratChildPosition + 1;
//            setInit();
//        } else {
//            for (int i = 0; i < mList.get(1).getList().size(); i++) {
//                if (!TextUtils.isEmpty(mList.get(1).getList().get(i).getDate())) {
//                    this.startGroupPosition = 0;
//                    this.startChildPosition = c_stratChildPosition;
//                    this.endGroupPosition = 1;
//                    this.endChildPosition = i;
//                    setInit();
//                    break;
//                }
//            }
//        }
    }

    /**
     * 设置起始时间和结束时间的选中标识，或者设置不选中
     *
     * @param startDate
     * @param endDate
     * @param status    选中设置为true 设置不选中false
     */
    @SuppressLint("SetTextI18n")
    private void getoffsetDate(String startDate, String endDate, boolean status) {

        //更新开始日期和结束日期的信息和状态
        Calendar sCalendar = CalendarUtil.toDate(startDate);
        Calendar eCalendar = CalendarUtil.toDate(endDate);
        tvStartDate.setText((sCalendar.get(Calendar.MONTH) + 1) + "月" + sCalendar.get(Calendar.DAY_OF_MONTH) + "日");
        tvStartWeek.setText("周" + CalendarUtil.getWeekByFormat(startDate));
        tvEndDate.setText((eCalendar.get(Calendar.MONTH) + 1) + "月" + eCalendar.get(Calendar.DAY_OF_MONTH) + "日");
        tvEndWeek.setText("周" + CalendarUtil.getWeekByFormat(endDate));
        int daysOffset = Integer.parseInt(CalendarUtil.getTwoDay(endDate, startDate));
        if (daysOffset < 0) return;
        if (dayFalg) {
            tvTime.setText("共" + (daysOffset + 1) + "天");
        } else {
            tvTime.setText("共" + daysOffset + "晚");
        }
        //更改结束日期和完成按钮状态
//        llEnd.setVisibility(View.VISIBLE);
        tvHintText.setVisibility(View.GONE);
        tvOk.setText("完成");
        tvOk.setEnabled(true);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        DayInfo info = mList.get(startGroupPosition).getList().get(startChildPosition);
        try {
            c.setTime(sdf.parse(info.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //根据2个时间的相差天数去循环
        for (int i = 0; i < daysOffset; i++) {
            //下一天（目标天）
            c.add(Calendar.DATE, 1);
            //改天的日期（目标天）
            String d = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
            //循环group列表
            for (int j = 0; j < mList.size(); j++) {
                //获取该月的随机一个dayInfo
                DayInfo dayInfo = mList.get(j).getList().get(mList.get(j).getList().size() - 1);
                boolean isCheck = false;
                //判断该天是否和目标天是否是同一个月
                if (!TextUtils.isEmpty(dayInfo.getDate()) && Integer.valueOf(dayInfo.getDate().split("-")[0]) == (c.get(Calendar.YEAR))
                        && Integer.valueOf(dayInfo.getDate().split("-")[1]) == ((c.get(Calendar.MONTH) + 1))) {
                    //是同一个月，则循环该月多有天数
                    for (int t = 0; t < mList.get(j).getList().size(); t++) {
                        //找到该月的日期与目标日期相同，存在，设置选择标记
                        if (mList.get(j).getList().get(t).getDate().equals(d)) {
                            mList.get(j).getList().get(t).setSelect(status);
                            isCheck = true;
                            break;
                        }
                    }
                }
                if (isCheck) {
                    mDateAdapter.notifyItemChanged(j);
                    break;
                }
            }
        }
    }

    private String FormatDate(String date) {
        if (TextUtils.isEmpty(date)) return "";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(date.split("-")[0]);
        stringBuffer.append("-");
        stringBuffer.append(date.split("-")[1].length() < 2 ? "0" + date.split("-")[1] : date.split("-")[1]);
        stringBuffer.append("-");
        stringBuffer.append(date.split("-")[2].length() < 2 ? "0" + date.split("-")[2] : date.split("-")[2]);
        return stringBuffer.toString();
    }

    private void create(View view) {
        this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * return startDate、endDate(格式：2012-12-10)
     * 选中完成后返回开始时间和结束时间
     * return startGroupPosition、startChildPosition、endGroupPosition、endChildPosition
     * 返回选中时间区间的状态标记，监听中接收后在builder中setInitSelect()方法中直接传出入（可用于记录上次选中的状态，用户再点击进入的时候恢复上一次的区间选中状态）
     */
    public interface DateOnClickListener {
        void getDate(String startDate, String endDate, int days, int startGroupPosition, int startChildPosition, int endGroupPosition, int endChildPosition);
    }

    public static class Builder {
        private String date;
        private Activity context;
        private View parentView;
        private String startDesc;
        private String endDesc;
        private String begDate;
        private String endDate;
        private String day;
        private String customDate;
        private boolean dayFalg = true;
        private int startGroupPosition = -1;
        private int endGroupPosition = -1;
        private int startChildPosition = -1;
        private int endChildPosition = -1;
        private List<String> inTimeList;
        private List<String> outTimeList;
        private DateOnClickListener mOnClickListener = null;


        @SuppressLint("SimpleDateFormat")
        public Builder(Activity context, Date date, View parentView) {
            this.date = new SimpleDateFormat("yyyy-MM-dd").format(date);
            this.context = context;
            this.parentView = parentView;
            this.startDesc = "开始";
            this.endDesc = "结束";
            this.dayFalg = true;
        }

        public DatePopupWindow2 builder() {
            return new DatePopupWindow2(this);
        }

        public Builder setInitSelect(int startGroup, int startChild, int endGroup, int endChild) {
            this.startGroupPosition = startGroup;
            this.startChildPosition = startChild;
            this.endGroupPosition = endGroup;
            this.endChildPosition = endChild;
            return this;
        }

        public Builder setInitDate(List<String> inTimeList, List<String> outTimeList) {
            if (Tools.NotNull(begDate)) {
                for (int j = 0; j < inTimeList.size(); j++) {
                    if (inTimeList.get(j).equals(begDate)) {
                        inTimeList.remove(j);
                        outTimeList.remove(j);
                    }
                }
            }
            listSort(inTimeList);
            listSort(outTimeList);
            this.inTimeList = inTimeList;
            this.outTimeList = outTimeList;
            return this;
        }

        //按日期排序（降序）
        private void listSort(List<String> list) {//TODO 这里先排序是为了判断续住时当前的离店时间和已预订的第一个时间开始比较，如果有比当前预定时间大的，就记录下来
            //Collections的sort方法默认是升序排列，如果需要降序排列时就需要重写compare方法
            Collections.sort(list, (o1, o2) -> {
                try {
                    //获取体检日期，并把其类型由String转成Date，便于比较。
                    Date dt1 = DEFAULT_SDF.parse(o1);
                    Date dt2 = DEFAULT_SDF.parse(o2);
                    //以下代码决定按日期降序排序，若将return“-1”与“1”互换，即可实现升序。
                    //getTime 方法返回一个整数值，这个整数代表了从 1970 年 1 月 1 日开始计算到 Date 对象中的时间之间的毫秒数。
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }

                } catch (Exception e) {
                    loge("日期排序出错：" + e);
                }
                return 0;
            });
        }

        public Builder setInitDay(boolean dayFalg) {
            this.dayFalg = dayFalg;
            if (dayFalg) {
                this.startDesc = "开始";
                this.endDesc = "结束";
            } else {
                this.startDesc = "入住";
                this.endDesc = "离店";
            }
            return this;
        }

        public Builder setDateOnClickListener(DateOnClickListener mlListener) {
            mOnClickListener = mlListener;
            return this;
        }

        @NotNull
        public Builder setInitAtDate(@NotNull String begDate, @NotNull String endDate, String day) {
            this.begDate = begDate;
            this.endDate = endDate;
            this.day = day;
            return this;
        }

        @NotNull
        public Builder setEndDate(@NotNull String customDate) {
            this.customDate = customDate;
            return this;
        }

    }

    private class DateAdapter extends BaseQuickAdapter<DateInfo, BaseViewHolder> {

        DateAdapter(@Nullable List<DateInfo> data) {
            super(R.layout.adapter_hotel_select_date, data);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int positions) {
            super.onBindViewHolder(holder, positions);
            TextView tv = holder.getView(R.id.tv_date);
            tv.setText(mList.get(positions).getDate());
        }

        @Override
        protected void convert(final BaseViewHolder helper, final DateInfo item) {
            RecyclerView rv = helper.getView(R.id.rv_date);
            GridLayoutManager manager = new GridLayoutManager(activity, 7);
            rv.setLayoutManager(manager);
            final TempAdapter groupAdapter = new TempAdapter(item.getList());
            rv.setAdapter(groupAdapter);
            rv.setItemViewCacheSize(200);
            rv.setHasFixedSize(true);
            rv.setNestedScrollingEnabled(false);
            groupAdapter.setOnItemClickListener((adapter, view, position) -> {
                if (!item.getList().get(position).isEnable()) return;
                if (TextUtils.isEmpty(item.getList().get(position).getName())) return;
                if (TextUtils.isEmpty(item.getList().get(position).getDate())) return;
                int status = item.getList().get(position).getStatus();

                endGroupPosition = helper.getAdapterPosition();//起止时间都可用
                endChildPosition = position;
                startGroupPosition = helper.getAdapterPosition();
                startChildPosition = position;
                //结束
                int offset = Integer.parseInt(CalendarUtil.getTwoDay(item.getList().get(position).getDate(), string2string(begDate, DEFAULT_SDF, HOUR_SDF_D)));
//                    boolean myCheckRemoveDay = myCheckRemoveDay(mList, startGroupPosition, startChildPosition, helper.getAdapterPosition(), position);

                //判断该离开日期是否比入住时间还小，是则重新设置入住时间。
                if (offset < 0) {
                    //刷新上一个开始日期
                    tvOk.setText("请选择" + endDesc + "时间");
                    tvOk.setEnabled(false);
//                            llEnd.setVisibility(View.GONE);
                    tvHintText.setText(endDesc + "日期");
                    tvHintText.setVisibility(View.VISIBLE);
                    return;
                }
                for (DateInfo dateInfo : mList) {
                    for (DayInfo dayInfo2 : dateInfo.getList()) {
                        if (dayInfo2.getStatus() != 3) {
                            dayInfo2.setStatus(0);
                            logi("dateInfo--->" + dayInfo2.getDate());
                        }
                    }
                }
                int daysOffset = Integer.parseInt(CalendarUtil.getTwoDay(item.getList().get(position).getDate(), endDate));
                int maxday = 180 - Integer.parseInt(day);
                if (daysOffset <= maxday) {
                    tvTime.setText(daysOffset + "晚");
                    item.getList().get(position).setStatus(4);
                    mDateAdapter.notifyDataSetChanged();
                    tvCustom.setText(item.getList().get(position).getDate());
                } else {
                    ToastUtil.show("最多只能续" + maxday + "晚");
                    return;
                }


            });
        }

        public void updateData() {
            notifyDataSetChanged();
        }
    }

    private class TempAdapter extends BaseQuickAdapter<DayInfo, BaseViewHolder> {
        TempAdapter(@Nullable List<DayInfo> data) {
            super(R.layout.adapter_hotel_select_date_child, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DayInfo item) {
            String name = item.getName();
            boolean isSelect = item.isSelect();
            boolean isEnable = item.isEnable();
            int status = item.getStatus();
            helper.setText(R.id.tv_date, name);
            //默认
            if (status == 0) {
                if (isSelect) {
                    //选中
                    helper.getView(R.id.tv_date).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                    helper.getView(R.id.tv_dateDel).setVisibility(View.GONE);
                    ((TextView) helper.getView(R.id.tv_date)).setTextColor(activity.getResources().getColor(R.color.white));
                    (helper.getView(R.id.ll_bg)).setBackgroundColor(activity.getResources().getColor(R.color.light_blue));
                } else {
                    //没选中状态
                    helper.getView(R.id.tv_date).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                    helper.getView(R.id.tv_dateDel).setVisibility(View.GONE);
                    ((TextView) helper.getView(R.id.tv_date)).setTextColor(activity.getResources().getColor(R.color.black));
                    (helper.getView(R.id.ll_bg)).setBackgroundColor(activity.getResources().getColor(R.color.white));
                }
            } else if (status == 1) {
                //开始
                helper.getView(R.id.tv_date).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, startDesc);
                helper.getView(R.id.tv_status).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_dateDel).setVisibility(View.GONE);
                ((TextView) helper.getView(R.id.tv_status)).setTextColor(activity.getResources().getColor(R.color.white));
                ((TextView) helper.getView(R.id.tv_date)).setTextColor(activity.getResources().getColor(R.color.white));
                (helper.getView(R.id.ll_bg)).setBackgroundColor(activity.getResources().getColor(R.color.blue));
            } else if (status == 2) {
                //结束
                helper.getView(R.id.tv_date).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, endDesc);
                helper.getView(R.id.tv_status).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_dateDel).setVisibility(View.GONE);
                ((TextView) helper.getView(R.id.tv_status)).setTextColor(activity.getResources().getColor(R.color.white));
                ((TextView) helper.getView(R.id.tv_date)).setTextColor(activity.getResources().getColor(R.color.white));
                (helper.getView(R.id.ll_bg)).setBackgroundColor(activity.getResources().getColor(R.color.blue));
            } else if (status == 3) {
                TextView textView = helper.getView(R.id.tv_dateDel);
                if (TextUtils.isEmpty(name)) {
                    textView.setVisibility(View.GONE);
                } else {
                    textView.setText(name);
                    textView.setVisibility(View.VISIBLE);
                }
                textView.setTextColor(activity.getResources().getColor(R.color.white));
                helper.getView(R.id.tv_date).setVisibility(View.GONE);
                helper.getView(R.id.tv_status).setVisibility(View.GONE);
                //结束
                TextView view = helper.getView(R.id.tv_status);
                view.setVisibility(View.VISIBLE);
                view.setText("订");
                (helper.getView(R.id.ll_bg)).setBackgroundColor(activity.getResources().getColor(R.color.green));
            } else if (status == 4) {
                helper.getView(R.id.tv_date).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "续住");
                helper.getView(R.id.tv_status).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_dateDel).setVisibility(View.GONE);
                ((TextView) helper.getView(R.id.tv_status)).setTextColor(activity.getResources().getColor(R.color.white));
                ((TextView) helper.getView(R.id.tv_date)).setTextColor(activity.getResources().getColor(R.color.white));
                (helper.getView(R.id.ll_bg)).setBackgroundColor(activity.getResources().getColor(R.color.blue));
            }
            //设置当前日期前的样式，没选中，并状态为0情况下
            if (!isSelect && status == 0) {
                if (!isEnable) {
                    //无效
                    TextView textView = helper.getView(R.id.tv_dateDel);
                    if (TextUtils.isEmpty(name)) {
                        textView.setVisibility(View.GONE);
                    } else {
                        textView.setText(name);
                        textView.setVisibility(View.VISIBLE);
                    }
                    textView.setTextColor(activity.getResources().getColor(R.color.text_enable));
                    helper.getView(R.id.tv_date).setVisibility(View.GONE);
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.tv_date).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                    helper.getView(R.id.tv_dateDel).setVisibility(View.GONE);
                    TextView textView = helper.getView(R.id.tv_date);
                    textView.setTextColor(activity.getResources().getColor(R.color.black));
                }
            }

        }
    }

    private class ShareDismissListener implements OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(activity, 1f);
        }
    }
}
