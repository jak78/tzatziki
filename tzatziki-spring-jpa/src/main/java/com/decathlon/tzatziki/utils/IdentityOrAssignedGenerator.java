package com.decathlon.tzatziki.utils;

import org.hibernate.id.IdentityGenerator;

public class IdentityOrAssignedGenerator extends IdentityGenerator {
    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }
}
