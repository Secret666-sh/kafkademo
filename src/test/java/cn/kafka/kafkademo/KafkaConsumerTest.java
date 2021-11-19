package cn.kafka.kafkademo;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

public class KafkaConsumerTest {


    public static Properties getProperties(){
        Properties props = new Properties();

        /**
         * 2钟连接方式
         */
        //props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"39.106.36.223:9092");
        props.setProperty("bootstrap.servers","39.106.36.223:9092");

        //消费者分组ID，同分组内的消费之只能消费该消息一次,不同分组内的消费者可重复消费该消息
        props.put("group.id","consumer-group-g3");

        //默认是latest(实时消费最新), 如果需要重头开始消费,需要改为 earliest, 且消费者组名变更才会生效
        props.put("auto.offset.reset","earliest");

        //开启自动提交offset
        //props.put("enable.auto.commit","true");

        //自动提交offset延迟时间
        //props.put("auto.commit.interval.ms","1000");

        //关闭自动提交offset
        props.put("enable.auto.commit","false");



        //反序列化
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        return props;

    }



    @Test
    public void simpleConsumerTest(){

        Properties properties = getProperties();

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        //订阅主题
        kafkaConsumer.subscribe(Arrays.asList("huishao-topic"));


        while (true){
            //阻塞超时时间
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord record:records){

                System.err.printf("topic=%s, offset=%d, key=%s, value=%s %n", record.topic(),record.offset(),record.key(),record.value());
            }

            if(!records.isEmpty()){
                //同步阻塞提交offset,阻塞当前线程（自动失败重试）
                //kafkaConsumer.commitSync();

                //异步提交offset,不会阻塞当前线程（没有失败重试, 回调callback函数获取提交信息, 记录日志）
                kafkaConsumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                        if (e == null){
                            System.out.println("手动提交offset成功："+map.toString());
                        }else {
                            System.out.println("手动提交offset失败："+map.toString());
                        }
                    }
                });
            }
        }


    }
}
