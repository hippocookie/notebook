# Docker
## 常用操作
### 镜像
#### 查看镜像
```bash
$ docker images
REPOSITORY                        TAG                 IMAGE ID            CREATED             SIZE
ubuntu                            latest              1318b700e415        5 weeks ago         72.8MB
postgres                          latest              817f2d3d51ec        11 months ago       314MB
```
- REPOSITORY: 镜像在苍空中的名称
- TAG: 镜像标签
- IMAGE ID: 镜像ID
- CREATED: 创建日期
- SIZE: 镜像大小


#### 搜索镜像
```bash
$ docker search redis
NAME                             DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
redis                            Redis is an open source key-value store that…   10226               [OK]                
```
#### 拉取镜像
```bash
$ docker pull redis
Using default tag: latest
eff15d958d66: Pull complete 
1aca8391092b: Pull complete 
06e460b3ba1b: Pull complete 
def49df025c0: Pull complete 
646c72a19e83: Pull complete 
db2c789841df: Pull complete 
Digest: sha256:619af14d3a95c30759a1978da1b2ce375504f1af70ff9eea2a8e35febc45d747
Status: Downloaded newer image for redis:latest
```

#### 删除镜像
```bash
$ docker rmi redis
Untagged: redis:latest
Untagged: redis@sha256:619af14d3a95c30759a1978da1b2ce375504f1af70ff9eea2a8e35febc45d747
Deleted: sha256:40c68ed3a4d246b2dd6e59d1b05513accbd2070efb746ec16848adc1b8e07fd4
Deleted: sha256:bec90bc59829e7adb36eec2a2341c7d39454152b8264e5f74988e6c165a2f6a2
Deleted: sha256:c881a068a82210f7964146ebc83e88889224831178f4b8a89ddb0fba91fe96cd
Deleted: sha256:8e9a414cbe1dc316cfa02c0ee912b9c0af0e086accda4e2f340a10c4870a5b35
Deleted: sha256:37d8a78bebeb894e21a8c3bd9041bd4fb600e77154fbb58491d57ef6e70584d5
Deleted: sha256:e8755b67e77af585d946a6078463f45313ec0f385bebdb5bbebadaafbe3b4a2c
Deleted: sha256:e1bbcf243d0e7387fbfe5116a485426f90d3ddeb0b1738dca4e3502b6743b3251
```

### 容器
#### 查看容器
```bash
$ docker ps
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
```

查看停止容器
> docker ps -f status=exited

查看所有容器
> docker ps -a

查看最近创建的n个容器
> docker ps -n 5

#### 创建容器
> docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
- -i: 运行容器
- -t: 创建后登录容器，进入命令行
- --name: 容器命名
- -v: 目录映射关系(宿主机目录->容器目录)
- -d: 以守护进程运行
- -p: 端口映射(宿主机端口->容器端口)
- -P: 随机使用宿主机端口与容器内暴露端口映射

```bash
# 绑定容器8080端口至宿主机8080端口
$ docker run --name mynginx -p 8080:8080 nginx

# -P 使用随机端口
$ docker run --name mynginx -P nginx
```

**创建并进入容器**
```bash
docker run -it --name 容器名称 镜像名称:标签 /bin/bash
```

> docker容器必须有一个前台进程，如果没有前台进程执行，容器认为是空闲状态，会自动退出

**守护式容器**
```bash
docker run -di --name 容器名称 镜像名称:标签
```

登录守护式容器
```bash
docker exec -it 容器名称|容器ID /bin/bash 
```

#### 启停容器
```bash
docker start 容器名称|容器ID
docker stop 容器名称|容器ID
```

#### 拷贝文件
```bash
# copy local -> container
docker cp 本地文件或目录 容器名称:容器目录
# copy container -> local
docker cp 容器名称:容器目录 本地文件或目录
```

#### 目录挂载
**指定目录挂载**
```bash
docker run -di -v /宿主机目录:/容器目录 -v /宿主机目录2:/容器目录2 镜像名
## 例如
docker run -di -v /local/tmp:/tmp --name centos7 centos:7
```

**匿名挂载**
```bash
docker run -di -v /容器目录 镜像名

# 例如
docker run -di -v /usr/local/data --name centos7 centos:7

# 查看volume数据卷信息
docker volume ls
```

**具名挂载**
```bash
docker run -di -v 数据卷名称:/容器目录 镜像名

docker run -di -v docker_data:/usr/local/data --name centos7 centos:7
```

**只读挂载**
```bash
docker run -di -v /宿主机目录:/容器目录:挂载模式 镜像名

docker run -di -v /local/data:/usr/local/data:ro --name centos7 centos:7
# ro - read only
# rw -read write
```

