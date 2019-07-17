        InetAddress IP;

        InetAddress[] IP_all;

        URL source;

        // получить адрес LocalHost
        IP=InetAddress.getLocalHost();
        System.out.println("IP:"+IP);

        // получить IP адрес из имени узла
        IP=InetAddress.getByName("www.ya.ru");
        System.out.println("IP:"+IP);

        // получить все IP по имени
       IP_all=InetAddress.getAllByName("mail");
        for(int i=0;i<IP_all.length;i++){
       System.out.println(IP_all[i]);
       }
