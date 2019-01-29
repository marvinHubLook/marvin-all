package cn.structure;

/**
 * @Author : bingo
 * @category : 平衡二叉树
 * @Date : 2018/6/1 9:58
 **/
public class AVLTreeNode<T extends Comparable> {
    transient AVLNode<T> rootNode;

    static class AVLNode<T>{
        AVLNode<T> left;
        AVLNode<T> right;
        int height;
        T value;
        public AVLNode(AVLNode<T> left, AVLNode<T> right, int height, T value) {
            this.left = left;
            this.right = right;
            this.height = height;
            this.value = value;
        }
    }

    int getHeight(AVLNode<T> node){
        if(null==node) return 0;
        return node.height;
    }
    int max(int h1,int h2){
       return h1>h2?h1:h2;
    }

    void insert(T value){
       if(null==rootNode){
           rootNode=new AVLNode<T>(null,null,1,value);
           return ;
       }
       rootNode=insert(rootNode,value);
    }
    void remove(T value){
        if(null==rootNode || null==value){
            return ;
        }
        rootNode=remove(rootNode,value);
    }

    private AVLNode<T>  getCurrMaxNode(AVLNode<T> node){
        if(null==node || node.right==null) return node;
        node=getCurrMaxNode(node.right);
        return node;
    }

    private AVLNode<T>  getCurrMinNode(AVLNode<T> node){
        if(null==node || node.left==null) return node;
        node=getCurrMinNode(node.left);
        return node;
    }

    void prePrint(){
        prePrint(rootNode);
    }
    void mediPrint(){
        mediPrint(rootNode);
    }
    void afterPrint(){
        afterPrint(rootNode);
    }

    private void prePrint(AVLNode<T> node){
        if(null!=node){
            System.out.print(node.value+",");
            prePrint(node.left);
            prePrint(node.right);
        }
    }
    private void mediPrint(AVLNode<T> node){
        if(null!=node){
            mediPrint(node.left);
            System.out.print(node.value+",");
            mediPrint(node.right);
        }
    }
    private void afterPrint(AVLNode<T> node){
        if(null!=node){
            afterPrint(node.left);
            afterPrint(node.right);
            System.out.print(node.value+",");
        }
    }


    /**   
     * @category : LL 旋转
     * @Description :（LeftLeft，也称为"左左"。插入或删除一个节点后，根节点的左子树的左子树还有非空子节点，导致"根的左子树的高度"比"根的右子树的高度"大2，导致AVL树失去了平衡。）
     *
     *           8                                    4
     *          / \                                  / \
     *         4   12       需要右旋                 2    8
     *        / \          --------->             /    / \
     *       2   6                               1    6  12
     *      /
     *     1
     *
     */
    private AVLNode<T> llRotation(AVLNode<T> node){
         AVLNode<T> left=node.left;
         node.left=left.right;
         left.right=node;
         node.height=max(getHeight(node.left),getHeight(node.right))+1;
         left.height=max(getHeight(left.left),node.height)+1;
         return left;
    }

    /**
     * @category : RR 旋转
     * @Description : RightRight，称为"右右"。插入或删除一个节点后，根节点的右子树的右子树还有非空子节点，导致"根的右子树的高度"比"根的左子树的高度"大2，导致AVL树失去了平衡。
     *
     *           8                                    12
     *          / \                                  /  \
     *         4   12       需要左旋                 8     14
     *            /  \     --------->             / \    /
     *           10  14                          4   10 13
     *               /
     *              13
     */
    private AVLNode<T> rrRotation(AVLNode<T> node){
        AVLNode<T> right=node.right;
        node.right=right.left;
        right.left=node;
        node.height=max(getHeight(node.left),getHeight(node.right))+1;
        right.height=max(getHeight(right.left),node.height)+1;
        return right;
    }

