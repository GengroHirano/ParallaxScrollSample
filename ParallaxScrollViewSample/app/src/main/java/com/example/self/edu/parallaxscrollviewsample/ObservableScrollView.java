package com.example.self.edu.parallaxscrollviewsample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

  public interface ScrollCangeCallBack {
    void onScrollChanged(int deltaX, int deltaY) ;
  }

  private ScrollCangeCallBack mCallBack ;

  public ObservableScrollView(Context context) {
    super(context);
  }

  public ObservableScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setScrollChangeCallBack(ScrollCangeCallBack callBack) {
    mCallBack = callBack ;
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (mCallBack != null) {
      mCallBack.onScrollChanged(l - oldl, t - oldt);
    }
  }
}
