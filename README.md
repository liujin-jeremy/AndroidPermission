## 权限申请库

## 使用

* 实现结果监听

```
private class PermissionResult implements OnRequestPermissionResultListener {
      @Override
      public void onSuccess ( String permission ) {
            Log.e( TAG, "onSuccess: " + permission );
            Toast.makeText( MainActivity.this, permission + "成功", Toast.LENGTH_SHORT ).show();
      }
      @Override
      public void onFailed ( String permission ) {
            Log.e( TAG, "onFailed: " + permission );
            Toast.makeText( MainActivity.this, permission + "解释", Toast.LENGTH_SHORT ).show();
      }
      @Override
      public void onFinalDenied ( String permission ) {
            Log.e( TAG, "onFinalDenied: " + permission );
            Toast.makeText( MainActivity.this, permission + "失败", Toast.LENGTH_SHORT ).show();
      }
}
```

* 请求

```
PermissionManager.request(
    this,
    permission.WRITE_EXTERNAL_STORAGE,
    mPermissionResult
);
```

![](img/pic00.gif)