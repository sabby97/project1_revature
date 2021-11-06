package com.trms.models;

import javax.persistence.*;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int eventId;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "event_date", columnDefinition = "bigint")
    private long eventDate;

    @Column(name = "event_location")
    private String eventLocation;

    @Column(name = "event_description")
    private String eventDescription;

    @Column(name = "event_cost", columnDefinition = "numeric(8,2)")
    private double eventCost;

    @OneToOne
    @JoinColumn(name = "event_type_id", referencedColumnName = "type_id")
    private EventType eventType;

    @Column(name = "event_justification")
    private String eventJustification;

    @OneToOne
    @JoinColumn(name = "grade_format_id", referencedColumnName = "grade_id")
    private GradeFormat gradeFormat;

    @Column(name = "event_grade")
    private String eventGrade;

    @OneToOne
    @JoinColumn(name = "event_curr_status_id", referencedColumnName = "status_id")
    private Status eventCurrStatus;

    @OneToOne
    @JoinColumn(name = "event_last_status_id", referencedColumnName = "status_id")
    private Status eventLastStatus;

    @Column(name = "admin_msg")
    private String adminMsg;

    @Column(name = "event_submission_date", columnDefinition = "bigint")
    private long eventSubmissionDate;

    public Event() {
    }

    public Event(int eventId, Employee employeeId, String employeeName, long eventDate, String eventLocation, String eventDescription, double eventCost, EventType eventType, String eventJustification, GradeFormat gradeFormat, String eventGrade, Status eventCurrStatus, Status eventLastStatus, String adminMsg, long eventSubmissionDate) {
        this.eventId = eventId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
        this.eventCost = eventCost;
        this.eventType = eventType;
        this.eventJustification = eventJustification;
        this.gradeFormat = gradeFormat;
        this.eventGrade = eventGrade;
        this.eventCurrStatus = eventCurrStatus;
        this.eventLastStatus = eventLastStatus;
        this.adminMsg = adminMsg;
        this.eventSubmissionDate = eventSubmissionDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public double getEventCost() {
        return eventCost;
    }

    public void setEventCost(double eventCost) {
        this.eventCost = eventCost;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getEventJustification() {
        return eventJustification;
    }

    public void setEventJustification(String eventJustification) {
        this.eventJustification = eventJustification;
    }

    public GradeFormat getGradeFormat() {
        return gradeFormat;
    }

    public void setGradeFormat(GradeFormat gradeFormat) {
        this.gradeFormat = gradeFormat;
    }

    public String getEventGrade() {
        return eventGrade;
    }

    public void setEventGrade(String eventGrade) {
        this.eventGrade = eventGrade;
    }

    public Status getEventCurrStatus() {
        return eventCurrStatus;
    }

    public void setEventCurrStatus(Status eventCurrStatus) {
        this.eventCurrStatus = eventCurrStatus;
    }

    public Status getEventLastStatus() {
        return eventLastStatus;
    }

    public void setEventLastStatus(Status eventLastStatus) {
        this.eventLastStatus = eventLastStatus;
    }

    public String getAdminMsg() {
        return adminMsg;
    }

    public void setAdminMsg(String adminMsg) {
        this.adminMsg = adminMsg;
    }

    public double getEventSubmissionDate() {
        return eventSubmissionDate;
    }

    public void setEventSubmissionDate(long eventSubmissionDate) {
        this.eventSubmissionDate = eventSubmissionDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", eventDate=" + eventDate +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventCost=" + eventCost +
                ", eventType=" + eventType +
                ", eventJustification='" + eventJustification + '\'' +
                ", gradeFormat=" + gradeFormat +
                ", eventGrade='" + eventGrade + '\'' +
                ", eventCurrStatus=" + eventCurrStatus +
                ", eventLastStatus=" + eventLastStatus +
                ", adminMsg='" + adminMsg + '\'' +
                ", eventSubmissionDate=" + eventSubmissionDate +
                '}';
    }
}
