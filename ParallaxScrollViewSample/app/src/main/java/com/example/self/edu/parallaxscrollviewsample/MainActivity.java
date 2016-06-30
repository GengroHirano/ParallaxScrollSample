package com.example.self.edu.parallaxscrollviewsample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity implements ObservableScrollView.ScrollCangeCallBack{

  private ObservableScrollView mScrollView ;
  private ImageView mHeaderImageView ;
  private Toolbar mToolBar ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mToolBar = (Toolbar)findViewById(R.id.toolbar) ;
    //最初はツールバーは透明にしておくとGood
    mToolBar.getBackground().setAlpha(0);
    mToolBar.setTitleTextColor(Color.argb(0, 255, 255, 255));
    //ToolBarにアクションバーの機能を持たせる。
    setSupportActionBar(mToolBar);

    //ScrollViewにコールバックをセットする。
    mScrollView = (ObservableScrollView)findViewById(R.id.scrollView) ;
    mScrollView.setScrollChangeCallBack(this);

    //ヘッダーコンテンツはメンバ変数に入れておく。あとで使うからね。
    mHeaderImageView = (ImageView)findViewById(R.id.imageView) ;

  }

  @Override
  public void onScrollChanged(int deltaX, int deltaY) {
    //ぶっちゃけ戻り値のdeltaX, deltaYは使ってない。
    int scrollY = mScrollView.getScrollY() ;

    //スクロールを半分にすることによってパララックス効果を得る
    mHeaderImageView.setTranslationY(scrollY * 0.5f);

    //スクロールしただけツールバーを動かすことによって常に上に固定される
    //これはこの処理をコメントアウトして見てみ？面白いから
    mToolBar.setTranslationY(scrollY);

    //スクロールした分だけツールバーを不透明にする
    int alpha = (int)(((float)scrollY / mHeaderImageView.getHeight()) * 255) ;
    if (alpha < 0) {
      alpha = 0 ;
    } else if (alpha > 255) {
      alpha = 255 ;
    }
    //alphaだけ変える便利なカラー指定が有れば募集する
    mToolBar.setTitleTextColor(Color.argb(alpha, 255, 255, 255));
    mToolBar.getBackground().setAlpha(alpha);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void nextButtonClick(View v) {
    Intent intent = new Intent(this, ParallaxListActivity.class) ;
    startActivity(intent);
  }
}
