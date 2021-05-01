package com.yaxon.bubiao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yaxon.bubiao.R;
import com.yaxon.bubiao.utils.FConversUtils;
import com.yaxon.bubiao.utils.FLog;
import com.yaxon.bubiao.utils.FontUtil;



/**
 * autour : openX
 * date : 2017/7/24 10:46
 * className : ProgressBar
 * version : 1.0
 * description : 进度条
 */
public class MySeekBar extends View {


    private int min = 0;
    private int max = 100;
    private int progress = 50;
    private float barSize;
    private float textSize;
    private float buttonRaidus;
    private float textSpace = FConversUtils.dip2px(getContext(), 3);    //文字间距
    private int textColor = Color.parseColor("#FF9B9B9B");
    private int textColorPro = Color.parseColor("#FFFF6E47");


    private int colorBackground = Color.parseColor("#FFD8D8D8");
    private int colorPro = Color.parseColor("#FFFF6E47");
    private int colorButton = Color.parseColor("#FFEDEDED");

    private float textHeight, textLead, textWidth;
    protected Paint paint;
    protected Paint paintLabel;
    protected RectF rectChart;    //图表矩形

    public MySeekBar(Context context) {
        this(context, null);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            //获取自定义属性的值
            TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MySeekBar, defStyle, 0);
            textSize = ta.getDimension(R.styleable.MySeekBar_textSize, 1);
            textSpace = ta.getDimension(R.styleable.MySeekBar_textSpace, 1);
            barSize = ta.getDimension(R.styleable.MySeekBar_barSize, 1);
            buttonRaidus = ta.getDimension(R.styleable.MySeekBar_buttonRaidus, 1);
            min = ta.getInt(R.styleable.MySeekBar_min, 1);
            max = ta.getInt(R.styleable.MySeekBar_max, 1);
            progress = ta.getInt(R.styleable.MySeekBar_progress, 1);
            ta.recycle();
        }
        paint = new Paint();
        paint.setAntiAlias(true);
        paintLabel = new Paint();
        paintLabel.setAntiAlias(true);

        paintLabel.setTextSize(textSize);
        textHeight = FontUtil.getFontHeight(paintLabel);
        textLead = FontUtil.getFontLeading(paintLabel);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        heightSize = (int) (textHeight + textSpace + buttonRaidus*2)+getPaddingTop()+getPaddingBottom();
        FLog.w("高度："+heightSize);
        setMeasuredDimension(widthSize, heightSize);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectChart = new RectF(getPaddingLeft(),getPaddingTop(),getMeasuredWidth()-getPaddingRight(),
                getMeasuredHeight()-getPaddingBottom());
        barCenter = rectChart.bottom - buttonRaidus;
        barleft = rectChart.left+FontUtil.getFontlength(paintLabel, ""+min) + textSpace;
        barRight = rectChart.right-FontUtil.getFontlength(paintLabel, ""+max) - textSpace;
    }
    float barCenter;
    float barleft;
    float barRight;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float pro = (float)(progress-min) / (float) (max-min);
        float proX = barleft + (barRight - barleft) * pro;

        paintLabel.setColor(textColor);
        canvas.drawText(""+min, rectChart.left,
                barCenter - textHeight/2 + textLead, paintLabel);
        canvas.drawText(""+max, barRight + textSpace,
                barCenter - textHeight/2 + textLead, paintLabel);
        paintLabel.setColor(textColorPro);
        canvas.drawText(""+progress, proX - FontUtil.getFontlength(paintLabel, ""+progress)/2,
                rectChart.top+ textLead, paintLabel);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorBackground);
        RectF rect = new RectF(barleft, barCenter-barSize/2, barRight, barCenter+barSize/2);
        canvas.drawRoundRect(rect, barSize/2, barSize/2, paint);
        paint.setColor(colorPro);
        rect = new RectF(barleft, barCenter-barSize/2, proX, barCenter+barSize/2);
        canvas.drawRoundRect(rect, barSize/2, barSize/2, paint);
        paint.setColor(colorButton);
        canvas.drawCircle(proX, barCenter, buttonRaidus,paint);
    }
    protected float moveX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moved();
                return true;
            case MotionEvent.ACTION_UP:
                moveX = -1;
                return true;
        }
        return true;
    }

    private void moved(){

        moveX = Math.max(moveX, barleft);
        moveX = Math.min(moveX, barRight);
        float pro = (float)(moveX-barleft) / (float)(barRight - barleft);
        progress = (int) (min+(max-min)*pro);
        FLog.w(moveX+"当前进度："+progress);
        invalidate();
    }

}
