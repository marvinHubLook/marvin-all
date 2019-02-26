package cn.leetcode;

/**
 *
 * @program: marvin-all
 * @description: 判断link链表是否成环？哪个节点开始成环?环有几个节点?
 * @author: Mr.Wang
 * @create: 2019-02-21 15:15
 *
 * https://www.cnblogs.com/dancingrain/p/3405197.html
 **/
public class LinkCircle {
    class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
   }

    /**
     * 判断是否成环
     * @param head
     * @return
     */
   public boolean isCircle(ListNode head){
       if(head==null){
           return false;
       }
       ListNode slow=head;
       ListNode fast=head;
       while(slow.next !=null && fast.next!=null){
           slow=slow.next;
           fast=fast.next.next;
           if(slow==fast){
               break;
           }
       }
       if(slow.next==null || fast.next==null){
           return false;
       }
       return true;
   }

    /**
     * 判断哪个节点
     * @param head
     * @return
     */
   public ListNode getCircleNode(ListNode head){
       if(head==null){
           return null;
       }
       ListNode slow=head;
       ListNode fast=head;
       while(slow.next !=null && fast.next!=null){
           slow=slow.next;
           fast=fast.next.next;
           if(slow==fast){
               break;
           }
       }
       if(slow.next==null || fast.next==null){
           return null;
       }
       ListNode node1=head;
       ListNode node2=slow;
       while(node1!=node2){
           node1=node1.next;
           node2=node2.next;
       }
       return node1;
   }

}
