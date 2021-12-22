package constants;

public class AppConstants {
	public static final int ADMIN_MODE = 0, PROFESSOR_MODE = 1, STUDENT_MODE = 2;
	
	public static final int STUDENT_TABLE = 0, PROFESSOR_TABLE= 1, DEPARTMENT_TABLE=2, COURSE_TABLE=3, CLUB_TABLE=4,
					 PAYMENT_TABLE = 5, GUIDE_TABLE = 6, DEPT_PROFESSOR_TABLE = 7, COURSE_HISTORY_TABLE = 8, STUDENT_CLUB_TABLE = 9;
	
	public static final int admin_init=0, admin_all=1, admin_insert=2, admin_update=3, admin_delete=4;
	public static final int professor_course=0, professor_students=1, professor_department=2, professor_timetable=3;
	public static final int student_course=0, student_timetable=1, student_club=2, student_gradecard=3;
	
	public static final String[] mode = {"admin", "professor", "student"};
	public static final String[] table = {"학생", "교수", "학과", "강좌", "동아리", "납부", "지도", "교수학과", "수강내역", "학생동아리"};
	public static final String[] tableEng = {"student", "professor", "department", "course", "club", "payment", "guide", "dept_professor", "course_history", "student_club"};
	public static final String[] adminButton = {"초기화", "전체보기", "등록", "수정", "삭제"};
	public static final String[] professorButton = {"수강기록", "지도학생", "소속", "강의시간표"};
	public static final String[] studentButton = {"수강기록", "강의시간표", "동아리", "성적표"};
}
