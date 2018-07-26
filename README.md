# springboot-rabbitmq
Spring Boot + RabbitMQ 예제

[Spring Boot]RabbitMQ 연동하기

https://github.com/blusky10/study_spring

Spring Boot에서 RabbitMQ 를 연결을 해보자.

원래는 AWS에 있는 Ubuntu 서버에 설치를 한후 연결을 하려고 했다. 그런데 설치까지는 문제가 없이 잘 진행 됐는데 이상하게 SpringBoot에서 연결 하려고 하면 Connection Refuse가 계속 나와 몇일을 보냈다. port를 안연것도 아니고, management 콘솔은 15672포트로 잘만 접속 되는데 5672포트로 연결 하려고만 하면 안됐다. 오늘까지 하다가 안되면 GG 치려고 했다. 실제로 GG 치고 로컬에 깔고 진행을 했다.

그래서 정상 작동하는거 보고 혹시나 해서 다시 AWS에 있는 Rabbit MQ에 접속을 시도해 봤는데 됐다... 왜 되는거지???? --_--;;

출처: http://blusky10.tistory.com/311 [Polpid's World]



★ 허원철의 Spring Boot - RabbitMQ

http://heowc.tistory.com/36

