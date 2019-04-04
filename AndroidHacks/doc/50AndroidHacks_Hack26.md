# Hack 26 为ListView添加分段标头

## 问题：为了显示较长列表，需要在列表中添加分段信息。
分段标头就像是有的手机中通讯录的样式，根据姓名首字母进行分组，分段标头一直显示在屏幕顶端。 
## 解决方案：分段表头   

![含有分段信息的列表](https://yingvickycao.github.io/img/android/AndroidHacks/hack_25_1.jpg)

在 Android中创建图中所示界面有点困难，因为ListView 中没有分段分段表头的概念，它仅仅包含列表项目。   

## 常见的实现方式： 
1. 开发者实现这个需求通常是创建两种类型的列表： 
- 常规列表用于显示数据 
- 特殊列表用于显示分段标头     
这种方式需要重写getViewTypeCount()方法，让其返回2；然后修改getView()方法，在该方法中创建并返回对应类型的列表项。    
缺点：会导致代码逻辑混乱。 

2. 在列表项中嵌入分段标头，然后根据需求显示或隐藏分段标头。(推荐)      
可以创建一个特殊的TextView，让其叠加在列表的顶部，当列表滚动到一个新的分段时，就更新其内容。 
优点：简化了创建列表以及选项列表项的逻辑。


## 在列表项中嵌入分段标头，然后根据需求显示或隐藏分段标头。(推荐)     

`activity_hack_26_header.xml` - 分段表头，随着列表滚动的分段表头，以及列表顶部固定分段。
```
<?xml version="1.0" encoding="utf-8"?><!--
  Copyright (c) 2012 Manning
  See the file license.txt for copying permission.
-->
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/header"
    style="@android:style/TextAppearance.Small"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:background="#00ff00" />
```

- `activity_hack_26.xml` - 包含顶部固定分段表头的布局文件   
```
<?xml version="1.0" encoding="utf-8"?><!--
  Copyright (c) 2012 Manning
  See the file license.txt for copying permission.
-->
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <!-- 将分段表头包含在帧布局中，这样该表头可以可以与列表重叠在一起，以显示当前所在分段。 -->
    <include layout="@layout/activity_hack_26_header" />

</FrameLayout>

```
- `activity_hack_26_list_item.xml` - 包含数据项，也包含分段标头。
```
<?xml version="1.0" encoding="utf-8"?><!--
  Copyright (c) 2012 Manning
  See the file license.txt for copying permission.
-->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <!-- 分段标头 -->
    <!-- 分段标头会在新的分段开始时显示，否则就会被隐藏  -->
    <include layout="@layout/activity_hack_26_header" />

    <!-- 数据项 -->
    <TextView
        android:id="@+id/label"
        style="@android:style/TextAppearance.Large"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content" />

</LinearLayout>
```


`Hack26Activity.java`
```
/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack26;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.TextView;

import com.example.hades.androidhacks.Countries;
import com.example.hades.androidhacks.R;

public class Hack26Activity extends ListActivity {

    /**
     * 用于访问分段表头
     */
    private TextView topHeader;
    private int topVisiblePosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_26);

        topHeader = findViewById(R.id.header);
        setListAdapter(new SectionAdapter(this, Countries.COUNTRIES));

        setTopHeader(0);
        getListView().setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        // Empty.
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        setTopHeader(firstVisibleItem);
                    }
                });
    }

    /**
     * 开始创建或者滚动滚动列表时，找到分段表头对应字母，并更新其内容。
     */
    private void setTopHeader(int pos) {
        if (!isNeedUpdateTopHeader(pos)) {
        }
        topVisiblePosition = pos;
        final String text = Countries.COUNTRIES[pos].substring(0, 1);
        topHeader.setText(text);
    }

    private boolean isNeedUpdateTopHeader(int pos) {
        return pos != topVisiblePosition;
    }
}
```

## 总结：
- 每一个 itemview 含有 有一个 header，首字母改变时显示。     
-  layout 也有一个header，作为最顶部的 TopHeader。当创建或者滚动滚动列表时，找到分段表头对应字母，并更新其内容。

## References：    
- [ListView](https://developer.android.google.cn/reference/android/widget/ListView.html)
- [BaseAdapter](https://developer.android.google.cn/reference/android/widget/BaseAdapter.html)