    /**
     * @category : LR 旋转
     * @Description :（LeftRight，也称为"左右"。插入或删除一个节点后，根节点的左子树的右子树还有非空子节点，导致"根的左子树的高度"比"根的右子树的高度"大2，导致AVL树失去了平衡。）
     *
     *           8                                    8                                    6
     *          / \                                  / \                                 /  \
     *         4   12       需要先左旋                6   12              然后右旋          4    8
     *        / \          --------->              / \                --------->       /    / \
     *       2   6                                4   7                               2    7  12
     *            \                              /
     *             7                            2
     *
     */
    private AVLNode<T> lrRotation(AVLNode<T> node){
        node.left=rrRotation(node.left);
        return llRotation(node);
    }
    /**
     * @category : RL 旋转
     * @Description :（RightLeft，称为"右左"。插入或删除一个节点后，根节点的右子树的左子树还有非空子节点，导致"根的右子树的高度"比"根的左子树的高度"大2，导致AVL树失去了平衡。）
     *
     *           8                                    8                                    10
     *          / \                                  / \                                 /   \
     *         4   12       需要先右旋                4  10              然后左旋          8     12
     *            /        --------->                 / \            --------->       / \    /
     *           10                                  9  12                           4   9  11
     *          / \                                     /
     *         9   11                                  11
     *
     */
    private AVLNode<T> rlRotation(AVLNode<T> node){
        node.right=llRotation(node.right);
        return rrRotation(node);
    }


    private AVLNode<T> insert(AVLNode<T> node,T value){
       if(null==node){
           node=new AVLNode(null,null,1,value);
       }else{
           int i = node.value.compareTo(value);
           if(i>0){
               node.left=insert(node.left,value);
               if(getHeight(node.left)-getHeight(node.right)>1){
                   if(node.left.value.compareTo(value)>0){   //添加的值在左侧节点
                       node=llRotation(node);
                   }else{
                       node=lrRotation(node);
                   }
               }
           }else if(i<0){
               node.right=insert(node.right,value);
               if(getHeight(node.right)-getHeight(node.left)==2){
                  if(node.right.value.compareTo(value)>0){  //添加的值在右侧节点
                      node=rlRotation(node);
                  }else{
                      node=rrRotation(node);
                  }
               }
           }else{
               System.out.println("添加失败：不允许添加相同的节点！");
           }
       }
       node.height=max(getHeight(node.left),getHeight(node.right))+1;
       return node;
    }

    private AVLNode<T> remove(AVLNode<T> node,T value){
        if(null==node || value==null){
            return null;
        }else{
           int index=node.value.compareTo(value);
           if(index>0){
               node.left=remove(node.left,value);
                if(getHeight(node.right)-getHeight(node.left)==2){  //AVL 失去平衡
                    if(getHeight(node.right.left)>getHeight(node.right.right)){
                        rlRotation(node);
                    }else{
                        rrRotation(node);
                    }
                }
           }else if(index<0){
               node.right=remove(node.right,value);
               if(getHeight(node.left)-getHeight(node.right)==2){  //AVL 失去平衡
                    if(getHeight(node.left.left)>getHeight(node.left.right) ){
                        llRotation(node);
                    }else{
                        lrRotation(node);
                    }
               }
           }else{
                if(node.left!=null && node.right!=null){
                    if(getHeight(node.left)>getHeight(node.right) ){
                         // 如果tree的左子树比右子树高；
                         // 则(01)找出tree的左子树中的最大节点
                         //   (02)将该最大节点的值赋值给tree。
                         //   (03)删除该最大节点。
                         // 这类似于用"tree的左子树中最大节点"做"tree"的替身；
                         // 采用这种方式的好处是：删除"tree的左子树中最大节点"之后，AVL树仍然是平衡的。
                        AVLNode<T> currMaxNode = getCurrMaxNode(node.left);
                        node.value=currMaxNode.value;
                        node.left=remove(node.left,currMaxNode.value);
                    }else{
                        // 如果tree的左子树不比右子树高(即它们相等，或右子树比左子树高1)
                        // 则(01)找出tree的右子树中的最小节点
                        //   (02)将该最小节点的值赋值给tree。
                        //   (03)删除该最小节点。
                        // 这类似于用"tree的右子树中最小节点"做"tree"的替身；
                        // 采用这种方式的好处是：删除"tree的右子树中最小节点"之后，AVL树仍然是平衡的。
                        AVLNode<T> currMinNode = getCurrMinNode(node.right);
                        node.value=currMinNode.value;
                        node.right=remove(node.right,currMinNode.value);
                    }
                }else{
                    AVLNode<T> tmp = node;
                    node=(node.left==null?node.right:node.left);
                    tmp=null;
                }
           }
        }
        if(null!=node) node.height=max(getHeight(node.left),getHeight(node.right))+1;
        return node;
    }

    public static void main(String[] args) {
        int[] arr= {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};
        //int[] arr= {7,16,15};
        //int[] arr= {3,1,2};
        AVLTreeNode<Integer> tree=new AVLTreeNode();
        for(int a:arr ){
            tree.insert(a);
            tree.prePrint();
            System.out.println();
        }
        tree.remove(8);
        tree.remove(10);
        tree.prePrint();
    }
}
