package com.example.kingverse.model;

public class ConnectionView {
    private Integer connectionId;
    private Integer subjectId;
    private Integer objectId;
    private String  objectName;
    private String  connectionType;
    private String  bookTitle;
    private String  note;

    public ConnectionView() { }

    public Integer getConnectionId() { return connectionId; }
    public void setConnectionId(Integer connectionId) { this.connectionId = connectionId; }

    public Integer getSubjectId() { return subjectId; }
    public void setSubjectId(Integer subjectId) { this.subjectId = subjectId; }

    public Integer getObjectId() { return objectId; }
    public void setObjectId(Integer objectId) { this.objectId = objectId; }

    public String getObjectName() { return objectName; }
    public void setObjectName(String objectName) { this.objectName = objectName; }

    public String getConnectionType() { return connectionType; }
    public void setConnectionType(String connectionType) { this.connectionType = connectionType; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
