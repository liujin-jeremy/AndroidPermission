## 权限申请库

```
implementation 'tech.threekilogram:permission:2.0.2'
```

## 使用

* 请求

```
PermissionFragment.request(
    this,
    permission.WRITE_EXTERNAL_STORAGE,
    new OnRequestPermissionResultListener() {
          @Override
          public void onResult (
              String permission, 
              boolean success,
              boolean isFinalResult ) {
               
          }
    }
);
```

```
PermissionActivity.requestPermission(
    this, 
    permission.SEND_SMS,
    new OnRequestPermissionResultListener() {
          @Override
          public void onResult (
              String permission, 
              boolean success,
              boolean isFinalResult ) {
               
          }
    }
);
```

![](img/pic00.gif)