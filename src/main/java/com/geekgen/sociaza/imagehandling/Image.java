package com.geekgen.sociaza.imagehandling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekgen.sociaza.registration.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class Image implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9183357862673673728L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @Column
    private String fileName;

    @Column
    private String fileType;

    @Column
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;

    @JsonIgnore
    @OneToOne(mappedBy = "image")
    private User user;

    public Image() {

    }

    public Image(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