**继承挂载**
```bash
# 继承指定容器挂载目录
docker run -di --volumes-from 继承容器名称 --name 容器名称 镜像名

# 容器centos7_01指定目录
docker run -di -v /local/data:/usr/local/data --name centos7_01 centos:7
# 容器centos7_02继承centos7_01容器挂载目录
docker run -di --volumes-from centos7_01 --name centos7_02 centos:7
```


**查看目录挂载关系**
```bash

docker volume inspect 具名挂载数据卷名称 # 可以查看该数据卷对应宿主机目录地址

docker inspect 容器ID或名称  # 返回体中查找Mounts字段
```

#### 查看容器IP地址
查看容器元信息
```bash
docker inspect 容器名称|容器ID
```

直接查看IP地址
```bash
docker inspect --format='{{.NetworkSettings.IPAddress}}' 容器名称|容器ID
```

#### 删除容器
```bash
docker rm 容器名称|容器ID 容器名称|容器ID
```

#### 构建镜像
```bash
docker commit [OPTIOINS] CONTAINER [REPOSITORY[:TAG]]

docker commit -a="helloworld" -m="jdk11 and tomcat8" centos7 mycentos:7
# -a: 镜像作者
# -m: 提交信息
```

## Dockerfile
### FROM
语法: FROM <image>:<tag>
指明构建的镜像来源的基础镜像，如果没有tag，默认为latest
> 如果不以任何镜像为基础，写法为: FROM scratch
> sractch为一个空镜像

### LABEL
语法: LABEL <key>=<value>
为镜像指定标签
```bash
LABEL maintainer="helloworld"
```

### RUN
语法: RUN <command>
构建镜像时运行的Shell命令
```bash
RUN mkdir -p /usr/local/java
```

### ADD
语法: ADD <src>...<dest>
拷贝文件或目录到镜像中，src可以是一个本地文件或者是一个本地压缩文件，压缩文件会自动解压，还可以是一个url，如果把src写成一个url，那么ADD命令就类似于wget命令，然后自动下载和解压
```bash
ADD jdk-11.0.6_linux-x64_bin.tar.gz /usr/local/java
```

### COPY
语法: COPY <src>...<dest>
拷贝文件或目录到镜像中，用法同ADD，不支持自动下载和解压
```bash
COPY jdk-11.0.6_linux-x64_bin.tar.gz /usr/local/java
```

### EXPOSE
语法: EXPOSE <port> [<port>/<protocol>]
暴露容器运行时的监听端口给外部，可以指定端口是监听TCP还是UDP，如果未指定协议，则默认为TCP
```bash
EXPOSE 80 443 8080/tcp
```

> 如果想使得容器与宿主机端口有映射关系，启动容器需使用 -P 参数

