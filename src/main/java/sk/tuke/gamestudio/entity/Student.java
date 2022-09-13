package sk.tuke.gamestudio.entity;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private long ident;

    @Column(nullable = false, length = 64)
    private String firstName;

    @Column(nullable = false, length = 64)
    private String lastName;


    @ManyToOne
    @JoinColumn(name="StudyGroup.ident", nullable = false)
    private StudyGroup studyGroup;

    public Student(){}

    public Student(String firstName, String lastName, StudyGroup studyGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studyGroup = studyGroup;
    }

    public long getIdent() {
        return ident;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ident=" + ident +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studyGroup=" + studyGroup +
                '}';
    }
}
