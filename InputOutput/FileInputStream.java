    try{
        // запись в файл
	//FileOutputStream os= new FileOutputStream("2.txt");
        //os.write(48);os.write(48);os.write(49);os.close();
        
	// чтение из файла
	FileInputStream is= new FileInputStream("2.txt");
        while((b=is.read())!=-1){
        System.out.print(b);}
    }
    // поймать Exception, если файл не найден
    catch(java.io.FileNotFoundException e){
       System.out.println("File not found");
    }
    // поймать другой Exception
    catch(IOException e){
       System.out.println("other Input Output Exception");
    }
    catch(Exception e){
       System.out.println("other Exception");
    }

