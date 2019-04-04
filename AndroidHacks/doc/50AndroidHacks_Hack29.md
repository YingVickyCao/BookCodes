# Hack 29 在ViewPager中处理转屏

## 问题：假设需要创建一个电子杂志风格的app，要在不用分页中分别采取不同横竖屏显示。 

## 解决方案：ViewPager可以用于创建任何需要显示分页视图的应用程序，用法与AdapterView相似。

Activity:持有ViewPager的引用、控制屏幕旋转    
ViewPager:使用ColorAdapter显示Fragment     
ColorAdapter类：负责创建Fragment、通知Activity对于哪个Fragment需要改变屏幕显示方向    
ColorFragment类：用于显示颜色，并在屏幕中央显示文本内容   


`Hack29Activity.java` 
```
public class Hack29Activity extends FragmentActivity {
    private static final String TAG = Hack29Activity.class.getSimpleName();

    private ViewPager mViewPager;
    private ColorAdapter mAdapter;
    private OnPageChangeListener mOnPageChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

        //1.设置默认屏幕方向
//        enforcePortrait();
        setContentView(R.layout.activity_hack_29);

        mViewPager = findViewById(R.id.pager);
        mAdapter = new ColorAdapter(getSupportFragmentManager());

        //2.引用viewpager
        mViewPager.setAdapter(mAdapter);

        mOnPageChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (mAdapter.usesLandscape(position)) {
                    allowOrientationChanges();
                } else {
                    enforcePortrait();
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
    }

    public void allowOrientationChanges() {
        Log.d(TAG, "allowOrientationChanges: ");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public void enforcePortrait() {
        Log.d(TAG, "enforcePortrait: ");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

```


`ColorAdapter.java`
```
public class ColorAdapter extends FragmentStatePagerAdapter {
    public static final int[] COLORS = {Color.MAGENTA, Color.BLUE,
            Color.CYAN, Color.GREEN, Color.RED, Color.WHITE, Color.MAGENTA};

    public ColorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ColorFragment.newInstance(COLORS[position], "" + position, (position % 2) == 0);
    }

    @Override
    public int getCount() {
        return COLORS.length;
    }

    public boolean usesLandscape(int position) {
        return (position % 2) == 0;
    }
}
```

添加一个OnPageChangeListener监听器用于监听页面切换。该监听器会通过适配器判断是否需要改变屏幕方向，最后调用Activity类提供的setRequestedOrientation()方法改变屏幕方向。 

ViewPager类是Android用于水平视图切换的标准实现，可以向后兼容到API level 4。最好每个视图都可以支持两种不同屏幕方向，当用户使用应用程序的时候，如果可以改变屏幕方向，会提升用户体验。

## References: 
- [horizontal-view-swiping-with-viewpager.](http://android-developers.blogspot.com/2011/08/horizontal-view-swiping-with-viewpager.html)
- [Support Library](https://developer.android.google.cn/topic/libraries/support-library/index.html)