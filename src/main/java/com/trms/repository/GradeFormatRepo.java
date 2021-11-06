package com.trms.repository;

import com.trms.models.GradeFormat;

import java.util.List;

public interface GradeFormatRepo {

    public GradeFormat addGradeFormat(GradeFormat g);

    public List<GradeFormat> getAllGradeFormats();

    public GradeFormat getGradeFormat(int gradeId);

    public GradeFormat updateGradeFormat(GradeFormat change);

    public GradeFormat deleteGradeFormat(int gradeId);
}
