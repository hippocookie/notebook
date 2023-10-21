# 启动集群

## 选举主节点

算法：Bully算法改进

每个节点均执行，对节点ID进行排序，选取ID最大的为Master，确定唯一主节点。

约定条件：

- 参选人数需要过半，达到quorum（多数）后就选出了临时的主
- 得票数需过半
- 当探测到节点离开事件时，必须判断当前节点数是否过半，否则放弃master，防止脑裂

quorum值从配置中读取
> discovery.zen.minmum_master_nodes

## 选举元信息

从个节点收集元信息，根据版本号确定最新的元信息，然后广播到各节点。

元信息级别：索引级、集群级

## allocation过程

在初始阶段，所有的shard都处于UNASSIGNED的状态，ES通过分配过程决定那个分片位于那个节点，重构内容路由表。

### 选主分片

通过集群中记录的最新主分片列表来确定主分片

> "cluster.routing.allocation.enable":"none"

禁止分配分片，集群仍会分配主分片

### 选副分片

主分片选举完成后，从汇总shard信息中选一个副本为副分片，如果汇总信息中不存在，则分配新的副本延迟操作依赖于配置项

> index.unassigned.node_left.delayed_timeout

## index recovery

### 主分片recovery

将最后一次Lucene提交后translog进行重放，建立Lucene索引

### 副分片recovery
