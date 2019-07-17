package cherkashyn.vitalii.privatefield;

public class ChildClass extends ParentClass{

    private int param3;

    public ChildClass(int paramOne, String paramTwo, int paramThree){
        ReflectionUtils.setSuperField(this, "fieldInt", paramOne);
        ReflectionUtils.setSuperField(this, "fieldString", paramTwo);
    }


    public String getString(){
        return ReflectionUtils.getSuperField(this, "fieldString");
    }

    public int getInt(){
        return ReflectionUtils.getSuperField(this, "fieldInt");
    }


    public int getCurrentInt(){
        return this.param3;
    }

}
