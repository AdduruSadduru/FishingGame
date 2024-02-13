package FishingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
public class Panel_Game extends JPanel implements ActionListener {
    private FishingGame fg;                                             // 시작 프레임 변수 선언
    private JLabel locatLabel, fishCount, moveCount, moveLabel ;        // 레이블 변수 선언(좌표, 남은 물고기, 미끼 움직임)
    private JButton rowBtn, colBtn;                                     // 버튼변수 선언(좌우 움직일 버튼)
    private JLabel imgLabel;                                            // 이미지 넣을 레이블 변수 선언
    private JLabel timeLabel;                                           // 초시계 레이블(걸린시간 체크용)
    private int time = 0;                                               // 걸린 시간
    private Thread timeStamp;                                           // 시간을 보낼 스레드 객체
    private boolean threadBreak = true;                                 // 게임 종료후 스레드 멈출 불리언 객체
    private String[] images = {                                         // 이미지 경로 미리 저장
            "/FishingGame/images/mainImg.jpg",                          // 기본 사진
            "/FishingGame/images/rowImg.jpg",                           // row 움직임 사진
            "/FishingGame/images/colImg.jpg"                            // col 움직임 사진
    };
    private Lake lake;                                                  // 호수 변수 선언
    private User user;                                                  // 유저 변수 선언
    Panel_Game(FishingGame fg) {
        lake = new Lake();                                              // 호수 생성
        user = new User();                                              // 유저 생성
        user.setFishBasket(new Fish[lake.getFishCount()]);              // 유저가 잡은 물고기를 담을 바구니 갯수 선언(3)
        this.fg = fg;                                                   // 시작 프레임 셋팅
        super.setLayout(null);                                          // 레이아웃 설정 (위치는 내맘대로 저장)
        Font font = new Font("Serif", Font.PLAIN, 20);      // 폰트 만들기(폰트, 기본굵기, 20사이즈) (기본폰트)
        // 움직인 횟수 레이블
        moveCount = new JLabel();                                       // 움직인 횟수 카운터 레이블 새로 생성
        moveCount.setFont(font);                                        // 레이블에 기본 폰트 넣기
        moveCount.setBounds(10, 10, 200, 25);         // 레이블에 위치 및 크기 정하기
        add(moveCount);                                                // 패널에 레이블 넣기
        // 좌표 레이블
        locatLabel = new JLabel();                                      // 좌표 보여줄 레이블 새로 생성
        locatLabel.setFont(font);                                       // 레이블에 기본 폰트 넣기
        locatLabel.setBounds(140, 40, 220, 25);       // 레이블에 위치 및 크기 정하기
        add(locatLabel);                                                // 패널에 레이블 넣기
        // 남은 물고기 레이블
        fishCount = new JLabel();                                       // 남은 물고기 보여줄 레이블 새로 생성
        fishCount.setFont(font);                                        // 레이블에 기본 폰트 넣기
        fishCount.setBounds(300, 10, 200, 25);       // 레이블에 위치 및 크기 정하기
        add(fishCount);                                                // 패널에 레이블 넣기
        // 지시 레이블
        moveLabel = new JLabel();                                       // 지시용 레이블 새로 생성
        moveLabel.setFont(font);                                        // 레이블에 기본 폰트 넣기
        moveLabel.setBounds(150, 70, 350, 25);       // 레이블에 위치 및 크기 정하기
        add(moveLabel);                                                // 패널에 레이블 넣기
        // 이미지 레이블
        imgLabel = new JLabel();                                       // 이미지 레이블 새로 생성
        // string으로 미리 만들어놓은 url을 이용해서 아이콘으로 만들어서 삽입(485픽셀사진이 들어가야 딱맞음)
        imgLabel.setIcon(new ImageIcon(Panel_Game.class.getResource(images[0])));
        imgLabel.setBounds(0, 100, 485, 485);       // 이미지 레이블의 크기랑 위치 정하기
        add(imgLabel);                                                 // 패널에 레이블 넣기
        // 왼쪽이동 버튼
        rowBtn = new JButton("왼쪽 이동");                          // 왼쪽 이동용 버튼 만들기
        rowBtn.setBounds(90, 600, 140, 50);         // 버튼 위치랑 크기 정하기(이미지 아래쪽)
        rowBtn.setFont(font);                                           // 버튼에 기본 폰트 넣기
        rowBtn.addActionListener(this);                              // 액션 삽입(아래에서 메소드 만들기)
        add(rowBtn);                                                    // 패널에 버튼 넣기
        // 오른쪽이동 버튼
        colBtn = new JButton("오른쪽 이동");                          // 오른쪽 이동용 버튼 만들기
        colBtn.setBounds(270, 600, 140, 50);         // 버튼 위치랑 크기 정하기(이미지 아래쪽)
        colBtn.setFont(font);                                           // 버튼에 기본 폰트 넣기
        colBtn.addActionListener(this);                              // 액션 삽입(아래에서 메소드 만들기)
        add(colBtn);                                                    // 패널에 버튼 넣기
        // 걸린 시간을 표시할 스레드 새로 생성 (타이머 레이블을 스레드에서 조종하기위해 나 자신을 넘겨줌)
        timeStamp = new TimeStamp(this);
        // 타이머 레이블
        timeLabel = new JLabel();                                       // 걸린시간 체크용 레이블 새로 생성
        timeLabel.setFont(font);                                        // 레이블에 기본 폰트 넣기
        timeLabel.setBounds(10, 70, 140, 25);        // 레이블에 위치 및 크기 정하기
        add(timeLabel);                                                // 패널에 레이블 넣기
        gameStart();                                                    // 게임 시작
    }
    void gameStart() {          // 게임 시작 메소드

        user.setRow();          // 미끼 위치는 기본 0/0에서 시작
        user.setCol();
        timeStamp.start();                          // 타이머 시작
        labelSetting();                             // 레이블 세팅용 메소드
        moveLabel.setText("미끼를 움직이세요");        // 지시 레이블 보여주기
    }
    void labelSetting() {       // 자주 써야할 레이블 세팅 메소드
        moveCount.setText("움직인 횟수 : " + user.getMoveCount()); // 움직인 횟수(0회부터 시작, 계속 늘어납니다.)
        locatLabel.setText("현재 좌표 : 행 - " + user.getRow() + ", 열 - " + user.getCol());  // 좌표 라벨 기록
        fishCount.setText("남은 물고기 : " + lake.getFishCount() + "마리");    // 호수에서 물고기카운트 가져와서 저장
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean active = true;                                          // 버튼 버그 방지
        if (e.getSource() == rowBtn && active) {                        // rowBtn 눌렸을때
            active = false;                                             // 일단 버튼 비활성화
            imgLabel.setIcon(new ImageIcon(Panel_Game.class.getResource(images[1])));// 이미지를 변경(여기서는 왼쪽 이동)
            moveLabel.setText(user.rowMove());                          // 유저의 메소드로 row값 변경, 지시레이블 받기
        } else {
            active = false;                                             // 일단 버튼 비활성화
            imgLabel.setIcon(new ImageIcon(Panel_Game.class.getResource(images[2])));// 이미지를 변경(여기서는 오른쪽 이동)
            moveLabel.setText(user.colMove());                          // 유저의 메소드로 col값 변경, 지시레이블 받기
        }
        labelSetting();                                                 // 레이블 세팅
        // 호수가 유저의 row, col값을 받아서 물고기를 넘겨줌
        // 여기서 물고기가 없으면 null을 줌
        // 물고기가 있으면 물고기카운터를 줄이면서 물고기 객체를 넘겨줌
        // 그걸 유저가 받아서 바구니 객체에 담음
        // 여기서 물고기가 없으면 빈 바구니 그대로 유지, 물고기가 있으면 물고기를 담게됨
        user.fishCatch(lake.giveFish(user.getRow(), user.getCol()));
        try {
            active = check();               // 물고기 체크 및 승리 체크, boolean으로 버튼 다시 활성화
        } catch (InterruptedException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public boolean check() throws InterruptedException, SQLException {
        if (lake.getFishCount() == 0) {     // 더이상 물고기가 없다면
            threadBreak = false;            // 스레드 정지를 위해 boolean변수에 false 대입
            fishCount.setText("남은 물고기 : " + lake.getFishCount() + "마리");        // 남은 물고기 확인
            moveLabel.setText("남은 물고기가 없습니다");     // 지시 레이블 보여주기
            RankSheet rs = new RankSheet();     // 랭킹 기록할 시트 객체 생성
            rs.setClearTime(time);              // 시트에 시간 넣기
            rs.setMoveCount(user.getMoveCount());   // 시트에 움직인 횟수 넣기
            DB_Ranking dbRanking = new DB_Ranking();    // 데이터베이스 불러올 객체 생성
            dbRanking.insertRank(rs);                   // 데이터베이스에 만들어놓은 메소드로 랭킹 기록한 시트를 삽입
            // 팝업을 띄워줍니다 (움직인 횟수 + 걸린시간도 같이 보여주기)
            JOptionPane.showMessageDialog(null, "물고기를 모두 잡았습니다!!!\r\n" +
                            "움직인 휫수 : " + user.getMoveCount() + "\t" + "  걸린시간 : 총 " + time + " 초");
            fg.changePanel("pMain");        //메인 화면 돌아가기
        } else if (lake.getLake()[user.getRow()][user.getCol()] != null) {  // 만약 움직인 위치에 물고기가 있다면
            // 위에서 이미 물고기객체를 유저에게 넘겨준 상태이므로 호수에 있어서는 안되는 물고기객체에 null 대입
            lake.getLake()[user.getRow()][user.getCol()] = null;
            moveLabel.setText("물고기를 잡았습니다");            // 레이블 보여주기
            user.setRow();                                  // 처음부터 다시 캐스팅
            user.setCol();
            imgLabel.setIcon(new ImageIcon(Panel_Game.class.getResource(images[0])));   // 이미지를 변경
        } else {            // 4 X 4에 물고기가 없다고 걸러진 이후에 미끼가 4 X 4까지 온 경우

            if (user.getRow()==4 && user.getCol() == 4) {
                moveLabel.setText("물고기를 잡지 못했습니다");   // 레이블 보여주기
                user.setRow();                                  // 처음부터 다시 캐스팅
                user.setCol();
                imgLabel.setIcon(new ImageIcon(Panel_Game.class.getResource(images[0])));   // 이미지를 변경
            }
        }
        labelSetting();     // 레이블 세팅
        return true;        // true 넘겨주면서 버튼 다시 활성화
    }
    public int getTime() {        // 스레드가 시간 계산을 하기 위해 넘겨줘야할 time메소드
        return time;
    }
    public void setTime(int time) {     // 스레드가 계산한 시간을 가져올 time 메소드
        this.time = time;
    }
    public JLabel getTimeLabel() {      // 스레드가 조종할 레이블을 넘겨줄 timlLabel 메소드 (set은 필요 없고, 줘도 안됨)
        return timeLabel;
    }
    public boolean isThreadBreak() {
        // 게임이 끝났을때 스레드가 멈추질 않아서 만든 무한반복 정지시킬 객체를 스레드에 넘겨주기 위한  메소드
        return threadBreak;
    }
}
