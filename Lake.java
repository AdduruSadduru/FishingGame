package FishingGame;

import java.util.Random;
class Fish{}    // 물고기 클래스
public class Lake {
    private Fish[][] lake = new Fish[5][5]; // 물고기 클래스를 이용해서 5X5사이즈 호수 생성 (물고기는 어디에나 있을수 있다)
    private Random rd = new Random();       // 랜덤받을 객체 생성
    private int fishCount = 0;              // 남은 물고기 카운트할 int 변수 생성
    Lake() {
        for (int i = 0; i < 3; i++) {           // 3회 반복용 for문
            int row = rd.nextInt(5);     // 랜덤숫자 0~4까지 받기 (행 설정용)
            int col = rd.nextInt(5);     // 랜덤숫자 0~4까지 받기 (열 설정용)
            // 랜덤숫자 0 X 0일경우 캐스팅은 0 X 0 으로 정해놓은 게임이므로 시작하자마자 잡히면 안됩니다.
            // 0 X 0일 경우 또는 배열 안에 이미 물고기 객체가 있는 경우
            if ((row == 0 && col == 0) || (lake[row][col] != null)) {
                i--;                                // 랜덤 다시 돌리기
            } else {                                // if 걸러지고 나머지
                lake[row][col] = new Fish();        // 새 물고기 1마리 넣기
                fishCount++;                        // 물고기 카운트 1마리 추가
            }
        }
    }
    public Fish[][] getLake() {     // 다른 객체에 호수 넘겨주기
        return lake;
    }
    public int getFishCount() {     // 다른 객체에 카운트 보여주기
        return fishCount;
    }
    public Fish giveFish(int row, int col) {        // 호수의 내용물을 넘겨주는 메소드
        if (lake[row][col] != null) {   // 내용물이 비어있지 않다면
            fishCount--;                // 물고기 카운트 줄이기
        }                               // 만약 내용물이 비어있으면 null
        return lake[row][col];          // 호수의 내용물 리턴
    }
}
