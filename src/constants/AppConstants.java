package constants;

public class AppConstants {
	public static final int ADMIN_MODE = 0, PROFESSOR_MODE = 1, STUDENT_MODE = 2;
	
	public static final int STUDENT_TABLE = 0, PROFESSOR_TABLE= 1, DEPARTMENT_TABLE=2, COURSE_TABLE=3, CLUB_TABLE=4,
					 PAYMENT_TABLE = 5, GUIDE_TABLE = 6, DEPT_PROFESSOR_TABLE = 7, COURSE_HISTORY_TABLE = 8, STUDENT_CLUB_TABLE = 9;
	
	public static final int admin_init=0, admin_all=1, admin_insert=2, admin_update=3, admin_delete=4;
	public static final int professor_course=0, professor_students=1, professor_department=2, professor_timetable=3;
	public static final int student_course=0, student_timetable=1, student_club=2, student_gradecard=3;
	
	public static final String[] mode = {"admin", "professor", "student"};
	public static final String[] table = {"�л�", "����", "�а�", "����", "���Ƹ�", "����", "����", "�����а�", "��������", "�л����Ƹ�"};
	public static final String[] tableEng = {"student", "professor", "department", "course", "club", "payment", "guide", "dept_professor", "course_history", "student_club"};
	public static final String[] adminButton = {"�ʱ�ȭ", "��ü����", "���", "����", "����"};
	public static final String[] professorButton = {"�������", "�����л�", "�Ҽ�", "���ǽð�ǥ"};
	public static final String[] studentButton = {"�������", "���ǽð�ǥ", "���Ƹ�", "����ǥ"};
}
