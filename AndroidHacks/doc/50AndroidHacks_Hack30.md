# Hack 30 ListView的选择模式

## 问题：如何在ListView中提供选择数据的功能。
## 解决方案：ListView的choiceMode属性。

- ListView定义了choiceMode属性，开发文档如下： 
“用于为视图定义选择行为。默认情况下，列表是没有任何选择行为的。如果把choiceMode设置为singleChoice，列表允许又一个列表项处于被选择状态。如果把choiceMode设置为multipleChoice，那么列表允许有任意数量的列表项处于被选择状态。“ 
- ListView另一个有趣的功能是：不管使用singleChoice还是multipleChoice，所选列表项的位置信息都会被自动保存 - `ListView.getCheckedItemPosition()`。    

- ListView 是如何将某个位置设置为已选或者未选？
ListView的默认视图`android.R.layout.simple_list_item_multiple_choice`，`android.R.layout.simple_list_item_single_choice`使用了 CheckedTextView。    
CheckedTextView 是 TextView 的子类，并实现了Checkable接口。   
因此，ListView是以某种方式通过Checkable接口来处理视图的选择状态，在ListView源码中有如下代码：   
```
if(mChoiceMode != CHOICE_MODE_NONE && mCheckStates != null){
    if(child instanceof Checkable){
        ((Checkable)child).setChecked(mCheckStates.get(position));
    }
}
```

**`android.widget.Checkable.java`**

```
package android.widget;

/**
 * Defines an extension for views that make them checkable.
 *
 */
public interface Checkable {
    
    /**
     * Change the checked state of the view
     * 
     * @param checked The new checked state
     */
    void setChecked(boolean checked);
        
    /**
     * @return The current checked state of the view
     */
    boolean isChecked();
    
    /**
     * Change the checked state of the view to the inverse of its current state
     *
     */
    void toggle();
}
```

所以如果需要ListView处理选择行为，需要令列表项对应的自定义视图实现Checkable接口。这种方式必须创建自定义视图。       

### 创建的CountryView类，重写所有Checkable接口方法。
**`CountryView.java`**

```
public class CountryView extends LinearLayout implements Checkable {

    private TextView mTitle;
    private CheckBox mCheckBox;

    public CountryView(Context context) {
        this(context, null);
    }

    public CountryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_hack_30_country_view, this, true);
        mTitle = v.findViewById(R.id.country_view_title);
        mCheckBox = v.findViewById(R.id.country_view_checkbox);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    /**
     * 重写所有Checkable接口方法
       这里每个被实现的接口方法都调用了mCheckBox的相应方法。这意味着，在ListView中选择一行时，会调用CountryView的setChecked()方法。 
     */
    @Override
    public boolean isChecked() {
        return mCheckBox.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        mCheckBox.setChecked(checked);
    }

    @Override
    public void toggle() {
        mCheckBox.toggle();
    }

}

```


点击某行时CheckBox中没有打勾，但点击CheckBox时，CheckBox就会打勾，这是哪里出问题了呢？    
问题出现在我们添加了一个可获取焦点的ui控件CheckBox。   
解决方法就是设置CheckBox不能点击。   
```
android:clickable="false"
android:focusable="false"
android:focusableInTouchMode="false"
```

**`country_view_checkbox.xml`**

```
<!--
  Copyright (c) 2012 Manning
  See the file license.txt for copying permission.
-->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <TextView
        android:id="@+id/country_view_title"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="0.9"
        android:padding="10dp" />

    <CheckBox
        android:id="@+id/country_view_checkbox"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="0.1"
        android:clickable="false"
        android:focusable="false"
        android:focusableInTouchMode="false"
        android:gravity="center_vertical"
        android:padding="10dp" />

</LinearLayout>

```

**`Hack30Activity.java`**

```
public class Hack30Activity extends Activity {
    private ListView mListView;
    private CountryAdapter mAdapter;
    private List<Country> mCountries;
    private String mToastFmt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_30);

        createCountriesList();
        mToastFmt = getString(R.string.activity_main_toast_fmt);
        mAdapter = new CountryAdapter(this, -1, mCountries);
        mListView = findViewById(R.id.activity_main_list);
        mListView.setAdapter(mAdapter);
        
    }

    public void onPickCountryClick(View v) {
        /**
         * 不管使用singleChoice还是multipleChoice，所选列表项的位置信息都会被自动保存 - ListView.getCheckedItemPosition()。
         */
        int pos = mListView.getCheckedItemPosition();

        if (ListView.INVALID_POSITION != pos) {
            String msg = String.format(mToastFmt, mCountries.get(pos).getName());
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void createCountriesList() {
        mCountries = new ArrayList<Country>(Countries.COUNTRIES.length);
        for (int i = 0; i < Countries.COUNTRIES.length; i++) {
            Country country = new Country();
            country.setName(Countries.COUNTRIES[i]);
            mCountries.add(country);
        }
    }

}
```


## References:
- [AbsListView - android:choiceMode](http://developer.android.google.cn/reference/android/widget/AbsListView.html#arrt_android:choiceMode)
- [stackoverflow - ListView with CHOICE_MODE_MULTIPLE using CheckedText in a custom view](https://stackoverflow.com/questions/5612600/listview-with-choice-mode-multiple-using-checkedtext-in-a-custom-view)