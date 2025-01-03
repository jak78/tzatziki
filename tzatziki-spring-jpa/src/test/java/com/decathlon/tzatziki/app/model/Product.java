package com.decathlon.tzatziki.app.model;

import com.decathlon.tzatziki.utils.IdentityOrAssignedGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "products", schema = "store")
public class Product {

    @Id
    @GeneratedValue(generator = "identity_or_assigned")
    @GenericGenerator(name = "identity_or_assigned", type = IdentityOrAssignedGenerator.class)
    Integer id;

    @Column(name = "name")
    String name;
}
