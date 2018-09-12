# 开发：
1. 下载 protoc-3.0.0-win32.zip： https://blog.csdn.net/warrially/article/details/52683611
2. 执行extentions/genCode.bat+ template.proto生成 protobuf java类
3. CommandLineRunner.run方法可以在spring加载后执行netty的启动方法。
4.  NettyServerListener监听端口
5. ChannelHandlerRegister初始化隧道，设置netty内置的protobuf的编解码handler
6. handler中定义业务模型