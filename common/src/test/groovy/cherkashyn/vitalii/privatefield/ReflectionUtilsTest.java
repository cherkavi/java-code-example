package cherkashyn.vitalii.privatefield;


import org.junit.Assert;
import org.junit.Test;

public class ReflectionUtilsTest  {

    @Test
    public void setFieldAndReadThem() {
        // given
        int value1 = 5;
        String value2 = "hello";
        int value3 = 12;

        // when
        ChildClass child = new ChildClass(value1, value2, value3);
        ReflectionUtils.setField(child, "param3", value3);

        // then
        Assert.assertEquals(value1, child.getInt());
        Assert.assertEquals(value2, child.getString());
        Assert.assertEquals(value2, child.getString());
        Assert.assertEquals(value3, (int)ReflectionUtils.getField(child, "param3"));
    }

}
