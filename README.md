# **浙经院健康自动填报(金数据)**

## **Idea Spring Boot项目**

**dev分支，添加管理后台**

**如不需管理后台，切换stable分支**

### **配置文件**

/src/main/resources/application.properties

### **定时调度**(**schedule**)

#### Daily：  

到期提醒，过期7天清理

#### Zjy:

执行自动填报

#### 数据库结构

zjy.sql

说明：目前只需要晨检，晨检配置在src/main/java/cn/myflavor/zjy/check/Morning.java，当需要午检的时候，创建一个类继承BaseCheck，并覆写抽象方法。并在src/main/java/cn/myflavor/zjy/schedule/Zjy.java添加定时任务。后台管理地址/admin/index.html
