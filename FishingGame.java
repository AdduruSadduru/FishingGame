package FishingGame;
import javax.swing.*;
import java.sql.SQLException;

public class FishingGame extends JFrame {
    private Panel_Main pMain;                                    // 첫번째 패널 변수 선언
    private Panel_Game pGame;                                    // 두번째 패널 변수 선언
    private Panel_Ranking pRank;                                 // 세번째 패널 변수 선언
    FishingGame() {
        pMain = new Panel_Main(this);                                // 메인 패널 생성
        super.getContentPane().add(pMain);                               // 시작은 첫번째 패널
        super.setSize(500, 700);                            // 크기 정하기
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // 닫기 누르면 프로그램 종료
        super.setTitle("낚시 게임");                                      // 프레임 제목
        super.setLocationRelativeTo(null);                              // 화면 가운데
        super.setVisible(true);                                         // 화면 나타내기
    }
    public void changePanel(String change) throws InterruptedException, SQLException {  // 페널 교체용 메소드
        if (change.equals("pMain")) {                                    // change가 "pMain"이면 실행
            getContentPane().removeAll();                               // 일단 패널 지우기
            getContentPane().add(pMain);                                 // 새로운 패널 넣기(메인 화면)
            revalidate();                                               // 유효성 검사
            repaint();                                                  // 그대로 반영해서 그리기
        } else if (change.equals("pRank")) {
            pRank = new Panel_Ranking(this);                         // 랭킹 패널 생성
            getContentPane().removeAll();                               // 일단 패널 지우기
            getContentPane().add(pRank);                                 // 새로운 패널 넣기(랭킹 화면)
            revalidate();                                               // 유효성 검사
            repaint();                                                  // 그대로 반영해서 그리기
        } else {                                                        // change가 "pMain"이 아니면 실행(pGame)
            pGame = new Panel_Game(this);                           // 게임 패널 생성
            getContentPane().removeAll();                               // 일단 패널 지우기
            getContentPane().add(pGame);                                 // 새로운 패널 넣기(게임 화면)
            revalidate();                                               // 유효성 검사
            repaint();                                                  // 그대로 반영해서 그리기
        }
    }
    public static void main(String[] args) throws InterruptedException, SQLException {
        FishingGame fg = new FishingGame();
    }
}
