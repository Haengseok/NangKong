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
		setTitle("�л�����ý���");
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
			System.out.println("��ư����");
			mainFrame.remove(pnlSelect);
			System.out.println("select panel �����");
			for (int i=0; i<3; i++) {
				if (pnlSelect.getRbtnMode()[i].isSelected()) {
					switch(i) {
					case AppConstants.ADMIN_MODE:
						mainFrame.add(pnlAdmin);
						System.out.println("������ ȭ��");
						break;
					case AppConstants.PROFESSOR_MODE:
						mainFrame.add(pnlProfessor);
						System.out.println("���� ȭ��");
						break;
					case AppConstants.STUDENT_MODE:
						mainFrame.add(pnlStudent);
						System.out.println("�л� ȭ��");
						break;
					}
					mainFrame.revalidate();
				}
			}
		}
		
	}
}
