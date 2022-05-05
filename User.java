import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User{
	String name="Bob";
	String gender="O";
	String cardnum="114514";
	List<Order>orders=new ArrayList<Order>();
	
	static List<User> users=new ArrayList<User>();
	static Map<String,Integer> genderMap=new HashMap<String,Integer>();
	static {
		if(genderMap.isEmpty()){
			genderMap.put("F",0);
			genderMap.put("M",1);
			genderMap.put("O",2);
		}
	}

	User(){}
	User(String name,String gender,String cardnum){
		this.name=name;
		this.gender=gender;
		this.cardnum=cardnum;
	}
	public static boolean checkCardnum(String cardnum){
		if(cardnum.length()!=12)return false;
		int area=Integer.parseInt(cardnum.substring(0,4));
		int type=Integer.parseInt(cardnum.substring(4,8));
		int bio=Integer.parseInt(cardnum.substring(8,11));
		int gend=Integer.parseInt(cardnum.substring(11,12));
		if(!(1<=area&&area<=1237))
			return false;
		if(!(20<=type&&type<=460))
			return false; 
		if(!(0<=bio&&bio<=100))
			return false;
		if(!(0<=gend&&gend<=2))
			return false;
		return true;
	}
	public static void addUser(String args[]){
		if(args.length!=4){
			System.out.println("Arguments illegal");
			return;
		}
		if(!args[0].equals("addUser"))
			System.out.println("Arguments illegal");
		else{
			addUser(args[1],args[2],args[3]);
		}
	}
	public static void addUser(String name,String gender,String cardnum) {
		if(!name.matches("[a-zA-Z_]+")){
			System.out.println("Name illegal");
			return ;
		}
		if(gender.length()>1||!genderMap.containsKey(gender)){
			System.out.println("Sex illegal");
			return ;
		}
		if(cardnum.length()!=12||!cardnum.matches("[0-9]*")){
			System.out.println("Aadhaar number illegal");
			return ;
		}
		int area=Integer.parseInt(cardnum.substring(0,4));
		int type=Integer.parseInt(cardnum.substring(4,8));
		int bio=Integer.parseInt(cardnum.substring(8,11));
		int gend=Integer.parseInt(cardnum.substring(11,12));
		if(!(1<=area&&area<=1237)){
			System.out.println("Aadhaar number illegal");
			return ;
		}
		if(!(20<=type&&type<=460)){
			System.out.println("Aadhaar number illegal");
			return ;
		}
		if(!(0<=bio&&bio<=100)){
			System.out.println("Aadhaar number illegal");
			return ;
		}
		if(gend!=genderMap.get(gender)){
			System.out.println("Aadhaar number illegal");
			return ;
		}
		if(User.getByCardnum(cardnum) != null){
			System.out.println("Aadhaar number exist");
			return;
		}
		User user=new User(name,gender,cardnum);
		users.add(user);
		System.out.println(user);
	}
	public static void buyTicket(String args[]){
		User user=Test.loginUser;
		if(args.length!=6){
			System.out.println("Arguments illegal");
			return;
		}
		if(user==null){
			System.out.println("Please login first");
			return ;
		}
		Train train=Train.getByTrainNum(args[1]);
		if(train==null){
			System.out.println("Train does not exist");
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
				if(!args[5].matches("[1-9][0-9]*")){
					System.out.println("Ticket number illegal");
					return;
				}
				int ticketNum=Integer.parseInt(args[5]);
				int dis=Math.abs(departure.distance-arrival.distance);
				if(train.remains[i]<ticketNum){
					System.out.println("Ticket does not enough");
					return;
				}
				double totalPrice=dis*train.prices[i]*ticketNum;
				user.orders.add(
					new Order(train,departure,arrival,args[4],ticketNum,totalPrice));
				train.remains[i]-=ticketNum;
				System.out.println("Thanks for your order");
				return ;
			}
		}
		System.out.println("Seat does not match");
	}
	public static void listOrder(String args[]){
		User user=Test.loginUser;
		if(args.length!=1){
			System.out.println("Arguments illegal");
			return;
		}
		if(user==null){
			System.out.println("Please login first");
			return ;
		}
		if(user.orders.size()==0){
			System.out.println("No order");
			return ;
		}
		for(int i=user.orders.size()-1; i>=0; i--){
			System.out.println(user.orders.get(i));
		}
	}
	public static User getByCardnum(String cardnum) {
		for(int i=0;i<users.size();++i){
			if(users.get(i).cardnum.equals(cardnum))
				return users.get(i);
		}
		return null;
	}
	public String toString() {
		return "Name:"+name
		+"\nSex:"+gender
		+"\nAadhaar:"+cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getGender() {
		return gender;
	}
}