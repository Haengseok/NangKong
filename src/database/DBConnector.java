package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import constants.AppConstants;

public final class DBConnector {
	private static DBConnector s_instance;
	JFrame frame;
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	String Driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	
	public static DBConnector getInstance() {
		if (s_instance == null)
			s_instance = new DBConnector();
		return s_instance;
	}
	
	public void conDB() {
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(url, userid, pwd);
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(this.frame, "DB ���� ����\n");
			e1.printStackTrace();
		}
	}
	
	public void disconDB() {
		try {
            if (rs != null)
               rs.close();
            if (stmt != null)
               stmt.close();
            if (con != null)
               con.close();
         } catch (Exception e3) {
        	 JOptionPane.showMessageDialog(this.frame, "DB ���� ���� ����\n");
         }
	}
	
	public void initDB() {
		conDB();
		
		String dropProfessor = "DROP TABLE IF EXISTS `madang`.`professor`";
		String dropDepartment = "DROP TABLE IF EXISTS `madang`.`department`";
		String dropStudent = "DROP TABLE IF EXISTS `madang`.`student`";
		String dropCourse = "DROP TABLE IF EXISTS `madang`.`course`";
		String dropCourseHistory = "DROP TABLE IF EXISTS `madang`.`course_history`";
		String dropClub = "DROP TABLE IF EXISTS `madang`.`club`";
		String dropGuide = "DROP TABLE IF EXISTS `madang`.`guide`";
		String dropPayment = "DROP TABLE IF EXISTS `madang`.`payment`";
		String dropStudentClub = "DROP TABLE IF EXISTS `madang`.`student_club`";
		String dropDeptProfessor = "DROP TABLE IF EXISTS `madang`.`dept_professor`";
		
		String createProfessor = "CREATE TABLE IF NOT EXISTS `madang`.`professor` (\r\n"
				+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` VARCHAR(30) NOT NULL,\r\n"
				+ "  `address` VARCHAR(45) NOT NULL,\r\n"
				+ "  `tell` VARCHAR(13) NOT NULL,\r\n"
				+ "  `email` VARCHAR(45) NOT NULL,\r\n"
				+ "  PRIMARY KEY (`id`))\r\n"
				+ "ENGINE = InnoDB";
		String createDepartment = "CREATE TABLE IF NOT EXISTS `madang`.`department` (\r\n"
				+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` VARCHAR(30) NOT NULL,\r\n"
				+ "  `address` VARCHAR(45) NOT NULL,\r\n"
				+ "  `tell` VARCHAR(13) NOT NULL,\r\n"
				+ "  `dean_id` INT NOT NULL,\r\n"
				+ "  PRIMARY KEY (`id`),\r\n"
				+ "  INDEX `dean_id_idx` (`dean_id` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_Department_Professor`\r\n"
				+ "    FOREIGN KEY (`dean_id`)\r\n"
				+ "    REFERENCES `madang`.`professor` (`id`)\r\n"
				+ "    ON DELETE NO ACTION\r\n"
				+ "    ON UPDATE NO ACTION)\r\n"
				+ "ENGINE = InnoDB";
		String createStudent = "CREATE TABLE IF NOT EXISTS `madang`.`student` (\r\n"
				+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` VARCHAR(30) NOT NULL,\r\n"
				+ "  `address` VARCHAR(45) NOT NULL,\r\n"
				+ "  `tell` VARCHAR(13) NOT NULL,\r\n"
				+ "  `email` VARCHAR(45) NOT NULL,\r\n"
				+ "  `dept_id` INT NOT NULL,\r\n"
				+ "  `account` INT NOT NULL,\r\n"
				+ "  `minor_id` INT NULL,\r\n"
				+ "  PRIMARY KEY (`id`),\r\n"
				+ "  INDEX `dept_id_idx` (`dept_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `minor_id_idx` (`minor_id` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_Student_Department1`\r\n"
				+ "    FOREIGN KEY (`dept_id`)\r\n"
				+ "    REFERENCES `madang`.`department` (`id`)\r\n"
				+ "    ON DELETE NO ACTION\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "  CONSTRAINT `fk_Student_Department2`\r\n"
				+ "    FOREIGN KEY (`minor_id`)\r\n"
				+ "    REFERENCES `madang`.`department` (`id`)\r\n"
				+ "    ON DELETE NO ACTION\r\n"
				+ "    ON UPDATE NO ACTION)\r\n"
				+ "ENGINE = InnoDB";
		String createCourse = "CREATE TABLE IF NOT EXISTS `madang`.`course` (\r\n"
				+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` VARCHAR(30) NOT NULL,\r\n"
				+ "  `class` INT NOT NULL,\r\n"
				+ "  `prof_id` INT NOT NULL,\r\n"
				+ "  `day_of_week` VARCHAR(3) NOT NULL,\r\n"
				+ "  `time_per_week` INT NOT NULL,\r\n"
				+ "  `credit` INT NOT NULL,\r\n"
				+ "  `dept_id` INT NOT NULL,\r\n"
				+ "  `location` VARCHAR(45) NOT NULL,\r\n"
				+ "  `period` INT NOT NULL,\r\n"
				+ "  PRIMARY KEY (`id`),\r\n"
				+ "  INDEX `fk_Course_Professor_idx` (`prof_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `fk_Course_Department_idx` (`dept_id` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_Course_Professor`\r\n"
				+ "    FOREIGN KEY (`prof_id`)\r\n"
				+ "    REFERENCES `madang`.`professor` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "  CONSTRAINT `fk_Course_Department`\r\n"
				+ "    FOREIGN KEY (`dept_id`)\r\n"
				+ "    REFERENCES `madang`.`department` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION)\r\n"
				+ "ENGINE = InnoDB\r\n"
				+ "COMMENT = '	'";
		String createCourseHistory = "CREATE TABLE IF NOT EXISTS `madang`.`course_history` (\r\n"
				+ "  `st_id` INT NOT NULL,\r\n"
				+ "  `prof_id` INT NOT NULL,\r\n"
				+ "  `course_id` INT NOT NULL,\r\n"
				+ "  `attend_score` INT NULL,\r\n"
				+ "  `mid_score` INT NULL,\r\n"
				+ "  `final_score` INT NULL,\r\n"
				+ "  `etc_score` INT NULL,\r\n"
				+ "  `total_score` INT NULL,\r\n"
				+ "  `grade` VARCHAR(1) NULL,\r\n"
				+ "  `year` YEAR(4) NOT NULL,\r\n"
				+ "  `semester` INT NOT NULL,\r\n"
				+ "  INDEX `st_id_idx` (`st_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `prof_id_idx` (`prof_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `course_id_idx` (`course_id` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_CouseHistoty_Student`\r\n"
				+ "    FOREIGN KEY (`st_id`)\r\n"
				+ "    REFERENCES `madang`.`student` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "  CONSTRAINT `fk_CouseHistoty_Professor`\r\n"
				+ "    FOREIGN KEY (`prof_id`)\r\n"
				+ "    REFERENCES `madang`.`professor` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "  CONSTRAINT `fk_CouseHistoty_Course`\r\n"
				+ "    FOREIGN KEY (`course_id`)\r\n"
				+ "    REFERENCES `madang`.`course` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION)\r\n"
				+ "ENGINE = InnoDB";
		String createClub = "CREATE TABLE IF NOT EXISTS `madang`.`club` (\r\n"
				+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` VARCHAR(30) NOT NULL,\r\n"
				+ "  `num_of_student` INT NOT NULL DEFAULT 0,\r\n"
				+ "  `leader_id` INT NOT NULL,\r\n"
				+ "  `prof_id` INT NOT NULL,\r\n"
				+ "  `location` VARCHAR(45) NOT NULL,\r\n"
				+ "  PRIMARY KEY (`id`),\r\n"
				+ "  INDEX `leader_id_idx` (`leader_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `prof_id_idx` (`prof_id` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_Club_Professor`\r\n"
				+ "    FOREIGN KEY (`prof_id`)\r\n"
				+ "    REFERENCES `madang`.`professor` (`id`)\r\n"
				+ "    ON DELETE NO ACTION\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "  CONSTRAINT `fk_Club_Student`\r\n"
				+ "    FOREIGN KEY (`leader_id`)\r\n"
				+ "    REFERENCES `madang`.`student` (`id`)\r\n"
				+ "    ON DELETE NO ACTION\r\n"
				+ "    ON UPDATE NO ACTION)\r\n"
				+ "ENGINE = InnoDB";
		String createGuide = "CREATE TABLE IF NOT EXISTS `madang`.`guide` (\r\n"
				+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `st_id` INT NOT NULL,\r\n"
				+ "  `prof_id` INT NOT NULL,\r\n"
				+ "  `grade` INT NOT NULL,\r\n"
				+ "  `semester` INT NOT NULL,\r\n"
				+ "  PRIMARY KEY (`id`),\r\n"
				+ "  INDEX `st_id_idx` (`st_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `prof_id_idx` (`prof_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `st_prof_idx` (`st_id` ASC, `prof_id` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_Guide_Student`\r\n"
				+ "    FOREIGN KEY (`st_id`)\r\n"
				+ "    REFERENCES `madang`.`student` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "  CONSTRAINT `fk_Guide_Professor`\r\n"
				+ "    FOREIGN KEY (`prof_id`)\r\n"
				+ "    REFERENCES `madang`.`professor` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "    unique key guide_uk (st_id,grade,semester))\r\n"
				+ "ENGINE = InnoDB";
		String createPayment = "CREATE TABLE IF NOT EXISTS `madang`.`payment` (\r\n"
				+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `st_id` INT NOT NULL,\r\n"
				+ "  `pay_year` YEAR(4) NOT NULL,\r\n"
				+ "  `pay_semester` INT NOT NULL,\r\n"
				+ "  `total_tuition` INT NOT NULL,\r\n"
				+ "  `total_payment` INT NOT NULL,\r\n"
				+ "  `latest_payment` DATETIME NOT NULL,\r\n"
				+ "  INDEX `st_id_idx` (`st_id` ASC) VISIBLE,\r\n"
				+ "  PRIMARY KEY (`id`),\r\n"
				+ "  CONSTRAINT `fk_Payment_Student`\r\n"
				+ "    FOREIGN KEY (`st_id`)\r\n"
				+ "    REFERENCES `madang`.`student` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION)\r\n"
				+ "ENGINE = InnoDB";
		String createStudentClub = "CREATE TABLE IF NOT EXISTS `madang`.`student_club` (\r\n"
				+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `st_id` INT NOT NULL,\r\n"
				+ "  `club_id` INT NOT NULL,\r\n"
				+ "  PRIMARY KEY (`id`),\r\n"
				+ "  INDEX `st_id_idx` (`st_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `club_id_idx` (`club_id` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_StudentClub_Student`\r\n"
				+ "    FOREIGN KEY (`st_id`)\r\n"
				+ "    REFERENCES `madang`.`student` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "  CONSTRAINT `fk_StudentClub_Club`\r\n"
				+ "    FOREIGN KEY (`club_id`)\r\n"
				+ "    REFERENCES `madang`.`club` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION)\r\n"
				+ "ENGINE = InnoDB";
		String createDeptProfessor = "CREATE TABLE IF NOT EXISTS `madang`.`dept_professor` (\r\n"
				+ "  `dept_id` INT NOT NULL,\r\n"
				+ "  `prof_id` INT NOT NULL,\r\n"
				+ "  INDEX `dept_id_idx` (`dept_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `prof_id_idx` (`prof_id` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_DepartmentProfessor_Department`\r\n"
				+ "    FOREIGN KEY (`dept_id`)\r\n"
				+ "    REFERENCES `madang`.`department` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "  CONSTRAINT `fk_DepartmentProfessor_Professor`\r\n"
				+ "    FOREIGN KEY (`prof_id`)\r\n"
				+ "    REFERENCES `madang`.`professor` (`id`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE NO ACTION,\r\n"
				+ "	unique key dept_prof_uk (prof_id,dept_id)) \r\n"
				+ "ENGINE = InnoDB";
		
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(dropDeptProfessor);
			stmt.executeUpdate(dropStudentClub);
			stmt.executeUpdate(dropPayment);
			stmt.executeUpdate(dropGuide);
			stmt.executeUpdate(dropClub);
			stmt.executeUpdate(dropCourseHistory);
			stmt.executeUpdate(dropCourse);
			stmt.executeUpdate(dropStudent);
			stmt.executeUpdate(dropDepartment);
			stmt.executeUpdate(dropProfessor);
			
			
			stmt.executeUpdate(createProfessor);      
			stmt.executeUpdate(createDepartment);     
			stmt.executeUpdate(createStudent);        
			stmt.executeUpdate(createCourse);         
			stmt.executeUpdate(createCourseHistory);  
			stmt.executeUpdate(createClub);           
			stmt.executeUpdate(createGuide);          
			stmt.executeUpdate(createPayment);        
			stmt.executeUpdate(createStudentClub);    
			stmt.executeUpdate(createDeptProfessor);
			
			stmt.executeUpdate("INSERT INTO professor VALUES (1001, '���԰�', '����Ư���� �ھ絿', '010-0000-0001', '���԰�@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1002, '������', '��⵵ ������', '010-0000-0002', '������@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1003, '�ֻ�ö', '��⵵ �ϳ�', '010-0000-0003', '�ֻ�ö@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1004, '�ۼ�ȣ', '����Ư���� ȭ�絿', '010-0000-0004', '�ۼ�ȣ@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1005, '�ּ���', '����Ư���� ���ε�', '010-0000-0005', '�ּ���@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1006, '����ȣ', '������ ��ô', '010-0000-0006', '����ȣ@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1007, '�����', '���� ����', '010-0000-0007', '�����@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1008, '�ѹ���', '��� �λ�', '010-0000-0008', '�ѹ���@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1009, '������', '������ ���', '010-0000-0009', '������@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1010, '�ֻ��', '���� ����', '010-0000-0010', '�ֻ��@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1011, '������', '���� ����', '010-0000-0011', '������@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1012, '�����ҹ�', '��⵵ ��õ', '010-0000-0012', '�����ҹ�@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1013, '������', '���ֵ� ��������', '010-0000-0013', '������@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1014, '��������', '��� �λ�', '010-0000-0014', '��������@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1015, '��������', '���� ����', '010-0000-0015', '��������@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1016, '��ȣ����', '����Ư���� ȭ�絿', '010-0000-0016', '��ȣ����@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1017, '��������', '����Ư���� ȭ�絿', '010-0000-0017', '��������@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1018, '���󸶴�', '������ ���', '010-0000-0018', '���󸶴�@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1019, '�ֺ�ѱ�', '������ ���', '010-0000-0019', '�ֺ�ѱ�@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1020, '������', '������ ��ô', '010-0000-0020', '������@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1021, '���ڵ���', '������ ��ô', '010-0000-0021', '���ڵ���@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1022, '������', '������ ��', '010-0000-0022', '������@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1023, '�̻����', '������ ��', '010-0000-0023', '�̻����@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1024, '���Ѱ�', '��⵵ �뼺��', '010-0000-0024', '���Ѱ�@sejong.ac.kr')");
			stmt.executeUpdate("INSERT INTO professor VALUES (1025, '�ڼ���', '��⵵ �뼺��', '010-0000-0025', '�ڼ���@sejong.ac.kr')");
			
			stmt.executeUpdate("INSERT INTO department VALUES(2001, '��ǻ�Ͱ��а�', '�̳뺣�̼�301', '02-000-0001', 1001)");
			stmt.executeUpdate("INSERT INTO department VALUES(2002, '����Ʈ�����а�', '�̳뺣�̼�302', '02-000-0002', 1002)");
			stmt.executeUpdate("INSERT INTO department VALUES(2003, '������ȣ�а�', '�̳뺣�̼�303', '02-000-0003', 1003)");
			stmt.executeUpdate("INSERT INTO department VALUES(2004, '���ɱ������к�', '�̳뺣�̼�708', '02-000-0004', 1004)");
			stmt.executeUpdate("INSERT INTO department VALUES(2005, '�ΰ������а�', '�̳뺣�̼�401', '02-000-0005', 1005)");
			stmt.executeUpdate("INSERT INTO department VALUES(2006, '������а�', '�湫��601', '02-000-0006', 1006)");
			stmt.executeUpdate("INSERT INTO department VALUES(2007, '�Ǽ�ȯ����а�', '�湫��708', '02-000-0007', 1007)");
			stmt.executeUpdate("INSERT INTO department VALUES(2008, '�����а�', '�湫��602', '02-000-0008', 1008)");
			stmt.executeUpdate("INSERT INTO department VALUES(2009, '�װ����ְ��а�', '�湫��603', '02-000-0009', 1009)");
			stmt.executeUpdate("INSERT INTO department VALUES(2010, '����ż�����а�', '�湫��701', '02-000-0010', 1010)");
			stmt.executeUpdate("INSERT INTO department VALUES(2011, '����ý��۰��а�', '�湫��702', '02-000-0011', 1011)");
			stmt.executeUpdate("INSERT INTO department VALUES(2012, 'ȯ�濡�������������а�', '�湫��703', '02-000-0012', 1012)");
			stmt.executeUpdate("INSERT INTO department VALUES(2013, '������а�', '�������301', '02-000-0013', 1013)");
			stmt.executeUpdate("INSERT INTO department VALUES(2014, '������а�', '�������302', '02-000-0014', 1014)");
			stmt.executeUpdate("INSERT INTO department VALUES(2015, '�Ͼ��Ϲ��а�', '�������303', '02-000-0015', 1015)");
			stmt.executeUpdate("INSERT INTO department VALUES(2016, '�߱������', '�������304', '02-000-0016', 1016)");
			stmt.executeUpdate("INSERT INTO department VALUES(2017, '�����а�', '�������701', '02-000-0017', 1017)");
			stmt.executeUpdate("INSERT INTO department VALUES(2018, '�����а�', '�������702', '02-000-0018', 1018)");
			stmt.executeUpdate("INSERT INTO department VALUES(2019, '���а�', '������701', '02-000-0019', 1019)");
			stmt.executeUpdate("INSERT INTO department VALUES(2020, '����õ���а�', '������702', '02-000-0020', 1020)");
			stmt.executeUpdate("INSERT INTO department VALUES(2021, 'ȭ�а�', '������703', '02-000-0021', 1021)");
			stmt.executeUpdate("INSERT INTO department VALUES(2022, 'ȸȭ��', '����201', '02-000-0022', 1022)");
			stmt.executeUpdate("INSERT INTO department VALUES(2023, '���ǰ�', '����202', '02-000-0023', 1023)");
			stmt.executeUpdate("INSERT INTO department VALUES(2024, 'ü���а�', '����301', '02-000-0024', 1024)");
			stmt.executeUpdate("INSERT INTO department VALUES(2025, '�����', '����302', '02-000-0025', 1025)");
			
			stmt.executeUpdate("INSERT INTO student VALUES(13011001, '������', '����', '010-1111-0001', '������@sejong.ac.kr', 2001, 9009001, 2004);        ");
			stmt.executeUpdate("INSERT INTO student VALUES(13011002, '�ڼ���', '��⵵ ����', '010-1111-0002', '�ڼ���@sejong.ac.kr', 2002, 9009002, 2010);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(13011003, '��â��', '��⵵ ����', '010-1111-0003', '��â��@sejong.ac.kr', 2003, 9009003, NULL);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(14011001, '�Ӱ�', '������ ��ô', '010-1111-0004', '�Ӱ�@sejong.ac.kr', 2004, 9009004, NULL);      ");
			stmt.executeUpdate("INSERT INTO student VALUES(14011002, '���翬', '��� �뱸', '010-1111-0005', '���翬@sejong.ac.kr', 2005, 9009005, NULL);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(14011003, '�ӱ�', '���ֵ� ���ֽ�', '010-1111-0006', '�ӱ�@sejong.ac.kr', 2006, 9009006, NULL);     ");
			stmt.executeUpdate("INSERT INTO student VALUES(15011001, '������', '���ֵ� �Դ�', '010-1111-0007', '������@sejong.ac.kr', 2007, 9009007, 2006);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(15011002, '������', '����Ư���� ����', '010-1111-0008', '������@sejong.ac.kr', 2008, 9009008, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(15011003, '������', '����Ư���� ���', '010-1111-0009', '������@sejong.ac.kr', 2009, 9009009, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(16011001, '����ȣ', '��⵵ ����', '010-1111-0010', '����ȣ@sejong.ac.kr', 2010, 9009010, NULL);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(16011002, '���缮', '������ ��', '010-1111-0011', '���缮@sejong.ac.kr', 2011, 9009011, 2009);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(16011003, '�ڸ��', '�︪��', '010-1111-0012', '�ڸ��@sejong.ac.kr', 2012, 9009012, NULL);       ");
			stmt.executeUpdate("INSERT INTO student VALUES(17011001, '������', '����', '010-1111-0013', '������@sejong.ac.kr', 2013, 9009013, NULL);        ");
			stmt.executeUpdate("INSERT INTO student VALUES(17011002, '����', '���� ����', '010-1111-0014', '����@sejong.ac.kr', 2014, 9009014, 2008);      ");
			stmt.executeUpdate("INSERT INTO student VALUES(17011003, '��ȫö', '���� ����', '010-1111-0015', '��ȫö@sejong.ac.kr', 2015, 9009015, NULL);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(13011004, '���', '����', '010-1111-0016', '���@sejong.ac.kr', 2016, 9009016, NULL);          ");
			stmt.executeUpdate("INSERT INTO student VALUES(13011005, '�������', '��⵵ ����', '010-1111-0017', '�������@sejong.ac.kr', 2017, 9009017, NULL);");
			stmt.executeUpdate("INSERT INTO student VALUES(13011006, '��ȭ��', '��⵵ ����', '010-1111-0018', '��ȭ��@sejong.ac.kr', 2018, 9009018, NULL);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(14011004, '��������', '������ ��ô', '010-1111-0019', '��������@sejong.ac.kr', 2019, 9009019, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(14011005, '�豸���', '��� �뱸', '010-1111-0020', '�豸���@sejong.ac.kr', 2020, 9009020, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(14011006, '�Ӱ���', '���ֵ� ���ֽ�', '010-1111-0021', '�Ӱ���@sejong.ac.kr', 2021, 9009021, 2009); ");
			stmt.executeUpdate("INSERT INTO student VALUES(15011004, '��Ű��õ', '���ֵ� �Դ�', '010-1111-0022', '��Ű��õ@sejong.ac.kr', 2022, 9009022, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(15011005, '������ȣ', '����Ư���� ����', '010-1111-0023', '������ȣ@sejong.ac.kr', 2023, 9009023, NULL);");
			stmt.executeUpdate("INSERT INTO student VALUES(15011006, '����Ŀ', '����Ư���� ���', '010-1111-0024', '����Ŀ@sejong.ac.kr', 2024, 9009024, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(16011004, '���۷ι�', '��⵵ ����', '010-1111-0025', '���۷ι�@sejong.ac.kr', 2025, 9009025, 2001);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(16011005, '�̹���ŷ', '������ ��', '010-1111-0026', '�̹���ŷ@sejong.ac.kr', 2001, 9009026, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(16011006, '���Ƶ�����', '�︪��', '010-1111-0027', '���Ƶ�����@sejong.ac.kr', 2002, 9009027, NULL);   ");
			stmt.executeUpdate("INSERT INTO student VALUES(17011004, '�̴Ͼ�', '����', '010-1111-0028', '�̴Ͼ�@sejong.ac.kr', 2003, 9009028, 2002);        ");
			stmt.executeUpdate("INSERT INTO student VALUES(17011005, '���ʹ�', '���� ����', '010-1111-0029', '���ʹ�@sejong.ac.kr', 2004, 9009029, NULL);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(17011006, '���Ұ��', '���� ����', '010-1111-0030', '���Ұ��@sejong.ac.kr', 2005, 9009030, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(13011007, '��ġ������', '����', '010-1111-0031', '��ġ������@sejong.ac.kr', 2006, 9009031, NULL);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(13011008, '��������', '��⵵ ����', '010-1111-0032', '��������@sejong.ac.kr', 2001, 9009032, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(13011009, '���߻�⽾', '��⵵ ����', '010-1111-0033', '���߻�⽾@sejong.ac.kr', 2002, 9009033, NULL);");
			stmt.executeUpdate("INSERT INTO student VALUES(14011007, '������', '������ ��ô', '010-1111-0034', '������@sejong.ac.kr', 2003, 9009034, 2001);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(14011008, '��ߴ��', '��� �뱸', '010-1111-0035', '��ߴ��@sejong.ac.kr', 2004, 9009035, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(14011009, '�踮��', '���ֵ� ���ֽ�', '010-1111-0036', '�踮��@sejong.ac.kr', 2005, 9009036, NULL);   ");
			stmt.executeUpdate("INSERT INTO student VALUES(15011007, '���϶����', '���ֵ� �Դ�', '010-1111-0037', '���϶����@sejong.ac.kr', 2006, 9009037, 2001);");
			stmt.executeUpdate("INSERT INTO student VALUES(15011008, '��ƮŸ', '����Ư���� ����', '010-1111-0038', '��ƮŸ@sejong.ac.kr', 2007, 9009038, NULL);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(15011009, '�����', '����Ư���� ���', '010-1111-0039', '�����@sejong.ac.kr', 2008, 9009039, NULL);");
			stmt.executeUpdate("INSERT INTO student VALUES(16011007, '�ڻ�̶�', '��⵵ ����', '010-1111-0040', '�ڻ�̶�@sejong.ac.kr', 2009, 9009040, 2001);  ");
			stmt.executeUpdate("INSERT INTO student VALUES(16011008, '������', '������ ��', '010-1111-0041', '������@sejong.ac.kr', 2010, 9009041, NULL);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(16011009, '�����', '�︪��', '010-1111-0042', '�����@sejong.ac.kr', 2008, 9009042, NULL);       ");
			stmt.executeUpdate("INSERT INTO student VALUES(17011007, '��ī�̻�', '����', '010-1111-0043', '��ī�̻�@sejong.ac.kr', 2009, 9009043, 2008);      ");
			stmt.executeUpdate("INSERT INTO student VALUES(17011008, '�ϼ���', '���� ����', '010-1111-0044', '�ϼ���@sejong.ac.kr', 2015, 9009044, NULL);    ");
			stmt.executeUpdate("INSERT INTO student VALUES(17011009, '���þ�', '���� ����', '010-1111-0045', '���þ�@sejong.ac.kr', 2025, 9009045, 2001);  ");
			
			stmt.executeUpdate("INSERT INTO course VALUE(3001, '�����ͺ��̽�', 1, 1001, '��_��', 3, 3, 2001, '�̳뺣�̼�B101', 1);   ");
			stmt.executeUpdate("INSERT INTO course VALUE(3002, '�����ͺ��̽�', 2, 1001, 'ȭ_��', 3, 3, 2001, '�̳뺣�̼�B101', 1);   ");
			stmt.executeUpdate("INSERT INTO course VALUE(3003, '�˰���׽ǽ�', 1, 1001, '��_��', 4, 4, 2001, '�̳뺣�̼�B102', 3);  ");
			stmt.executeUpdate("INSERT INTO course VALUE(3004, '�˰���׽ǽ�', 2, 1002, 'ȭ_��', 4, 4, 2001, '�̳뺣�̼�B102', 3);  ");
			stmt.executeUpdate("INSERT INTO course VALUE(3005, 'C++', 1, 1001, '��_��', 4, 3, 2001, '�̳뺣�̼�B101', 5);      ");
			stmt.executeUpdate("INSERT INTO course VALUE(3006, 'C#', 1, 1002, '��', 4, 3, 2002, '�̳뺣�̼�B102', 1);         ");
			stmt.executeUpdate("INSERT INTO course VALUE(3007, '����ó��', 1, 1003, '��_��', 3, 3, 2003, '�̳뺣�̼�208', 1);      ");
			stmt.executeUpdate("INSERT INTO course VALUE(3008, '��ȣȭ', 1, 1003, 'ȭ_��', 3, 3, 2003, '�̳뺣�̼�208', 1);       ");
			stmt.executeUpdate("INSERT INTO course VALUE(3009, '���ɹ׽ǽ�', 1, 1004, 'ȭ_��', 3, 3, 2004, '�̳뺣�̼�210', 2);     ");
			stmt.executeUpdate("INSERT INTO course VALUE(3010, '�����׽ǽ�', 1, 1004, '��_��', 3, 3, 2004, '�̳뺣�̼�210', 2);     ");
			stmt.executeUpdate("INSERT INTO course VALUE(3011, '�ΰ�����', 1, 1005, '��', 3, 3, 2005, '�̳뺣�̼�101', 3);        ");
			stmt.executeUpdate("INSERT INTO course VALUE(3012, '����׽���', 1, 1006, '��_��', 4, 3, 2006, '�湫��201', 5);       ");
			stmt.executeUpdate("INSERT INTO course VALUE(3013, '�Ǽ��׽���', 1, 1007, '��_��', 4, 3, 2007, '�湫��202', 1);       ");
			stmt.executeUpdate("INSERT INTO course VALUE(3014, '����н�', 1, 1008, '��_��', 4, 3, 2008, '�湫��203', 2);        ");
			stmt.executeUpdate("INSERT INTO course VALUE(3015, '�װ��ý���', 1, 1009, 'ȭ_��', 4, 3, 2009, '�湫��204', 3);       ");
			stmt.executeUpdate("INSERT INTO course VALUE(3016, '����ż���', 1, 1010, 'ȭ_��', 4, 3, 2010, '�湫��205', 4);       ");
			stmt.executeUpdate("INSERT INTO course VALUE(3017, '���ֱ���', 1, 1011, 'ȭ_��', 4, 3, 2011, '�湫��206', 6);        ");
			stmt.executeUpdate("INSERT INTO course VALUE(3018, '������', 1, 1012, 'ȭ_��', 4, 3, 2012, '�湫��207', 4);         ");
			stmt.executeUpdate("INSERT INTO course VALUE(3019, '����', 1, 1013, '��_��', 4, 3, 2013, '�������801', 4);         ");
			stmt.executeUpdate("INSERT INTO course VALUE(3020, '����', 1, 1014, 'ȭ_��', 4, 3, 2014, '�������802', 3);         ");
			stmt.executeUpdate("INSERT INTO course VALUE(3021, '�Ϻ���', 1, 1015, 'ȭ_��', 4, 3, 2015, '�������803', 5);        ");
			stmt.executeUpdate("INSERT INTO course VALUE(3022, '�߱���', 1, 1016, '��_��', 4, 3, 2016, '�������804', 1);        ");
			stmt.executeUpdate("INSERT INTO course VALUE(3023, '�ѱ���', 1, 1017, '��_��', 4, 3, 2017, '�������805', 2);        ");
			stmt.executeUpdate("INSERT INTO course VALUE(3024, '������', 1, 1018, '��_��', 4, 3, 2018, '�������806', 5);        ");
			stmt.executeUpdate("INSERT INTO course VALUE(3025, '�̺���', 1, 1019, '��_��', 4, 3, 2019, '������506', 5);         ");
			stmt.executeUpdate("INSERT INTO course VALUE(3026, 'õ����', 1, 1020, 'ȭ_��', 4, 3, 2020, '������506', 4);         ");
			stmt.executeUpdate("INSERT INTO course VALUE(3027, 'ȭ�й׽���', 1, 1021, '��_��', 4, 3, 2021, '������506', 3);       ");
			stmt.executeUpdate("INSERT INTO course VALUE(3028, 'ȸȭ��', 1, 1022, '��_��', 4, 3, 2022, '����101', 3);          ");
			stmt.executeUpdate("INSERT INTO course VALUE(3029, '���亥��', 1, 1023, '��_��', 4, 3, 2023, '����102', 3);         ");
			stmt.executeUpdate("INSERT INTO course VALUE(3030, '3��', 1, 1024, 'ȭ_��', 4, 3, 2024, '����103', 4);         ");
			stmt.executeUpdate("INSERT INTO course VALUE(3031, '���빫��', 1, 1025, 'ȭ_��', 4, 3, 2025, '����104', 4);         ");
			
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011001, 1001, 3001, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011001, 1002, 3004, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011001, 1004, 3009, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011002, 1002, 3006, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011002, 1010, 3016, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011003, 1003, 3007, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011003, 1003, 3008, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011004, 1016, 3022, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011005, 1017, 3023, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011006, 1018, 3024, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011007, 1006, 3012, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011008, 1001, 3002, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011008, 1001, 3003, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011008, 1001, 3005, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(13011009, 1002, 3006, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011001, 1004, 3009, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011001, 1004, 3010, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011002, 1005, 3011, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011003, 1006, 3012, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011004, 1019, 3025, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011005, 1020, 3026, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011006, 1021, 3027, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011006, 1009, 3015, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011007, 1003, 3007, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011007, 1003, 3008, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011007, 1001, 3005, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011008, 1004, 3009, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011008, 1004, 3010, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(14011009, 1005, 3011, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011001, 1006, 3012, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011001, 1007, 3013, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011002, 1008, 3014, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011003, 1009, 3015, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011004, 1022, 3028, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011005, 1023, 3029, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011006, 1024, 3030, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011007, 1006, 3012, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011007, 1001, 3005, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011007, 1001, 3001, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011008, 1007, 3013, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(15011009, 1008, 3014, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011001, 1010, 3016, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011002, 1009, 3015, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011002, 1011, 3017, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011003, 1012, 3018, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011004, 1025, 3031, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011004, 1001, 3001, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011004, 1001, 3003, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011005, 1001, 3002, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011005, 1002, 3004, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011005, 1001, 3005, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011006, 1002, 3006, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011007, 1009, 3015, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011007, 1001, 3005, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011007, 1001, 3001, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011008, 1010, 3016, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(16011009, 1008, 3014, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011001, 1013, 3019, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011002, 1014, 3020, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011002, 1008, 3014, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011003, 1015, 3021, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011004, 1002, 3006, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011004, 1003, 3007, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011004, 1003, 3008, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011005, 1004, 3009, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011005, 1004, 3010, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011006, 1005, 3011, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011007, 1008, 3014, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011007, 1009, 3015, 79, 79, 79, 79, 79, 'C', '2021', 1);     ");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011008, 1015, 3021, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011009, 1025, 3031, 100, 100, 100, 100, 100, 'A', '2021', 1);");
			stmt.executeUpdate("INSERT INTO course_history VALUE(17011009, 1001, 3005, 89, 89, 89, 89, 89, 'B', '2021', 1);     ");
			
			stmt.executeUpdate("INSERT INTO club VALUE(4001, '�౸', 11, 13011001, 1001, '�л�ȸ��401');  ");
			stmt.executeUpdate("INSERT INTO club VALUE(4002, '�߱�', 9, 13011002, 1002, '�л�ȸ��402');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4003, '��', 5, 13011003, 1003, '�л�ȸ��403');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4004, '�Ǳ�', 5, 13011004, 1004, '�л�ȸ��404');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4005, '����', 3, 13011005, 1005, '�л�ȸ��405');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4006, '��', 5, 13011006, 1006, '�л�ȸ��406');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4007, '����', 3, 13011007, 1007, '�л�ȸ��407');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4008, '�����Ƹ�', 3, 13011008, 1008, '�л�ȸ��408'); ");
			stmt.executeUpdate("INSERT INTO club VALUE(4009, '���', 5, 13011009, 1009, '�л�ȸ��409');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4010, '��Ű', 2, 14011001, 1010, '�л�ȸ��501');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4011, '������Ʈ', 3, 14011002, 1011, '�л�ȸ��502'); ");
			stmt.executeUpdate("INSERT INTO club VALUE(4012, '����', 3, 14011003, 1012, '�л�ȸ��503');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4013, '������', 3, 14011004, 1013, '�л�ȸ��504');  ");
			stmt.executeUpdate("INSERT INTO club VALUE(4014, '�������', 3, 14011005, 1014, '�л�ȸ��505'); ");
			stmt.executeUpdate("INSERT INTO club VALUE(4015, '�±ǵ�', 3, 14011006, 1015, '�л�ȸ��506');  ");
			stmt.executeUpdate("INSERT INTO club VALUE(4016, '����', 3, 14011007, 1016, '�л�ȸ��507');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4017, '�˵�', 3, 14011008, 1017, '�л�ȸ��508');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4018, '�̽��౸', 3, 14011009, 1018, '�л�ȸ��509'); ");
			stmt.executeUpdate("INSERT INTO club VALUE(4019, 'ǲ��', 5, 15011001, 1019, '�л�ȸ��601');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4020, '�丮', 3, 15011002, 1020, '�л�ȸ��602');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4021, '�ڵ�', 3, 15011003, 1021, '�л�ȸ��603');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4022, '��ǻ������', 3, 15011004, 1022, '�л�ȸ��604');");
			stmt.executeUpdate("INSERT INTO club VALUE(4023, '����', 3, 15011005, 1023, '�л�ȸ��605');   ");
			stmt.executeUpdate("INSERT INTO club VALUE(4024, '����', 3, 15011006, 1024, '�л�ȸ��606');  ");
			stmt.executeUpdate("INSERT INTO club VALUE(4025, '����', 5, 15011007, 1025, '�л�ȸ��607');   ");
			
			stmt.executeUpdate("INSERT INTO guide VALUE(5001, 13011001, 1001, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5002, 13011002, 1002, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5003, 13011003, 1003, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5004, 13011004, 1004, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5005, 13011005, 1005, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5006, 13011006, 1006, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5007, 13011007, 1007, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5008, 13011008, 1008, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5009, 13011009, 1009, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5010, 14011001, 1010, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5011, 14011002, 1011, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5012, 14011003, 1012, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5013, 14011004, 1013, 4, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5014, 14011005, 1014, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5015, 14011006, 1015, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5016, 14011007, 1016, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5017, 14011008, 1017, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5018, 14011009, 1018, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5019, 15011001, 1019, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5020, 15011002, 1020, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5021, 15011003, 1021, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5022, 15011004, 1022, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5023, 15011005, 1023, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5024, 15011006, 1024, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5025, 15011007, 1025, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5026, 15011008, 1001, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5027, 15011009, 1002, 3, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5028, 16011001, 1003, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5029, 16011002, 1004, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5030, 16011003, 1005, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5031, 16011004, 1006, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5032, 16011005, 1007, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5033, 16011006, 1007, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5034, 16011007, 1008, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5035, 16011008, 1009, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5036, 16011009, 1010, 2, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5037, 17011001, 1010, 1, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5038, 17011002, 1011, 1, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5039, 17011003, 1012, 1, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5040, 17011004, 1013, 1, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5041, 17011005, 1014, 1, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5042, 17011006, 1015, 1, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5043, 17011007, 1016, 1, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5044, 17011008, 1017, 1, 1);");
			stmt.executeUpdate("INSERT INTO guide VALUE(5045, 17011009, 1018, 1, 1);");
			
			stmt.executeUpdate("INSERT INTO payment VALUE(6001, 13011001, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6002, 13011002, '2021', 1, 450, 450, STR_TO_DATE('2021-02-23','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6003, 13011003, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6004, 13011004, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6005, 13011005, '2021', 1, 450, 450, STR_TO_DATE('2021-02-25','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6006, 13011006, '2021', 1, 450, 450, STR_TO_DATE('2021-02-25','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6007, 13011007, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6008, 13011008, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6009, 13011009, '2021', 1, 450, 450, STR_TO_DATE('2021-02-23','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6010, 14011001, '2021', 1, 450, 450, STR_TO_DATE('2021-02-22','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6011, 14011002, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6012, 14011003, '2021', 1, 450, 300, STR_TO_DATE('2021-02-22','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6013, 14011004, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6014, 14011005, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6015, 14011006, '2021', 1, 450, 250, STR_TO_DATE('2021-02-23','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6016, 14011007, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6017, 14011008, '2021', 1, 450, 450, STR_TO_DATE('2021-02-23','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6018, 14011009, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6019, 15011001, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6020, 15011002, '2021', 1, 450, 450, STR_TO_DATE('2021-02-25','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6021, 15011003, '2021', 1, 450, 450, STR_TO_DATE('2021-02-25','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6022, 15011004, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6023, 15011005, '2021', 1, 450, 250, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6024, 15011006, '2021', 1, 450, 450, STR_TO_DATE('2021-02-23','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6025, 15011007, '2021', 1, 450, 450, STR_TO_DATE('2021-02-22','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6026, 15011008, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6027, 15011009, '2021', 1, 450, 200, STR_TO_DATE('2021-02-22','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6028, 16011001, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6029, 16011002, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6030, 16011003, '2021', 1, 450, 450, STR_TO_DATE('2021-02-23','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6031, 16011004, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6032, 16011005, '2021', 1, 450, 450, STR_TO_DATE('2021-02-23','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6033, 16011006, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6034, 16011007, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6035, 16011008, '2021', 1, 450, 450, STR_TO_DATE('2021-02-25','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6036, 16011009, '2021', 1, 450, 450, STR_TO_DATE('2021-02-25','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6037, 17011001, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6038, 17011002, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6039, 17011003, '2021', 1, 450, 450, STR_TO_DATE('2021-02-23','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6040, 17011004, '2021', 1, 450, 450, STR_TO_DATE('2021-02-22','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6041, 17011005, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6042, 17011006, '2021', 1, 450, 300, STR_TO_DATE('2021-02-22','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6043, 17011007, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6044, 17011008, '2021', 1, 450, 450, STR_TO_DATE('2021-02-24','%Y-%m-%d'));");
			stmt.executeUpdate("INSERT INTO payment VALUE(6045, 17011009, '2021', 1, 450, 450, STR_TO_DATE('2021-02-23','%Y-%m-%d'));");
			
			stmt.executeUpdate("INSERT INTO student_club VALUE(7001, 13011001, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7002, 14011001, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7003, 15011001, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7004, 16011001, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7005, 17011001, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7006, 13011002, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7007, 14011002, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7008, 15011002, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7009, 16011002, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7010, 17011002, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7011, 17011002, 4001)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7012, 13011002, 4002)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7013, 14011004, 4002)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7014, 15011004, 4002)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7015, 16011004, 4002)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7016, 17011004, 4002)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7017, 13011003, 4002)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7018, 14011005, 4002)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7019, 15011005, 4002)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7020, 16011005, 4002)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7021, 13011003, 4003)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7022, 14011001, 4003)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7023, 15011002, 4003)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7024, 16011003, 4003)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7025, 17011004, 4003)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7026, 13011004, 4004)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7027, 14011006, 4004)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7028, 15011007, 4004)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7029, 16011008, 4004)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7030, 17011009, 4004)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7031, 13011005, 4005)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7032, 14011007, 4005)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7033, 15011007, 4005)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7034, 13011006, 4006)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7035, 14011008, 4006)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7036, 15011008, 4006)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7037, 16011007, 4006)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7038, 17011007, 4006)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7039, 13011007, 4007)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7040, 14011009, 4007)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7041, 17011008, 4007)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7042, 13011008, 4008)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7043, 13011009, 4008)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7044, 13011007, 4008)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7045, 13011009, 4009)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7046, 14011009, 4009)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7047, 15011009, 4009)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7048, 16011009, 4009)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7049, 17011009, 4009)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7050, 14011001, 4010)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7051, 15011005, 4010)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7052, 14011002, 4011)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7053, 16011005, 4011)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7054, 16011006, 4011)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7055, 14011003, 4012)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7056, 13011005, 4012)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7057, 17011008, 4012)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7058, 14011004, 4013)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7059, 15011004, 4013)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7060, 16011004, 4013)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7061, 14011005, 4014)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7062, 17011005, 4014)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7063, 13011005, 4014)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7064, 14011006, 4015)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7065, 13011006, 4015)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7066, 16011006, 4015)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7067, 14011007, 4016)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7068, 13011007, 4016)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7069, 17011007, 4016)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7070, 14011008, 4017)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7071, 13011008, 4017)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7072, 15011008, 4017)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7073, 14011009, 4018)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7074, 15011009, 4018)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7075, 17011009, 4018)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7076, 15011001, 4019)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7077, 16011001, 4019)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7078, 17011001, 4019)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7079, 15011002, 4020)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7080, 16011002, 4020)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7081, 17011003, 4020)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7082, 15011003, 4021)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7083, 16011003, 4021)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7084, 17011004, 4021)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7085, 15011004, 4022)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7086, 13011004, 4022)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7087, 16011005, 4022)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7088, 15011005, 4023)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7089, 16011005, 4023)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7090, 17011006, 4023)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7091, 15011006, 4024)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7092, 16011006, 4024)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7093, 13011007, 4024)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7094, 15011007, 4025)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7095, 16011008, 4025)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7096, 17011009, 4025)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7097, 13011009, 4025)");
			stmt.executeUpdate("INSERT INTO student_club VALUE(7098, 17011005, 4025)");
			
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2001, 1001)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2001, 1002)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2002, 1001)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2002, 1002)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2003, 1003)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2003, 1004)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2003, 1005)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2004, 1003)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2004, 1004)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2004, 1005)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2005, 1003)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2005, 1004)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2005, 1005)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2006, 1006)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2006, 1007)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2006, 1008)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2006, 1009)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2007, 1006)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2007, 1007)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2007, 1008)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2007, 1009)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2008, 1006)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2008, 1007)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2008, 1008)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2008, 1009)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2009, 1006)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2009, 1007)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2009, 1008)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2009, 1009)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2010, 1010)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2010, 1011)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2010, 1012)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2011, 1010)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2011, 1011)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2011, 1012)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2012, 1010)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2012, 1011)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2012, 1012)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2013, 1013)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2013, 1014)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2013, 1015)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2013, 1016)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2014, 1013)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2014, 1014)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2014, 1015)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2014, 1016)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2015, 1013)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2015, 1014)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2015, 1015)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2015, 1016)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2016, 1013)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2016, 1014)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2016, 1015)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2016, 1016)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2017, 1017)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2017, 1018)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2018, 1017)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2018, 1018)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2019, 1019)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2020, 1020)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2021, 1021)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2022, 1022)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2023, 1023)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2024, 1024)");
			stmt.executeUpdate("INSERT INTO dept_professor VALUE(2025, 1025)");
			
			con.commit();
			disconDB();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "DB �ʱ�ȭ ����\n");
			e.printStackTrace();
		}
		
	}
	
	public void showAll(JTextArea ta) {
		String str;
		conDB();
		ta.setText("");
		try {
			stmt = con.createStatement();
			ta.append("student\nId\tName\tAddress\t\tTell\t\tEmail\t\tDept_Id\tAccount\tMinor_Id\n");
			rs = stmt.executeQuery("select * from student");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) 
				+ "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getInt(7) + "\t" + rs.getInt(8) + "\n";
				ta.append(str);
			}
			ta.append("professor\nId\tName\tAddress\t\tTell\t\tEmail\t\t\n");
			rs = stmt.executeQuery("select * from professor");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) 
				+ "\t" + rs.getString(5) + "\n";
				ta.append(str);
			}
			ta.append("department\nId\tName\tAddress\tTell\tDean_Id\t\n");
			rs = stmt.executeQuery("select * from department");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) 
				+ "\t" + rs.getInt(5) + "\n";
				ta.append(str);
			}
			ta.append("course\nId\tName\tClass\tProf_Id\tDay_Of_Week\tTime_Per_Week\tCredit\tDept_id\tLocation\tPeriod\n");
			rs = stmt.executeQuery("select * from course");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) 
				+ "\t" + rs.getString(5) + "\t\t" + rs.getInt(6) + "\t\t" + rs.getInt(7) + "\t" + rs.getInt(8) 
				+ "\t" + rs.getString(9) + "\t" + rs.getInt(10) + "\n";
				ta.append(str);
			}
			ta.append("club\nId\tName\tNum_Of_Student\tLeader_Id\tProf_Id\tLocation\n");
			rs = stmt.executeQuery("select * from club");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t\t" + rs.getInt(4) 
				+ "\t" + rs.getInt(5) + "\t" + rs.getString(6) + "\n";
				ta.append(str);
			}
			ta.append("payment\nId\tSt_Id\tPay_Year\tPay_Semester\tTotal_tuition\tTotal_payment\tLatest_Payment\n");
			rs = stmt.executeQuery("select * from payment");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) 
				+ "\t\t" + rs.getInt(5) + "\t" + rs.getInt(6)+ "\t\t\t" + rs.getString(7) + "\n";
				ta.append(str);
			}
			ta.append("guide\nId\tSt_Id\tProf_Id\tGrade\tSemester\n");
			rs = stmt.executeQuery("select * from guide");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) 
				+ "\t" + rs.getInt(5) + "\n";
				ta.append(str);
			}
			ta.append("Dept_Professor\nDept_Id\tProf_Id\n");
			rs = stmt.executeQuery("select * from dept_professor");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getInt(2) + "\n";
				ta.append(str);
			}
			ta.append("Course_History\nSt_Id\tProf_Id\tCourse_Id\tAttend_Score\tMid_Score\tFinal_Score\tEtc_Score\tTotal_Score\tGrade\tYear\tSemester\n");
			rs = stmt.executeQuery("select * from course_history");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) 
				+ "\t" + rs.getInt(5) + "\t" + rs.getInt(6) + "\t" + rs.getInt(7) + "\t" + rs.getInt(8) 
				+ "\t" + rs.getString(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11) + "\n";
				ta.append(str);
			}
			ta.append("Student_Club\nId\tSt_Id\tClub_Id\n");
			rs = stmt.executeQuery("select * from course_history");
			while (rs.next()) {
				str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\n";
				ta.append(str);
			}
			
			disconDB();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "��� Table ��ȸ ����");
			e.printStackTrace();
		}
	}
	
	public void showAll(JTable table) {
		Object[] row;
		conDB();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowNum = model.getRowCount();
		for (int i=0 ;i<rowNum;i++) {
			model.removeRow(0);
		}
		try {
			stmt = con.createStatement();
			model.addRow(new String[] {"student"});
			model.addRow(new String[] {"Id","Name","Address","Tell","Email","Dept_Id","Account","Minor_Id"});
			rs = stmt.executeQuery("select * from student");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8)};
				model.addRow(row);
			}
			model.addRow(new String[] {"professor"});
			model.addRow(new String[] {"Id","Name","Address","Tell","Email"});
			rs = stmt.executeQuery("select * from professor");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5)};
				model.addRow(row);
			}
			model.addRow(new String[] {"department"});
			model.addRow(new String[] {"Id","Name","Address","Tell","Dean_Id"});
			rs = stmt.executeQuery("select * from department");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5)};
				model.addRow(row);
			}
			model.addRow(new String[] {"course"});
			model.addRow(new String[] {"Id","Name","Class","Prof_Id","Day_Of_Week","Time_Per_Week","Credit","Dept_id","Location","Period"});
			rs = stmt.executeQuery("select * from course");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),
						rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getString(9),rs.getInt(10)};
				model.addRow(row);
			}
			model.addRow(new String[] {"club"});
			model.addRow(new String[] {"Id","Name","Num_Of_Student","Leader_Id","Prof_Id","Location"});
			rs = stmt.executeQuery("select * from club");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6)};
				model.addRow(row);
			}
			model.addRow(new String[] {"payment"});
			model.addRow(new String[] {"Id","St_Id","Pay_Year","Pay_Semester","Total_tuition","Total_payment","Latest_Payment"});
			rs = stmt.executeQuery("select * from payment");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7)};
				model.addRow(row);
			}
			model.addRow(new String[] {"guide"});
			model.addRow(new String[] {"Id","St_Id","Prof_Id","Grade","Semester"});
			rs = stmt.executeQuery("select * from guide");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)};
				model.addRow(row);
			}
			model.addRow(new String[] {"dept_professor"});
			model.addRow(new String[] {"Dept_Id","Prof_Id"});
			rs = stmt.executeQuery("select * from dept_professor");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1),rs.getInt(2)};
				model.addRow(row);
			}
			model.addRow(new String[] {"course_history"});
			model.addRow(new String[] {"St_Id","Prof_Id","Course_Id","Attend_Score","Mid_Score","Final_Score","Etc_Score","Total_Score","Grade","Year","Semester"});
			rs = stmt.executeQuery("select * from course_history");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getString(9),rs.getInt(10),rs.getInt(11)};
				model.addRow(row);
			}
			model.addRow(new String[] {"student_club"});
			model.addRow(new String[] {"Id","St_Id","Club_Id"});
			rs = stmt.executeQuery("select * from student_club");
			while (rs.next()) {
				row = new Object [] {rs.getInt(1),rs.getInt(2),rs.getInt(3)};
				model.addRow(row);
			}
			
			disconDB();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "��� Table ��ȸ ����");
			e.printStackTrace();
		}
		
	}
	
	public void insertStudent (String ...values) {
		String sql = "insert into student (name,address,tell,email,dept_id,account) values(?,?,?,?,?,?)";
		conDB();
		
		try {
			pstmt = con.prepareStatement(sql);
			for (int i=0;i<6;i++) {
				pstmt.setString(i+1, values[i]);
			}
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
				JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		disconDB();
	}
	
	public void insertProfessor(String ...values) {
		String sql = "select count(*) from guide where st_id=? and grade=? and semester=?";
		String sql1 = "insert into professor (name,address,tell,email) values(?,?,?,?)";
		String sql2 = "insert into dept_professor (dept_id,prof_id) values(?,?)";
		String sql3 = "insert into course (name,class,prof_id,day_of_week,time_per_week,credit,dept_id,location,period) values (?,?,?,?,?,?,?,?,?)";
		String sql4 = "insert into guide (st_id, prof_id, grade, semester) values (?,?,?,?)";
		conDB();
		try {
			int time = Integer.valueOf(values[11]);
			if (time > 6 || time < 1) {
				JOptionPane.showMessageDialog(frame, "���ǽð��� �ּ� 1�ð����� 6�ð����� �Դϴ�.");
				disconDB();
				return ;
			}
			int credit = Integer.valueOf(values[12]);
			if (credit > 4 || credit < 1) {
				JOptionPane.showMessageDialog(frame, "��������� �ּ� 1������ 4������ �Դϴ�.");
				disconDB();
				return ;
			}
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, values[5]);
			pstmt.setString(2, values[6]);
			pstmt.setString(3, values[7]);
			rs = pstmt.executeQuery();
			int cnt = 0;
			if (rs.next()) 
				cnt = rs.getInt(1);
			if (cnt == 1) {
				JOptionPane.showMessageDialog(frame, "�ش� �л��� ���� ������ �������� �� �����ϴ�.");
				disconDB();
				return ;
			}
			
			pstmt = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			for (int i=0;i<4;i++)
				pstmt.setString(i+1, values[i]);
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			int profId = 0;
			if (rs.next()) profId = rs.getInt(1);
			
			pstmt = con.prepareStatement(sql4);
			pstmt.setString(1, values[5]);
			pstmt.setInt(2, profId);
			pstmt.setString(3, values[6]);
			pstmt.setString(4, values[7]);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, values[4]);
			pstmt.setInt(2, profId);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(sql3);
			pstmt.setString(1, values[8]);
			pstmt.setString(2, values[9]);
			pstmt.setInt(3, profId);
			pstmt.setString(4, values[10]);
			pstmt.setString(5, values[11]);
			pstmt.setString(6, values[12]);
			pstmt.setString(7, values[4]);
			pstmt.setString(8, values[13]);
			pstmt.setString(9, values[14]);
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		disconDB();
	}
	
	public void insertGuide(String ...values) {
		String sql = "insert into course (st_id,prof_id,grade,semester) valuse (?,?,?,?)";
		
		conDB();
		try {
			pstmt = con.prepareStatement(sql);
			for (int i=0;i<9;i++) 
				pstmt.setString(i+1, values[i]);
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		disconDB();
	}
	
	public void insertDepartment(String ...values) {
		String sql = "insert into department (name, address, tell, dean_id) values (?,?,?,?)";
		String sql2 = "insert into dept_professor (dept_id, prof_id) values (?,?)";
		String sql3 = "insert into course (name,class,prof_id,day_of_week,time_per_week,credit,dept_id,location,period) values (?,?,?,?,?,?,?,?,?)";
		
		conDB();
		try {
			int time = Integer.valueOf(values[7]);
			if (time > 6 || time < 1) {
				JOptionPane.showMessageDialog(frame, "���ǽð��� �ּ� 1�ð����� 6�ð����� �Դϴ�.");
				disconDB();
				return ;
			}
			int credit = Integer.valueOf(values[8]);
			if (credit > 4 || credit < 1) {
				JOptionPane.showMessageDialog(frame, "��������� �ּ� 1������ 4������ �Դϴ�.");
				disconDB();
				return ;
			}
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, values[0]);
			pstmt.setString(2, values[1]);
			pstmt.setString(3, values[2]);
			pstmt.setString(4, values[3]);
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			int deptId = 0;
			if (rs.next()) deptId = rs.getInt(1);
			
			pstmt = con.prepareStatement(sql2);
			pstmt.setInt(1, deptId);
			pstmt.setString(2, values[3]);
			
			pstmt = con.prepareStatement(sql3);
			pstmt.setString(1, values[4]);
			pstmt.setString(2, values[5]);
			pstmt.setString(3, values[3]);
			pstmt.setString(4, values[6]);
			pstmt.setString(5, values[7]);
			pstmt.setString(6, values[8]);
			pstmt.setInt(7, deptId);
			pstmt.setString(8, values[9]);
			pstmt.setString(9, values[10]);
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		disconDB();
	}
	
	public void insertCourse(String ...values) {
		String sql = "insert into course (name, class, prof_id, day_or_week, time_per_week, credit, dept_id, location, period) values (?,?,?,?,?,?,?,?,?)";
		
		conDB();
		try {
			int time = Integer.valueOf(values[4]);
			if (time > 6 || time < 1) {
				JOptionPane.showMessageDialog(frame, "���ǽð��� �ּ� 1�ð����� 6�ð����� �Դϴ�.");
				disconDB();
				return ;
			}
			int credit = Integer.valueOf(values[5]);
			if (credit > 4 || credit < 1) {
				JOptionPane.showMessageDialog(frame, "��������� �ּ� 1������ 4������ �Դϴ�.");
				disconDB();
				return ;
			}
			pstmt = con.prepareStatement(sql);
			for (int i=0; i<9; i++)
				pstmt.setString(i+1, values[i]);
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		disconDB();
	}
	
	public void insertCourseHistory(String ...values) {
		String sql = "insert into course_history (st_id,prof_id,course_id,year,semester) values (?,?,?,?,?)";
		String sql2 = "select total_tuition, total_payment from payment where st_id=? and pay_year=? and pay_semester=?";
		String sql3 = "select sum(credit) from course where id in (select course_id from course_history where st_id = ? and year = ? and semester=?)";
		conDB();
		try {
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, values[0]);
			pstmt.setString(2, values[3]);
			pstmt.setString(3, values[4]);
			rs = pstmt.executeQuery();
			
			if (!rs.next() || rs.getInt(1) > rs.getInt(2)) {
				JOptionPane.showMessageDialog(frame, "��ϱ� �̳�");
				disconDB();
				return;
			}
			
			pstmt = con.prepareStatement(sql);
			for (int i=0; i<5; i++)
				pstmt.setString(i+1, values[i]);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(sql3);
			pstmt.setString(1, values[0]);
			pstmt.setString(2, values[3]);
			pstmt.setString(3, values[4]);
			rs = pstmt.executeQuery();
			
			int current = 0;
			if (rs.next())
				current = rs.getInt(1);
			
			if (current > 10) {
				JOptionPane.showMessageDialog(frame, "���� ������ ������ �ʰ��Ͽ����ϴ�.");
				con.rollback();
				disconDB();
				return;
			}
			
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		disconDB();
	}
	
	public void insertClub (String ...values) {
		String sql = "insert into club (name, num_of_student, leader_id, prof_id, location) values (?,?,?,?,?)";
		String sql2 = "insert into student_club (st_id,club_id) values (?,?)";
		
		conDB();
		try {
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, values[0]);
			pstmt.setInt(2, 1);
			pstmt.setString(3, values[1]);
			pstmt.setString(4, values[2]);
			pstmt.setString(5, values[3]);
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			int clubId=0;
			if (rs.next()) clubId = rs.getInt(1);
			
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, values[1]);
			pstmt.setInt(2, clubId);
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		disconDB();
	}
	
	public void insertStudentClub (String ...values) {
		String sql = "insert into student_club (st_id,club_id) values (?,?)";
		String sql2 = "update club set num_of_student=(select count(*) from student_club where club_id=?) where id=?;";
		
		conDB();
		try {
			pstmt = con.prepareStatement(sql);
			for (int i=0; i<2; i++)
				pstmt.setString(i+1, values[i]);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, values[1]);
			pstmt.setString(2, values[1]);
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		disconDB();
	}
	
	public void insertDepartmentProfessor(String ...values) {
		String sql = "insert into dept_professor (dept_id,prof_id) values (?,?)";
		
		conDB();
		try {
			pstmt = con.prepareStatement(sql);
			for (int i=0; i<2; i++)
				pstmt.setString(i+1, values[i]);
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		disconDB();
	}
	
	public void insertPayment(String ...values) {
		String sql = "insert into payment (st_id, pay_year, pay_semester, total_tuition, total_payment, latest_payment) values (?,?,?,?,?,?)";
		
		conDB();
		try {
			pstmt = con.prepareStatement(sql);
			for (int i=0; i<6; i++)
				pstmt.setString(i+1, values[i]);
			pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		disconDB();
	}
	
	public void updateQuery(String sql, JTextArea ta, int tableNum) {
		conDB();
		
		try {
			stmt = con.createStatement();
			int rows = stmt.executeUpdate(sql);
			if (tableNum == AppConstants.GUIDE_TABLE) {
				if (hasNoStudentForProfessor()) {
					JOptionPane.showMessageDialog(ta, "��� ������ ��� �л��� �־�� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
			}
			if (tableNum == AppConstants.DEPT_PROFESSOR_TABLE) {
				if (isDeanChangeOrDelete()) {
					JOptionPane.showMessageDialog(ta, "�а����� �Ҽ� ����� ������ �� �����ϴ�.");
					con.rollback();
					disconDB();
					return;
				}
				if (hasNoDepartmentForProfessor()) {
					JOptionPane.showMessageDialog(ta, "��� ������ �Ҽ� �а��� 1�� �̻� �����ؾ� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
			}
			if (tableNum == AppConstants.COURSE_TABLE) {
				if (hasNoCourseForDepartment()) {
					JOptionPane.showMessageDialog(ta, "��� �а��� ���� ���°� 1�� �̻� �����ؾ� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
				if (hasNoCourseForProfessor()) {
					JOptionPane.showMessageDialog(ta, "��� ������ ���� ���°� 1�� �̻� �����ؾ� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
			}
			if (tableNum == AppConstants.STUDENT_CLUB_TABLE) {
				if (isLeaderChangeOrDelete()) {
					JOptionPane.showMessageDialog(ta, "���Ƹ� ȸ���� �Ҽ� ����� ������ �� �����ϴ�.");
					con.rollback();
					disconDB();
					return;
				}
				autoCountNumOfStudentInClub();
			}
			
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		disconDB();
	}
	
	public void deleteQuery(String sql, JTextArea ta, int tableNum) {
		conDB();
		
		try {
			stmt = con.createStatement();
			int rows = stmt.executeUpdate(sql);
			
			if (tableNum == AppConstants.STUDENT_TABLE) {
				if (hasNoStudentForProfessor()) {
					JOptionPane.showMessageDialog(ta, "��� ������ �����ϴ� �л��� �־�� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
				autoCountNumOfStudentInClub();
			}
			if (tableNum == AppConstants.STUDENT_CLUB_TABLE) {
				if (isLeaderChangeOrDelete()) {
					JOptionPane.showMessageDialog(ta, "���Ƹ� ȸ���� �Ҽ� ����� ������ �� �����ϴ�.");
					con.rollback();
					disconDB();
					return;
				}
				autoCountNumOfStudentInClub();
			}
			if (tableNum == AppConstants.PROFESSOR_TABLE) {
				if (hasNoCourseForDepartment()) {
					JOptionPane.showMessageDialog(ta, "��� �а��� ���� ���°� 1�� �̻� �����ؾ� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
			}
			if (tableNum == AppConstants.GUIDE_TABLE) {
				if (hasNoStudentForProfessor()) {
					JOptionPane.showMessageDialog(ta, "��� ������ �����ϴ� �л��� �־�� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
			}
			if (tableNum == AppConstants.COURSE_TABLE) {
				if (hasNoCourseForDepartment()) {
					JOptionPane.showMessageDialog(ta, "��� �а��� ���� ���°� 1�� �̻� �����ؾ� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
				if (hasNoCourseForProfessor()) {
					JOptionPane.showMessageDialog(ta, "��� ������ ���� ���°� 1�� �̻� �����ؾ� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
			}
			if (tableNum == AppConstants.DEPARTMENT_TABLE) {
				if (hasNoDepartmentForProfessor()) {
					JOptionPane.showMessageDialog(ta, "��� ������ �Ҽ� �а��� 1�� �̻� �����ؾ� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
				if (hasNoCourseForProfessor()) {
					JOptionPane.showMessageDialog(ta, "��� ������ ���� ���°� 1�� �̻� �����ؾ� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
			}
			if (tableNum == AppConstants.DEPT_PROFESSOR_TABLE) {
				if (hasNoDepartmentForProfessor()) {
					JOptionPane.showMessageDialog(ta, "��� ������ �Ҽ� �а��� 1�� �̻� �����ؾ� �մϴ�.");
					con.rollback();
					disconDB();
					return;
				}
				if (isDeanChangeOrDelete()) {
					JOptionPane.showMessageDialog(ta, "�а����� �Ҽ� ����� ������ �� �����ϴ�.");
					con.rollback();
					disconDB();
					return;
				}
			}
			
			
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "�߸��� �Է� �Ǵ� ���� ���� ����");
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		disconDB();
	}
	
	public void autoCountNumOfStudentInClub() {
		String sql = "update club c \r\n"
				+ "inner join student_club sc \r\n"
				+ "on c.id = sc.club_id\r\n"
				+ "set c.num_of_student = (select count(*)\r\n"
				+ "						from student_club\r\n"
				+ "                        where club_id=c.id);";
		try {                                                                                                                                                                                                                                       
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isDeanChangeOrDelete() {
		String sql = "select (select count(*) from department) = (select count(*)\r\n"
				+ "from department d inner join dept_professor dp \r\n"
				+ "on dp.prof_id = d.dean_id\r\n"
				+ "where dp.dept_id = d.id)";
		int flag = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) flag = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == 1) return false;
		else return true;
	}
	
	public boolean isLeaderChangeOrDelete() {
		String sql = "select (select count(*) from club) = (select count(*) from club c inner join student_club sc on c.leader_id=sc.st_id where c.id = sc.club_id)";
		int flag = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) flag = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == 1) return false;
		else return true;
	}
	
	public boolean hasNoStudentForProfessor() {
		int num = 1;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select (select count(*) from professor) = (select count(*) from (select count(*) from guide group by prof_id) a)");
			if (rs.next()) num = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (num == 0) return true;
		return false;
	}
	
	public boolean hasNoCourseForProfessor() {
		String sql = "select (select count(*) from professpr) = (select count(*) from (select count(*) from course group by prof_id) a)";
		int num = 1;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) num = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (num == 0)
			return true;
		return false;
	}
	
	public boolean hasNoCourseForDepartment() {
		int num = 1;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select (select count(*) from department) = (select count(*) from (select count(*) from course group by dept_id) a)");
			if (rs.next()) num = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (num == 0) return true;
		return false;
	}
	
	public boolean hasNoDepartmentForProfessor() {
		int num = 1;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select (select count(*) from professor) = (select count(*) from (select count(*) from dept_professor group by prof_id) a)");
			if (rs.next()) num = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (num == 0) return true;
		return false;
	}
}
