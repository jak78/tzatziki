package com.decathlon.tzatziki.app.model;

import com.decathlon.tzatziki.utils.IdentityOrAssignedGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(generator = "identity_or_assigned")
    @GenericGenerator(name = "identity_or_assigned", type = IdentityOrAssignedGenerator.class)
    Integer id;

    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "group")
    List<User> users;
}
