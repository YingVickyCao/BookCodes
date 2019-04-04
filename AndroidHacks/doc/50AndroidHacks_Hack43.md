 # Hack 43 数据库批处理
 ## 数据库的使用
### 方法1：将数据保存在数据库中，然后使用CursorAdapter将其显示在列表中。

### 方法2：使用ContentProvider处理数据库操作，可以返回一个Cursor，当数据改变时，该Cursor会随之更新。  
即：如果一切正常，开发者可以专注于在后台线程中提供修改数据库表中的信息逻辑，UI就会自动更新。  
- 存在的问题：如果执行了大量数据库操作，Cursor会被频繁更新，UI会出现闪烁（flicker）。   

## 使用ContentProvider处理数据库操作  
提供三种可能的实现：   
1.不适用批处理   
2.使用批处理   
3.使用批处理，并且使用SQLiteContentProvider类   

演示示例：显示从1到100的列表。当用户点击Update按钮后，老数字被删除，新数字被添加。为了实现上述功能，我们将为下面四种控件编码：   
1. 一个Activity，用于显示数字   
2. 一个适配器，用于创建并填充ListView中的视图   
3. 一个ContentProvider用于处理数据库查询   
4. 一个Service，用于通过ContentProvider更新数据库表    
actvity -> service -> cp -> 自动更新 Cursor -> 自动 UI 刷新  

只分析每种方案中不同的部分的代码，这些代码主要位于Service和ContentProvider中。  

## 43.1 不使用批处理操作

`NoBatchService.java`

```
public class NoBatchService extends IntentService {
    public NoBatchService() {
        super(NoBatchService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        deleteOldNum();
        insertNewNum();
    }

    private void deleteOldNum() {
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(NoBatchNumbersContentProvider.CONTENT_URI, null, null);
    }

    private void insertNewNum() {
        ContentResolver contentResolver = getContentResolver();
        //在for循环中，创建ContentView，并通过ContentResolver插入数字
        for (int i = 1; i <= ONCE_FRESH_DATA_NUM; i++) {
            ContentValues cv = new ContentValues();
            cv.put(NoBatchNumbersContentProvider.COLUMN_TEXT, "" + i);
            contentResolver.insert(NoBatchNumbersContentProvider.CONTENT_URI, cv);
        }
    }
}

```

现了闪烁情况，原因是：因为每次通过NoBatchNumbersContentProvider执行插入或删除操作时,都执行notifyChange操作,那么，通过NoBatchNumbersContentProvider的query()方法检索出的Cursor会被更新，适配器会令ListView自身也发生刷新。      
**Current:点一次Update,要更新1000条数据，=>每次 insert 1条 => 执行1000 insert => notifyChange操作 1000 次 => Cursor会被更新 1000 次 => ListView自身也发生刷新100次 => UI闪烁。**      

`NoBatchNumbersContentProvider.java`  
```
// public Uri insert(Uri uri, ContentValues initialValues) 
// public int delete(Uri uri, String selection, String[] selectionArgs) 

getContext().getContentResolver().notifyChange(uri,null);
```

`NoBatchActivity.java`  
```
private Cursor getCursor() {
     return getContentResolver().query(NoBatchNumbersContentProvider.CONTENT_URI, PROJECTION, null, null, NoBatchNumbersContentProvider.DEFAULT_SORT_ORDER);
}
```

## 43.2 使用批处理操作
思路是创建一个`ContentProviderOperation`的列表，然后调用applyBatch()一起处理列表项。

`ContentProvider.java`   
```
public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations);
```

`BatchService.java`    
```
public class BatchService extends IntentService {
    private static final String TAG = BatchService.class.getCanonicalName();

    public BatchService() {
        super(BatchService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 思路是创建一个ContentProviderOperation的列表，然后一起处理列表项。
        try {
            //以操作列表为参数调用applyBatch()方法
            getContentResolver().applyBatch(BatchNumbersContentProvider.AUTHORITY, getContentProviderOperationList());
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't apply batch: " + e.getMessage());
        } catch (OperationApplicationException e) {
            Log.e(TAG, "Couldn't apply batch: " + e.getMessage());
        }
    }

    //创建ContentProviderOperations的列表
    private ArrayList<ContentProviderOperation> getContentProviderOperationList() {
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        //使用ContentProviderOperations的构建器创建删除操作，并将该操作添加到操作列表中
        operations.add(createNewDeleteBuilder().build());

        for (int i = 1; i <= ONCE_FRESH_DATA_NUM; i++) {
            operations.add(createNewInsertBuilder(i).build());
        }

        return operations;
    }

    private Builder createNewInsertBuilder(int index) {
        ContentValues cv = new ContentValues();
        cv.put(NoBatchNumbersContentProvider.COLUMN_TEXT, "" + index);

        Builder builder = ContentProviderOperation.newInsert(BatchNumbersContentProvider.CONTENT_URI);
        //为每个数字创建要给插入操作
        builder.withValues(cv);
        return builder;
    }

    private Builder createNewDeleteBuilder() {
        return ContentProviderOperation.newDelete(BatchNumbersContentProvider.CONTENT_URI);
    }
}
```

