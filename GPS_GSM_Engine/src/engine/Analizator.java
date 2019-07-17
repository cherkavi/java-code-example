package engine;


/** функци€ данного класса - анализировать StringBuffer, измен€€ его*/
public class Analizator {
	private void error(Object information){
		System.out.print("Analizator ");
		System.out.print(" ERROR ");
		System.out.println(information);
	}

	String time;
	String latitude;
	String latitudeIndicator;
	String longitude;
	String longitudeIndicator;
	String position;
	
	String preambula="$GPGGA";
	String postambula="\n";
	
	public Analizator(){
		
	}
	public Analizator(String preambula, String postambula ){
		this.preambula=preambula;
		this.postambula=postambula;
	}

	private void parseString(String value){
		try{
			// поиск первой зап€той - указывающей на врем€ 
			int tempComma=value.indexOf(',');
			if(tempComma>0){
				int endOfTime=value.indexOf(',',tempComma+1);
				if(endOfTime>0){
					this.setTime(value.substring(tempComma+1,endOfTime));
					int endOfLatitude=value.indexOf(',',endOfTime+1);
					if(endOfLatitude>0){
						this.setLatitude(value.substring(endOfTime+1,endOfLatitude));
						int endOfLatitudeIndicator=value.indexOf(',',endOfLatitude+1);
						if(endOfLatitudeIndicator>0){
							this.setLatitudeIndicator(value.substring(endOfLatitude+1,endOfLatitudeIndicator));
							int endOfLongitude=value.indexOf(',',endOfLatitudeIndicator+1);
							if(endOfLongitude>0){
								this.setLongitude(value.substring(endOfLatitudeIndicator+1,endOfLongitude));
								int endOfLongitudeIndicator=value.indexOf(',',endOfLongitude+1);
								if(endOfLongitudeIndicator>0){
									this.setLongitudeIndicator(value.substring(endOfLongitude+1,endOfLongitudeIndicator));
									int endPositionIndicator=value.indexOf(',',endOfLongitudeIndicator+1);
									if(endPositionIndicator>0){
										this.setPosition(value.substring(endOfLongitudeIndicator+1,endPositionIndicator));
									}else{
										error("parseString error in package7:"+value);
									}
								}else{
									error("parseString error in package6:"+value);
								}
							}else{
								error("parseString error in package5:"+value);
							}
						}else{
							error("parseString error in package4:"+value);
						}
					}else{
						error("parseString error in package3:"+value);
					}
				}else{
					error("parseString error in package2:"+value);
				}
			}else{
				error("parseString error in package1:"+value); 
			}
			System.out.println("Latitude:"+this.latitude+"    LatitudeIndicator:"+this.latitudeIndicator+"    Longitude:"+this.longitude+"   LongitudeIndicator:"+this.longitudeIndicator+"   Position:"+this.position);
		}catch(Exception ex){
			error("#parseString Exception:"+ex.getMessage()+"    Value:"+value);
		}
	}
	
	/** */
	public StringBuffer analize(StringBuffer bufferValue){
        String buffer=bufferValue.toString();
        int preambulaTemp=buffer.indexOf(this.preambula);
        int preambulaLastIndex=preambulaTemp;
        while((preambulaTemp=buffer.indexOf(this.preambula,preambulaTemp+1))>=0){
            preambulaLastIndex=preambulaTemp;
        }
        if(preambulaLastIndex==(-1)){
            preambulaLastIndex=preambulaTemp;
        }
		if(preambulaLastIndex>=0){
			//System.out.println("0");
			// последн€€ позици€ найдена - поиск postambula, после преамбулы
			int postambulaLastIndex=buffer.indexOf(this.postambula,preambulaLastIndex);
			if(postambulaLastIndex>0){
				//System.out.println("1");
				// постамбула у последнего символа найдена
				String forAnalize=buffer.substring(preambulaLastIndex,postambulaLastIndex);
				buffer=buffer.substring(postambulaLastIndex+this.postambula.length());
				this.parseString(forAnalize);
			}else{
				//System.out.println("2");
				// постамбула у последнего символа не найдена - найти предпоследний символ преамбулы
				int preambulaIndex=buffer.indexOf(this.preambula);
				int preambulaIndexBefore=(-1);
				while(preambulaIndex!=preambulaLastIndex){
					preambulaIndexBefore=preambulaIndex;
					preambulaIndex=buffer.indexOf(this.preambula,preambulaIndex+1);
				}
				if(preambulaIndexBefore>=0){
					//System.out.println("2.1");
					int postambulaIndex=buffer.indexOf(this.postambula,preambulaIndexBefore);
					if(postambulaIndex>0){
						//System.out.println("2.1.1");
						String forAnalize=buffer.substring(preambulaIndexBefore, postambulaIndex);
						buffer=buffer.substring(postambulaIndex+this.postambula.length());
						this.parseString(forAnalize);
					}else{
						//System.out.println("2.1.2");
						// ошибка данных при получении пакета - удалить
						buffer=buffer.substring(preambulaIndexBefore);
					}
				}else{
					//System.out.println("2.2");
					// нет терминального символа дл€ предыдущего-последнего символа преамбулы
				}
			}
		}else{
			//System.out.println("00");
			// data is not found - removeAll;
			buffer="";
		}
        return new StringBuffer(buffer);
	}
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		if(this.time!=null){
			synchronized(this.time){
				this.time = time;
			}
		}else{
			this.time=time;
		}
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		if(this.latitude!=null){
			synchronized(this.latitude){
				this.latitude = latitude;
			}
		}else{
			this.latitude=latitude;
		}
	}
	public String getLatitudeIndicator() {
		return latitudeIndicator;
	}
	public void setLatitudeIndicator(String latitudeIndicator) {
		if(this.latitudeIndicator!=null){
			synchronized(this.latitudeIndicator){
				this.latitudeIndicator = latitudeIndicator;
			}
		}else{
			this.latitudeIndicator=latitudeIndicator;
		}
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		if(this.longitude!=null){
			synchronized(this.longitude){
				this.longitude = longitude;
			}
		}else{
			this.longitude=longitude;
		}
	}
	public String getLongitudeIndicator() {
		return longitudeIndicator;
	}
	public void setLongitudeIndicator(String longitudeIndicator) {
		if(this.longitudeIndicator!=null){
			synchronized(this.longitudeIndicator){
				this.longitudeIndicator = longitudeIndicator;
			}
		}else{
			this.longitudeIndicator = longitudeIndicator;
		}
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		if(this.position!=null){
			synchronized(this.position){
				this.position = position;
			}
		}else{
			this.position = position;
		}
	}
	
	
}
