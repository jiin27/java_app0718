package org.sp.app0718.table;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class EmpMain extends JFrame implements TableModelListener{
	JTable table;
	JScrollPane scroll;
	Emp2Model model;
	int row; //현재 마우스로 클릭한 행
	int col; //현재 마우스로 클릭한 열
	
	
	public EmpMain() {
		table = new JTable(model=new Emp2Model());
		scroll = new JScrollPane(table);
		
		add(scroll);
		
		setSize(800, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				row=table.getSelectedRow();
				col=table.getSelectedColumn();

				String value=(String)table.getValueAt(row, col);
				//System.out.println(row+","+col+"의 값은 "+value);
			}
		});
		
		table.getModel().addTableModelListener(table);
		table.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//System.out.println("입력 완료");
				//오라클에 반영하기 update 문
				//중재자(tablemodel).emp2DAO.update();
				
				//현재 선택된 행의 모든 열을 가져오기
				String empno=(String)table.getValueAt(row, 0); //empno
				String ename=(String)table.getValueAt(row, 1); //ename
				String job=(String)table.getValueAt(row, 2); //job
				String mgr=(String)table.getValueAt(row, 3); //mgr
				String hiredate=(String)table.getValueAt(row, 4); //hiredate
				hiredate=hiredate.substring(0, 10);
				
				String sal=(String)table.getValueAt(row, 5); //sal
				String comm=(String)table.getValueAt(row, 6); //comm
				String deptno=(String)table.getValueAt(row, 7); //deptno
				
				Emp2DTO dto=new Emp2DTO();
				dto.setEmpno(Integer.parseInt(empno));
				dto.setEname(ename);
				dto.setJob(job);
				dto.setMgr(Integer.parseInt(mgr));
				dto.setHiredate(hiredate);
				dto.setSal(Integer.parseInt(sal));
				dto.setComm(Integer.parseInt(comm));
				dto.setDeptno(Integer.parseInt(deptno));
				
				model.emp2DAO.update(dto);

			}
		});
	}
	
	public static void main(String[] args) {
		new EmpMain();
	}

	public void tableChanged(TableModelEvent e) {
		System.out.println("변경됨");
	}
}
