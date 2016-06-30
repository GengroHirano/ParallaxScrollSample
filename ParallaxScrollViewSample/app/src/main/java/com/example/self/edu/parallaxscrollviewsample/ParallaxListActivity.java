package com.example.self.edu.parallaxscrollviewsample;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ParallaxListActivity extends ActionBarActivity {

  ListView mListView ;
  Toolbar mToolBar ;
  int headerTopCoordinate = 0 ;

  @SuppressLint("InflateParams")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_parallax_list);

    mToolBar = (Toolbar)findViewById(R.id.toolbar) ;
    mToolBar.getBackground().setAlpha(0);
    mToolBar.setTitleTextColor(Color.argb(0, 255, 255, 255));
    setSupportActionBar(mToolBar);

    //ダミーのリストアイテムをセット
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1) ;
    for (int i = 0; i < 50; i++) {
      adapter.add("row :" + i);
    }

    final View headerView = LayoutInflater.from(this).inflate(R.layout.sample_header_view, null, false) ;
    final View headerContent = headerView.findViewById(R.id.image) ;
    mListView = (ListView)findViewById(R.id.listView) ;
    mListView.addHeaderView(headerView);
    mListView.setAdapter(adapter);
    mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        //ヘッダーを描画している矩形情報を取得する
        Rect rect = new Rect() ;
        headerContent.getLocalVisibleRect(rect) ;

        //矩形のトップ座標と以前保存した矩形のトップ座標と違っていればパララックス効果を与える
        if (headerTopCoordinate != rect.top) {
          //現在表示している矩形のトップ座標を保存する
          headerTopCoordinate = rect.top ;
          //ヘッダーとしてセットしているViewの位置を変化している矩形の半分だけズラす
          headerContent.setTranslationY(rect.top * 0.5f);
        }

        //今度はスクロールした分だけツールバーの移動はしなくてもいいけどもっと面倒

        //スクロールした分だけツールバーを不透明にする
        //ListViewにはgetScrollYを使っても常に0が返ってくるため、ヘッダーとしてセットしているViewのY座標の絶対値から判断する
        int alpha = (int)((Math.abs(headerView.getY() / headerContent.getHeight())) * 255) ;
        if (alpha < 0) {
          alpha = 0 ;
        } else if (alpha > 255) {
          alpha = 255 ;
        }
        mToolBar.setTitleTextColor(Color.argb(alpha, 255, 255, 255));
        mToolBar.getBackground().setAlpha(alpha);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_parallax_list, menu);
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
}
