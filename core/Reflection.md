{
        StringWriter writer = new StringWriter();
        PrintStream out = new PrintStream(new WriterOutputStream(writer));

        // when
        setStaticField(System.class, "out", out);
}

  private void setStaticField(Class<?> clazz, String fieldName, Object value) {
        try{
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField( "modifiers" );
            modifiersField.setAccessible( true );
            modifiersField.setInt( field, field.getModifiers() & ~Modifier.FINAL );

            field.set(clazz, value);
        }catch(NoSuchFieldException ex){
            throw new RuntimeException("can't set value");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("can't set value", e);
        }
    }
