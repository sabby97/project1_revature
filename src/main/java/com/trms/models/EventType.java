package com.trms.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "event_types")
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int typeId;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "type_percentage", columnDefinition = "numeric(5,2)")
    private double typePercentage;

    public EventType() {
    }

    public EventType(String typeName,double typePercentage) {
        this.typeName = typeName;
        this.typePercentage = typePercentage;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getTypePercentage() {
        return typePercentage;
    }

    public void setTypePercentage(double typePercentage) {
        this.typePercentage = typePercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventType eventType = (EventType) o;
        return getTypeId() == eventType.getTypeId() && Double.compare(eventType.getTypePercentage(), getTypePercentage()) == 0 && Objects.equals(getTypeName(), eventType.getTypeName());
    }

    @Override
    public String toString() {
        return "EventType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", typePercentage=" + typePercentage +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeId(), getTypeName(), getTypePercentage());
    }
}
