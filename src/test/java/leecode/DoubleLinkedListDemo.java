package leecode;


/**
 * 双向链表
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {

        HeroNode2 hero1 =  new HeroNode2(1,"111","11111");
        HeroNode2 hero2 =  new HeroNode2(2,"222","22222");
        HeroNode2 hero3 =  new HeroNode2(3,"333","33333");
        HeroNode2 hero4 =  new HeroNode2(4,"444","44444");

        DoubleLinkedListDemo doubleLinkedList = new DoubleLinkedListDemo();

        doubleLinkedList.addOrderByNo(hero4);
        doubleLinkedList.addOrderByNo(hero2);
        doubleLinkedList.addOrderByNo(hero3);
        doubleLinkedList.addOrderByNo(hero1);

        doubleLinkedList.list();

//        doubleLinkedList.add(hero1);
//        doubleLinkedList.add(hero2);
//        doubleLinkedList.add(hero3);
//        doubleLinkedList.add(hero4);
//
//        doubleLinkedList.list();
//
//        doubleLinkedList.delete(hero4);
//
//        System.out.println();
//
//        doubleLinkedList.list();

    }

    private HeroNode2 head =  new HeroNode2(0,"","");


    //遍历节点
    public void list(){

        //为空
        if(head==null){
            return;
        }

        HeroNode2 temp = head.next;

        while (true){
            if (temp==null){
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }

    }

    //插入节点(尾部插入)
    public void add(HeroNode2 node){

        //定义辅助节点
        HeroNode2 temp = head;

        while (true){
            if(temp.next == null){
                break;
            }
            temp = temp.next;
        }
        temp.next = node;
        node.pre = temp;

    }


    //插入节点 (顺序添加)
    public void addOrderByNo(HeroNode2 node){

        HeroNode2 temp = head;

        boolean flag = false;

        while (true){
            if(temp.next==null){
                break;
            }else if(temp.next.no>node.no){
                break;
            }else if(temp.next.no == node.no){
                flag = true;
                System.out.println("编号已存在");
            }
            temp = temp.next;
        }

        if(flag){
            System.out.println("编号已存在, no is "+node.no);
        }else {
            //尾部添加
            if(temp.next==null){
                temp.next = node;
                node.pre = temp;
            }else {
               HeroNode2 temp2 =  temp.next;
               temp.next = node;
               node.pre = temp;

               node.next = temp2;
               temp2.pre = node;
            }

        }
    }


    //删除双向链表节点
    public void delete(HeroNode2 node){

        HeroNode2 temp = head;
        boolean flag = false;

        while(true){
            if(temp==null){
                break;
            }
            if(temp.no==node.no){
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if(flag){
            temp.pre.next = temp.next;
            //如果删除的是最后一个节点,temp.next.pre会报空指针异常
            if(temp.next!=null){
                temp.next.pre = temp.pre;
            }
        }else {
            System.out.println("删除的节点不存在");
        }


    }



    static class HeroNode2{

        public int no;
        public String name;
        public String nickname;
        public HeroNode2 next;
        public HeroNode2 pre;

        public HeroNode2(int no,String name,String nickname){
            this.no = no;
            this.name = name;
            this.nickname = nickname;
        }

        @Override
        public String toString() {
            return "HeroNode2{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }
    }

}
