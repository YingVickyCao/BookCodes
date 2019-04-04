# Hack 35 同时发起多个Intent
## 问题：如果想要实现一个向设置头像功能，可以从相册或直接拍照选择，也可以使用Intent实现，但这个无法只使用一个Intent来实现。
## 解决方案：整合两种Intent

Intent系统是Android提供的优秀特性之一。如果开发者想与另一个应用程序共享信息，就可以使用Intent；如果开发者想打开一个链接也可以使用Intent。在Android中，几乎所有操作都可以通过Intent完成。 

**`Hack35Activity.java`**

```
public class Hack35Activity extends Activity {

    private static final int PICK_PICTURE = 10;
    private static final int TAKE_PICTURE = 11;
    private static final int PICK_OR_TAKE_PICTURE = 12;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_35);
    }

    /**
     * 拍照
     */
    public void onTakePicture(View v) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooserIntent = Intent.createChooser(takePhotoIntent, getString(R.string.pick_picture));
        startActivityForResult(chooserIntent, TAKE_PICTURE);
    }

    /**
     * 从相册中选择照片
     */
    public void onPickPicture(View v) {
        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(pickIntent, getString(R.string.take_picture));
        startActivityForResult(chooserIntent, PICK_PICTURE);
    }

    /**
     * 整合两种Intent
     */
    public void onTakeAndPickPicture(View v) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(pickIntent, getString(R.string.pick_both));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});

        startActivityForResult(chooserIntent, PICK_OR_TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        Toast.makeText(this, "onActivityResult with req code: " + requestCode, Toast.LENGTH_SHORT).show();
    }
}
```

需要在当前Activity中重写onActivityResult()方法，用于进行处理使用。   

## 35.4 概要    
理解 Intent的原理是很重要。 Intent 是 Android 的核心组成部分。正确使用 Intent 可以使得程序更好地与其他应用程序交互。

## References:   
- [stackoverflow.com - How to make an intent with multiple actions](https://stackoverflow.com/questions/11021021/how-to-make-an-intent-with-multiple-actions)
- [stackoverflow.com - Single intent to let user take picture OR pick image from gallery in Android](https://stackoverflow.com/questions/2708128/single-intent-to-let-user-take-picture-or-pick-image-from-gallery-in-android)