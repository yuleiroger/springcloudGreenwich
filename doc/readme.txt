1.gateway可能会与web包有版本冲突,不能同时引入

2.consul启动命令
./consul agent -dev -bind=192.168.100.129 -client=0.0.0.0 -bootstrap-expect=1 -data-dir=/opt/consul/consul_data/ -node=server1

进入WEB页面
http://192.168.100.129:8500

删除consul中无效的service
http://192.168.100.129:8500/v1/agent/service/deregister/systemConfig-8083
put方法

3.mongodb cluster
https://blog.csdn.net/luonanqin/article/details/8497860
step1 start up every mongo step by step
./mongod -f /opt/mongodb/mongodb-cluster/conf/master.conf

step2进入一个mongo
./mongo 192.168.100.129:27017
step3 cfg={_id:"testrs", members:[ {_id:0,host:'192.168.100.129:27017',priority:2}, {_id:1,host:'192.168.100.129:27018',priority:1},
{_id:2,host:'192.168.100.129:27019',arbiterOnly:true}] };
step4 rs.initiate(cfg)             #使配置生效
step5验证rs.status()

4.kafka
./kafka-server-start.sh /opt/kafka/config/server.properties &

https://www.cnblogs.com/chanshuyi/p/mysql_user_mng.html

https://windmt.com/2018/05/09/spring-cloud-15-spring-cloud-gateway-ratelimiter

https://www.cnblogs.com/jinshuaishuai/p/10831172.html

https://blog.csdn.net/qq_35868811/article/details/90257663
