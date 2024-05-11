package com.cefomar.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class StudentResponse {
    private String id;
    private String code;
    private Date createdDate;
    private Date lastUpdateDate;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String photo;
}
