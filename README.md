事件管理应用 README
一、概述
本事件管理应用是一个用于处理和管理各类事件信息的系统，提供了创建、删除、修改和查询事件等功能。
二、技术栈
它采用了后端使用 Spring Boot 框架进行开发，前端使用 React 构建用户界面的架构模式
三、项目结构
incident-management-frontend：前端端代码目录，react的所有代码。
src/main/java：主要的 Java 代码存放位置，包括控制器、实体类、数据访问接口（Repository）、服务层接口及实现类等。
src/main/resources：配置文件和静态资源存放位置，如application.properties用于配置 Spring Boot 应用的各种属性，static目录可存放前端打包后需要的静态资源（如果有）。
src/test/java：用于存放单元测试代码，对后端各个组件进行功能测试。
Dockerfile：用于构建 Docker 镜像，将后端应用容器化，方便部署到不同环境。
incident-management-deployment.yaml 和 incident-management-service.yaml：用于在 Kubernetes 环境中部署应用的配置文件，定义了部署和服务的相关参数。
pom.xml：Maven 项目的配置文件，用于管理项目的依赖关系、构建配置等。
四、功能实现
1.技术栈
Java 17 和 Spring Boot
2.存储
使用内存存储（在dao层中模拟了数据库存储），在内存中存储事件数据，模拟数据库操作。
3.API
使用了restful风格的api
4.所有主要操作的性能
压力测试
5.使用容器
Docker、K8s
6.缓存
使用了spring-boot-starter-cache做缓存，spring.cache.type=simple即内存缓存。
7.验证和异常处理
在后端的IncidentController中，各个业务方法（如创建事件、删除事件、修改事件、查询事件等）都使用了try-catch块来捕获可能出现的异常。当捕获到异常时，根据不同的业务需求，将具体的错误信息以合适的方式返回给客户端。例如，最初是将错误信息添加到响应头部的Error-Message字段中，后来修改为将包含错误信息（通过e.getMessage()获取）和状态码（如HttpStatus.INTERNAL_SERVER_ERROR.value()）的Map对象作为响应体返回给客户端，以便客户端能更准确地了解发生错误的原因并进行相应处理。
8.高效的数据查询，包括使用数据库技术（Spring Data、SQL、分页、索引）
提供了分页查询接口，并模拟了索引
9.使用 RESTful 风格的 API
10.交付物应该是一个可以轻松运行和测试的自包含项目
通过docker和主函数均可运行
11.使用 Maven
12.使用了标准库和spring boot的部分库
13.确保页面具备基本功能：添加 / 修改 / 删除事件，并在页面上显示事件列表
使用react实现了一个简单的前端页面
五、测试
单元测试：
在后端项目中，使用 JUnit 5 和 Mockito 编写了单元测试代码。例如，IncidentControllerTest类用于测试IncidentController的各个方法，通过模拟IncidentService的行为，验证控制器层方法的正确性，包括输入参数验证、返回结果验证以及对服务层方法调用次数的验证等。同样，IncidentServiceImplTest类用于测试服务层IncidentServiceImpl的方法。
压力测试：
使用多线程进行压力测试
六、部署
容器化部署（Docker）：
项目根目录下的Dockerfile文件定义了如何将后端应用构建成 Docker 镜像。通过运行docker build -t incident-management-app.命令（其中-t指定镜像标签，最后的.表示当前目录为构建上下文），可以构建出名为incident-management-app的 Docker 镜像。然后通过docker run -p 8080:8080 incident-management-app命令，将容器内的 8080 端口映射到主机的 8080 端口，使得可以通过http://localhost:8080/incidents访问应用程序的 API。
Kubernetes 部署：
使用incident-management-deployment.yaml和incident-management-service.yaml这两个配置文件进行 Kubernetes 部署。首先确保已安装kubectl并且连接到 Kubernetes 集群，然后通过kubectl apply -f incident-management-deployment.yaml和kubectl apply -f incident-management-service.yaml命令分别应用部署和服务配置，将应用部署到 Kubernetes 环境中。