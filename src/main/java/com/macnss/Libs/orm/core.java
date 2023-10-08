package com.macnss.Libs.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class core {


    public static void getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(List.of(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

    }

    public static Object getObjectWithAllFields(Object obj) {
        try {
            Class<?> clazz = obj.getClass();
            Object newObject = clazz.getDeclaredConstructor().newInstance();
            List<Field> allFields = getAllFields(clazz);
            System.out.println("\n");
            for (Field field : allFields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                System.out.println(field.getName() + " " + field.getType() + " " + field.get(obj));
                field.set(obj, value);
                field.setAccessible(false);
            }
            System.out.println(obj);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'objet avec tous les champs et valeurs.", e);
        }
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        while (type != null) {
            fields.addAll(List.of(type.getDeclaredFields()));
            type = type.getSuperclass();
        }
        return fields;
    }

}
