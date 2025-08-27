package com.example.kingverse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("character_connection")
public class CharacterConnection {
    @Id
    @Column("connection_id")
    private Integer connectionId;

    @Column("subject_id")
    private Integer subjectId;

    @Column("object_id")
    private Integer objectId;

    @Column("connection_type")
    private String connectionType; // kills, helps_catch, etc.

    @Column("book_context_id")
    private Integer bookContextId; // nullable

    private String note; // nullable

    public CharacterConnection() {}

    public CharacterConnection(Integer connectionId, Integer subjectId, Integer objectId,
                               String connectionType, Integer bookContextId, String note) {
        this.connectionId = connectionId;
        this.subjectId = subjectId;
        this.objectId = objectId;
        this.connectionType = connectionType;
        this.bookContextId = bookContextId;
        this.note = note;
    }

    public Integer getConnectionId() { return connectionId; }
    public void setConnectionId(Integer connectionId) { this.connectionId = connectionId; }

    public Integer getSubjectId() { return subjectId; }
    public void setSubjectId(Integer subjectId) { this.subjectId = subjectId; }

    public Integer getObjectId() { return objectId; }
    public void setObjectId(Integer objectId) { this.objectId = objectId; }

    public String getConnectionType() { return connectionType; }
    public void setConnectionType(String connectionType) { this.connectionType = connectionType; }

    public Integer getBookContextId() { return bookContextId; }
    public void setBookContextId(Integer bookContextId) { this.bookContextId = bookContextId; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() {
        return "CharacterConnection{" +
                "connectionId=" + connectionId +
                ", subjectId=" + subjectId +
                ", objectId=" + objectId +
                ", connectionType='" + connectionType + '\'' +
                ", bookContextId=" + bookContextId +
                ", note='" + note + '\'' +
                '}';
    }
}
