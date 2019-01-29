###Java字节码指令集
> 　对于大部分为与数据类型相关的字节码指令，他们的操作码助记符中都有特殊的字符来表明专门为哪种数据类型服务：i代表对int类型的数据操作，l代表long，s代表short，b代表byte，c代表char，f代表float，d代表double，a代表reference。

* 加载和存储指令
    * 将一个局部变量加载到操作栈的指令包括有：iload、iload_<n>、lload、lload_<n>、fload、fload_<n>、dload、dload_<n>、aload、aload_<n>
    * 将一个数值从操作数栈存储到局部变量表的指令包括有：istore、istore_<n>、lstore、lstore_<n>、fstore、fstore_<n>、dstore、dstore_<n>、astore、astore_<n>
    * 将一个常量加载到操作数栈的指令包括有：bipush、sipush、ldc、ldc_w、ldc2_w、aconst_null、iconst_m1、iconst_<i>、lconst_<l>、fconst_<f>、dconst_<d>
    * 扩充局部变量表的访问索引的指令：wide
    
* 运算指令
    * 加法指令：iadd、ladd、fadd、dadd
    * 减法指令：isub、lsub、fsub、dsub
    * 乘法指令：imul、lmul、fmul、dmul
    * 除法指令：idiv、ldiv、fdiv、ddiv
    * 求余指令：irem、lrem、frem、drem
    * 取反指令：ineg、lneg、fneg、dneg
    * 位移指令：ishl、ishr、iushr、lshl、lshr、lushr
    * 按位或指令：ior、lor
    * 按位与指令：iand、land
    * 按位异或指令：ixor、lxor
    * 局部变量自增指令：iinc
    * 比较指令：dcmpg、dcmpl、fcmpg、fcmpl、lcmp
  
*  类型转换指令：  
   Java虚拟机对于宽化类型转换直接支持，并不需要指令执行，包括：
    * int类型到long、float或者double类型
    * long类型到float、double类型
    * float类型到double类型
   窄化类型转换指令包括有：i2b、i2c、i2s、l2i、f2i、f2l、d2i、d2l和d2f。但是窄化类型转换很可能会造成精度丢失。
   
* 对象创建与操作指令
    * 创建类实例的指令：new
    * 创建数组的指令：newarray，anewarray，multianewarray
    * 访问类字段（static字段，或者称为类变量）和实例字段（非static字段，或者成为实例变量）的指令：getfield、putfield、getstatic、putstatic
    * 把一个数组元素加载到操作数栈的指令：baload、caload、saload、iaload、laload、faload、daload、aaload
    * 将一个操作数栈的值储存到数组元素中的指令：bastore、castore、sastore、iastore、fastore、dastore、aastore
    * 取数组长度的指令：arraylength
    * 检查类实例类型的指令：instanceof、checkcast   
   
*  操作数栈管理指令
    * Java虚拟机提供了一些用于直接操作操作数栈的指令，包括：pop、pop2、dup、dup2、dup_x1、dup2_x1、dup_x2、dup2_x2和swap；   
    
*  控制转移指令
    * 条件分支：ifeq、iflt、ifle、ifne、ifgt、ifge、ifnull、ifnonnull、if_icmpeq、if_icmpne、if_icmplt, if_icmpgt、if_icmple、if_icmpge、if_acmpeq和if_acmpne。
    * 复合条件分支：tableswitch、lookupswitch
    * 无条件分支：goto、goto_w、jsr、jsr_w、ret
  
*   方法调用和返回指令：
    * invokevirtual指令用于调用对象的实例方法，根据对象的实际类型进行分派（虚方法分派），这也是Java语言中最常见的方法分派方式。  
    ```
         1. 找到操作数栈顶的第一个元素所指向的对象的实际类型，记作C  
         2. 如果在类型C中找到与常量中的描述符和简单名称都相符的方法，则进行访问权限校验，如果通过则返回这个方法的直接引用，查找过程结束；如果不通过，则返回java.lang.IllegalAccessError异常。  
         3. 否则，按照继承关系从下往上依次对C的各个父类进行第2步的搜索和验证过程。  
         4. 如果始终没有找到合适的方法，则抛出java.lang.AbstractMethodError异常。 

    ```
        
    * invokeinterface指令用于调用接口方法，它会在运行时搜索一个实现了这个接口方法的对象，找出适合的方法进行调用。
    * invokespecial指令用于调用一些需要特殊处理的实例方法，包括实例初始化方法（§2.9）、私有方法和父类方法。
    * invokestatic指令用于调用类方法（static方法）。
    * 而方法返回指令则是根据返回值的类型区分的，包括有ireturn（当返回值是boolean、byte、char、short和int类型时使用）、lreturn、freturn、dreturn和areturn，另外还有一条return指令供声明为void的方法、实例初始化方法、类和接口的类初始化方法使用
   
    
 备注：   
 * 在非静态方法中，aload_0 表示将this 加载到操作数栈   
 * 在static 方法中，aload_0表示将方法中地第一个参数(局部变量表中的第一位)，加载到操作数栈中。   
 
 
 
 [jvm指令详解](https://blog.csdn.net/lz710117239/article/details/79808142)