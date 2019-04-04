# Hack 28 充分利用ListView的头视图

## 问题：在界面上提供一个显示图片的相册和一个显示数字的列表，当向下滚动界面时，相册也会随之滚动，直到图片消失。 
## 问题解决：ListView提供了可以为列表添加头视图（Header Veiw）(`ListView.addHeaderView()`)和尾视图（Footer View）(`ListView.addFooterView()`)的方法。 

ListView提供了可以为列表添加头视图（Header Veiw）和尾视图（Footer View）的方法。 
使用如上方法把Gallery设置为ListView的头视图（Header Veiw).

`Hack28Activity.java`
```
public class Hack28Activity extends Activity {

    private static final String[] NUMBERS = {"1", "2", "3", "4", "5", "6", "7", "8"};
    private Gallery mGallery;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_28);

        mListView = findViewById(R.id.main_listview);

        View headerView = getHeaderView();
        mGallery = getGallery(headerView);
        /**
         将这个头视图添加到ListView
         */
        mListView.addHeaderView(headerView, null, false);
        mListView.setAdapter(getAdapter());

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGallery.setSelection(position - 1, true);
            }
        });
    }

    private ArrayAdapter<String> getAdapter() {
        return new ArrayAdapter<>(this, R.layout.activity_hack_28_list_item, NUMBERS);
    }

    private View getHeaderView() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_hack_28_header, mListView, false);
        headerView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT));
        return headerView;
    }

    private Gallery getGallery(View mHeader) {
        mGallery = mHeader.findViewById(R.id.listview_header_gallery);
        mGallery.setAdapter(new ImageAdapter(this));
        return mGallery;
    }
}
```

## References:
- [ListView](https://developer.android.google.cn/reference/android/widget/ListView.html)