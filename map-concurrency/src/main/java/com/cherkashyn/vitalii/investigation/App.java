package com.cherkashyn.vitalii.investigation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( " - begin - " );
        MapHolder mapHolder=new MapHolder();
        mapHolder.getValue().add("3");
        mapHolder.getValue().add("4");
        mapHolder.getValue().add("5");
        mapHolder.getValue().add("6");
        mapHolder.getValue().add("7");

        MapFiller mapFiller=new MapFiller(mapHolder.getValue());
        
        mapHolder.start();
        mapFiller.start();
        System.out.println( " -  end  - " );
    }
}
