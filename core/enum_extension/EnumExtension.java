package enum_extension;

import java.util.EnumMap;
import java.util.EnumSet;

public class EnumExtension {

	public static void main(String[] args){
		System.out.println("begin");
		Values oneValue=Values.first;
		Values twoValue=Values.second;
		System.out.println("Values:"+oneValue.name());
		// реализация интерфейса
		Description description=oneValue;
		System.out.println("Description:"+description.getDescription());
		
		// EnumSet.
		/*
		 * for (Day d : EnumSet.range(Day.MONDAY, Day.FRIDAY))
        System.out.println(d);
		 */
		
		
		// EnumMap
		/*
		 * private static Map<Suit, Map<Rank, Card>> table =new EnumMap<Suit, Map<Rank, Card>>(Suit.class);
		static {
		    for (Suit suit : Suit.values()) {
		        Map<Rank, Card> suitTable = new EnumMap<Rank, Card>(Rank.class);
		        for (Rank rank : Rank.values())
		            suitTable.put(rank, new Card(rank, suit));
		        table.put(suit, suitTable);
		    }
		}
		
		public static Card valueOf(Rank rank, Suit suit) {
		    return table.get(suit).get(rank);
		}
		 */
		
		System.out.println("-end-");
	}
}

/** интерфейс для реализации его в enum */
interface Description{
	public String getDescription();
}

/** перечислитель */
enum Values implements Description{
	first(1){
		public String getNameAndNumber(){
			return this.name()+Integer.toString(this.intValue);
		}
	},
	second(2){
		public String getNameAndNumber(){
			return this.name()+Integer.toString(this.intValue);
		}
	},
	third(3){
		public String getNameAndNumber(){
			return this.name()+Integer.toString(this.intValue);
		}
	};
	/** метод, который является общим для всех перечислений, и релизует уникальный функционал для каждого из них */
	protected abstract String getNameAndNumber();

	protected int intValue;
	
	Values(int value){
		this.intValue=value;
	}
	
	public int getIntValue(){
		return this.intValue;
	}

	@Override
	public String getDescription() {
		return "Ordinal:"+this.ordinal();
	}
	
}