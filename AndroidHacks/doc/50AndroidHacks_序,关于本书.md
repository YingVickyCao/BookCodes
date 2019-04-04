# 序
- 作者模仿Joshua Bloch 《Effective Java》的风格，总结Android开发过程中的窍门：没有特定章节顺序，读者可打乱顺序阅读。
- 书中 Demo 最小 SDk 为1.6

# 关于本书
- 每次新版本发布，Android 都会引入一组新的 API 和新特性。给 Android 开发者带来了2个大难题：
1. 必须面对和适配 Android 支持的不同类型的设备。    
（1）处理屏幕尺寸和像素密度。     
（2）用户对手机、平板、电视等的使用习惯是不同的。需要对各种设备可能导致的用户体验不一致情况做出处理。     
=> 不仅仅如此，这次gradle 2.3.3升级到3.0，SDK target 8.0， 有一个 LinearLayout 不正确使用 weight，Pre-0显示正常，O显示不全。可见SDK 升级的确增加不少验证，对开发者使用API 要求更严格了。      
2.  Android的 版本更新问题。使用新特性同时，还要兼顾老用户，或者做出取舍。   

- 书中以“问题/解决方案”的形式提出开发过程中遇到的问题，并给出解决方法，并对一些已有问题提供了进一步的处理方案。  

=> Android O中为了提高 OS 性能和用户体验，强制开发者做出更改。

## 代码下载： 
1.  [https://github.com/Macarse/50AH-code](https://github.com/Macarse/50AH-code)
2.  [www.manning.com/50AndroidHacks](www.manning.com/50AndroidHacks)

## QA:Android studio项目忽略某些文件提交Git
[http://blog.csdn.net/github_25928675/article/details/52280960](http://blog.csdn.net/github_25928675/article/details/52280960)