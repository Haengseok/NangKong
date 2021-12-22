package frame;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import constants.AppConstants;
import database.DBConnector;

public class InsertFrame extends JFrame {
	int tableNum;
	JLabel lblInput[];
	JButton btnApply, btnCancel;
	JTextField txtInput[];
	
	public InsertFrame(int tableNum) {
		setTitle(AppConstants.table[tableNum]+"등록");
		this.tableNum = tableNum;
		setPreferredSize(new Dimension(600,600));
		setLayout(null);
		
		lblInput = new JLabel[15];
		for (int i=0;i<15;i++) {
			lblInput[i] = new JLabel();
			lblInput[i].setBounds(100, 50+i*30, 100, 20);
			add(lblInput[i]);
		}
		
		txtInput = new JTextField[15];
		for (int i=0;i<15;i++) {
			txtInput[i] = new JTextField();
			txtInput[i].setBounds(250, 50+i*30, 100, 20);
			add(txtInput[i]);
		}
		
		showInputButton(tableNum);
		
		btnApply = new JButton("적용");
		btnApply.setBounds(400, 500, 80, 30);
		btnApply.addActionListener(new ApplyButtonListener());
		add(btnApply);
		
		btnCancel = new JButton("취소");
		btnCancel.setBounds(490, 500, 80, 30);
		btnCancel.addActionListener(new CancelButtonListener());
		add(btnCancel);
		
		pack();
		setVisible(true);
	}
	
	public void showInputButton(int tableNum) {
		switch(tableNum) {
		case AppConstants.STUDENT_TABLE:
			setLabelsName("이름","주소","연락처","메일","학과번호","계좌번호");
			break;
		case AppConstants.PROFESSOR_TABLE:
			setLabelsName("이름","주소","연락처","메일","학과번호","학생번호","학생학년","학기","강좌이름","분반","요일","강의시간","학점","교실","교시");
			break;
		case AppConstants.GUIDE_TABLE:
			setLabelsName("학생번호","교수번호","지도학년","지도학기");
			break;
		case AppConstants.DEPARTMENT_TABLE:
			setLabelsName("이름","주소","연락처","학과장번호","강좌이름","분반","요일","강의시간","학점","교실","교시");
			break;
		case AppConstants.COURSE_TABLE:
			setLabelsName("이름","분반","교수번호","요일","강의시간","학점","학과번호","교실","교시");
			break;
		case AppConstants.COURSE_HISTORY_TABLE:
			setLabelsName("학생번호","교수번호","강좌번호","개설년도","개설학기");
			break;
		case AppConstants.CLUB_TABLE:
			setLabelsName("이름","회장번호","교수번호","위치");
			break;
		case AppConstants.STUDENT_CLUB_TABLE:
			setLabelsName("학생번호","동아리번호");
			break;
		case AppConstants.DEPT_PROFESSOR_TABLE:
			setLabelsName("학과번호", "교수번호");
			break;
		case AppConstants.PAYMENT_TABLE:
			setLabelsName("학생번호", "납부년도", "납부학기", "등록금총액", "납부총액", "마지막 납부날짜");
			break;
		}
	}
	
	public void setLabelsName(String ...labels) {
		
		for (int i=0;i<labels.length;i++) {
			System.out.println(labels[i]);
			lblInput[i].setText(labels[i]);
			lblInput[i].setVisible(true);
			txtInput[i].setVisible(true);
		}
		for (int i=labels.length;i<15;i++) {
			lblInput[i].setVisible(false);
			txtInput[i].setVisible(false);
		}
	}
	
	public class CancelButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
		
	}
	
	public class ApplyButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> values = new ArrayList<>();
			boolean flag = getAllTextFromTextField(values);
			if (flag)
				JOptionPane.showMessageDialog(txtInput[0], "빈 칸을 채워주세요.");
			else {
				switch(tableNum) {
				case AppConstants.STUDENT_TABLE:
					DBConnector.getInstance().insertStudent(values.toArray(new String[values.size()]));
					break;
				case AppConstants.PROFESSOR_TABLE:
					DBConnector.getInstance().insertProfessor(values.toArray(new String[values.size()]));
					break;
				case AppConstants.GUIDE_TABLE:
					DBConnector.getInstance().insertGuide(values.toArray(new String[values.size()]));
				case AppConstants.DEPARTMENT_TABLE:
					DBConnector.getInstance().insertDepartment(values.toArray(new String[values.size()]));
					break;
				case AppConstants.COURSE_TABLE:
					DBConnector.getInstance().insertCourse(values.toArray(new String[values.size()]));
					break;
				case AppConstants.COURSE_HISTORY_TABLE:
					DBConnector.getInstance().insertCourseHistory(values.toArray(new String[values.size()]));
					break;
				case AppConstants.CLUB_TABLE:
					DBConnector.getInstance().insertClub(values.toArray(new String[values.size()]));
					break;
				case AppConstants.STUDENT_CLUB_TABLE:
					DBConnector.getInstance().insertStudentClub(values.toArray(new String[values.size()]));
					break;
				case AppConstants.DEPT_PROFESSOR_TABLE:
					DBConnector.getInstance().insertDepartmentProfessor(values.toArray(new String[values.size()]));
					break;
				case AppConstants.PAYMENT_TABLE:
					DBConnector.getInstance().insertPayment(values.toArray(new String[values.size()]));
					break;
				}
				dispose();
			}
		}
		
	}
	
	public boolean getAllTextFromTextField(List<String> values) {
		boolean flag = false;
		for (int i=0;i<15;i++) {
			if (!txtInput[i].isVisible())
				break;
			if (txtInput[i].getText().equals("")) {
				flag = true;
				break;
			}
			values.add(txtInput[i].getText());
		}
		return flag;
	}
	
}