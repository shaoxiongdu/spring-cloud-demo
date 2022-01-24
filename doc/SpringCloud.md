微服务-分布式-Java栈-学习笔记

> 2022Q1学习笔记   dushaoxiong@lixiang.com 

> 配套demo项目: https://github.com/shaoxiongdu/spring-cloud-demo

# 一、微服务概述

1. ## 单体架构

> 将业务的所有功能集中在一个项目中开发，打成一个包部署。

![img](https://images-1301128659.cos.ap-beijing.myqcloud.com/img/asynccode)

1. ## **分布式架构**

> 根据业务功能对系统做拆分，每个业务功能模块作为独立项目开发，称为一个服务。

![img](https://images-1301128659.cos.ap-beijing.myqcloud.com/img/asynccode)

1. ## 微服务架构

![img](https://images-1301128659.cos.ap-beijing.myqcloud.com/img/asynccode)

- 单一职责：微服务拆分粒度更小，每一个服务都对应唯一的业务能力，做到单一职责

- 自治：团队独立、技术独立、数据独立，独立部署和交付

- 面向服务：服务提供统一标准的接口，与语言和技术无关

- 隔离性强：服务调用做好隔离、容错、降级，避免出现级联问题

> 微服务的上述特性其实是在给分布式架构制定一个标准，进一步降低服务之间的耦合度，提供服务的独立性和灵活性。做到高内聚，低耦合。

> 因此，可以认为**微服务是一种经过良好架构设计的分布式架构方案** 。

但方案该怎么落地？选用什么样的技术栈？

全球的互联网公司都在积极尝试自己的微服务落地方案。

其中在Java领域最引人注目的就是SpringCloud提供的方案了。

1. ## SpringCloud

![img](https://images-1301128659.cos.ap-beijing.myqcloud.com/img/asynccode)

1. ## 总结

> 单体架构：简单方便，高度耦合，扩展性差，适合小型项目。例如：学生管理系统

> 分布式架构：松耦合，扩展性好，但架构复杂，难度大。适合大型互联网项目，例如：京东、淘宝

> 微服务：一种良好的分布式架构方案

> ①优点：拆分粒度更小、服务更独立、耦合度更低

> ②缺点：架构非常复杂，运维、监控、部署难度提高

> SpringCloud是微服务架构的一站式解决方案，集成了各种优秀微服务功能组件

# 一、服务注册中心

1. ## Eureka

![img](https://images-1301128659.cos.ap-beijing.myqcloud.com/img/asynccode)

1. 消费者如何得知生产者的实际地址？

- 生产者启动之后，将自己的ip端口等信息注册到Eureka服务端。（服务注册）
- Eureka保存生产者服务名称和IP端口等信息
- 消费者根据需要的生产者名称从Eureka服务端拉取列表，然后远程调用 （服务拉取）

1. 消费者如何从多个生产者实例中选择具体的实例？

- 从生产者实例列表中利用负载均衡算法选中一个实例地址

1. 消费者如何得知某个生产者实例是否依然健康，是不是已经宕机？

- 生产者会每隔一段时间（默认30秒）向eureka-server发起请求，报告自己状态，称为心跳

- 当超过一定时间没有发送心跳时，eureka-server会认为微服务实例故障，将该实例从服务列表中剔除

- 消费者拉取服务时，就能将故障实例排除了

1. ## Nacos

> ​    国内公司一般都推崇阿里巴巴的技术，比如注册中心，SpringCloudAlibaba也推出了一个名为Nacos的注册中心。

> [Nacos](https://nacos.io/)是阿里巴巴的产品，现在是[SpringCloud](https://spring.io/projects/spring-cloud)中的一个组件。相比[Eureka](https://github.com/Netflix/eureka)功能更加丰富，在国内受欢迎程度较高。

![img](https://images-1301128659.cos.ap-beijing.myqcloud.com/img/asynccode)



1. ### 使用方式

   1. [下载nacos](https://nacos.io/) 启动
   2.  项目导入依赖

```XML
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

1. **配置nacos地址**

```YAML
spring:
  cloud:
    nacos:
      server-addr: localhost:8848
```

1. 启动服务，即可在nacos的控制页面找到对应的微服务注册信息。

![img](https://images-1301128659.cos.ap-beijing.myqcloud.com/img/asynccode)

1. 通过远程RPC消费者即可调用生产者的服务。

1. ### 集群概念

> Nacos就将同一机房内的实例 划分为一个**集群**。

![img](https://images-1301128659.cos.ap-beijing.myqcloud.com/img/asynccode)

> 微服务互相访问时，应该尽可能访问同集群实例，因为本地访问速度更快。当本集群内不可用时，才访问其它集群。

通过配置文件即可设置当前服务所属的集群。

```YAML
spring:
  cloud:
    nacos:
      server-addr: localhost:8848
      discovery:
        cluster-name: HZ # 集群名称
```

![img](https://images-1301128659.cos.ap-beijing.myqcloud.com/img/asynccode)

1. ## **Nacos与Eureka对比**

   1. ### 共同点

- 都支持服务注册和服务拉取

- 都支持服务提供者心跳方式做健康检测

1. ### 不同点

Nacos的服务实例分为两种类型：

- Nacos支持服务端主动检测提供者状态：临时实例采用心跳模式，非临时实例采用主动检测模式

- 临时实例心跳不正常会被剔除，非临时实例则不会被剔除

- Nacos支持服务列表变更的消息推送模式，服务列表更新更及时

- Nacos集群默认采用AP方式，当集群中存在非临时实例时，采用CP模式；Eureka采用AP方式
