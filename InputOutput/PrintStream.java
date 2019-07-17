 try{
	// Поток для вывода в файл текстовой информации на основе потока
        PrintStream ps=new PrintStream(new FileOutputStream("out.txt"));
        // вывод в файл данных
	ps.println("This is a text to file");
        ps.println(" PrintStream to file");
	// закрытие потока
        ps.close();
    }
    catch(IOException e){
       System.out.println(" Ошибка ввода-вывода "+e);
    }
    catch(Exception e){
       System.out.println("other Exception");
    }
