package com.decathlon.tzatziki.app.model;

import com.decathlon.tzatziki.utils.IdentityOrAssignedGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;

import static jakarta.persistence.InheritanceType.JOINED;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
@Inheritance(strategy = JOINED)
public class User {

    @Id
    @GeneratedValue(generator = "identity_or_assigned")
    @GenericGenerator(name = "identity_or_assigned", type = IdentityOrAssignedGenerator.class)
    Integer id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "birth_date")
    Instant birthDate;

    @Column(name = "updated_at")
    Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "group_id")
    Group group;
}
