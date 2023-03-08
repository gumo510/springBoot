# 基础镜像
FROM openjdk:8-jdk
# 作者声明，原为MAINTAINER，变更为LABEL
# MAINTAINER gumo
LABEL author="gumo"
# 容器卷设置
VOLUME /tmp
# 向基础镜像内添加文件，并重命名为`docker_boot.jar`
add target/demo-0.0.1-SNAPSHOT.jar docker_boot.jar
# 执行前置脚本。据说是修改文件时间？不太明白具体作用，望有大佬指点迷津！
RUN bash -c 'touch /docker_boot.jar'
# 最终执行脚本
ENTRYPOINT ["java", "-jar", "/docker_boot.jar"]
# 暴露端口
EXPOSE 8088