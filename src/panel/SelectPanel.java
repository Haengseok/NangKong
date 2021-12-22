package panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import constants.AppConstants;

public class SelectPanel extends JPanel {
	JPanel pnlSelect, pnlInput;
	JLabel lblTitle;
	ButtonGroup group;
	JRadioButton rbtnMode[];
	JButton btnEnter;
	
	public SelectPanel() {
		setPreferredSize(new Dimension(900,700)); // 600, 800
		setBackground(Color.white);
		setLayout(null);
		
		lblTitle = new JLabel("학사관리시스템");
		lblTitle.setBounds(250, 200, 400, 100);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("plane", Font.BOLD, 50));
		add(lblTitle);
		
		pnlSelect = new JPanel();
		pnlSelect.setPreferredSize(new Dimension(900,150));
		pnlSelect.setBounds(0, 300, 900, 50);
		
		rbtnMode = new JRadioButton[3];
		group = new ButtonGroup();
		for(int i=0;i<3;i++) {
			rbtnMode[i] = new JRadioButton(AppConstants.mode[i]);
			if (i==0)
				rbtnMode[i].setSelected(true);
			group.add(rbtnMode[i]);
			pnlSelect.add(rbtnMode[i]);
		}
		add(pnlSelect);
		
		pnlInput = new JPanel();
		pnlInput.setBounds(0, 350, 900, 50);
		
		btnEnter = new JButton("입장");
		btnEnter.setBounds(700, 450, 100, 30);
		add(btnEnter);
	}
	
	public void addEnterButtonListener(ActionListener listener) {
		btnEnter.addActionListener(listener);
	}
	
	public JButton getBtnEnter() {
		return this.btnEnter;
	}
	
	public JRadioButton[] getRbtnMode() {
		return this.rbtnMode;
	}
}
