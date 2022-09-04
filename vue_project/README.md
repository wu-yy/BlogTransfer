# vue_project

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn serve
```

### Compiles and minifies for production
```
yarn build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

# java 后端

# 本地测试h2

```
javac H2jdbcCreateDemo.java
java -cp .:h2/bin/h2-2.1.214.jar H2jdbcCreateDemo
```

```
java -jar target/markdown-0.0.1-SNAPSHOT.jar     
```

## 本地启动后端服务

```
mvn clean package -Dmaven.test.skip=true
java -jar target/markdown-0.0.1-SNAPSHOT.jar
```