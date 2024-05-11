package com.cefomar.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter @Setter
@Builder
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Student {
    @Id
    private String id;
    private String code;
    private Date createdDate;
    private Date lastUpdateDate;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String photo;
}
