package panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import constants.AppConstants;
import panel.ProfessorPanel.Button_pro;

public class StudentPanel extends JPanel {
	JLabel lblTitle,la1,la2,aaa;
	JPanel pnlMenu, pnlContent;
	JButton[] btnFunction;
	JTable table,table1;
	JScrollPane scroll, sss;
	String contents[], header[]; 
	DefaultTableModel dtm1, dtm2, dtm3, dtm4,dtm5,dtm6;
	JTextField professor_1, year_1, semester_1;
	String id,year,semester;
	JTextArea test1;
	
	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	
	public void conDB() {
	      try {
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         //System.out.println("����̹� �ε� ����");
	         System.out.println("����̹� �ε� ����...\n");
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	      }
	      
	      try { /* �����ͺ��̽��� �����ϴ� ���� */
	          //System.out.println("�����ͺ��̽� ���� �غ�...");
	    	  System.out.println("�����ͺ��̽� ���� �غ�...\n");
	          con = DriverManager.getConnection(url, userid, pwd);
	          //System.out.println("�����ͺ��̽� ���� ����");
	    	  System.out.println("�����ͺ��̽� ���� ����...\n");
	       } catch (SQLException e1) {
	          e1.printStackTrace();
	       }  
	   }
	
	public StudentPanel() {
		conDB();
		setPreferredSize(new Dimension(600,800));
		setBackground(Color.gray);
		
		pnlMenu = new JPanel();
		
		lblTitle = new JLabel("�л�");
		add(lblTitle);
		professor_1 = new JTextField(8);
		add(professor_1);
		
		la1 = new JLabel("�⵵");
		add(la1);
		year_1 = new JTextField(4);
		add(year_1);
		
		la2 = new JLabel("�б�");
		add(la2);
		semester_1 = new JTextField(1);
		add(semester_1);
		
		btnFunction = new JButton[4];
		Button_pro hang = new Button_pro();
		for (int i=0;i<4;i++) {
			btnFunction[i] = new JButton(AppConstants.studentButton[i]);
			btnFunction[i].addActionListener(hang);
			add(btnFunction[i]);
		}
		
		String title1[] = {"id", "�̸�", "�й�", "����", "����", "�ð�", "����", "�����а�id", "����", "����"};
		String title3[] = {"id", "���Ƹ�", "�ο�", "���Ƹ�ȸ��", "��米��", "����"};
		String title2[] = {"����","��", "ȭ", "��", "��", "��"};
		String title4[] = {"����id", "����", "����", "����", "�⵵", "������"};
		String title5[] = {"�й�", "�̸�", "�ּ�", "��ȭ��ȣ", "email", "������", "���¹�ȣ", "��������"};
		String title6[] = {"�������(100������)" ,"G  P  A"};
		
		Object data[][] = {
		};
		
		dtm1 = new DefaultTableModel(data, title1);
		dtm2 = new DefaultTableModel(data, title2);
		dtm3 = new DefaultTableModel(data, title3);
		dtm4 = new DefaultTableModel(data, title4);
		dtm5 = new DefaultTableModel(data, title5);
		dtm6 = new DefaultTableModel(data, title6);
		
		
		table = new JTable();
		scroll = new JScrollPane(table);
		add(scroll);
		
		aaa = new JLabel("�� ���Ƹ�ȸ���϶� ���Ƹ��� ���� �л����� or ����ǥ�������� ���������(GPA) ��");
		add(aaa);
		
		table1 = new JTable();
		sss = new JScrollPane(table1);
		add(sss);
		
		test1 = new JTextArea(10,50);
		add(test1);
		
		table.setPreferredScrollableViewportSize(new Dimension(700,200));
		table1.setPreferredScrollableViewportSize(new Dimension(700,200));
		
		test1.append("�⵵�� �б�� ������Ϲ�ư���� �ʿ��մϴ�. �ٸ���ư���� �����̾����ϴ�.");
	}
	
	
	public class Button_pro implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			try {
		         stmt = con.createStatement();
		         Object obj = e.getSource();
		         test1.setText("");
		         dtm5.setRowCount(0);
		         dtm6.setRowCount(0);
		         
