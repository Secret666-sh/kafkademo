package leecode;

public class SingleLinkedListDemo {

    public static void main(String[] args) {

        HeroNode hero1 =  new HeroNode(1,"111","11111");
        HeroNode hero2 =  new HeroNode(2,"222","22222");
        HeroNode hero3 =  new HeroNode(3,"333","33333");
        HeroNode hero4 =  new HeroNode(4,"444","44444");

        SingleLinkedList singleLinkedList = new SingleLinkedList();


//        singleLinkedList.add(hero1);
//        singleLinkedList.add(hero2);
//        singleLinkedList.add(hero3);
//        singleLinkedList.add(hero4);

        //按编号顺序添加节点
        singleLinkedList.addOrderByNo(hero4);
        singleLinkedList.addOrderByNo(hero3);
        singleLinkedList.addOrderByNo(hero1);
        singleLinkedList.addOrderByNo(hero2);
      //  singleLinkedList.addOrderByNo(hero2);

        singleLinkedList.list();

        singleLinkedList.delete(hero3);

        System.out.println();

        singleLinkedList.list();

    }


}



 class SingleLinkedList{

    private HeroNode head =  new HeroNode(0,"","");



    //插入节点
    public void add(HeroNode node){

        //定义辅助节点
        HeroNode temp = head;

        while (true){
            if(temp.next == null){
                break;
            }
            temp = temp.next;
        }
        temp.next = node;

    }


    //按顺序添加新的节点
    public void addOrderByNo(HeroNode node){

        HeroNode temp = head;

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
            node.next = temp.next;
            temp.next = node;
        }
    }


    //删除节点
     public void delete(HeroNode node){

        HeroNode temp = head;
        boolean flag = false;

        while(true){
            if(temp.next==null){
                break;
            }
            if(temp.next.no==node.no){
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if(flag){
            temp.next = temp.next.next;
        }else {
            System.out.println("删除的节点不存在");
        }


     }





     public void list(){

        //为空
        if(head==null){
            return;
        }

        HeroNode temp = head.next;

        while (true){
            if (temp==null){
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }

    }


}



class HeroNode{

    public int no;
    public String name;
    public String nickname;
    public HeroNode next;

    public HeroNode(int no,String name,String nickname){
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}