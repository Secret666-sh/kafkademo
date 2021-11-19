package cn.kafka.kafkademo;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;

public class KafkaAdminTest {


    private static final String TOPIC_NAME = "huige666";


    public static AdminClient initAdminClient(){
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"39.106.36.223:9092");

        AdminClient adminClient = AdminClient.create(properties);
        return adminClient;
    }

    /**
     * 创建topic
     */
    @Test
    public void createTopicTest()  {
        AdminClient adminClient = initAdminClient();
        NewTopic newTopic = new NewTopic(TOPIC_NAME, 2, (short) 1);
        CreateTopicsResult createTopicsResult = adminClient.createTopics(Arrays.asList(newTopic));

        try{
            createTopicsResult.all().get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    /**
     * 获取topic列表
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void listTopicTest() throws ExecutionException, InterruptedException {
        AdminClient adminClient = initAdminClient();
        /**
         * adminClient.listTopics() 查看自己创建的topic
         * adminClient.listTopics(options) 查看内部所有的topic
         */
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true);

        ListTopicsResult listTopicsResult = adminClient.listTopics(options);
        adminClient.listTopics(options);

        Set<String> topics = listTopicsResult.names().get();

        for(String name :topics){
            System.err.println(name);
        }
    }


    /**
     * 删除topic
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void deleteTopicTest() throws ExecutionException, InterruptedException {

        AdminClient adminClient = initAdminClient();
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Arrays.asList("huige666"));

        deleteTopicsResult.all().get();



    }


}
