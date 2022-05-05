import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Train {
	int level;
	String type;
	String trainNum;
	Line line;
	int seatKind;
	String seat[];
	double prices[];
	int remains[];
	static List<Train> trains=new ArrayList<Train>();
	Train(){}
	Train(String trainNum, Line line){
		this.trainNum=trainNum;
		this.line=line;
	}
	Train(String type, String trainNum, Line line){
		this.type=type;
		this.trainNum=trainNum;
		this.line=line;
		if(type.equals("0")){
			seatKind=3;
			seat=new String[]{"CC","SB","GG"};
			level=1;
		}
		else if(type.equals("G")){
			seatKind=3;
			seat=new String[]{"SC","HC","SB"};
			level=2;
		}
		else if(type.equals("K")){
			seatKind=2;
			seat=new String[]{"1A","2A"};
			level=3;
		}
	}
	public static void addTrain(String args[]){
		if(args.length<7){
			System.out.println("Arguments illegal");
			return ;
		}
		if(!args[1].matches("[GK0][0-9]{4}")){
			System.out.println("Train serial illegal");
			return ;
		}
		if(Train.getByTrainNum(args[1])!=null){
			System.out.println("Train serial duplicate");
			return ;
		}
		Line line=Line.getByNum(args[2]);
		if(line==null||line.load>=line.capacity){
			System.out.println("Line illegal");
			return ;
		}
		String type=args[1].substring(0,1);
		if(!args[3].matches("[0-9]+(\\.[0-9]+)?")||
			!args[5].matches("[0-9]+(\\.[0-9]+)?")){
				System.out.println("Price illegal");
			return ;
		}
		if(type.equals("0")||type.equals("G")){
			if(args.length!=9){
				System.out.println("Arguments illegal");
				return ;
			}
			if(!args[7].matches("[0-9]+(\\.[0-9]+)?")){
				System.out.println("Price illegal");
				return ;
			}
		}
		else {
			if(args.length!=7){
				System.out.println("Arguments illegal");
				return ;
			}
		}
		if(!args[4].matches("[0-9]+")||
			!args[6].matches("[0-9]+")){
			System.out.println("Ticket num illegal");
			return ;
		}
		if(type.equals("0")||type.equals("G")){
			if(!args[8].matches("[0-9]+")){
				System.out.println("Ticket num illegal");
				return ;
			}
		}
		Train train=new Train(type,args[1],line);
		double price[]=new double[3];
		int remain[]=new int[3];
		for(int i=0;i<train.seatKind;++i){
			price[i]=Double.parseDouble(args[3+i*2]);
			remain[i]=Integer.parseInt(args[4+i*2]);
		}
		train.setPrice(price);
		train.setRemain(remain);
		Train.trains.add(train);
		line.trains.add(train);
		line.load++;
		System.out.println("Add Train Success");
	}
	public static void delTrain(String args[]){
		if(args.length!=2){
			System.out.println("Arguments illegal");
			return ;
		}
		Train train=Train.getByTrainNum(args[1]);
		if(train==null){
			System.out.println("Train does not exist");
			return ;
		}
		Train.trains.remove(train);
		Line line=train.line;
		line.trains.remove(train);
		line.load--;
		System.out.println("Del Train Success");
	}
	public static void listTrain(String args[]){
		if(args.length==2){
			Line line=Line.getByNum(args[1]);
			if(line==null){
				System.out.println("Line does not exist");
				return ;
			}
			if(line.load==0){
				System.out.println("No Trains");
				return ;
			}
			line.trains.sort(new TrainCompartor());
			for(int i=0;i<line.trains.size();i++){
				System.out.print(String.format("[%d] ",i+1));
				System.out.println(line.trains.get(i));
			}
		}
		else if(args.length==1){
			if(trains.isEmpty()){
				System.out.println("No Trains");
				return ;
			}
			trains.sort(new TrainCompartor());
			for(int i=0;i<trains.size();i++){
				System.out.print(String.format("[%d] ",i+1));
				System.out.println(trains.get(i));
			}
		}
		else {
			System.out.println("Arguments illegal");
		}
	}
	public static void checkTicket(String args[]){
		if(args.length!=5){
			System.out.println("Arguments illegal");
			return ;
		}
		if(!args[1].matches("[GK0][0-9]{4}")){
			System.out.println("Train serial illegal");
			return ;
		}
		Train train=Train.getByTrainNum(args[1]);
		if(train==null){
			System.out.println("Train serial does not exist");
			return ;
		}
		Line line=train.line;
		Station departure=line.getStationByName(args[2]);
		Station arrival=line.getStationByName(args[3]);
		if(departure==null||arrival==null){
			System.out.println("Station does not exist");
			return ;
		}
		for(int i=0;i<train.seatKind;++i){
			if(train.seat[i].equals(args[4])){
				int dis=Math.abs(departure.distance-arrival.distance);
				System.out.println(String.format("[%s: %s->%s] seat:%s remain:%d distance:%d price:%.2f",
						train.trainNum,departure.name,arrival.name,train.seat[i],train.remains[i],
						dis,train.prices[i]*dis));
				return ;
			}
		}
		System.out.println("Seat does not match");
	}
	public static Train	getByTrainNum(String trainNum){
		for(Train train:trains){
			if(train.trainNum.equals(trainNum)){
				return train;
			}
		}
		return null;
	}
	public String toString(){
		String str="";
		str+=trainNum+": ";
		str+=line.lineNum+" ";
		for(int i=0;i<seatKind;++i){
			str+=String.format("[%s]%.2f:%d ",seat[i],prices[i],remains[i]);
		}
		return str;
	}
	public void setType(String type){
		this.type=type;
	}
	public void setPrice(double prices[]){
		this.prices=prices;
	}
	public void setRemain(int remains[]){
		this.remains=remains;
	}
}

class TrainCompartor implements Comparator<Train>
{
	@Override
	public int compare(Train a, Train b) {
		if(a.level==b.level)
			return a.trainNum.compareTo(b.trainNum);
		else 
			return a.level>b.level?-1:1;
	}
}