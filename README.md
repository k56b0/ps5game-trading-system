

<img width="459" height="306" alt="Image" src="https://github.com/user-attachments/assets/f60053c5-3880-48c2-99ae-aa2b1ca166f5" />

# 介绍

一个简单的项目，采用前后端分离模式开发。主要使用 Vue + Java(Spring Boot) + Mysql 技术。模拟了在网页上选购游戏，并使用二手游戏回收服务的情况。由于开发过程中并未充分考虑安全防护问题，并不建议将该项目用于生产环境。

[https://github.com/k56b0/ps5game-trading-system](https://github.com/k56b0/ps5game-trading-system)

# **整体效果**

- 首页

![image.png](images/image.png)

- 二手回收

![image.png](images/image%201.png)

- 个人中心

![image.png](images/image%202.png)

- 登陆

![image.png](images/image%203.png)

- 购物车

![image.png](images/image%204.png)

- 问题反馈

![image.png](images/image%205.png)

# 架构图

- 应用架构

![image.png](images/image%206.png)

- 技术架构

![image.png](images/image%207.png)

# 主要技术栈

**前端：**

1. Vue.js
2. Bootstrap
3. axios

**后端：**

1. Spring Boot
2. MybatisPlus
3. Tomcat

**数据库：**

1. MySQL

**涉及开发软件:**

| 软件/环境名 | 版本号 | 说明 |
| --- | --- | --- |
| IntelliJ IDEA  | 2025.3.1.1 | 集成开发环境，java后端在此开发 |
| WebStorm  | 2025.3.1 | 集成开发环境，vue前端在此开发 |
| java | 25.0.2 2026-01-20 LTS | spring boot2 至少需要 java 17及以上java版本 |
| Node.js | v24.0.0 | Node.js 是一个开源的、跨平台的 JavaScript 运行时环境，vue架构必须使用 |
| nvm | 1.2.2 | 通过nvm管理Node.js的版本 |
| Postman | 11.82.6 | Postman是一款API协作平台，它让开发者和测试人员能够更轻松地构建、测试、调试和文档化API。就个人而言，方便调试自己的后端项目的controller请求是否可用 |
| maven | 3.8.8 | 每个项目必须配置好本地的maven仓库 |
| mysql | 5.7 | 数据库软件 |
| Navicat | 15 | 可视化操作数据库，方便操作。 |

# 项目更新

预计时间：

- 代码工程预计完成时间：2026年2月20日
- 论文撰写初稿预计完成时间：2026年2月28日
- 论文后续修订预计完成时间：2026年3月25日

（如果提前于第一次设定好的预计时间，就去买一款游戏）

| 进度名 | 完成日期 | 备注 |
| --- | --- | --- |
| springboot基本功能初步完成 | 2026年2月9日 | 已经一边做一边用postman做了一些请求测试。github上也已经更新了，github还是很方便的。 |
| springboot正式的API测试 | 2026年2月10日—— | 需求学习如何进行正式的API测试工作。一个一个控制器开始测试 |
| 前端基本搭建 | 2026年2月12日 | 就是基本页面可见 |
| 前端用户登陆、注册、修改信息服务 | 2026年2月13日 | 大菠萝整好了 |
| 基础建设完成度90% | 2026年2月17日 | 只剩下首页和搜索页没有搞好。回收页的基础已经完成 |
| 基础建设完成度100% | 2026年2月19日 | 接下来就是完善。一边写文档一边完善了 |

# 部署方法

1.clone 项目到本地

```bash
https://github.com/k56b0/ps5game-trading-system.git
```

2.在 mysql 中创建数据库 `ps5game`，运行项目，将自动注入数据。如需关闭此功能，请将 `application.yml` 中的 `spring.datasource.initialization-mode=always` 代码删除。

数据库完整脚本 `data.sql` 放在后端项目的 `src\main\resources` 目录下，也可根据需要自行在 MySQL 中执行数据库脚本。。

3.数据库配置在后端项目的 `src\main\resources` 目录下的`application.yml` 文件中，mysql 版本为 5.7 。

4.在IntelliJ IDEA中运行后端项目，为了保证项目成功运行，可以右键点击 `pom.xml` 选择 maven -> reimport ，并重启项目。（请确保配置好了自己的本地仓库）

至此，服务端就启动成功了，同时，运行前端项目，访问 `http://localhost:8888` ，即可进入登录页面，默认账号是 `admin123456@qq.com`，密码是 `123456`

# 制作流程说明

[vue项目前端-说明手册](https://www.notion.so/vue-2f688752e15d802a9448c616b6c33eef?pvs=21)

[springboot 后端-说明手册](https://www.notion.so/springboot-2fc88752e15d80a08ff0c6c641b44b07?pvs=21)

[数据库-说明手册](https://www.notion.so/2ff88752e15d80a48465dfa88a535e14?pvs=21)

# 后记

这是漫漫征程啊。本来只是想应付一下毕业论文。想不到，越学越多，越做越多。想做成一件事，真是太不容易了。

# 参考资料

1.    [在线制作ico图标 | 在线ico图标转换工具 方便制作favicon.ico - 比特虫 - Bitbug.net](https://www.bitbug.net/)
2.    [PS游戏信息参考-shopitree.com](https://www.shopitree.com/)
3.    [uuid 生成器](https://www.97235.com/uuid/)