import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User{
	String name="Bob";
	String gender="O";
	String cardnum="114514";
	boolean cert=false;
	double balance=0;
	int discount=0;
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
	public static void addUser(String args[]) {
		if(args.length!=4&&args.length!=5) {
			System.out.println("Arguments illegal");
			return;
		}
		if(!args[1].matches("[a-zA-Z_]+")){
			System.out.println("Name illegal");
			return ;
		}
		if(args[2].length()>1||!genderMap.containsKey(args[2])){
			System.out.println("Sex illegal");
			return ;
		}
		if(args[3].length()!=12||!args[3].matches("[0-9]*")){
			System.out.println("Aadhaar number illegal");
			return ;
		}
		int area=Integer.parseInt(args[3].substring(0,4));
		int type=Integer.parseInt(args[3].substring(4,8));
		int bio=Integer.parseInt(args[3].substring(8,11));
		int gend=Integer.parseInt(args[3].substring(11,12));
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
		if(gend!=genderMap.get(args[2])){
			System.out.println("Aadhaar number illegal");
			return ;
		}
		if(User.getByCardnum(args[3]) != null){
			System.out.println("Aadhaar number exist");
			return;
		}
		User user;
		if(args.length==5)
			user=new Student(args[1],args[2],args[3],Integer.parseInt(args[4]));
		else user=new User(args[1],args[2],args[3]);
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
				if(args[4].equals("1A")||args[4].equals("2A"))
					if(user.cert==false){
						System.out.println("Cert illegal");
						return ;
					}
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
					new Order(train,departure,arrival,args[4],ticketNum,dis*train.prices[i]));
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
	public static void rechargeBalance(String args[]){
		User user=Test.loginUser;
		if(args.length!=2){
			System.out.println("Arguments illegal");
			return;
		}
		if(user==null){
			System.out.println("Please login first");
			return ;
		}
		double amount=Double.parseDouble(args[1]);
		if(amount<0){
			System.out.println("Arguments illegal");
			return ;
		}
		user.balance+=amount;
		System.out.println("Recharge Success");
	}
	public static void checkBalance(String args[]){
		User user=Test.loginUser;
		if(args.length!=1){
			System.out.println("Arguments illegal");
			return;
		}
		if(user==null){
			System.out.println("Please login first");
			return ;
		}
		System.out.println("UserName:"+user.name+
		String.format("\nBalance:%.2f",user.balance));
	}
	public static void cancelOrder(String args[]){
		User user=Test.loginUser;
		if(args.length!=6){
			System.out.println("Arguments illegal");
			return;
		}
		if(user==null){
			System.out.println("Please login first");
			return ;
		}
		boolean flag=false;
		int cancelNum=Integer.parseInt(args[5]);
		for(int i=user.orders.size()-1; i>=0; i--){
			if(user.orders.get(i).train.trainNum.equals(args[1])
			&&user.orders.get(i).departure.name.equals(args[2])
			&&user.orders.get(i).arrival.name.equals(args[3])
			&&user.orders.get(i).seat.equals(args[4])
			&&user.orders.get(i).paid==false){
				int sub=Math.min(user.orders.get(i).num,cancelNum);
				user.orders.get(i).num-=sub;
				cancelNum-=sub;
				flag=true;
				Train train=user.orders.get(i).train;
				for(int j=0;j<train.seatKind;++j){
					if(train.seat[j].equals(user.orders.get(i).seat)){
						train.remains[j]+=sub;
						break;
					}
				}
				if(user.orders.get(i).num==0){
					user.orders.remove(i);
				}
				if(cancelNum==0){
					System.out.println("Cancel success");
					return;
				}
			}
		}
		if(cancelNum!=0){
			if(flag==true)System.out.println("No enough orders");
			else System.out.println("No such Record");
		}
	}
	public static void payOrder(String args[]){
		User user=Test.loginUser;
		if(args.length!=1){
			System.out.println("Arguments illegal");
			return;
		}
		if(user==null){
			System.out.println("Please login first");
			return ;
		}
		boolean flag=false;
		for(int i=user.orders.size()-1; i>=0; i--){
			if(user.orders.get(i).paid==false){
				double totalPrice,d=0;
				flag=true;
				if(user.discount>0){
					d=Math.min(user.discount,user.orders.get(i).num);
					totalPrice=user.orders.get(i).price*(user.orders.get(i).num-d)
						+user.orders.get(i).price*d*0.05;
				}
				else 
					totalPrice=user.orders.get(i).price*user.orders.get(i).num;
				if(user.balance>=totalPrice){
					user.balance-=totalPrice;
					user.orders.get(i).paid=true;
					user.discount-=d;
				}
				else{
					System.out.println("Balance does not enough");
					return;
				}
			}
		}
		if(flag==false){
			System.out.println("No order");
			return;
		}
		System.out.println("Payment success");
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