package FishingGame;

public class TimeStamp extends Thread {

    Panel_Game pg;      // 스레드 내에서 게임패널의 화면을 주기적으로 바꾸기위해 바꿀 객체 미리 선언
    TimeStamp(Panel_Game pg) {
        this.pg = pg;   // 미리 건네받은 객체를 생성자 밖에 미리 만들어놓은 객체에 연결
    }
    @Override
    public void run() {//스레드 시작
        // 게임 끝나기 전까지 반복 (게임 끝날시 브레이크변수가 false를 리턴해서 스레드가 멈추도록 변경
        while (pg.isThreadBreak()) {
            try {
                String minute;                        // 걸린 시간을 x분으로 만들 스트링 객체
                String second;                        // 걸린 시간을 x초로 만들 스트링 객체

                if (pg.getTime() / 60 == 0) {         // 만약 60초가 안넘었다면
                    minute = "";                      // 객체에 빈 공간을 대입(null이 아님)
                } else {                              // 만약 60초가 넘었다면
                    minute = (pg.getTime() / 60) + " 분 ";   // 시간을 60으로 나눠서 나머지 버리고 분만 뽑아서 객체 대입
                }
                if (pg.getTime() % 60 < 10) {         // 만약 분을 버리고 60초중(나머지연산) 초가 10초를 넘기지 못했다면
                    second = "0" + (pg.getTime() % 60) + " 초";  // 걸린 시간을 나머지연산해서 60이하면 앞에 0을붙여 꾸밈
                } else {                              // 만약 초가 10초를 넘겼다면
                    second = (pg.getTime() % 60) + " 초";    // 나머지연산으로 분을 버리고 초만 뽑아서 출력
                }
                pg.getTimeLabel().setText(minute + second); // 꾸며진 문자열 객체를 분, 초를 더해서 대입
                pg.setTime(pg.getTime() + 1);         // 계속 반복해서 초를 넣을 객체에 +1 더함
                // 더하기 자체를 1초에 한번씩 하도록
                // 스레드를 1000마일초(1초)간 지연시켜 반복
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
