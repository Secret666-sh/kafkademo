package cn.kafka.kafkademo;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;

public class KafkaProducerTest {


    public static Properties getProperties(){
        Properties props = new Properties();

        /**
         * 2钟连接方式
         */
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"39.106.36.223:9092");
        //props.setProperty("bootstrap.servers","39.106.36.223:9092");

        //当producer向leader发送消息时,可通过request，require，acks设置可靠级别，分别为0，1，all
        props.put("acks","all");
        //props.put(ProducerConfig.ACKS_CONFIG,"all");

        //请求失败，生产者会自动重启，指定是0次，如果重启，则会有重复消费消息的可能性
        props.put("retries",0);
        //props.put(ProducerConfig.RETRIES_CONFIG,0);

        //生产者缓存每个分区未发送的消息，缓存的大小是通过 batch.size 配置指定的，默认是16KB
        props.put("batch.size",16384);

        /**
         * 默认值是0 ，消息会立即发送，即 batch.size 的缓冲空间还未满
         * batch.size 和 linger.ms 其中之一达标，消息就会发送
         */
        props.put("linger.ms",5);

        /**
         * buffer.memory用来约束kafka Producer能够使用的内存缓冲的大小，默认是是32M
         * 如果buffer.memory设置的过小，可能导致消息快速的写入内存缓冲里面，但是 sender线程来不及把消息发送到kafka服务器
         * 会造成内存缓冲很快被写满，而一旦写满，就会阻塞用户线程，不让继续往kafka写消息了
         * buffer.memory 要大于 batch.size 否则汇报申请内存不足，且不要超过物理内存
         * 需要根据实际情况压测配置
         */
        props.put("buffer.memory",33554432);

        //序列化
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        return props;

    }


    /**
     * send()是异步方法，添加消息到缓冲区等待发送，并立即返回
     * 通过batch.size 和 linger.ms 结合来提高效率
     *
     * 实现同步发送： 一条消息发送后，会阻塞当前线程，直至返回 ack
     * 发送消息会返回Future 对象，调用get()即可
     */
    @Test
    public void testSend(){
        Properties properties = getProperties();
        KafkaProducer<Object, Object> producer = new KafkaProducer<>(properties);

        for (int i = 0; i <10 ; i++) {
            Future<RecordMetadata> future = producer
                    .send(new ProducerRecord<>("huishao-topic", "huige" + i, "huishao" + i));
            RecordMetadata recordMetadata = null;
            try {
                recordMetadata = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("发送消息状态：" + recordMetadata.toString());
        }

        producer.close();


    }


    /**
     * 发送消息携带回调函数
     */
    @Test
    public void testSendWithCallback(){
        Properties properties = getProperties();
        KafkaProducer<Object, Object> producer = new KafkaProducer<>(properties);

        for (int i = 11; i <15 ; i++) {
            Future<RecordMetadata> future = producer
                    .send(new ProducerRecord<>("huishao-topic", "huige" + i, "huishao" + i),
                            new Callback() {
                                @Override
                                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                                    if (e == null){
                                        System.err.println("发送消息状态：" + recordMetadata.toString());
                                    }else {
                                        e.printStackTrace();
                                    }
                                }
                            });
            System.out.println(i+"发送："+ LocalDateTime.now().toString());
        }

        producer.close();


    }


    /**
     * 发送消息携带回调函数 指定分区
     */
    @Test
    public void testSendWithCallbackAndPatition(){
        Properties properties = getProperties();
        KafkaProducer<Object, Object> producer = new KafkaProducer<>(properties);

        for (int i = 16; i <20 ; i++) {
            Future<RecordMetadata> future = producer
                    .send(new ProducerRecord<>("huishao-topic",1, "huige" + i, "huishao" + i),
                            new Callback() {
                                @Override
                                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                                    if (e == null){
                                        System.err.println("发送消息状态：" + recordMetadata.toString());
                                    }else {
                                        e.printStackTrace();
                                    }
                                }
                            });
            System.out.println(i+"发送："+ LocalDateTime.now().toString());
        }

        producer.close();


    }



    /**
     * 发送消息携带回调函数 自定义分区
     */
    @Test
    public void testSendWithPatitionStrategy(){
        Properties properties = getProperties();
        //指定自定义分区策略
        properties.put("partitioner.class","cn.kafka.kafkademo.KafkaPatitioner");

        KafkaProducer<Object, Object> producer = new KafkaProducer<>(properties);

        for (int i = 30; i <35 ; i++) {
            Future<RecordMetadata> future = producer
                    //指定自定义分区策略时，key一定要传，会根据key去判定发送至那个分区
                    .send(new ProducerRecord<>("huishao-topic", "huishao" + i),
                            new Callback() {
                                @Override
                                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                                    if (e == null){
                                        System.err.println("发送消息状态：" + recordMetadata.toString());
                                    }else {
                                        e.printStackTrace();
                                    }
                                }
                            });
            System.out.println(i+"发送："+ LocalDateTime.now().toString());
        }

        producer.close();


    }

}
