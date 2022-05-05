import java.util.Comparator;

public class Station {
	String name;
	int distance;
	
	Station(){}
	Station(String name, int distance) {
		this.name = name;
		this.distance = distance;
	}
	public String toString(){
		return name+":"+distance;
	}
}
class StationCompartor implements Comparator<Station>
{
	@Override
	public int compare(Station a, Station b) {
		if (a.distance != b.distance) {
			return a.distance - b.distance;
		}
		return a.name.compareTo(b.name);
	}
}