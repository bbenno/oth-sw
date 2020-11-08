package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Student {
    @Id
    private Long matrikelNr;
    private String vorname;
    private String nachname;
    private String studiengang;

    // Needed for object construction in repository.
    public Student() {};

    public Student(Long matrikelNr, String vorname, String nachname, String studiengang) {
        this.matrikelNr = matrikelNr;
        this.vorname = vorname;
        this.nachname = nachname;
        this.studiengang = studiengang;
    }

    public Long getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(Long matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getStudiengang() {
        return studiengang;
    }

    public void setStudiengang(String studiengang) {
        this.studiengang = studiengang;
    }

    @Override
    public boolean equals(Object other) {
        if (!other.getClass().equals(this.getClass()))
            return false;
        Student otherStudent = (Student)other;
        return this.matrikelNr.equals(otherStudent.matrikelNr);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.matrikelNr);
    }
}
