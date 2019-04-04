# Hack 20 模型-视图-主导器模式,Android 1.6+
## 问题：Android 开发模式之 MVP
## 解决方案：...

mvp非常适合大型的APP开发，越复杂它的优势越明显，但是如果APP代码本身很简明，mvp就有点绕弯子的感觉了。

##  MVP（Model-View-Presenter）（模型-视图-主导器）
- M(Model) 数据相关层/数据层   
Model层是数据层，用来存储数据并且提供数据。  
 Model层定义Modle.interface,这个是用来定义数据层发生变化时的通知接口，因为Model不能直接与View交互，所以它与Presenter交互，然后再通过Presenter间接达到与View的交互。 
- V(View) 视图层，如Activity上的布局    
View层定义View.interface，用来定义View的行为。一般由Activity或者是Fragment来实现这个接口，它定义了View视图的各种变化，如设置Textview,加载对话框，更新进度条等。    
- P(Presenter) 纽带层，用来连接Model与View.   
Presenter翻译的意思是主持人，也就是主持场合，控制节奏的意思。在这时Presenter就负责具体的业务逻辑，请求数据，把数据送到Model，或者监听Model的数据变化，接受View层的动作，负责通过通知View层的视图变化。  

##  为什么使用MVP？
1. 易分工
- Activity不再是最小的开发单元
- 把任务查分为 MVP 三部分。

2. 提供代码的易测试性。  
- 单元测试通过模拟View和Model的数据来测试Presenter的逻辑。这样仅仅 Junit（Java而非Android）测试提供测试效率。  
- 简化创建测试用例的过程。   
- 更容易实施 TTS(test-driven development,测试驱动开发)   
- 在单元测试中更容易（mock）视图。 

3. 使代码更易组织

### <b style="color:red">MVP VS MVC</b>  

### 测试代码：
- Case1: Splash page 中检查网络链接是否正常？若正常则跳转。
- Case2：假设现在需要做一款APP，就是显示天气，界面很简单，一个TextView显示天气信息，一个Button用来请求实时天气。 

## TBD
1.  <b style="color:red">MVC模式(Model-View-Controller)（模型-视图-控制器）</b>  
2. <b style="color:red">Mockito</b>  
3. <b style="color:red">MVC</b>  
4. <b style="color:red"> MVP VS MVC</b>  

## Reference:
- [mockito](http://site.mockito.org/)
- [ JUnit + Mockito 单元测试（二）](http://blog.csdn.net/zhangxin09/article/details/42422643)
- [Android中MVP模式讲解及实践](http://blog.csdn.net/briblue/article/details/52839242)
- [MVP模式在Android开发中的应用](https://www.cnblogs.com/wanqieddy/p/4478690.html)
