## 权限申请库

## 引入

**Step 1.** Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency



```
	dependencies {
	        implementation 'com.github.threekilogram:AndroidPermission:1.3.1'
	}
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