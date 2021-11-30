package streamApi;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import entity.UserInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamListDemo {

    public static void main(String[] args) {

        ArrayList<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(new UserInfo("huishao0",10,"000"));
        userInfos.add(new UserInfo("huishao0",11,"111"));
        userInfos.add(new UserInfo("huishao1",12,"222"));
        userInfos.add(new UserInfo("huishao1",13,"333"));

        List<UserInfo> userInfoList = userInfos.stream()
                .filter(a -> a.getAddress().equals("000"))
                .filter(b -> b.getAge()==10)
                .collect(Collectors.toList());

        System.out.println(userInfoList);

        Map<String, List<UserInfo>> collect = userInfos.stream().collect(Collectors.groupingBy(a -> a.getName()));

        System.out.println(collect);

        Map<String, Double> collect1 = userInfos.stream().collect(
                Collectors.groupingBy(a -> a.getName(),
                        Collectors.averagingInt(a -> a.getAge())));

        System.out.println(collect1);

    }

}
