package com.decathlon.tzatziki.cycle;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {
    Integer id;
    String sku;
    Integer quantity;
    Order order;
}
