package com.vitaliy.rs485_module;
/**
 * настроить параметры конфигурации модуля
 */
public class AANNTTCCFF extends Command{
	private String value_AA;
        private String value_NN;
        private String value_TT;
        private String value_CC;
        private String value_FF;
        /**
	 * конструктор, который инициирует данные в объекте,
	 * при неудачной инициализации конструктор выбрасывает исключение 
	 * @param AA - адрес настраиваемого модуля
	 * @param NN - новый адрес настраиваемого модуля
	 * @param TT - тип входа настраиваемого модуля
	 * @param CC - новое значение скорости передачи настраиваемого модуля
	 * @param FF - новый формат данных настраиваемого модуля
	 */
	public AANNTTCCFF(String _AA,String _NN, String _TT, String _CC, String _FF) throws Exception{
		if(utility.isHEX_byte(_AA)
                 &&utility.isHEX_byte(_NN)
                 &&TT.isTTkod(_TT)
                 &&CC.isCCkod(_CC)
                 &&FF.isFFkod(_FF)){
                    this.value_AA=_AA;
                    this.value_NN=_NN;
                    this.value_TT=_TT;
                    this.value_CC=_CC;
                    this.value_FF=_FF;
                }
                else{
                    throw new Exception ("Create error - parameter's not valid");
                }
	}
	/**
	 * конструктор без параметров
	 */
	public AANNTTCCFF(){
            this.value_AA="00";
            this.value_NN="00";
            this.value_TT=TT.getDefaultKod();
            this.value_CC=CC.getDefaultKod();
            this.value_FF=FF.getDefaultKod();
	}
        /**
         * очистка параметров объекта
         */
        public void clear(){
            this.value_AA="00";
            this.value_NN="00";
            this.value_TT=TT.getDefaultKod();
            this.value_CC=CC.getDefaultKod();
            this.value_FF=FF.getDefaultKod();
        }
        /**
         * установить AA - адрес настраиваемого модуля
         */
        public boolean set_AA(String _AA){
            boolean result=false;
            if(utility.isHEX_byte(_AA)){
                this.value_AA=new String(_AA);
                result=true;
            }
            return result;
        }
        /**
         * установить NN - новый адрес настраиваемого модуля
         */
        public boolean set_NN(String _NN){
            boolean result=false;
            if(utility.isHEX_byte(_NN)){
                this.value_NN=new String(_NN);
                result=true;
            }
            return result;
        }
        /**
         * установить тип входа настраиваемого модуля
         */
        public boolean set_TT(String _TT){
            boolean result=false;
            if(TT.isTTkod(_TT)){
                this.value_TT=new String(_TT);
                result=true;
            }
            return result;
        }
        /**
         * установить CC - новое значение скорости передачи настраиваемого модуля
         */
        public boolean set_CC(String _CC){
            boolean result=false;
            if(CC.isCCkod(_CC)){
                this.value_CC=new String(_CC);
                result=true;
            }
            return result;
        }
        /**
         * установить FF - новый формат данных настраиваемого модуля
         */
        public boolean set_FF(String _FF){
            boolean result=false;
            if(FF.isFFkod(_FF)){
                this.value_FF=new String(_FF);
                result=true;
            }
            return result;
        }
       
        /**
         * Получить команду для отправки данных
         */
        public String get_command(){
            return "%"+this.value_AA+this.value_NN+this.value_TT+this.value_CC+this.value_FF;
        }
        /**
         * Проверить валидность ответа
         */
        public boolean response_is_valid(String response){
            boolean result=false;
            if(response.length()>=3){
                if(response.charAt(0)=='!'){
                    result=true;
                }
            }
            else{
                // нехватка символов в ответе - или модуль не ответил - ошибка передачи
            }
            return result;
        }
        /**
         * 
         */
        public String get_name(){
        	return "AANNTTCCFF";
        }
}
