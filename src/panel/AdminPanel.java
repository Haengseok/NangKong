package panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import constants.AppConstants;
import database.DBConnector;
import frame.DeleteFrame;
import frame.InsertFrame;
import frame.UpdateFrame;

public class AdminPanel extends JPanel {
	JPanel pnlButton, pnlRadioButton;
	JLabel lblTitle;
	JButton[] btnFunction;
	JRadioButton[] rbtnTable;
	ButtonGroup group;
	JTextArea taTable;
	String cols[] = {"加己1", "加己2", "加己3", "加己4", "加己5", "加己6", "加己7", "加己8", "加己9", "加己10", "加己11"};
	DefaultTableModel model = new DefaultTableModel(cols, 0);
	JTable table;
	JScrollPane scroll;
	
	
	public AdminPanel() {
		setPreferredSize(new Dimension(900,700)); // 600, 800
		setBackground(Color.white);
		setLayout(null);
		
		pnlButton = new JPanel();
		pnlButton.setBounds(150, 25, 600, 50);
		pnlButton.setBackground(Color.white);
		
		lblTitle = new JLabel("包府磊");
		pnlButton.add(lblTitle);
		
		btnFunction = new JButton[5];
		AdminButtonListener adminButtonListener = new AdminButtonListener();
		for (int i=0;i<5;i++) {
			btnFunction[i] = new JButton(AppConstants.adminButton[i]);
			btnFunction[i].addActionListener(adminButtonListener);
			pnlButton.add(btnFunction[i]);
		}
		add(pnlButton);
		
		pnlRadioButton = new JPanel();
		pnlRadioButton.setBounds(230, 75, 440, 80);
		pnlRadioButton.setLayout(new GridLayout(2,5));
		pnlRadioButton.setBackground(Color.white);
		
		rbtnTable= new JRadioButton[10];
		group = new ButtonGroup();
		
		for(int i=0;i<10;i++) {
			rbtnTable[i] = new JRadioButton(AppConstants.table[i]);
			group.add(rbtnTable[i]);
			rbtnTable[i].setBackground(Color.white);
			pnlRadioButton.add(rbtnTable[i]);
		}
		add(pnlRadioButton);
		
		table = new JTable(model);
		TableColumnModel columnModel = table.getColumnModel();
		for (int i=0; i<cols.length; i++) {
			columnModel.getColumn(i).setPreferredWidth(10);
		}
		
//		taTable = new JTextArea(50,50);
//		taTable.setEditable(false);
		scroll = new JScrollPane(table);
		scroll.setBounds(150, 155, 600, 400);
		
		add(scroll);
	}
	
	private class AdminButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			
			if (obj == btnFunction[AppConstants.admin_init]) {
				DBConnector.getInstance().initDB();
			} else if (obj == btnFunction[AppConstants.admin_all]) {
				DBConnector.getInstance().showAll(table);
				scroll.revalidate();
			} else {
				for (int i=0; i<10; i++) {
					if (rbtnTable[i].isSelected()) {
						int tableNum = i;
						for (int j=2; j<5; j++) {
							if (obj == btnFunction[j]) {
								switch(j) {
								case AppConstants.admin_insert:
									new InsertFrame(tableNum);
									break;
								case AppConstants.admin_update:
									new UpdateFrame(tableNum);
									break;
								case AppConstants.admin_delete:
									new DeleteFrame(tableNum);
									break;
								}
							}
						}
					}
				}
			}
			
		}
		
	}
}
