package com.blog.entities;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data       //For getter & setters.---------------------------------------------------------------------------
@Entity    // Hibernate JPA Annotation -----------------------------------------------------------------------
@Table(name = "users",uniqueConstraints = {     // Table Creation---------------------------------------------
        @UniqueConstraint(columnNames = {"username"}), // To make username Unique ----------------------------
        @UniqueConstraint(columnNames = {"email"})     // To make email Unique -------------------------------
})
public class User {            //Entity Class ----------------------------------------------------------------
    @Id                       // helps to map  entity class variable of Primary Key---------------------------
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //helps to auto increment the value of data in DB.---
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",      // Use to JOIN 2 Tables---------------------------------------------
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;      // Set or list use only for MANY TO MANY Mapping---------------------------
                                 //ROLE is Unique here.------------------------------------------------------
            // LIST can Have Duplicate value but SET Can't have any duplicate values.------------------------
}

