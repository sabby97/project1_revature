package com.trms.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "requested_from_id", referencedColumnName = "employee_id")
    private Employee requestedFrom;

    @Column(name = "filename")
    private String filename;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "file")
    private byte[] file;

    public File() {
    }

    public File(Event event, Employee requestedFrom, String filename, byte[] file) {
        this.event = event;
        this.requestedFrom = requestedFrom;
        this.filename = filename;
        this.file = file;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Employee getRequestedFrom() {
        return requestedFrom;
    }

    public void setRequestedFrom(Employee requestedFrom) {
        this.requestedFrom = requestedFrom;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "File{" +
                "event=" + event +
                ", requestedFrom=" + requestedFrom +
                ", filename='" + filename + '\'' +
                '}';
    }
}
