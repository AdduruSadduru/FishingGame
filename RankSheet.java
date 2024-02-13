package FishingGame;

public class RankSheet {
    private String gameDate;                    // 데이터베이스column에 있는 날짜 변수
    private int clearTime;                      // 데이터베이스column에 있는 클리어타임 변수
    private int moveCount;                      // 데이터베이스column에 있는 움직임 카운트 변수
    public String getGameDate() {// 날짜변수 리턴
        return gameDate;
    }
    public void setGameDate(String gameDate) {// 날짜변수 받기
        this.gameDate = gameDate;
    }
    public int getClearTime() {// 클리어타임변수 리턴
        return clearTime;
    }
    public void setClearTime(int clearTime) {// 클리어타임변수 받기
        this.clearTime = clearTime;
    }
    public int getMoveCount() {// 움직임카운트변수 리턴
        return moveCount;
    }
    public void setMoveCount(int moveCount) {// 움직임카운트변수 받기
        this.moveCount = moveCount;
    }
}
