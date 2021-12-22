package frame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import constants.AppConstants;
import database.DBConnector;

public class DeleteFrame extends JFrame {
	int tableNum;
	JLabel lblWhere;
	JButton btnApply, btnCancel;
	JTextArea taWhere;
	
	public DeleteFrame(int tableNum) {
		setTitle(AppConstants.table[tableNum]+"삭제");
		this.tableNum = tableNum;
		setPreferredSize(new Dimension(600,600));
		setLayout(null);
		
		lblWhere = new JLabel("where");
		lblWhere.setBounds(100, 100, 100, 30);
		add(lblWhere);
		
		taWhere = new JTextArea();
		JScrollPane scroll = new JScrollPane(taWhere);
		scroll.setBounds(100, 140, 400, 150);
		add(scroll);
		
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
	
	public class CancelButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
		
	}
	
	public class ApplyButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String where = taWhere.getText();
			String sql = "delete from " + AppConstants.tableEng[tableNum];
			if (!where.equals(""))
				sql = sql + " where " + where;
			DBConnector.getInstance().deleteQuery(sql,taWhere, tableNum);
			dispose();
		}
		
	}
}
