       // установить адрес
       source=new URL("http://www.ya.ru");

       // показать страницу в текущем окне
       getAppletContext().showDocument(source,"_self");

       // создать входящий поток на основе source.openStream()
       InputStreamReader isr=new InputStreamReader(source.openStream());

       // буфер для входящего потока
       BufferedReader br=new BufferedReader(isr);

       // прочитать все из буфера
       String line=br.readLine();
       while(line!=null){
          System.out.println(line);
          line=br.readLine();
       }
