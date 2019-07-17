class sample_runtime{
	sample_runtime(){
		String[] s={"calc","notepad"};
		try{
			// получить Process из Runtime.getRuntime() и запустить процесс на выполнение
			Process p=Runtime.getRuntime().exec(s);
		}
		catch(IOException e){
			System.out.println("Error \n"+e.getMessage());
		}
		int memory_before=(int)Runtime.getRuntime().freeMemory();
		System.out.println("Before freeMemory:"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
		// выделить память для Integer[1000]
		Integer[] temp_array=new Integer[1000];
		for(int i=0;i<1000;i++){
			temp_array[i]=new Integer(i);
		}
		int memory_after=(int)Runtime.getRuntime().freeMemory();
		System.out.println("After Reserv Integer(1000) freeMemory:"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()+"  reserved:"+(memory_before-memory_after));
		// освободить память для Integer[1000]
		for(int i=0;i<1000;i++){
			temp_array[i]=null;
		}
		// активировать сборщик мусора
		System.gc();
		System.out.println("After System.gc() freeMemory:"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
	}
}
