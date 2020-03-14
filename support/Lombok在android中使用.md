

[Android Studio 3.x上使用Lombok](http://www.appblog.cn/2019/10/13/Android%20Studio%203.x上使用Lombok/)
[低版本Android上使用Lombok](https://www.jianshu.com/p/60b3ffc02bbe)



Android Studio 3.x上使用Lombok
 2019-10-13 Android  阅读量7
添加Gradle依赖
compileOnly 'org.projectlombok:lombok:1.18.8'  //添加lombok依赖
annotationProcessor 'org.glassfish:javax.annotation:10.0-b28'  //Java注解
配置开启annotation processor
在需要使用Lombok的模块的配置，默认build.gradle(Module:app)中添加如下配置开启Annotation Processor

android {

    ...
    
    defaultConfig {
        ...
        //添加如下配置 开启annotation processor
        javaCompileOptions {
            annotationProcessorOptions {
                includeCompileClasspath true
            }
        }
    }
    ...
}
Android Studio安装Lombok插件
添加依赖之后，虽然编译时是正确的。但是因为Android Studio语法识别器不认识@Getter和@Setter等注解，所以需要添加Lombok插件

在设置页面 -> plugins -> browser repository -> 搜索lombok -> install

成功安装之后，重启Android Studio即可
