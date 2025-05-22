package com.bank.antifraud.AOP;

public class AuditUtils {
    private static final String IMP = "ServiceImpl";
    private static final String SERVICE = "Service";
    private static final String DTO_SUFFIX = "Dto";

    public static String getEntityType(Class<?> dtoClass) {
        AuditEntity annotation = dtoClass.getAnnotation(AuditEntity.class);
        return annotation != null ? annotation.value()
                : dtoClass.getSimpleName().replace(DTO_SUFFIX, "");
    }

    public static String getEntityTypeFromService(Class<?> serviceClass) {
        return serviceClass.getSimpleName()
                .replace(IMP, "")
                .replace(SERVICE, "");
    }
}
