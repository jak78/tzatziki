package com.decathlon.tzatziki.utils;

import com.fasterxml.jackson.databind.Module;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.decathlon.tzatziki.utils.Unchecked.unchecked;

@SuppressWarnings("unchecked")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PersistenceUtil {
    private static final Map<String, Class<?>> persistenceClassByName = Collections.synchronizedMap(new HashMap<>());

    public static Module getMapperModule() {
        Class<?> tableClass = getPersistenceClass("Table");
        Class<Module> mapperModuleClass;

        // Detect Hibernate version
        boolean isHibernate6 = isHibernate6Available();

        if (tableClass.getPackageName().equals("javax.persistence")) {
            // javax.persistence + Hibernate 5
            mapperModuleClass = unchecked(() -> (Class<Module>) Class.forName("com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module"));
        } else if (isHibernate6) {
            // jakarta.persistence + Hibernate 6 (Spring Boot 3.4+ / 4.x)
            mapperModuleClass = unchecked(() -> (Class<Module>) Class.forName("com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module"));
        } else {
            // jakarta.persistence + Hibernate 5 (Spring Boot 3.0-3.3)
            mapperModuleClass = unchecked(() -> (Class<Module>) Class.forName("com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule"));
        }

        return unchecked(() -> mapperModuleClass.getConstructor().newInstance());
    }

    private static boolean isHibernate6Available() {
        try {
            // Try to load a Hibernate 6 specific class
            Class.forName("org.hibernate.query.criteria.JpaCriteriaQuery");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static <T> Class<T> getPersistenceClass(String className) {
        return (Class<T>) persistenceClassByName.computeIfAbsent(
                className,
                clazz -> {
                    Class<Object> foundClass;
                    try {
                        foundClass = (Class<Object>) Class.forName("javax.persistence." + className);
                    } catch (ClassNotFoundException e) {
                        foundClass = unchecked(() -> (Class<Object>) Class.forName("jakarta.persistence." + className));
                    }
                    return foundClass;
                }
        );
    }

}
