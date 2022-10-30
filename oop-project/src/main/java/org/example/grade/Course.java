package org.example.grade;

public class Course {

    private final String subject;
    private final int credit;
    private final String grade;

    public Course(String subject, int credit, String grade) {
        this.subject = subject;
        this.credit = credit;
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public int getCredit() {
        return credit;
    }

    public String getGrade() {
        return grade;
    }

    public double getGradeToNumber() { return 0.0;}

    public double multiplyCreditAndCourseGrade() {
        return credit * getGradeToNumber();
    }
}
