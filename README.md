# AndroidCache
Android缓存 
开发工具：Android Studio3.5.+、Gradle 3.5.2、Anddroid Gradle Plugin 5.4.1、最低SDK 21（Android5.0），目标SDK 29

使用两种开源框架分别是ASimpleCache和DiskLruCache。

ASimpleCache 轻量级的缓存框架，本Demo中只是验证使用，未进行接口二次封装
功能：
1、磁盘存储，默认存储在/data/data/包名/cache中。
2、可以存储String、JSON、Object、Bitmap、Drawable、byte。
3、所有类型均被存储为文件
4、所有文件key均被进行MD5加密
5、支持多线程
特色主要是：
1：轻，轻到只有一个JAVA文件。
2：可配置，可以配置缓存路径，缓存大小，缓存数量等。
3：可以设置缓存超时时间，缓存超时自动失效，并被删除。
4：支持多进程。
应用场景
1、替换SharePreference当做配置文件
2、可以缓存网络请求数据，比如oschina的android客户端可以缓存http请求的新闻内容，缓存时间假设为1个小时，超时后自动失效，让客户端重新请求新的数据，减少客户端流量，同时减少服务器并发量。

DiskLruCache 硬盘缓存框架，未被封装在SDK中
特点：
1、可自定义存储目录和容量大小
2、会根据AppVersion自动删除之前版本的缓存
3、容量不足会自动删除缓存数据，数据采用LRU（最近最少使用）原则删除。
本Demo对DiskLruCache框架的接口进行二次封装，目前只封装了针对图片的缓存。
