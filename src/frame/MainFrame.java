package frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import constants.AppConstants;
import panel.AdminPanel;
import panel.ProfessorPanel;
import panel.SelectPanel;
import panel.StudentPanel;

public class MainFrame extends JFrame{
	SelectPanel pnlSelect;
	MainFrame mainFrame = this;
	AdminPanel pnlAdmin;
	ProfessorPanel pnlProfessor;
	StudentPanel pnlStudent;
	
	public MainFrame() {
		setTitle("학사관리시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pnlSelect = new SelectPanel();
		pnlAdmin = new AdminPanel();
		pnlProfessor = new ProfessorPanel();
		pnlStudent = new StudentPanel();
		pnlSelect.addEnterButtonListener(new EnterButtonListener());
		add(pnlSelect);
		
		pack();
		setVisible(true);
	}
	
	private class EnterButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("버튼누름");
			mainFrame.remove(pnlSelect);
			System.out.println("select panel 사라짐");
			for (int i=0; i<3; i++) {
				if (pnlSelect.getRbtnMode()[i].isSelected()) {
					switch(i) {
					case AppConstants.ADMIN_MODE:
						mainFrame.add(pnlAdmin);
						System.out.println("관리자 화면");
						break;
					case AppConstants.PROFESSOR_MODE:
						mainFrame.add(pnlProfessor);
						System.out.println("교수 화면");
						break;
					case AppConstants.STUDENT_MODE:
						mainFrame.add(pnlStudent);
						System.out.println("학생 화면");
						break;
					}
					mainFrame.revalidate();
				}
			}
		}
		
	}
}