		        if(obj == btnFunction[0]) {
		        	dtm1.setRowCount(0);
					table.setModel(dtm1);
					id = professor_1.getText();
					year = year_1.getText();
					semester = semester_1.getText();
					String query = "select * from course where id IN(select course_id from course_history where st_id="
					+ id + " and year='" + year + "' and semester= " + semester + ")";
					rs = stmt.executeQuery(query);
					while (rs.next()) {
		                int str1 = rs.getInt(1); String str2 = rs.getString(2); int str3 = rs.getInt(3); int str4 = rs.getInt(4);
		                String str5 = rs.getString(5); int str6 = rs.getInt(6); int str7 = rs.getInt(7); int str8 = rs.getInt(8);
		                String str9 = rs.getString(9); int str10 = rs.getInt(10);
		                Object[] d = {str1, str2, str3, str4, str5 ,str6 ,str7 ,str8, str9, str10};
		                dtm1.addRow(d);
		             }
				}
				else if(obj == btnFunction[1]) {
					dtm2.setRowCount(0);
					table.setModel(dtm2);
//					table.setValueAt(1, 1, 1);
//					setValueAt(Object aValue, int row, int cols);
					String m1 = "��_��";
					String m2 = "ȭ_��";
					String m3 = "��";
					
					id = professor_1.getText();
					Object[] d = {"", "", "", "", "", ""};
					dtm2.addRow(d);dtm2.addRow(d);dtm2.addRow(d);dtm2.addRow(d);dtm2.addRow(d);dtm2.addRow(d);dtm2.addRow(d);
					table.setValueAt(1, 0, 0);table.setValueAt(2, 1, 0);table.setValueAt(3, 2, 0);table.setValueAt(4, 3, 0);
					table.setValueAt(5, 4, 0);table.setValueAt(6, 5, 0);table.setValueAt(7, 6, 0);
					
					String query = "select day_of_week, period, name from course where id IN(select course_id from course_history"
							+ " where st_id= " + id + " and year='2021' and semester=1)";
					rs = stmt.executeQuery(query);
					
					while (rs.next()) {
		                String str1 = rs.getString(1); int str2 = rs.getInt(2);String str3 = rs.getString(3);
		                if(str1.equals(m1)) {
		                	table.setValueAt(str3, str2-1, 1);table.setValueAt(str3, str2-1, 3);
		                }
		                else if(str1.equals(m2)) {
		                	table.setValueAt(str3, str2-1, 2);table.setValueAt(str3, str2-1, 4);
		                }
		                else if(str1.equals(m3))table.setValueAt(str3, str2-1, 5);
		             }
				}
				else if(obj == btnFunction[2]) {
					int i=0;
					dtm3.setRowCount(0);
					table.setModel(dtm3);
					table1.setModel(dtm5);
					id = professor_1.getText();
					String query = "select * from club where id IN(select club_id from student_club where st_id=" + id + ")";
					rs = stmt.executeQuery(query);
					while (rs.next()) {
		                int str1 = rs.getInt(1); String str2 = rs.getString(2); int str3 = rs.getInt(3); int str4 = rs.getInt(4);
		                int str5 = rs.getInt(5); String str6 = rs.getString(6);
		                if(Integer.parseInt(id) == str4)i=str1;
		                Object[] d = {str1, str2, str3, str4, str5, str6};
		                dtm3.addRow(d);
		             }
					if(i != 0) {
						String query2 = "select * from student where id IN(select st_id from student_club where club_id=" + i + ")";
						rs = stmt.executeQuery(query2);
						while (rs.next()) {
			                int str1 = rs.getInt(1); String str2 = rs.getString(2); String str3 = rs.getString(3); String str4 = rs.getString(4);
			                String str5 = rs.getString(5); int str6 = rs.getInt(6); int str7 = rs.getInt(7); int str8 = rs.getInt(8);
			                Object[] d = {str1, str2, str3, str4, str5 ,str6 ,str7 ,str8};
			                dtm5.addRow(d);
			             }
					}
				}
				else if(obj == btnFunction[3]) {
					dtm4.setRowCount(0);
					table.setModel(dtm4);
					table1.setModel(dtm6);
					id = professor_1.getText();
					int hop=0;
					int i=0;
					
					String query = "select c.id, c.name, c.credit, ch.grade, ch.year, ch.total_score from course_history ch, "
							+ "course c where ch.st_id=" + id + " and ch.course_id=c.id and ch.prof_id=c.prof_id;";
					rs = stmt.executeQuery(query);
					while (rs.next()) {
					int str1 = rs.getInt(1); String str2 = rs.getString(2); int str3 = rs.getInt(3); String str4 = rs.getString(4);
					String str5 = rs.getString(5); int str6 = rs.getInt(6);
					hop += str6;
					i++;
					Object[] d = {str1, str2, str3, str4, str5 ,str6};
					dtm4.addRow(d);
					}
					String gr = "";
					if(hop/i >= 90)gr = "A";
					else if(hop/i >= 80)gr = "B";
					else gr = "c";
					
					Object[] ad = {hop/i, gr};
					dtm6.addRow(ad);
				}
		         
		      } catch (Exception e2) {
		         System.out.println("���� �б� ���� :" + e2);
		         String ha = e2.getMessage();
					test1.append(ha);
		      }
	
		}
	}
}
