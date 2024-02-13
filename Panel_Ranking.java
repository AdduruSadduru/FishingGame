package FishingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
public class Panel_Ranking extends JPanel implements ActionListener {
    private FishingGame fg;                                             // 시작 프레임 변수 선언
    private JTextArea jta;                                              // 점수 기록할 텍스트박스 선언
    private JScrollPane sp;                                             // 텍스트박스에 스크롤 생성하기 위한 객체
    private JButton btn;                                                // 첫 화면으로 돌아갈 버튼
    Panel_Ranking (FishingGame fg) throws SQLException {
        DB_Ranking dbRanking = new DB_Ranking();                        // 데이터베이스 연결위한 객체 생성
        this.fg = fg;                                                   // 시작 프레임 셋팅
        setLayout(null);                                                // 레이아웃 설정 (위치는 내맘대로 저장)
        jta = new JTextArea();                                          // 텍스트박스 생성
        sp = new JScrollPane(jta);                           // 스크롤을 위해 텍스트박스를 매개변수로 스크롤패널 생성
        jta.setEditable(false);                                         // 텍스트박스는 수정불가
        Font rFont = new Font("Serif", Font.PLAIN, 15); // 기록용 폰트(폰트, 기본굵기, 15사이즈)
        jta.setFont(rFont);                                         // 텍스트박스 폰트 삽입
        sp.setSize(436, 500);                           // 텍스트박스를 포함한 스크롤바 크기 지정
        sp.setLocation(25, 50);                                 // 위치 지정
        jta.setText(dbRanking.rankList());                          // 데이터베이스연결객체에서 만든 텍스트 삽입
        add(sp);                                         // 스크롤을 포함한 텍스트박스 레이블에 넣기
        btn = new JButton("처음 화면으로");
        btn.setBounds(150, 580,200, 50);           // 버튼 크기, 위치 정하기
        Font btnFont = new Font("Serif", Font.PLAIN, 20); // 버튼용 폰트(폰트, 기본굵기, 20사이즈)
        btn.setFont(btnFont);                                        // 버튼에 폰트 넣기
        btn.addActionListener(this);                              // 이벤트 메소드 호출
        add(btn);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            fg.changePanel("pMain");                // change메소드 호출(매개변수 String pGame)
        } catch (InterruptedException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
