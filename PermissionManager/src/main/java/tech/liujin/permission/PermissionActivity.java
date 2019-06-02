package tech.liujin.permission;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liujin
 */
public class PermissionActivity extends AppCompatActivity {

      /**
       * 为不同的权限申请保存回调
       */
      private static final SparseArray<OnRequestPermissionResultListener> sRequest = new SparseArray<>();
      /**
       * 全局索引
       */
      private static       AtomicInteger                                  sIndex   = new AtomicInteger( 24 );

      /**
       * 请求码
       */
      private static final int REQUEST_CODE = 1026;

      /**
       * 标记请求的是一组权限
       */
      private static final String KEY_PERMISSION_GROUP = "KEY_PERMISSION_GROUP";
      /**
       * 标记索引
       */
      private static final String KEY_LISTENER_INDEX   = "KEY_LISTENER_INDEX";

      /**
       * 监听回调
       */
      private OnRequestPermissionResultListener mListener;

      /**
       * 请求一个权限
       *
       * @param context context
       * @param onRequestPermissionResult 结果回调
       * @param permission 请求的权限
       */
      public static void request (
          Context context,
          OnRequestPermissionResultListener onRequestPermissionResult,
          String permission ) {

            request( context, onRequestPermissionResult, new String[]{ permission } );
      }

      /**
       * 请求一组权限
       *
       * @param context context
       * @param onRequestPermissionResult 请求结果回调
       * @param permissions 权限组
       */
      public static void request (
          Context context,
          OnRequestPermissionResultListener onRequestPermissionResult,
          String... permissions ) {

            int index = sIndex.getAndAdd( 1 );
            sRequest.put( index, onRequestPermissionResult );

            Intent starter = new Intent( context, PermissionActivity.class );
            starter.putExtra( KEY_PERMISSION_GROUP, permissions );
            starter.putExtra( KEY_LISTENER_INDEX, index );
            starter.addFlags( FLAG_ACTIVITY_NEW_TASK );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );

            // 没有标题
            requestWindowFeature( Window.FEATURE_NO_TITLE );
            // 因为是dialog样式,设置窗口透明
            getWindow().setDimAmount( 0 );
            // 隐藏ActionBar
            if( getSupportActionBar() != null ) {
                  getSupportActionBar().hide();
            }
            // 设置窗口view大小为0,背景透明
            View view = new View( this );
            view.setBackgroundColor( Color.TRANSPARENT );
            view.setLayoutParams( new LayoutParams( 0, 0 ) );
            setContentView( view );

            // 获取需要请求的权限
            String[] stringArrayExtra = getIntent().getStringArrayExtra( KEY_PERMISSION_GROUP );
            int index = getIntent().getIntExtra( KEY_LISTENER_INDEX, -1 );
            mListener = sRequest.get( index );
            sRequest.delete( index );

            if( stringArrayExtra != null ) {
                  handlePermissions( stringArrayExtra );
            }
      }

      /**
       * 请求权限
       *
       * @param permissions 权限
       */
      private void handlePermissions ( String[] permissions ) {

            /* 标记需要请求的权限是否已经拥有 */
            boolean toRequest = false;
            for( String s : permissions ) {
                  if( !PermissionManager.check( this, s ) ) {
                        toRequest = true;
                        break;
                  }
            }

            // 获取请求码
            if( toRequest ) {
                  // 请求权限
                  ActivityCompat.requestPermissions( this, permissions, REQUEST_CODE );
            } else {
                  // 所有权限已经拥有
                  if( mListener != null ) {
                        for( String permission : permissions ) {
                              mListener.onResult( permission, true, false );
                        }
                  }
                  finishActivity();
            }
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

            super.onRequestPermissionsResult( requestCode, permissions, grantResults );

            if( requestCode != REQUEST_CODE ) {
                  return;
            }

            for( int i = 0; i < permissions.length; i++ ) {
                  String permission = permissions[ i ];

                  boolean success = grantResults[ i ] == PackageManager.PERMISSION_GRANTED;
                  if( success ) {
                        if( mListener != null ) {
                              mListener.onResult( permission, true, true );
                        }
                  } else {
                        if( ActivityCompat.shouldShowRequestPermissionRationale( this, permission ) ) {
                              if( mListener != null ) {
                                    mListener.onResult( permission, false, true );
                              }
                        } else {
                              if( mListener != null ) {
                                    mListener.onResult( permission, false, false );
                              }
                        }
                  }
            }

            finishActivity();
      }

      private void finishActivity ( ) {

            finish();
            overridePendingTransition( 0, 0 );
      }
}
