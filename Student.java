public class Student extends User {
	Student(){}
	Student(String name,String gender,String cardnum,int discount){
		super(name,gender,cardnum);
		this.discount=discount;
	}
	public String toString(){
		return "Name:"+name
		+"\nSex:"+gender
		+"\nAadhaar:"+cardnum
		+"\nDiscount:"+discount;
	}
}
