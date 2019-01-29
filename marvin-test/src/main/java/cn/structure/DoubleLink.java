package cn.structure;

/**
 * @Author : bingo
 * @category : 双向链表
 * @Date : 2018/5/31 17:57
 **/
public class DoubleLink<T> {
    DNode<T> head;
    int size;
    int modCount;

    // 构造函数
    public DoubleLink() {
        // 创建“表头”。注意：表头没有存储数据！
        head = new DNode<T>(null, null, null);
        head.prev = head.next = head;
        // 初始化“节点个数”为0
        size = 0;
    }

    public DNode<T> get(int index){
        if(index<0 || index>=size)
            throw new IndexOutOfBoundsException();
        int m;
        if(index<=(m=size/2)){
            DNode<T> node=head;
            for(int i=0;i<index;i++){
                if(null==node) return null;
                node=node.next;
            }
            return node;
        }else{
            DNode<T> node=head.prev;
            for(int i=(size-1);i>m;i--){
                if(null==node) return null;
                node=node.prev;
            }
            return node;
        }
    }
    public void insert(int index,T value){
        if(index==0){
            DNode node=new DNode<T>(head.prev,head,value);
            head.prev.next=node;
            head.prev=node;
            head=node;
        }else{
            DNode oldNode=get(index);
            DNode newNode=new DNode<T>(oldNode.prev,oldNode,value);
            oldNode.prev.next=newNode;
            oldNode.prev=newNode;
        }
        modCount++;
        size++;
    }
    public void del(int index){
        DNode oldNode=get(index);
        oldNode.next.prev=oldNode.prev;
        oldNode.prev.next= oldNode.next;
        oldNode=null;
        modCount++;
        size--;
    }
    public void insertHead(T value){
        insert(0,value);
    }
    public void insertTail(T value){
        if(size==0){
            insertHead(value);
        }else{
            DNode<T> tdNode = get(size - 1);
            DNode newNode=new DNode<T>(tdNode,tdNode.next,value);
            tdNode.next.prev=newNode;
            tdNode.next=newNode;
            modCount++;
            size++;
        }
    }

    static class  DNode<T>{
       private DNode<T> prev;
       private DNode<T> next;
       private T value;
       public DNode(DNode<T> prev, DNode<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
       }
    }

    public static void main(String[] args) {
        int[] iarr = {10, 20, 30, 40};
        DoubleLink<Integer> dlink = new DoubleLink<Integer>();
        for(int i:iarr){
            //dlink.insertHead(i);
            dlink.insertTail(i);
        }
        DNode<Integer> head = dlink.head;
        for(int i=0,j=dlink.size;i<j;i++){
            System.out.print(head.value+",");
            head=head.next;
        }
        System.out.println();
        dlink.insert(2,100);
        System.out.println(dlink.get(2).value);
        head = dlink.head;
        for(int i=0,j=dlink.size;i<j;i++){
            System.out.print(head.value+",");
            head=head.next;
        }
        dlink.del(2);
        System.out.println(dlink.get(2).value);
    }
}
