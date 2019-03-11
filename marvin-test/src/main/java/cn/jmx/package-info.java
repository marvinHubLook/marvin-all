package cn.jmx;

/*
    standard MBean	这种类型的MBean最简单，一个标准的MBean由一个MBean接口(该MBean接口列出了所有被暴露的属性和操作对应的方法)
                    和一个class(这 个class实现了这个MBean接口并提供被监测资源的功能)组成（接口和class必须放在同一个包下，不然会出错）。
                    它的命名也必须遵循一定的规范，例如我们的MBean为Hello，则接口必须为HelloMBean。标准MBean只能操作基本数据类型，
                    如 int、dubbo、lang等

    dynamic MBean	必须实现javax.management.DynamicMBean接口，所有的属性，方法都在运行时定义

    open MBean	    此MBean的规范还不完善，正在改进中

    model MBean	    与标准和动态MBean相比，你可以不用写MBean类，只需使用javax.management.modelmbean.RequiredModelMBean即可。
                    RequiredModelMBean实现了ModelMBean接口，而ModelMBean扩展了DynamicMBean接口，因此与DynamicMBean相似，
                    Model MBean的管理资源也是在运行时定义的。与DynamicMBean不同的是，DynamicMBean管理的资源一般定义
                    在DynamicMBean中（运行时才决定管理那些资源），而model MBean管理的资源并不在MBean中，而是在外部（通常是一个类），
                    只有在运行时，才通过set方法将其加入到model MBean中。后面的例子会有详细介绍

*/
