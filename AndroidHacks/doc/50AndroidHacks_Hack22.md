d## Hack 22 使用Android库项目时适用的架构模式
## 问题： 在Android库项目（Library Project）发布之前，不同 Android 项目共享代码是很困难的，甚至是不可能的。通常可以共享JAR 包共享Java 代码，但是无法共享需要引用资源文件的代码。
## 方案解决：将程序划分为 三种结构：    

- 后台逻辑和模型（JAR文件）:    
包含请求服务器的代码，业务对象以及业务逻辑。并不依赖于 Android平台，是纯 Java 代码。=> 可以 用 Junit 测试所有代码。   

- Android 库项目：   
Android库项目类似于JAR包，但却可以使用Android资源文件.将Android库项目设置为某个应用程序的依赖项目时，该应用程序就拥有了第二个R类。    

- Android 应用程序：    
即可以包含 JAR 包中的代码，也可以使用 Android 库项目中的代码。 开发此程序时，只需要处理代码在不同层的分布问题。   

使用Android库项目的好处是让代码变得可重用且易维护。

## References:
- [Projects Overview-
](https://developer.android.google.cn/studio/projects/index.html#Library-Projects)