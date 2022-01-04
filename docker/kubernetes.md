# Kubernetes
## 组件说明

- APIServer: 所有服务访问同一入库
- ControllerManager: 维持副本期望数目
- Scheduler: 负责选择合适节点进行分配任务
- etcd: 键值对数据库，存储K8S集群所有重要信息（持久化）
- Kubelet: 直接和容器引擎交互实现容器的声明周期管理
- Kube-proxy: 负责写入规则至IPTables，IPVS，实现服务映射访问
- CoreDNS: 为集群中的SVC创建一个域名IP的对应关系
- Dashboard: 为集群提供一个B/S结构访问体系
- Ingress Controller: 四层代理，可实现七层
- Federation: 提供一个可以跨集群中心多K8S统一管理功能
- Prometheus: 提供K8S集群的监控能力
- ELK: 集群日志统一分析接入平台
