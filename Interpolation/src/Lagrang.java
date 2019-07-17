
/** получение интерпол€ционного значени€ методом Ћагранжа*/
public class Lagrang {
	float[] x;
	float[] y;

	public Lagrang(float[] x, float[] y) throws Exception{
		this.x=x;
		this.y=y;
		if((x.length!=x.length)||(x.length==0)){
			throw new Exception("array must be not empty, and Equals Length");
		}
	}
	
	public float getValue(float value){
		float e=0;
		float p=0;
		for(int counter=0;counter<this.x.length;counter++){
			p=1;
			for(int index=0;index<this.x.length;index++){
				if(index!=counter){
					p=p*(value-this.x[index])/(this.x[counter]-this.x[index]);
				}
			}
			e=e+p*this.y[counter];
		}
		return e;
	}
}
