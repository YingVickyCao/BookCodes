# Hack 27 使用Activity和Delegate与适配器交互

## 问题：如何把所有业务逻辑从适配器移到 Activity？
## 解决方案：如何通过委托模式来使用关注点分离(separation concerns，SoC)的设计原则。

委托模式(Delegate Pattern)在iOS开发中被大量使用。

实现思路：   
在适配器中实现删除按钮点击处理器，但并不在适配器中实现删除对象的方法。我们通过一个委托接口调用Activity的方法删除对象。      
在onCreate()方法中将当前Activity设置为适配器的委托对象。     
在onResume()方法中注册代理对象。    
然后在onPause()方法中取消注册。     
这样做的目的是为了确保只在Activity显示在屏幕上的时候才作为委托对象使用。     

`NumbersAdapter.java`
```
public class NumbersAdapter extends ArrayAdapter<Integer> {
    private LayoutInflater mInflator;
    private NumbersAdapterDelegate mDelegate;

    public NumbersAdapter(Context context, List<Integer> objects) {
        super(context, 0, objects);
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View cv, ViewGroup parent) {
        if (null == cv) {
            cv = mInflator.inflate(R.layout.number_row, parent, false);
        }

        final Integer value = getItem(position);
        TextView tv = cv.findViewById(R.id.numbers_row_text);
        tv.setText(String.valueOf(value));

        View button = cv.findViewById(R.id.numbers_row_button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //2.删除对象
                if (null != mDelegate) {
                    mDelegate.removeItem(value);
                }
            }
        });

        return cv;
    }

    //3.为适配器设置委托对象
    public void setDelegate(NumbersAdapterDelegate delegate) {
        mDelegate = delegate;
    }

    //1.定义委托接口
    public static interface NumbersAdapterDelegate {
        void removeItem(Integer value);
    }
}
```

`Hack27Activity.java`
```
//1.实现NumberAdapterDelegate接口
public class Hack27Activity extends Activity implements NumbersAdapter.NumbersAdapterDelegate {
    private static final String TAG = Hack27Activity.class.getCanonicalName();

    private ListView mListView;
    private ArrayList<Integer> mNumbers;
    private NumbersAdapter mAdapter;
    private EditText mEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mEditText = findViewById(R.id.main_edittext);
        mListView = findViewById(R.id.main_listview);
        mNumbers = new ArrayList<>();
        mAdapter = new NumbersAdapter(this, mNumbers);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //2.在onResume()方法中注册委托对象
        mAdapter.setDelegate(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //3.在onPause()方法中取消注册委托对象
        mAdapter.setDelegate(null);
    }

    //从列表中移除指定项，然后通知适配器绑定的数据发生变化
    @Override
    public void removeItem(Integer value) {
        mNumbers.remove(value);
        Toast.makeText(this, "Removed object: " + value, Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
    }

    public void addNumber(View v) {
        String value = mEditText.getText().toString().trim();
        try {
            mNumbers.add(Integer.valueOf(value));
            mEditText.setText("");
            mAdapter.notifyDataSetChanged();
        } catch (NumberFormatException e) {
            Log.e(TAG, "Couldn't convert to integer the string: " + value);
        }
    }
}
```

## References:
- [Separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns)
- [Delegation pattern](https://en.wikipedia.org/wiki/Delegation_pattern)