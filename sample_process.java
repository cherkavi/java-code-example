class sample_process{
	sample_process(){
		// получаем объект текущего времени исполнения
		Runtime r=Runtime.getRuntime();
		try{
			System.out.println("run program");
			// создать Process и запустить его на выполнение
			Process p=r.exec("calc");
			// разрушить процесс
			//p.destroy();
			// ожидание окончания процесса
			p.waitFor();
			System.out.println("end run program");
		}
		catch(Exception e){
			System.out.println("Error in run program \n"+e.getMessage());
		};
	}
}
