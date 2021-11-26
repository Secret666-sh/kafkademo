package leecode;

import java.util.Stack;

public class SingleLinkedListDemo {

    public static void main(String[] args) {

        HeroNode hero1 =  new HeroNode(1,"111","11111");
        HeroNode hero2 =  new HeroNode(2,"222","22222");
        HeroNode hero3 =  new HeroNode(3,"333","33333");
        HeroNode hero4 =  new HeroNode(4,"444","44444");

        SingleLinkedList singleLinkedList = new SingleLinkedList();

        singleLinkedList.add(hero1);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero4);


        HeroNode hero7 =  new HeroNode(7,"777","77777");
        HeroNode hero6 =  new HeroNode(6,"666","66666");
        HeroNode hero9 =  new HeroNode(9,"999","99999");
        HeroNode hero8 =  new HeroNode(8,"888","88888");

        SingleLinkedList singleLinkedList1 = new SingleLinkedList();

        singleLinkedList1.add(hero7);
        singleLinkedList1.add(hero6);
        singleLinkedList1.add(hero9);
        singleLinkedList1.add(hero8);

        singleLinkedList.list();

        singleLinkedList1.list();
        System.out.println();
        System.out.println();

        //逆向打印链表
        //singleLinkedList.revertPrintLinked(singleLinkedList.getHead());



          //反转链表
//        singleLinkedList.revertLinked(singleLinkedList.getHead());
//
//        singleLinkedList.list();

        //按编号顺序添加节点
//        singleLinkedList.addOrderByNo(hero4);
//        singleLinkedList.addOrderByNo(hero3);
//        singleLinkedList.addOrderByNo(hero1);
//        singleLinkedList.addOrderByNo(hero2);
      //  singleLinkedList.addOrderByNo(hero2);

//        singleLinkedList.list();
//
//        singleLinkedList.delete(hero3);
//
//        System.out.println();
//
//        singleLinkedList.list();

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


    //单链表反转
    public void revertLinked(HeroNode head){

         HeroNode cur = head.next;
         HeroNode next = null;
         HeroNode revertHeroNode = new HeroNode(0, "", "");

         while (cur!=null){
             //提前寻址到下一个节点
             next = cur.next;
             //将 revertHeroNode的下一个节点指向 赋值给 当前cur节点的下一个指向
             cur.next = revertHeroNode.next;
             //将 revertHeroNode 指向 cur节点
             revertHeroNode.next = cur;

             //节点后移
             cur = next;
         }

         //将 revertHeroNode的指向赋值给 head 节点的指向
         head.next = revertHeroNode.next;

    }


     //单链表 逆向打印
     public void revertPrintLinked(HeroNode head){

         Stack<HeroNode> heroNodes = new Stack<>();

         HeroNode temp = head.next;

         while (temp!=null){

             heroNodes.push(temp);

             temp = temp.next;
         }

         while (heroNodes.size()>0){

             System.out.println(heroNodes.pop());


         }


     }


     public HeroNode getHead() {
         return head;
     }

     public void setHead(HeroNode head) {
         this.head = head;
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
