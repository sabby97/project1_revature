package com.trms.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "department_heads")
public class DepartmentHead {
     @Id
     @Column(name = "department_id")
     private int DepartmentId;

     @Column(name = "head_id")
     private int headId;

     public DepartmentHead() {
     }

     public DepartmentHead(int departmentId, int headId) {
          DepartmentId = departmentId;
          this.headId = headId;
     }

     public int getDepartmentId() {
          return DepartmentId;
     }

     public void setDepartmentId(int departmentId) {
          DepartmentId = departmentId;
     }

     public int getHeadId() {
          return headId;
     }

     public void setHeadId(int headId) {
          this.headId = headId;
     }

     @Override
     public String toString() {
          return "DepartmentHead{" +
                  "DepartmentId=" + DepartmentId +
                  ", headId=" + headId +
                  '}';
     }
}
