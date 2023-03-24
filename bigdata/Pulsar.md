# Apache Pulsar

## 有了kafka为什么还要pulsar？

尽管Kafka是一款非常成熟和流行的分布式消息队列，但Pulsar在某些方面提供了更多的功能和优势，使得在某些使用场景下选择Pulsar可能更加合适。

以下是一些使用Pulsar的优势：

多租户支持：Pulsar内置多租户支持，每个租户都有自己的命名空间和配额。这使得在一个集群中为多个用户和应用程序提供服务更加容易，而不必担心资源抢占和安全问题。

分层架构：Pulsar的分层架构允许数据分区和管理，支持更高的可扩展性和更好的性能。相比之下，Kafka的架构较为简单，仅仅是由若干个Broker节点组成的集群。

支持更多的API和语言：Pulsar支持多种编程语言和API，包括Java、Python、Go、C++、JavaScript等，而Kafka主要支持Java和Scala。这意味着Pulsar可以更好地适应不同的开发需求和技术栈。

更高的消息传递保证：Pulsar提供At-least-once和Exactly-once两种消息传递保证，而Kafka仅提供At-least-once保证。这使得在需要更高的数据可靠性和一致性的场景中，Pulsar更为适合。

更强大的存储和查询能力：Pulsar支持存储和查询大规模数据，支持数据批处理和流式处理。同时，Pulsar还支持使用Apache BookKeeper进行高效和可靠的存储。而Kafka则主要是为消息传递设计的，不太适合作为存储引擎使用。

总的来说，Pulsar和Kafka在某些方面具有相似的功能，但Pulsar在多租户、分层架构、API和语言支持、消息传递保证以及存储和查询方面提供了更多的功能和优势。因此，在一些特定的使用场景下，选择Pulsar可能会更加合适。

## Apache Pulsar和Kafka有什么区别？

Apache Pulsar和Kafka都是流处理平台，用于实时数据处理和流式数据传输。它们都支持高可用性、可扩展性和容错性，但也有一些区别。

以下是Apache Pulsar和Kafka之间的一些区别：

架构设计
Apache Pulsar和Kafka在架构上有所不同。Kafka采用分布式发布-订阅模型，而Pulsar使用分层发布-订阅模型。Pulsar的分层架构允许数据分区和管理，支持更高的可扩展性和更好的性能。

多租户支持
Apache Pulsar内置了多租户支持，可以为每个租户提供独立的命名空间和配额。这使得在一个集群中为多个用户和应用程序提供服务更加容易。Kafka则需要使用第三方工具来支持多租户。

消息传递保证
Pulsar和Kafka都提供了At-least-once和Exactly-once语义的消息传递保证。但是，Pulsar的实现比Kafka更加简单和可靠，能够避免Kafka可能会遇到的重复消息问题。

支持语言和API
Apache Pulsar支持多种编程语言和API，包括Java、Python、Go、C++、JavaScript等。而Kafka主要支持Java和Scala。这意味着Pulsar可以更好地适应不同的开发需求和技术栈。

数据保留和清理
Pulsar和Kafka在数据保留和清理方面也有所不同。Pulsar可以在不影响数据可用性的情况下删除过时数据，而Kafka需要手动清理或使用特定的工具来清理过时数据。

综上所述，Apache Pulsar和Kafka虽然有一些共同点，但在一些关键方面有所不同。选择哪个平台主要取决于具体的使用场景和需求。

### Java消费Pulsar消息的示例代码

```java
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.ConsumerConfiguration;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class PulsarConsumer {

    public static void main(String[] args) throws PulsarClientException {
        // 设置 Pulsar 客户端配置
        String pulsarUrl = "pulsar://localhost:6650";
        String subscriptionName = "my-subscription";
        String topicName = "my-topic";
        ConsumerConfiguration consumerConfig = new ConsumerConfiguration();
        consumerConfig.setSubscriptionName(subscriptionName);

        // 创建 Pulsar 客户端
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(pulsarUrl)
                .build();

        // 创建 Pulsar 消费者
        ConsumerBuilder<byte[]> consumerBuilder = client.newConsumer().topic(topicName);
        Consumer<byte[]> consumer = consumerBuilder
                .subscriptionName(subscriptionName)
                .subscriptionType(SubscriptionType.Exclusive)
                .subscribe();

        // 从 Pulsar 接收消息
        while (true) {
            Message<byte[]> message = consumer.receive();
            try {
                // 处理消息
                String msg = new String(message.getData(), StandardCharsets.UTF_8);
                System.out.printf("Received message: %s%n", msg);

                // 确认消息已经被消费
                consumer.acknowledge(message);
            } catch (Exception e) {
                // 处理消息异常，不确认消息被消费
                consumer.negativeAcknowledge(message);
            }
        }
    }
}

```

说明：

- 首先，需要设置 Pulsar 客户端的配置，包括 Pulsar 的 URL、订阅名称和主题名称。
- 创建 Pulsar 客户端，使用 Pulsar 客户端建造者模式创建。
- 创建 Pulsar 消费者，使用 Pulsar 客户端的 newConsumer() 方法创建。
- 从 Pulsar 接收消息，使用 Pulsar 消费者的 receive() 方法获取消息。注意，这是一个阻塞方法。
处理消息，使用消息中的数据。处理完成后，确认消息已经被消费，使用 Pulsar 消费者的 acknowledge() 方法。
- 如果处理消息过程中出现异常，不确认消息被消费，使用 Pulsar 消费者的 negativeAcknowledge() 方法。这样可以确保消息不会被误删，可以在之后再次消费。

Java发送消息到Pulsar的示例代码

```java

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class PulsarPublisher {

    public static void main(String[] args) throws PulsarClientException {
        // 设置 Pulsar 客户端配置
        String pulsarUrl = "pulsar://localhost:6650";
        String topicName = "my-topic";

        // 创建 Pulsar 客户端
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(pulsarUrl)
                .build();

        // 创建 Pulsar 生产者
        ProducerBuilder<String> producerBuilder = client.newProducer(Schema.STRING).topic(topicName);
        Producer<String> producer = producerBuilder.create();

        // 发送消息到 Pulsar
        String message = "Hello, Pulsar!";
        producer.send(message);

        // 关闭 Pulsar 客户端
        producer.close();
        client.close();
    }
}
```

说明：

- 首先，需要设置 Pulsar 客户端的配置，包括 Pulsar 的 URL 和主题名称。
- 创建 Pulsar 客户端，使用 Pulsar 客户端建造者模式创建。
- 创建 Pulsar 生产者，使用 Pulsar 客户端的 newProducer() 方法创建。此处使用 String 类型的消息数据模式。
- 发送消息到 Pulsar，使用 Pulsar 生产者的 send() 方法。
- 关闭 Pulsar 客户端，使用 Pulsar 生产者的 close() 方法关闭生产者，使用 Pulsar 客户端的 close() 方法关闭客户端。
