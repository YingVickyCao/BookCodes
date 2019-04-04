# Hack 24 处理空列表
## 问题：使用 列表展示数据时，考虑列表是空的状态
## 解决方案：setEmptyView(View)方法

ListView以及其它继承自AdapterView的类可以通过setEmptyView(View)方法处理空状态，当需要绘制AdapterView时，如果适配器为null或适配器的isEmpty()方法返回true，此时会显示setEmptyView(View)方法所设置的视图。       

为了测试，所以没有为ListView设置适配器，运行代码就会显示imageview。 


`activity_hack_24.xml`
```
<?xml version="1.0" encoding="utf-8"?><!--
  Copyright (c) 2012 Manning
  See the file license.txt for copying permission.
-->
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical">

    <ListView
        android:id="@+id/my_list_view"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" />

    <ImageView
        android:id="@+id/empty_view"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:src="@drawable/empty_view" />

</FrameLayout>
```

`Hack24Activity.java`
```
 private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_24);
        mListView = (ListView) findViewById(R.id.my_list_view);
        mListView.setEmptyView(findViewById(R.id.empty_view));
    }
````

## References:
- [ListView](https://developer.android.google.cn/reference/android/widget/ListView.html)