### ENV
语法: ENV <key> <value> ...
设置容器内环境变量
```bash
ENV JAVA_HOME /usr/local/java/jdk-11.0.6/
```
### CMD
语法: CMD ["executable", "param1", "param2']
启动容器时执行Shell命令，在Dockerfile中只能有一条CMD指令，如果设置了多条CMD，只有最后一条会生效
```bash
CMD ["/usr/local/tomcat/bin/catalina.sh", "run]

# 如果创建容器时指定了命令，则CMD命令会被替代
# 如下容器启动时会执行/bin/bash命令
docker run -it --name centos7 centos:7 /bin/bash
```

### ENTRYPOINT
语法: 
ENTRYPOINT ["executable", "param1", "param2"]
ENTRYPOINT "executable", "param1", "param2"

容器启动执行Shell命令，不会被docker run命令指定的参数所覆盖，Dockerfile中只能有一条ENTRYPOINT命令，如存在多条只有最后一条会生效

> 如果Dockerfile中同时写了ENTRYPOINT和CMD，并且CMD指令不是一个完整的可执行命令，那么CMD指定的内容将会作为ENTRYPOINT的参数
> 如果在Dockerfile中同时写了ENTRYPOINT和CMD，并且CMD是一个完整的指令，那么它俩会相互覆盖，谁在最后谁生效

### WORKDIR
语法: WORKDIR /path/to/workdir
为RUN、CMD、ENTRYPOINT以及COPY和AND设置工作目录
```bash
WORKDIR /usr/local
```

### VOLUME
语法: VOLUME ["/dir/in/docker"]
指定容器挂载点到宿主机自动生成的目录或其他容器，一般的使用场景为需要持久化存储数据时

> 一般不会在Dockerfile中用到，更常见的还是在docker run的时候通过-v指定数据卷



## 隔离与限制
一个正在运行的 Docker 容器，其实就是一个启用了多个 Linux Namespace 的应用进程，而这个进程能够使用的资源量，则受 Cgroups 配置的限制。容器是一个“单进程”模型。

由于一个容器的本质就是一个进程，用户的应用进程实际上就是容器里 PID=1 的进程，也是其他后续创建的所有进程的父进程。这就意味着，在一个容器中，你没办法同时运行两个不同的应用，除非你能事先找到一个公共的 PID=1 的程序来充当两个不同应用的父进程，这也是为什么很多人都会用 systemd 或者 supervisord 这样的软件来代替应用本身作为容器的启动进程。


Mount Namespace 跟其他 Namespace 的使用略有不同的地方：它对容器进程视图的改变，一定是伴随着挂载操作（mount）才能生效。可以在容器进程启动之前重新挂载它的整个根目录“/”。而由于 Mount Namespace 的存在，这个挂载对宿主机不可见。

这个挂载在容器根目录上、用来为容器进程提供隔离后执行环境的文件系统，就是所谓的“容器镜像”。它还有一个更为专业的名字，叫作：rootfs（根文件系统）。

对 Docker 项目来说，它最核心的原理实际上就是为待创建的用户进程：

- 启用 Linux Namespace 配置
- 设置指定的 Cgroups 参数
- 切换进程的根目录（Change Root）

同一台机器上的所有容器，都共享宿主机操作系统的内核。这就意味着，如果你的应用程序需要配置内核参数、加载额外的内核模块，以及跟内核进行直接的交互，你就需要注意了：这些操作和依赖的对象，都是宿主机操作系统的内核，它对于该机器上的所有容器来说是一个“全局变量”，牵一发而动全身。


### 镜像
镜像的层都放置在 /var/lib/docker/aufs/diff 目录下，然后被联合挂载在 /var/lib/docker/aufs/mnt 里面

#### 只读层
挂载方式都是只读的（ro+wh，即 readonly+whiteout）

#### 可读写层
挂载方式为：rw，即 read write。在没有写入文件之前，这个目录是空的。而一旦在容器里做了写操作，你修改产生的内容就会以增量的方式出现在这个层中。

要删除只读层里一个名叫 foo 的文件，那么这个删除操作实际上是在可读写层创建了一个名叫.wh.foo 的文件。这样，当这两个层被联合挂载之后，foo 文件就会被.wh.foo 文件“遮挡”起来，“消失”了。这个功能，就是“ro+wh”的挂载方式，即只读 +whiteout 的含义。我喜欢把 whiteout 形象地翻译为：“白障”。

#### Init层
以“-init”结尾的层，夹在只读层和读写层之间。Init 层是 Docker 项目单独生成的一个内部层，专门用来存放 /etc/hosts、/etc/resolv.conf 等信息。

这些文件本来属于只读的 Ubuntu 镜像的一部分，但是用户往往需要在启动容器时写入一些指定的值比如 hostname，所以就需要在可读写层对它们进行修改。所以，Docker 做法是，在修改了这些文件之后，以一个单独的层挂载了出来。而用户执行 docker commit 只会提交可读写层，所以是不包含这些内容的。



### Volume
支持两种 Volume 声明方式，可以把宿主机目录挂载进容器的 /test 目录当中：
```bash
$ docker run -v /test ...
$ docker run -v /home:/test ...
```

在第一种情况下，由于你并没有显示声明宿主机目录，那么 Docker 就会默认在宿主机上创建一个临时目录 /var/lib/docker/volumes/[VOLUME_ID]/_data，然后把它挂载到容器的 /test 目录上。

当容器进程被创建之后，尽管开启了 Mount Namespace，但是在它执行 chroot（或者 pivot_root）之前，容器进程一直可以看到宿主机上的整个文件系统。而宿主机上的文件系统，也自然包括了我们要使用的容器镜像。这个镜像的各个层，保存在 /var/lib/docker/aufs/diff 目录下，在容器进程启动后，它们会被联合挂载在 /var/lib/docker/aufs/mnt/ 目录中，这样容器所需的 rootfs 就准备好了。

更重要的是，由于执行这个挂载操作时，“容器进程”已经创建了，也就意味着此时 Mount Namespace 已经开启了。所以，这个挂载事件只在这个容器里可见。你在宿主机上，是看不见容器内部的这个挂载点的。这就保证了容器的隔离性不会被 Volume 打破。

容器 Volume 里的信息，并不会被 docker commit 提交掉；但这个挂载点目录 /test 本身，则会出现在新的镜像当中。


