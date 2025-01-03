package com.decathlon.tzatziki.app.model;

import com.decathlon.tzatziki.utils.IdentityOrAssignedGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "evilness")
public class Evilness {
    @Id
    @GeneratedValue(generator = "identity_or_assigned")
    @GenericGenerator(name = "identity_or_assigned", type = IdentityOrAssignedGenerator.class)
    Integer id;
    @Column(
            name = "evil"
    )
    boolean evil;
}
