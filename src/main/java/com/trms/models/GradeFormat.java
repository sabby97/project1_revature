package com.trms.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "grade_formats")
public class GradeFormat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;

    @Column(name = "grade_type")
    private String gradeType;

    @Column(name = "grade_cutoff")
    private String gradeCutoff;

    public GradeFormat() {
    }

    public GradeFormat(String gradeType, String gradeCutoff) {
        this.gradeType = gradeType;
        this.gradeCutoff = gradeCutoff;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getGradeCutoff() {
        return gradeCutoff;
    }

    public void setGradeCutoff(String gradeCutoff) {
        this.gradeCutoff = gradeCutoff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeFormat that = (GradeFormat) o;
        return getGradeId() == that.getGradeId() && Objects.equals(getGradeType(), that.getGradeType()) && Objects.equals(getGradeCutoff(), that.getGradeCutoff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGradeId(), getGradeType(), getGradeCutoff());
    }

    @Override
    public String toString() {
        return "GradeFormat{" +
                "gradeId=" + gradeId +
                ", gradeType='" + gradeType + '\'' +
                ", gradeCutoff='" + gradeCutoff + '\'' +
                '}';
    }
}
