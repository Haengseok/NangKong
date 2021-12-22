package frame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import constants.AppConstants;
import database.DBConnector;

public class UpdateFrame extends JFrame {
	int tableNum;
	JLabel lblSet, lblWhere;
	JButton btnApply, btnCancel;
	JTextArea taSet, taWhere;
	
	public UpdateFrame(int tableNum) {
		setTitle(AppConstants.table[tableNum]+"수정");
		this.tableNum = tableNum;
		setPreferredSize(new Dimension(600,600));
		setLayout(null);
		
		lblSet = new JLabel("set");
		lblSet.setBounds(100, 100, 100, 30);
		add(lblSet);
		
		taSet = new JTextArea();
		JScrollPane scroll1 = new JScrollPane(taSet);
		scroll1.setBounds(100, 140, 400, 150);
		add(scroll1);
		
		lblWhere = new JLabel("where");
		lblWhere.setBounds(100, 300, 100, 30);
		add(lblWhere);
		
		taWhere = new JTextArea();
		JScrollPane scroll2 = new JScrollPane(taWhere);
		scroll2.setBounds(100, 340, 400, 150);
		add(scroll2);
		
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
			if (taSet.getText().equals(""))
				JOptionPane.showMessageDialog(taSet, "set 명령어를 적어주세요.");
			else {
				String set = taSet.getText(), where = taWhere.getText();
				String sql = "update " + AppConstants.tableEng[tableNum] + " set " + set;
				if (!taWhere.getText().equals(""))
					sql = sql + " where " + where;
				DBConnector.getInstance().updateQuery(sql,taSet,tableNum);
				dispose();
			}
		}
		
	}
}
