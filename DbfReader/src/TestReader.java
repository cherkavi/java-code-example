import java.io.FileInputStream;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;


public class TestReader {
	
	public static void main(String[] args){
		String pathToDbf="C:\\vidd2.dbf";
		readHeaderDbfFile(pathToDbf);
		readDateDbfFile(pathToDbf);
		//printAviableCharset();
	}
	
	/** прочесить и вывести на консоль содержимое DBF файла */
	private static void readHeaderDbfFile(String pathToFile){
		try{
			DBFReader reader = new DBFReader( new FileInputStream(pathToFile)); 
		    // get the field count if you want for some reasons like the following
		    int numberOfFields = reader.getFieldCount();
		    // use this count to fetch all field information
		    // if required
		    for( int i=0; i<numberOfFields; i++) {
		    	DBFField field = reader.getField( i);
		        // do something with it if you want
		        // refer the JavaDoc API reference for more details
		    	System.out.print("Name:"+field.getName());
		    	System.out.println("   Type:"+field.getDataType());
		    }
		}catch(Exception ex){
			System.out.println("readFromDbfFile: "+ex.getMessage());
		}
	}

	/** прочесть и вывести на консоль содержимое DBF файла */
	private static void readDateDbfFile(String pathToFile){
		try{
			DBFReader reader = new DBFReader( new FileInputStream(pathToFile));
			Object[] record;
			while((record=reader.nextRecord())!=null){
				for(int counter=0;counter<record.length;counter++){
					if(record[counter] instanceof String){
						//System.out.print(record[counter]+" "+convertDosString((String)record[counter]));
						System.out.print(convertDosString((String)record[counter]));
					}else{
						System.out.print(record[counter]+"   ");
					}
				}
				System.out.println();
			}
		}catch(Exception ex){
			System.out.println("readFromDbfFile: "+ex.getMessage());
		}
	}
	
	/** преобразование прочитанных данных из DBF в "нормальное" представление русских букв */
	private static String convertDosString(String value){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<value.length();counter++){
			returnValue.append(getCharFromInteger(Integer.valueOf(value.charAt(counter)),value.charAt(counter)));
		}
		return returnValue.toString();
	}
	
	/** преобразование русских символов в "нормальное отображение "*/
	private static char getCharFromInteger(int value,char defaultValue){
		if(value==(128))return 'А';
		if(value==(129))return 'Б';
		if(value==(130))return 'В';
		if(value==(131))return 'Г';
		if(value==(132))return 'Д';
		if(value==(133))return 'Е';
		if(value==(134))return 'Ж';
		if(value==(135))return 'З';
		if(value==(136))return 'И';
		if(value==(137))return 'Й';
		if(value==(138))return 'К';
		if(value==(139))return 'Л';
		if(value==(140))return 'М';
		if(value==(141))return 'Н';
		if(value==(142))return 'О';
		if(value==(143))return 'П';
		if(value==(144))return 'Р';
		if(value==(145))return 'С';
		if(value==(146))return 'Т';
		if(value==(147))return 'У';
		if(value==(148))return 'Ф';
		if(value==(149))return 'Х';
		if(value==(150))return 'Ц';
		if(value==(151))return 'Ч';
		if(value==(152))return 'Ш';
		if(value==(153))return 'Щ';
		if(value==(154))return 'Ъ';
		if(value==(155))return 'Ы';
		if(value==(156))return 'Ь';
		if(value==(157))return 'Э';
		if(value==(158))return 'Ю';
		if(value==(159))return 'Я';
		if(value==(160))return 'а';
		if(value==(161))return 'б';
		if(value==(162))return 'в';
		if(value==(163))return 'г';
		if(value==(164))return 'д';
		if(value==(165))return 'е';
		if(value==(166))return 'ж';
		if(value==(167))return 'з';
		if(value==(168))return 'и';
		if(value==(169))return 'й';
		if(value==(170))return 'к';
		if(value==(171))return 'л';
		if(value==(172))return 'м';
		if(value==(173))return 'н';
		if(value==(174))return 'о';
		if(value==(175))return 'п';
		if(value==(224))return 'р';
		if(value==(225))return 'с';
		if(value==(226))return 'т';
		if(value==(227))return 'у';
		if(value==(228))return 'ф';
		if(value==(229))return 'х';
		if(value==(230))return 'ц';
		if(value==(231))return 'ч';
		if(value==(232))return 'ш';
		if(value==(233))return 'щ';
		if(value==(234))return 'ъ';
		if(value==(235))return 'ы';
		if(value==(236))return 'ь';
		if(value==(237))return 'э';
		if(value==(238))return 'ю';
		if(value==(239))return 'я';
		//if(value==())return 'Ґ';
		if(value==(240))return 'Ё';
		if(value==(242))return 'Є';
		if(value==(244))return 'Ї';
		//if(value==())return 'І';
		//if(value==())return 'і';
		//if(value==())return 'ґ';
		if(value==(241))return 'ё';
		if(value==(252))return '№';
		if(value==(243))return 'є';
		//if(value==())return 'ј';
		//if(value==())return 'Ѕ';
		//if(value==())return 'ѕ';
		if(value==(245))return 'ї';
		return defaultValue;
	}
}

/*
А	;	128	;
Б	;	129	;
В	;	130	;
Г	;	131	;
Д	;	132	;
Е	;	133	;
Ж	;	134	;
З	;	135	;
И	;	136	;
Й	;	137	;
К	;	138	;
Л	;	139	;
М	;	140	;
Н	;	141	;
О	;	142	;
П	;	143	;
Р	;	144	;
С	;	145	;
Т	;	146	;
У	;	147	;
Ф	;	148	;
Х	;	149	;
Ц	;	150	;
Ч	;	151	;
Ш	;	152	;
Щ	;	153	;
Ъ	;	154	;
Ы	;	155	;
Ь	;	156	;
Э	;	157	;
Ю	;	158	;
Я	;	159	;
а	;	160	;
б	;	161	;
в	;	162	;
г	;	163	;
д	;	164	;
е	;	165	;
ж	;	166	;
з	;	167	;
и	;	168	;
й	;	169	;
к	;	170	;
л	;	171	;
м	;	172	;
н	;	173	;
о	;	174	;
п	;	175	;
р	;	224	;
с	;	225	;
т	;	226	;
у	;	227	;
ф	;	228	;
х	;	229	;
ц	;	230	;
ч	;	231	;
ш	;	232	;
щ	;	233	;
ъ	;	234	;
ы	;	235	;
ь	;	236	;
э	;	237	;
ю	;	238	;
я	;	239	;
Ґ	;		;
Ё	;	240	;
Є	;	242	;
Ї	;	244	;
І	;		;
і	;		;
ґ	;		;
ё	;	241	;
№	;	252	;
є	;	243	;
ј	;		;
Ѕ	;		;
ѕ	;		;
ї	;	245	;

 
 */ 
