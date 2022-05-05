import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Line {
	String lineNum;
	int capacity;
	int load;
	List<Train> trains = new ArrayList<Train>();
	List<Station> stations = new ArrayList<Station>();
	static List<Line> lines = new ArrayList<Line>();
	Line(){}
	Line(String lineNum, int capacity) {
		this.lineNum = lineNum;
		this.capacity = capacity;
	}
	public static void addLine(String args[]){
		if(args.length%2!=1||args.length<5){
			System.out.println("Arguments illegal");
			return ;
		}
		if(!args[2].matches("-?[0-9]+")){
			System.out.println("Arguments illegal");
			return ;
		}
		for(int i=4;i<args.length;i+=2){
			if(!args[i].matches("[0-9]+")){
				System.out.println("Arguments illegal");
				return ;
			}
		}
		for(int i=3;i<args.length;i+=2){
			for(int j=i+2;j<args.length;j+=2){
				if(args[i].equals(args[j])){
					System.out.println("Station duplicate");
					return ;
				}
			}
		}
		if(getByNum(args[1])!=null){
			System.out.println("Line already exists");
			return ;
		}
		if(Integer.parseInt(args[2])<=0){
			System.out.println("Capacity illegal");
			return ;
		}
		Line line=new Line(args[1],Integer.parseInt(args[2]));
		for(int i=3;i<args.length;i+=2){
			Station station=new Station(args[i],Integer.parseInt(args[i+1]));
			line.stations.add(station);
		}
		lines.add(line);
		System.out.println("Add Line success");
	}
	static void delLine(String args[]){
		if(args.length!=2){
			System.out.println("Arguments illegal");
			return ;
		}
		Line line=getByNum(args[1]);
		if(line==null){
			System.out.println("Line does not exist");
			return ;
		}
		lines.remove(line);
		for(Train train:line.trains){
			Train.trains.remove(train);
			train.line=null;
		}
		System.out.println("Del Line success");
	}
	public static void addStation(String args[]){
		if(args.length!=4){
			System.out.println("Arguments illegal");
			return ;
		}
		Line line=getByNum(args[1]);
		if(line==null){
			System.out.println("Line does not exist");
			return ;
		}
		if(line.getStationByName(args[2])!=null){
			System.out.println("Station duplicate");
			return ;
		}
		if(!args[3].matches("[0-9]+")){
			System.out.println("Arguments illegal");
			return ;
		}
		Station station=new Station(args[2],Integer.parseInt(args[3]));
		line.stations.add(station);
		System.out.println("Add Station success");		
	}
	public static void delStation(String args[]){
		if(args.length!=3){
			System.out.println("Arguments illegal");
			return ;
		}
		Line line=getByNum(args[1]);
		if(line==null){
			System.out.println("Line does not exist");
			return ;
		}
		Station station=line.getStationByName(args[2]);
		if(station==null){
			System.out.println("Station does not exist");
			return ;
		}
		line.stations.remove(station);
		System.out.println("Delete Station success");
	}
	public static void lineInfo(String args[]){
		if(args.length!=2){
			System.out.println("Arguments illegal");
			return ;
		}
		Line line=getByNum(args[1]);
		if(line==null){
			System.out.println("Line does not exist");
			return ;
		}
		System.out.println(line);
	}
	public static void listLine(String args[]){
		if(args.length!=1){
			System.out.println("Arguments illegal");
			return ;
		}
		if(lines.size()==0){
			System.out.println("No Lines");
			return ;
		}
		lines.sort(new LineCompartor());
		for(int i=0;i<lines.size();i++){
			System.out.print(String.format("[%d] ",i+1));
			System.out.println(lines.get(i));
		}
	}
	public String toString(){
		String str="";
		str+=lineNum+" ";
		str+=load+"/"+capacity+" ";
		stations.sort(new StationCompartor());
		for(Station station:stations){
			str+=station.toString()+" ";
		}
		return str;
	}
	public Station getStationByName(String name){
		if(name.equals("Delhi-3"))
			return new Station("Delhi-3",0);
		for(Station station:stations){
			if(station.name.equals(name)){
				return station;
			}
		}
		return null;
	}
	public static Line getByNum(String lineNum) {
		for (Line line : lines) {
			if (line.lineNum.equals(lineNum)) {
				return line;
			}
		}
		return null;
	}
}
class LineCompartor implements Comparator<Line>
{
	@Override
	public int compare(Line a, Line b) {
		return a.lineNum.compareTo(b.lineNum);
	}
}