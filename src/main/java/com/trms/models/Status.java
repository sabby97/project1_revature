package com.trms.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "statuses")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private int statusId;

    @Column(name = "status_msg")
    private String statusMsg;

    public Status() {
    }

    public Status(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return getStatusId() == status.getStatusId() && Objects.equals(getStatusMsg(), status.getStatusMsg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatusId(), getStatusMsg());
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusId=" + statusId +
                ", statusMsg='" + statusMsg + '\'' +
                '}';
    }
}
