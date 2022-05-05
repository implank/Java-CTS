public class Order {
	Train train;
	Station departure;
	Station arrival;
	String seat;
	int num;
	double price;
	public Order(Train train,Station departure,Station arrival,String seat,int num,double price){
		this.train=train;
		this.departure=departure;
		this.arrival=arrival;
		this.seat=seat;
		this.num=num;
		this.price=price;
	}
	public String toString(){
		return String.format("[%s: %s->%s] seat:%s num:%d price:%.2f",
			train.trainNum,departure.name,arrival.name,seat,num,price);
	}	
}
