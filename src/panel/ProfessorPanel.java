package panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import constants.AppConstants;
import java.sql.*;

public class ProfessorPanel extends JPanel {
	JLabel lblTitle,la1,la2;
	JButton[] btnFunction;
	JButton btn,btn2;
	JTable table,table1;
	JScrollPane scroll,scroll2;
	String contents[], header[]; 
	DefaultTableModel dtm1, dtm2, dtm3, dtm4,dtm5,dtm6,dtm7,dtm8;
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
	
	
	public ProfessorPanel() {
		setPreferredSize(new Dimension(900,700));
		setBackground(Color.pink);
		conDB();
		
		lblTitle = new JLabel("������ȣ");
		add(lblTitle);
		professor_1 = new JTextField(4);
		add(professor_1);
		
		la1 = new JLabel("�⵵");
		add(la1);
		year_1 = new JTextField(4);
		add(year_1);
		
		la2 = new JLabel("�б�");
		add(la2);
		semester_1 = new JTextField(1);
		add(semester_1);
		
		Button_pro hang = new Button_pro();
		
		btn = new JButton("Ŭ��");
		btn.addActionListener(hang);
		add(btn);
		
		btnFunction = new JButton[4];
		for (int i=0;i<4;i++) {
			btnFunction[i] = new JButton(AppConstants.professorButton[i]);
			btnFunction[i].addActionListener(hang);
			add(btnFunction[i]);
		}
		
		
		btn2 = new JButton("�����Է�");
		btn2.addActionListener(hang);
		add(btn2);
		
		String title1[] = {"id", "�̸�", "�й�", "����", "����", "�ð�", "����", "�����а�id", "����", "����"};
		String title2[] = {"�й�", "�̸�", "�ּ�", "��ȭ��ȣ", "email", "������", "���¹�ȣ", "��������"};
		String title3[] = {"�а�id", "�а�", "�а��ּ�", "�а���ȣ", "�а���"};
		String title4[] = {"����","��", "ȭ", "��", "��", "��"};
		String title5[] = {"�й�", "�̸�", "�ּ�", "��ȭ��ȣ", "email", "������", "���¹�ȣ", "��������"};
		String title6[] = {"�й�", "�����ȣ", "����", "����", "�⵵", "�б�"};
		String title7[] = {"�й�", "����id", "����id", "�⼮", "�߰�", "�⸻", "��Ÿ","���", "����"};
		
		Object data[][] = {
		};
		
		dtm1 = new DefaultTableModel(data, title1);
		dtm2 = new DefaultTableModel(data, title2);
		dtm3 = new DefaultTableModel(data, title3);
		dtm4 = new DefaultTableModel(data, title4);
		dtm5 = new DefaultTableModel(data, title5);
		dtm6 = new DefaultTableModel(data, title6);
		dtm7 = new DefaultTableModel(data, title7);
		dtm8 = new DefaultTableModel(data, title7);
		
		table = new JTable();
		scroll = new JScrollPane(table);
		add(scroll);
		
		table1 = new JTable();
		JScrollPane sss = new JScrollPane(table1);
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
		         dtm8.setRowCount(0);
		         
		        if(obj == btnFunction[0]) {
		        	dtm1.setRowCount(0);
					table.setModel(dtm1);
					id = professor_1.getText();
					year = year_1.getText();
					semester = semester_1.getText();
					String query = "select * from course where id IN(select distinct course_id from course_history where year='" + year +
							"' and semester=" + semester + " and prof_id=" + id + ") ";
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
					id = professor_1.getText();
					String query = "select * from student where id IN(select st_id from guide where prof_id=" + id + ")";
					rs = stmt.executeQuery(query);
					while (rs.next()) {
		                int str1 = rs.getInt(1); String str2 = rs.getString(2); String str3 = rs.getString(3); String str4 = rs.getString(4);
		                String str5 = rs.getString(5); int str6 = rs.getInt(6); int str7 = rs.getInt(7); int str8 = rs.getInt(8);
		                Object[] d = {str1, str2, str3, str4, str5 ,str6 ,str7 ,str8};
		                dtm2.addRow(d);
		             }
				}
				else if(obj == btnFunction[2]) {
					dtm3.setRowCount(0);
					table.setModel(dtm3);
					id = professor_1.getText();
					String query = "select * from department where id IN(select dept_id from dept_professor where prof_id=" + id + ")";
					rs = stmt.executeQuery(query);
					while (rs.next()) {
		                int str1 = rs.getInt(1); String str2 = rs.getString(2); String str3 = rs.getString(3); String str4 = rs.getString(4);
		                int str5 = rs.getInt(5);
		                Object[] d = {str1, str2, str3, str4, str5};
		                dtm3.addRow(d);
		             }
				}
				else if(obj == btnFunction[3]) {
					dtm4.setRowCount(0);
					table.setModel(dtm4);
//					table.setValueAt(1, 1, 1);
//					setValueAt(Object aValue, int row, int cols);
					String m1 = "��_��";
					String m2 = "ȭ_��";
					String m3 = "��";
					
					id = professor_1.getText();
					Object[] d = {"", "", "", "", "", ""};
					dtm4.addRow(d);dtm4.addRow(d);dtm4.addRow(d);dtm4.addRow(d);dtm4.addRow(d);dtm4.addRow(d);dtm4.addRow(d);
					table.setValueAt(1, 0, 0);table.setValueAt(2, 1, 0);table.setValueAt(3, 2, 0);table.setValueAt(4, 3, 0);
					table.setValueAt(5, 4, 0);table.setValueAt(6, 5, 0);table.setValueAt(7, 6, 0);
					
					String query = "select day_of_week, period, name from course where id IN(select course_id from course_history"
							+ " where prof_id=" + id + " and year='2021' and semester=1)";
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
		        
				else if(obj==btn2) {
					dtm7.setRowCount(0);
					table.setModel(dtm7);
					id = professor_1.getText();
					String query = "select distinct st_id, prof_id, course_id, attend_score, mid_score, final_score, "
							+ "etc_score, total_score, grade "
							+ "from course_history where year='2021' and semester=1 and prof_id=" + id;
					rs = stmt.executeQuery(query);
					while (rs.next()) {
		                int str1 = rs.getInt(1); int str2 = rs.getInt(2); int str3 = rs.getInt(3); int str4 = rs.getInt(4);
		                int str5 = rs.getInt(5); int str6 = rs.getInt(6); int str7 = rs.getInt(7); int str8 = rs.getInt(8);
		                String str9 = rs.getString(9);
		                Object[] d = {str1, str2, str3, str4, str5 ,str6, str7, str8, str9};
		                dtm7.addRow(d);
		             }
					
					String ha1 = "�Է��Ϸ��� �⼮,�߰�,�⸻,��Ÿ,���,������ ���̺��� �Է��ѵ� ���� Ŭ���ѵ� 'Ŭ��' ��ư�� ��������.\n Ŭ����ư�� ������ �ݿ��˴ϴ�."
							+ "\n �й�, ����id, ����id �� �ٲٸ� �ȵ˴ϴ�. �ٲٴ� ���� �ƴմϴ�.";
					test1.append(ha1);
				}
		        
				else if(obj==btn) {
					test1.append("");
					int rowIndex = table.getSelectedRow();
					if(rowIndex == -1) {
						String haa = "�߸���Ŭ��";
						test1.append(haa);
					}
					else {
						int row_count = table.getColumnCount();
						
						
						if(row_count==10) {
							Object course_id = table.getValueAt(rowIndex, 1);
							String query = "select * from student where id IN(select  distinct st_id from "
									+ "course_history where course_id IN(select id from course where '" + course_id + "' = name))";
							rs = stmt.executeQuery(query);
							
							dtm5.setRowCount(0);
							table1.setModel(dtm5);
							
							while (rs.next()) {
				                int str1 = rs.getInt(1); String str2 = rs.getString(2); String str3 = rs.getString(3); String str4 = rs.getString(4);
				                String str5 = rs.getString(5); int str6 = rs.getInt(6); int str7 = rs.getInt(7); int str8 = rs.getInt(8);
				                Object[] d = {str1, str2, str3, str4, str5 ,str6 ,str7 ,str8};
				                dtm5.addRow(d);
				             }
						}
						else if(row_count==8) {
							Object st_id1 = table.getValueAt(rowIndex, 0);
							String query = "select ch.st_id, c.id, c.name, ch.grade, ch.year, ch.semester  "
									+ "from course_history ch, course c where ch.st_id=" + st_id1 + " and ch.course_id = c.id";
							rs = stmt.executeQuery(query);
							
							dtm6.setRowCount(0);
							table1.setModel(dtm6);
							
							while (rs.next()) {
				                int str1 = rs.getInt(1); int str2 = rs.getInt(2); String str3 = rs.getString(3); String str4 = rs.getString(4);
				                String str5 = rs.getString(5); int str6 = rs.getInt(6);
				                Object[] d = {str1, str2, str3, str4, str5 ,str6};
				                dtm6.addRow(d);
							}
						}
						else if(row_count == 9) {
							Object b1 = table.getValueAt(rowIndex, 3);Object b2 = table.getValueAt(rowIndex, 4);
							Object b3 = table.getValueAt(rowIndex, 5);Object b4 = table.getValueAt(rowIndex, 6);
							Object b5 = table.getValueAt(rowIndex, 7);Object b6 = table.getValueAt(rowIndex, 8);
							Object a1 = table.getValueAt(rowIndex, 0);Object a2 = table.getValueAt(rowIndex, 1);
							Object a3 = table.getValueAt(rowIndex, 2);
				
							try {
								
								String query = "update course_history SET"
										+ " attend_score=" + b1 + ", mid_score=" + b2 + ", final_score=" + b3 + ", "
												+ "etc_score=" + b4 + ", total_score=" + b5 + ", grade='" + b6 + "' "
										+ "where st_id=" + a1 + " and prof_id=" + a2 + " and course_id=" + a3 + " and year='2021' and semester=1";
								stmt.executeUpdate(query);
								
								test1.append("������ �߰� ����!\n"+a1+"\n"+a2+"\n"+a3 + "\n�й�, ����id, ����id�� �ٲٴ� ���� �ƴմϴ�.");
								
								String query1 = "select distinct st_id, prof_id, course_id, attend_score, mid_score, final_score, "
										+ "etc_score, total_score, grade "
										+ "from course_history where year='2021' and semester=1 and prof_id=" + id;
								rs = stmt.executeQuery(query1);
								
								dtm8.setRowCount(0);
								table1.setModel(dtm8);
								
								while (rs.next()) {
					                int str1 = rs.getInt(1); int str2 = rs.getInt(2); int str3 = rs.getInt(3); int str4 = rs.getInt(4);
					                int str5 = rs.getInt(5); int str6 = rs.getInt(6); int str7 = rs.getInt(7); int str8 = rs.getInt(8);
					                String str9 = rs.getString(9);
					                Object[] d = {str1, str2, str3, str4, str5 ,str6, str7, str8, str9};
					                dtm8.addRow(d);
					             }
							}
							catch(SQLException se){
								   
								
								test1.append("������ �Է� ����!!\n");
									
								String ha = se.getMessage();
								test1.append(ha);
							}
						}
						else {
							String ha2 = "Ŭ���� �� ���� ���̺�";
							test1.append(ha2);
						}
					}
				}
		         
		      } catch (Exception e2) {
		         System.out.println("���� �б� ���� :" + e2);
		         String ha = e2.getMessage();
					test1.append(ha);
		      }
	
		}
	}
}
