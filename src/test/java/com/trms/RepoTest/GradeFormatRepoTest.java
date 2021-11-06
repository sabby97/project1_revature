package com.trms.RepoTest;

import com.trms.models.GradeFormat;
import com.trms.repository.GradeFormatRepo;
import com.trms.repository.GradeFormatRepoImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GradeFormatRepoTest {
    GradeFormatRepo gfr = new GradeFormatRepoImpl();

    @Test
    public void getAllGradeFormats(){
        List<GradeFormat> test = gfr.getAllGradeFormats();
        System.out.println(test);
        assertNotNull(test);
    }

    @Test
    public void getGradeFormats(){
        GradeFormat test1 = gfr.getGradeFormat(1);
        GradeFormat test2 = gfr.getGradeFormat(50);
        System.out.println(test1);
        System.out.println(test2);
        assertEquals(1,test1.getGradeId());
        assertNotNull(test1);
        assertNull(test2);
    }
}
