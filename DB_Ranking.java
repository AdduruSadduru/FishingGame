package FishingGame;

import java.sql.*;
import java.util.ArrayList;

public class DB_Ranking {
    private final String ID = "root";                           // 데이터베이스 접속할 아이디(변동불가)
    private final String PASS = "1234";                         // 데이터베이스 접속할 비밀번호(변동불가)
    private final String URL = "jdbc:mysql://localhost:3306/fgr";   // 데이터베이스 접속할 ur(변동불가)
    private Connection con;                                         // 커넥션 변수
    private PreparedStatement psment;                               // sql 실행위한 변수
    DB_Ranking() {
        try {
            con = DriverManager.getConnection(URL, ID, PASS);       // 생성자 부를 경우 데이터베이스 바로 연결
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertRank(RankSheet rs) { // 데이터베이스에 넣기위한 메소드 (삽입할 데이터는 랭킹시트로 받음)
        // sql 실행할 쿼리문을 문자열 변수에 저장
        String str = "insert into FishingGameRank(clearTime, movecount) values (?, ?)";
        try {
            psment = con.prepareStatement(str);                 // 만든 문자열을 가지고 쿼리문 실행준비
            psment.setInt(1, rs.getClearTime());    // 첫번째 자리에 클리어타임 세팅
            psment.setInt(2, rs.getMoveCount());    // 두번째 자리에 움직임카운트 세팅
            psment.executeUpdate();                              // 세팅한거 데이터베이스에 반영
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String rankList() throws SQLException {          // 데이터베이스에 저장된 내용 가져오는 메소드
        // sql 실행할 쿼리문을 문자열 변수에 저장
        String str = "select * from FishingGameRank";
        psment = con.prepareStatement(str);                 // 만든 문자열을 가지고 쿼리문 실행준비
        ResultSet rs = psment.executeQuery();               // 쿼리문 실행해서 전체데이터 ResultSet 객체에 대입
        // 데이터가 얼마나 있을지 모르므로 랭킹시트를 하나하나 받아서 넣을 ArrayList객체 생성
        ArrayList<RankSheet> alist = new ArrayList<RankSheet>();
        String setStr = "";                                      // 리턴할 문자열 변수 선언
        if (rs.next()) {                                    // rs에 꺼내올 내용이 있다면?
            RankSheet temp = new RankSheet();               // 임시 랭킹시트 객체 생성
            temp.setGameDate(rs.getString("gameDate"));     // 임시 랭킹시트에 날짜데이터 삽입
            temp.setClearTime(rs.getInt("clearTime"));      // 임시 랭킹시트에 클리어시간 삽입
            temp.setMoveCount(rs.getInt("movecount"));      // 임시 랭킹시트에 움직임카운트 삽입
            alist.add(temp);                                     // 완성된 임시 랭킹시트를 미리만든 ArrayList에 더하기
            while (rs.next()) {                                     // rs에 꺼낼 내용이 또 있다면?
                temp = new RankSheet();                                // 새로운 랭킹시트 만들기
                temp.setGameDate(rs.getString("gameDate")); // 임시 랭킹시트에 날짜데이터 삽입
                temp.setClearTime(rs.getInt("clearTime"));  // 임시 랭킹시트에 클리어시간 삽입
                temp.setMoveCount(rs.getInt("movecount"));  // 임시 랭킹시트에 움직임카운트 삽입
                alist.add(temp);                                  // 완성된 임시 랭킹시트를 미리만든 ArrayList에 더하기
            }
            for (int i = 0; i < alist.size(); i++) {            // ArrayList 사이즈만큼 반복
                // 미리 만든 리턴할 문자변수에 리스트에 각각 담은 기록들 모양 맞춰서 더하기
                setStr = setStr + "날짜 : " + alist.get(i).getGameDate()
                        + "   움직인 횟수 : " + alist.get(i).getMoveCount()
                        + "   걸린시간 : " + alist.get(i).getClearTime() + "\r\n";
            }
        } else {                            // 만약 rs에 꺼낼 내용이 하나도 없다면?
            setStr = "저장된 정보가 없습니다\r\n";    // 미리 만든 리턴할 문자 변수에 데이터 없음 문자열 대입
        }
        return setStr;      // 문자열 데이터 리턴
    }
}
