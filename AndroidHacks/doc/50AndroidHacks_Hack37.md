# Hack 37 向media ContentProvider添加MP3文件

## 问题：把音乐文件拷贝到外部存储器上。文件拷贝完成打开音乐播放器就可以看到该音乐文件，这个是怎么实现的呢？ 
## 解决方案：ContentProvider

在Android中，提供了一个名为ContentProvider的组件。ContentProvider用于向外部应用程序提供数据。我们可以在Android中找到一个与媒体相关的ContentProvider。    
当用户向外部存储器拷贝媒体文件时，有一个进程正在浏览所有文件夹以搜索媒体文件，这些媒体文件会被添加到与媒体相关的ContentProvider后，所有应用程序都可以访问该文件。

## 37.1 使用ContentValues添加MP3文件
与其他ContentProvider一样，可以通过ContentValues添加新元素

```
 public void onLoop1Click(View v) {
        grantPermission();
        if (!canWriteInExternalStorage()) {
            Toast.makeText(this, "Can't write file", Toast.LENGTH_SHORT).show();
            return;
        }

        //首先将文件存储到外部存储器中
        Log.d("TAG", "LOOP1: " + LOOP1_PATH);
        MediaUtils.saveRaw(this, R.raw.loop1, LOOP1_PATH);

        //完成插入媒体文件所需的所有字段
        ContentValues values = new ContentValues(5);
        values.put(Media.ARTIST, "Android");
        values.put(Media.ALBUM, "60AH");
        values.put(Media.TITLE, "hack043");
        values.put(Media.MIME_TYPE, "audio/mp3");
        values.put(Media.DATA, LOOP1_PATH);

        //通过uri将所有值插入到ContentProvider中
        getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
    }
```

## 37.2 使用MediaScanner添加MP3文件   
这个方法可以避免手动添加值    
```
// TODO:
    public void onLoop2Click(View v) {
        grantPermission();
        if (!canWriteInExternalStorage()) {
            Toast.makeText(this, "Can't write file", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        //首先将文件保存到外部存储器
        MediaUtils.saveRaw(this, R.raw.loop2, LOOP2_PATH);

//        Uri uri = Uri.parse("file://" + LOOP2_PATH);
//        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
//        //发送广播，请求扫描并添加指定的文件
//        sendBroadcast(i);


        Uri uri = Uri.fromFile(new File(LOOP2_PATH));
        // SendBroadcastPermission: action:android.intent.action.MEDIA_SCANNER_SCAN_FILE, mPermissionType:0
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);

//        //发送广播，请求扫描并添加指定的文件
        sendBroadcast(intent);
    }
```

## References:
- [Content Providers](https://developer.android.google.cn/guide/topics/providers/content-providers.html)
- [Adding mp3 to the ContentResolver](https://stackoverflow.com/questions/3735771/adding-mp3-to-the-contentresolver)
- [http://www.flashkit.com/loops/Pop-Rock/Rock/Get_P-calpomat-4517/index.php](http://www.flashkit.com/loops/Pop-Rock/Rock/Get_P-calpomat-4517/index.php)
- [http://www.flashkit.com/loops/Pop-Rock/Rock/_Hard-XtremeWe-6500/index.php](http://www.flashkit.com/loops/Pop-Rock/Rock/_Hard-XtremeWe-6500/index.php)