package FishingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
public class Panel_Main extends JPanel implements ActionListener {
    private FishingGame fg;                                             // 시작 프레임 변수 선언
    private JLabel mainLabel;                                           // 레이블 변수 선언
    private JButton btn1, btn2;                                         // 버튼1 변수 선언
    Panel_Main(FishingGame fg) {
        this.fg = fg;                                                   // 시작 프레임 셋팅
        setLayout(null);                                                // 레이아웃 설정 (위치는 내맘대로 저장)
        // 대문 레이블
        mainLabel = new JLabel();                                       // 대문 레이블 만들기
        mainLabel.setText("낚시 게임");                                  // 레이블 내용 저장
        Font lFont = new Font("Serif", Font.BOLD, 50);      // 폰트(다들 있을법한 폰트, 볼드체, 50사이즈)
        mainLabel.setFont(lFont);                                      // 레이블에 만든 폰트 넣기
        mainLabel.setBounds(140, 50, 220, 50);       // 레이블 위치 정하기
        add(mainLabel);                                                 // 패널에 레이블 넣기
        // 게임 시작 버튼
        btn1 = new JButton("게임 시작");                            // 버튼1
        btn1.setBounds(150, 400, 200, 50);           // 버튼 크기, 위치 정하기
        Font b1Font = new Font("Serif", Font.PLAIN, 20);    // 버튼폰트 만들기(폰트, 기본굵기, 사이즈)
        btn1.setFont(b1Font);                                          // 버튼에 폰트 넣기
        btn1.addActionListener(this);                               // 이벤트 메소드 호출
        add(btn1);                                                     // 패널에 버튼1 넣기
        // 기록 확인 버튼
        btn2 = new JButton("기록 확인");                            // 버튼2
        btn2.setBounds(150, 500,200, 50);           // 버튼 크기, 위치 정하기
        Font b2Font = new Font("Serif", Font.PLAIN, 20);   // 버튼폰트 만들기(폰트, 기본굵기, 사이즈)
        btn2.setFont(b2Font);                                         // 버튼에 폰트 넣기
        btn2.addActionListener(this);                              // 이벤트 메소드 호출
        add(btn2);                                                   // 패널에 버튼2 넣기
    }
    @Override
    public void actionPerformed(ActionEvent e) {// 버튼 이벤트
        try {
            // 메인 패널을 change 메소드를 호출해서 변경
            if (e.getSource() == btn1) {    // 첫번째 버튼을 누를경우
                fg.changePanel("pGame");   // change메소드 호출(매개변수 String pGame)
            } else {                       // 두번째 버튼을 누를경우
                fg.changePanel("pRank");   // change메소드 호출(매개변수 String pRank)
            }
        } catch (InterruptedException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}