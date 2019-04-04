# Hack 25 通过ViewHolder优化适配器
## 问题：ListView Adapter 的工作原理，以及快速创建一个 Adapter 并使应用程序尽可能地响应灵敏。
## 解决方案：使用`ViewHolder`模式

- `Adapter`开发文档介绍： “Adapter对象是AdapterView和底层数据间的桥梁。Adapter用于访问数据项，并且负责为数据项生成视图。“ 

- `AdapterView`的工作原理     
`AdapterView`是一个抽象类，用于那些需要通过`Adapter`填充自身的视图。常见子类是`ListView`。显示`AdapterView`时，会调用`Adapter`的`getView()`方法创建并添加每个子条目的视图。    
`Adapter`的`getView()`方法就是用来创建这些视图的。`Adapter`并不会为每行数据都创建一个新视图，而是提供了回收视图的方法。     

- 运行机制：     

![竖屏](https://yingvickycao.github.io/img/android/AndroidHacks/hack_25_1.jpg)

图25-1， *A* 中显示的是列表第一次加载时的画面。    
*B* 中显示的是用户向下滑动界面时，Item1消失的情况。在这种情况下，并没有释放该视图的内存，而是把它交给`回收器（recycler）`。      
当`AdapterView`向适配器申请下一个视图时，`getView()`方法就会被调用，通过`convertView`参数获得一个被回收的视图。    
如果 Item1和 Item8的视图相同，可以修改视图文本内容并返回。返回的视图是 *C*中下方的空白区域。    
    
简而言之，   
【优化1：重复利用视图】当`getView()`方法被调用时，如果`convertView`参数不为null，就使用`convertView`，不用新建视图。需要通过`convertView.findViewById()`方法获取每个ui控件的引用然后使用与当前位置绑定的数据来填充视图。          
【优化2：避免重复查找 id】使用`ViewHolder`模式，`ViewHolder`是一个静态类，可以用于保存每行视图以**避免每次调用getView()时都会调用findViewById()。**    


`ModelAdapter.java`   
```
public class ModelAdapter extends ArrayAdapter<Model> {

    private LayoutInflater mInflater;

    public ModelAdapter(Context context, int textViewResourceId, List<Model> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        // 如果 convertView 为 null，就填充视图
        if (convertView == null) {
            // 创建视图
            convertView = mInflater.inflate(R.layout.activity_hack_25_item_view, parent, false);

            viewHolder = new ViewHolder();
            // 获取视图中各个控件的引用，并存入viewHolder中
            viewHolder.imageView = convertView.findViewById(R.id.image);
            viewHolder.text1 = convertView.findViewById(R.id.text1);
            viewHolder.text2 = convertView.findViewById(R.id.text2);

            // 把ViewHolder存入标记中
            convertView.setTag(viewHolder);

        } else {
            // 如果convertView 非null， 则回收利用它。
            // convertView的标记中获取viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 根据视图的索引位置，获取 model 对象
        Model model = getItem(position);

        // 填充视图
        // 使用 model 中的数据填充该视图
        viewHolder.imageView.setImageResource(model.getImage());
        viewHolder.text1.setText(model.getText1());
        viewHolder.text2.setText(model.getText2());

        return convertView;
    }

    // ViewHolder 类
    // ViewHolder中变量存储所有UI控件的引用
    private static class ViewHolder {
        ImageView imageView;
        TextView text1;
        TextView text2;
    }
}
```

## 25.1 概要    
- `Adapter` 类的子类都可以使用该方法。    
- 理解 `AdapterView `的原理以及如何与适配器交互对创建高性能的应用程序是很重要的。     
- 使用`ViewHolder`的技巧是获取高性能列表的好方法。    

## References：
- [Adapter](https://developer.android.google.cn/reference/android/widget/Adapter.html)
- [Making ListView Scrolling Smooth](https://developer.android.google.cn/training/improving-layouts/smooth-scrolling.html)