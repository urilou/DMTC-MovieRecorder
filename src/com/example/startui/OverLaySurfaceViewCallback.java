package com.example.startui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class OverLaySurfaceViewCallback implements SurfaceHolder.Callback {
 
  private Context ovContext;
 
  public OverLaySurfaceViewCallback(Context mContext) {
   // TODO 自動生成されたコンストラクター・スタブ
   ovContext = mContext;
  }
 
  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
    int height) {
   // TODO 自動生成されたメソッド・スタブ
  }
 
  @Override
  public void surfaceCreated(SurfaceHolder holder) {
   // TODO 自動生成されたメソッド・スタブ
   Canvas mCanvas = holder.lockCanvas();
 
   Bitmap image = BitmapFactory.decodeResource(
     ovContext.getResources(), R.drawable.ic_launcher);
   mCanvas.drawBitmap(image, 240,50, null);
    
   holder.unlockCanvasAndPost(mCanvas);
  }
 
  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
   // TODO 自動生成されたメソッド・スタブ
  }
 }