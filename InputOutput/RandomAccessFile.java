   try{
        // открыть произвольный доступ к файлу
        RandomAccessFile raf=new RandomAccessFile("2.txt","rw");
	// записать данные в файл
        for(int i=0;i<10;i++){
           raf.writeInt(i+10);
        };
        // перейти в файле на позицию
        raf.seek(0);
	// прочитать данные из файла, т.к. мы находимся в начале файла
        for(int i=0;i<10;i++){
           System.out.println(raf.readInt());
        }
    }
    catch(java.io.FileNotFoundException e){
       System.out.println("File not found");
    }
    catch(Exception e){
       System.out.println("other Exception");
    }