还是出现了闪烁现象，为什么呢？   
如果分析`ContentProvider`的实现代码，会发现`applyBatch()`方法并没有什么特别的地方，该方法只是一个循环遍历每一个操作，并调用`apply()`方法，该方法最终会调用`BatchNumbersContentProvider`类中的`insert()`/`delete()`方法。 

`applyBatch()`方法的官方文档：    
“重写该方法以处理批量操作请求。否则，默认实现会循环遍历各个操作并为每个操作调用apply()方法。     
如果所有`apply()`方法调用都成功，就会返回一个`ContentProviderResult`类型的数组，该数组会保存各个操作所返回的数据元素。    
如果任意一次方法调用失败，需要根据不同的实现来决定其他方法调用是否有效。”   

总结：  
**虽然使用`applyBatch()`，但是由于`applyBatch()`没有重写，跟方法1是一样的。**    
**Current:点一次Update,要更新1000条数据，=>每次 insert 1条 => 执行1000 insert => notifyChange操作 1000 次 => Cursor会被更新 1000 次 => ListView自身也发生刷新100次 => UI闪烁。**      


## 43.3 使用SQLiteContentProvider执行批处理操作
解决闪烁问题需要在ContentProvider的实现代码中队applyBatch()方法做一些修改。 

在Android开源项目中`（Android Open Source Project，简称AOSP）`有一个名为`SQLiteContentProvider`的类，这个类不属于SDK，而是位于com.android.providers.calendar中。    
所以在本例中我们不需要继承`ContentProvider`，而是继承`SQLiteContentProvider`。  

Android lib 中已经`SQLiteContentProvider`，跟 `AOSP` 中`SQLiteContentProvider`几乎一样的。  

Service的代码与第二种方法基本相同.  
接下来分析SQLiteContentProvider的applyBatch()方法。  

`SQLiteContentProvider.java` - `applyBatch()`：  
  ```
 @Override
 public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException{
    mDb = mOperationHelper.getWritableDatabase();
    //
    mDb.beginTransactionWithListener(this);

    try{
        mApplyingBatch.set(true);
        final int numOperations = operations.size();
        final ContentProviderResult[] results = new ContentProviderResult[numOperations];

        for(int i = 0;i < numOperations;i++){
            final ContentProviderOperation operation = operations.get(i);
            //这种实现也是调用apply()方法
            results[i] = operation.apply(this,results,i);
        }
        //结束数据库事务
        mDb.setTransactionSuccessful();
        return results;
    }finally{
        mApplyingBatch.set(false);
        mDb.endTransaction();
        //onEndTransaction负责在所有操作执行完毕后，通知数据发生变化
        onEndTransaction();
    }
}
```

每个操作都是在数据库事务中执行，但是这种实现方法仍需要为每个操作调用apply()方法。  

为什么不会在每次insert()/delete()方法调用时得到通知呢？ 
`SQLiteContentProvider.java` - `insert()`：  
```
@Override
public Uri insert(Uri uri,ContentValues values){
    Uri result = null;
    //检查是否在进行批量处理
    boolean applyingBatch = applyingBatch();

    if(!applyingBatch){
        mDb = mOpenHelper.getWritableDatabase();
        mDb.beginTransactionWithListener(this);
        try{
            result = insertInTransaction(uri,values);
            if(result != null){
                mNotifyChange = true;
            }
            mDb.setTransactionSuccessful();
        }finally{
            mDb.endTransaction();
        }
        onEndTransaction();
    }else{
        //如果在批处理操作中，调用insertInTransaction()方法
        result = insertInTransaction(uri,values);
        if(result != null){
            //如果插入了数据，就打开mNotifyChange标记，以使onEndTransaction()方法知道是否需要省略通知
            mNotifyChange = true;
        }
    }
    return result;
}
```

insertInTransaction()方法的逻辑在我们的实现方案中。该方法与其他方法是相同的，只是缺少了数据变化通知的逻辑。 
`MySQLContentProvider.java` - `insertInTransaction`  :   
```
  @Override
    protected Uri insertInTransaction(Uri uri, ContentValues values) {
        String table = null;

        switch (sUriMatcher.match(uri)) {
            case ITEM:
                table = TABLE_NAME;
                break;
            default:
                new RuntimeException("Invalid URI for inserting: " + uri);
        }

        long rowId = mDb.insert(table, null, values);

        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(uri, rowId);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

 @Override
  protected void notifyChange() {
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
  }
```
SQLiteContentProvider类不属于SDK很遗憾，如果ContentProvider使用SQLite数据库存储数据，就试试SQLiteContentProvider吧，它会使UI响应更灵敏，在单独事务中执行操作也会让程序运行更快。 

**Current:点一次Update,要更新1000条数据，=>开启事务 =>每次 insert 1条 => 执行1000 insert => 1000条执行完后 =>关闭事务 => notifyChange操作1次 => Cursor会被更新 1 次 => ListView自身也发生刷新1次 => UI不闪烁。**      

- https://developer.android.google.cn/reference/android/content/ContentProvider
- https://blog.csdn.net/hzc_01/article/details/50106037
- https://stackoverflow.com/questions/9801304/android-contentprovider-calls-bursts-of-setnotificationuri-to-cursoradapter-wh