package com.example.jdemo;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private int mHorizontalSpacing = dp2px(16); //每个item横向间距
    private int mVerticalSpacing = dp2px(8); //每个item横向间距

    private List<List<View>> allLines = new ArrayList<>(); // 记录所有的行，一行一行的存储，用于布局
    List<Integer> lineHeights = new ArrayList<>(); // 记录每一行的行高，用于布局

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 重写测量
        clearMeasureParams(); // 根据父布局的不同，可能会调用两次测量，所以clear 一下
        //先测量孩子
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);  // 解析父view 传过来的MeasureSpec
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

        List<View> lineViews = new ArrayList<>(); //保存一行中的所有的view
        int lineWidthUsed = 0; //记录这行已经使用了多宽的size
        int lineHeight = 0; // 一行的行高

        int parentNeededWidth = 0;  // measure过程中，子View要求的父ViewGroup的宽
        int parentNeededHeight = 0; // measure过程中，子View要求的父ViewGroup的高

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams childLP = childView.getLayoutParams(); // 子view 的布局参数
            if (childView.getVisibility() != View.GONE) {
                //将layoutParams转变成为 measureSpec
                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                        childLP.width);
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                        childLP.height);
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec); // 测量子view

                //因为测量完了，所以可以获取子view的度量宽高
                int childMesauredWidth = childView.getMeasuredWidth();
                int childMeasuredHeight = childView.getMeasuredHeight();

                //如果需要换行
                if (childMesauredWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {
                    //一旦换行，我们就可以判断当前行需要的宽和高了，所以此时要记录下来
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);

                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);

                    lineViews = new ArrayList<>();
                    lineWidthUsed = 0;
                    lineHeight = 0;
                }
                // view 是分行layout的，所以要记录每一行有哪些view，这样可以方便layout布局
                lineViews.add(childView);
                //每行都会有自己的宽和高
                lineWidthUsed = lineWidthUsed + childMesauredWidth + mHorizontalSpacing;
                lineHeight = Math.max(lineHeight, childMeasuredHeight);

                //处理最后一行数据
                if (i == childCount - 1) {
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);
                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
                }
            }
        }

        //再测量自己,保存
        //根据子View的度量结果，来重新度量自己ViewGroup
        // 作为一个ViewGroup，它自己也是一个View,它的大小也需要根据它的父亲给它提供的宽高来度量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth: parentNeededWidth; // 不是精确的，就用自己需要的就是围绕
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ?selfHeight: parentNeededHeight;
        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allLines.size();

        int curL = getPaddingLeft();
        int curT = getPaddingTop();

        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = allLines.get(i);

            int lineHeight = lineHeights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View view = lineViews.get(j);
                int left = curL;
                int top =  curT;

                int right = left + view.getMeasuredWidth(); // 使用getMeasuredWidth()，而不用view.getWidth() ，因为布局完才能用
                int bottom = top + view.getMeasuredHeight();

                view.layout(left,top,right,bottom);
                curL = right + mHorizontalSpacing;
            }
            curT = curT + lineHeight + mVerticalSpacing;
            curL = getPaddingLeft();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void clearMeasureParams() {
        allLines.clear();
        lineHeights.clear();
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
