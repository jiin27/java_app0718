package org.sp.app0718.table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Emp2 에 대한 CRUD를 담당하는 DAO를 정의한다.
public class Emp2DAO {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java";
	String pass="1234";

	//모든 레코드 가져오기
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<Emp2DTO> list = new ArrayList<Emp2DTO>();		
		
		try {
			// 1) 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) 접속
			con=DriverManager.getConnection(url, user, pass);
			if(con==null) {
				System.out.println("접속 실패");
			}else {
				System.out.println("접속 성공");
				String sql="select * from emp2 order by empno asc";
				pstmt=con.prepareStatement(sql); // 쿼리문 준비
				rs=pstmt.executeQuery(); //쿼리 실행
				
				//rs는 곧 close 될 예정이므로, rs를 대체할 매게체에 옮겨담자
				//어제는 배열에 담았지만, 오늘은 보다 객체지향적인 방법을 이용
				//레코드 1건 -> 클래스로부터 생성된 인스턴스 1건
				while(rs.next()) {
					Emp2DTO dto = new Emp2DTO(); //빈 dto 생성
					
					dto.setEmpno(rs.getInt("empno"));
					dto.setEname(rs.getString("ename"));
					dto.setJob(rs.getString("job"));
					dto.setMgr(rs.getInt("mgr"));
					dto.setHiredate(rs.getString("hiredate"));
					dto.setSal(rs.getInt("sal"));
					dto.setComm(rs.getInt("comm"));
					dto.setDeptno(rs.getInt("deptno"));
					list.add(dto);
				}
				System.out.println("사원 수는 "+list.size());
			}
			
			// 3)
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(null!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	//레코드 1건 수정하기
	public void update(Emp2DTO dto) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url, user, pass);
			if(con==null) {
				System.out.println("접속 실패");
			}else {
				//한 줄 업데이트 하기
				String sql="update emp2 set ename='"+dto.getEname()+"', job='"+dto.getJob()+"'";
				sql+=", mgr="+dto.getMgr()+", hiredate='"+dto.getHiredate()+"', sal="+dto.getSal()+", comm="+dto.getComm()+", deptno="+dto.getDeptno()+"";
				sql+=" where empno="+dto.getEmpno()+"";
				
				System.out.println(sql); //검증
				pstmt=con.prepareStatement(sql); //쿼리문 준비
				int result = pstmt.executeUpdate(); //쿼리 실행
				if(result>0) {
					System.out.println("성공");
				}else {
					System.out.println("실패"); 
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
