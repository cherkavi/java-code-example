package cherkashyn.vitalii.privatefield;

import java.lang.reflect.Field;

public class ReflectionUtils {

    /**
     * write data ( set ) into ( private ) field of Superclass object
     * @param target
     * @param fieldName
     * @param value
     */
    public static void setSuperField(Object target, String fieldName, Object value){
        try{
            Field field = target.getClass().getSuperclass().getDeclaredField(fieldName);
            setField(field, target, value);
        }catch(NoSuchFieldException ex){
        }
    }

    /**
     * write data ( set ) into ( private ) field of class object
     * @param target
     * @param fieldName
     * @param value
     */
    public static void setField(Object target, String fieldName, Object value){
        try{
            Field field = target.getClass().getDeclaredField(fieldName);
            setField(field, target, value);
        }catch(NoSuchFieldException ex){
        }
    }

    private static void setField(Field field, Object target, Object value){
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
        }
    }


    /**
     * read data from ( private ) field of superclass ( parent class )
     * @param target
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> T getSuperField(Object target, String fieldName){
        try {
            Field field = target.getClass().getSuperclass().getDeclaredField(fieldName);
            return (T)getField(field, target);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * read data from (private) field of class
     * @param target
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> T getField(Object target, String fieldName){
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            return (T)getField(field, target);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private static <T> T getField(Field field, Object target){
        try {
            field.setAccessible(true);
            return (T)field.get(target);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

}
