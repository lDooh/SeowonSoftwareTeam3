# SeowonSoftwareTeam3

## 2022-2 소프트웨어개발방법론 3팀

+ 팀장: 이두희
+ 팀원: 유승민, 이동훈

# [3주차 주사위 게임](https://github.com/lDooh/SeowonSoftwareTeam3/tree/main/DiceProject)

### 게임 규칙
+ 두 사람이 주사위를 세 개씩 굴림
+ 중복되는 숫자가 없을 시 세 숫자의 합이 높은 경우 승리
+ 중복되는 숫자가 있는 경우 중복되는 숫자를 가진 주사위가 많은 쪽이 승리
+ 중복되는 숫자를 가진 주사위의 개수가 같은 경우 중복된 숫자가 높은 쪽이 승리

# 멀티스레드 에코 프로토콜 C/S 코드 구현

## [서버](https://github.com/lDooh/SeowonSoftwareTeam3/tree/main/EchoProtocolServer)
+ `ServerMain.java` 클래스 실행
+ 클라이언트가 접속하면 자식 서버 스레드 생성

## [클라이언트](https://github.com/lDooh/SeowonSoftwareTeam3/tree/main/EchoProtocolProject)
+ `seowonSocket.OmokClientThread.java` 파일의 serverIp 속성에 서버 IP 주소 입력
+ `ClientMain.java` 클래스 실행
+ 메시지와 반복 횟수를 입력하고 전송 버튼을 누르면 메시지 전송
  + 반복 횟수(N-Echo)만큼 ACK를 받고, JList UI에 출력
+ 종료 버튼을 누르면 서버에 종료 메시지를 보냄
  + ACK_END를 받으면 프로그램 종료

# [오목 게임](https://github.com/lDooh/SeowonSoftwareTeam3/tree/main/OmokProject)
## 프로그램 작동 방식
1. 두 명의 사용자 접속
2. 두 명의 사용자가 번갈아가면서 돌을 착수
   1. 돌을 착수할 시 좌표 정보를 서버에 전송
   2. 서버가 해당 동작에 대한 처리(중복검사, 승패 판별 등)
   3. 처리 결과와 게임 종료 여부를 클라이언트에 전송
3. 2번을 반복
## GUI 테스트 모듈
[싱글 오목 프로그램](https://github.com/lDooh/SeowonSoftwareTeam3/tree/main/OmokProject/src/single_omok/OmokGameMain.java)