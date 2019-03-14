package com.w.wenda.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CommonLoading extends View {

    private int w;
    private int h;
    private Paint paint;
    private int bgColor = Color.parseColor("#ffffff");
    private int mainColor = Color.parseColor("#006cb5");
    private int textColor = Color.parseColor("#444444");

    private int startAngle = 0;

    private String text = "请稍候";

    private ValueAnimator valueAnimator;


    public CommonLoading(Context context) {
        this(context,null);
    }

    public CommonLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CommonLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0,0);
        paint.setColor(bgColor);
        RectF bgRect = new RectF(0,0,w,h);
        canvas.drawRoundRect(bgRect,10,10,paint);

        paint.setColor(mainColor);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        RectF arcRectF = new RectF(w/10*3,h/6,w/10*7,h/2);
        canvas.drawArc(arcRectF,startAngle,270,false,paint);

        paint.setColor(textColor);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        Rect rect = new Rect();
        paint.setTextSize(40);
        paint.getTextBounds(text,0,text.length(),rect);

        canvas.translate(w/2,h/4*3);
        canvas.drawText(text,-rect.width()/2,0,paint);
    }

    private void init(){
        paint = new Paint();
        paint.setColor(mainColor);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initAnim();
    }

    private void initAnim(){
        valueAnimator = ValueAnimator.ofInt(0,360);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(300);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                startAngle = (int)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void hide(){
        if (valueAnimator!=null){
//            valueAnimator.end();
        }
    }
}
