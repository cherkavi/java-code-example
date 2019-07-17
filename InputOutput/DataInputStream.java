    try{
        // блок для записи форматированных данных в файл
	FileOutputStream os= new FileOutputStream("2.txt");
        DataOutputStream dos=new DataOutputStream(os);
        dos.writeInt(10);dos.writeFloat(1.25f);dos.writeBoolean(true);
        dos.close();
	os.close();

        // блок для чтения форматированных данных из потока ввода-вывода
	FileInputStream is= new FileInputStream("2.txt");
        DataInputStream dis=new DataInputStream(is);
        System.out.println("Integer:"+dis.readInt());
        System.out.println("Float:"+dis.readFloat());
        System.out.println("Boolean:"+dis.readBoolean());
        dis.close();
        is.close();

    }
    catch(java.io.FileNotFoundException e){
       System.out.println("File not found");
    }
    catch(Exception e){
       System.out.println("other Exception");
    }
