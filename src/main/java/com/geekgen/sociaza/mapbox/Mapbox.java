package com.geekgen.sociaza.mapbox;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.geekgen.sociaza.audit.AuditModel;
import com.geekgen.sociaza.comments.Comment;
import com.geekgen.sociaza.registration.User;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "mapbox")
public class  Mapbox extends AuditModel {
    /**
     *
     */
    private static final long serialVersionUID = 5361989745338984712L;

    enum Status {
        ACTIVE, FULL, ARCHIVED
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serviceType")
    private String serviceType;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @Lob
    @Type(type = "text")
    private String description;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "place")
    private String place;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "attendeesLimit")
    private int attendeesLimit;

    @ManyToMany(mappedBy = "bookedServices")
    private List<User> attendees = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private User user;
}
