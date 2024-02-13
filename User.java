package FishingGame;

public class User {
    private int row = 0;        // 유저 미끼의 움직임을 위한 row 변수
    private int col = 0;        // 유저 미끼의 움직임을 위한 col 변수
    private int moveCount = 0;  // 미끼의 움직임 횟수를 보여줄 변수
    private Fish[] fishBasket;  // 물고기를 잡으면 담을 바스켓 객체
    public int getRow() {       // row 넘겨줄 메소드
        return row;
    }
    public void setRow() {      // row 0 만드는 메소드 (다른 클래스에서 설정하기 위해)
        this.row = 0;
    }
    public int getCol() {       // col 넘겨줄 메소드
        return col;
    }
    public void setCol() {      // col 0 만드는 메소드 (다른 클래스에서 설정하기 위해)
        this.col = 0;
    }
    public void setFishBasket(Fish[] fishBasket) {  // 물고기받을 객체 크기를 정해서 받아올 메소드
        this.fishBasket = fishBasket;
    }
    public int getMoveCount() {     // 내 움직인 총량 보여줄 메소드
        return moveCount;
    }
    public String rowMove() {       // 움직임을 반영할 메소드 (row 이동용)
        // String 객체를 넘겨줘서 레이블에 반영하기 위해 객체 미리 생성
        String text;
        if (row == 4) { // 호수 배열의 크기를 벗어나는 경우
            // 텍스트 레이블에 반영하기 위한 텍스트
            text = "더이상 왼쪽으로 당길 수 없습니다";
        } else { // 호수배열의 크기를 벗어나지 않는 경우
            // row 증가, 움직임증가
            row++;
            moveCount++;
            // 텍스트 레이블에 반영하기 위한 텍스트
            text = "미끼를 움직이세요";
        }
        return text; // 텍스트를 넘겨주기
    }
    public String colMove() {       // 움직임을 반영할 메소드 (col 이동용)
        // String 객체를 넘겨줘서 레이블에 반영하기 위해 객체 미리 생성
        String text;
        if (col == 4) {// 호수 배열의 크기를 벗어나는 경우
            // 텍스트 레이블에 반영하기 위한 텍스트
            text = "더이상 오른쪽으로 당길 수 없습니다";
        } else { // 호수배열의 크기를 벗어나지 않는 경우
            // col 증가, 움직임증가
            col++;
            moveCount++;
            // 텍스트 레이블에 반영하기 위한 텍스트
            text = "미끼를 움직이세요";
        }
        return text; // 텍스트를 넘겨주기
    }
    public void fishCatch(Fish fish) {      // 물고기 받기 위한 메소드
        for (int i = 0; i < fishBasket.length; i++) { // 바스켓 크기만금 반복
            if (fishBasket[i] == null) {    // 바스켓이 비어있다면
                fishBasket[i] = fish; // 바스켓에 받은 물고기를 넣기
                break;              // 더 반복하면 안되므로 break
            }
        }
    }
}
