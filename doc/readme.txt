1.gateway可能会与web包有版本冲突,不能同时引入

2.consul启动命令
./consul agent -dev -bind=192.168.100.129 -client=0.0.0.0 -bootstrap-expect=1 -data-dir=/opt/consul/consul_data/ -node=server1

3.删除consul中无效的service
http://192.168.100.129:8500/v1/agent/service/deregister/systemConfig-8083
put方法

https://windmt.com/2018/05/09/spring-cloud-15-spring-cloud-gateway-ratelimiter
