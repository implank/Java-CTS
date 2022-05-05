import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Test
 */
public class Test {
	static Scanner in = new Scanner(System.in);
	static String status="NormalUser";
	static int cmd=0;
	static Set<String> normalCommands = new HashSet<>();
	static Set<String> adminCommands = new HashSet<>();
	static{
		normalCommands.add("QUIT");
		normalCommands.add("TunakTunakTun");
		normalCommands.add("NutKanutKanut");
		normalCommands.add("addUser");
		normalCommands.add("lineInfo");
		normalCommands.add("listLine");
		normalCommands.add("checkTicket");
		normalCommands.add("listTrain");

		adminCommands.add("QUIT");
		adminCommands.add("TunakTunakTun");
		adminCommands.add("NutKanutKanut");
		adminCommands.add("addLine");
		adminCommands.add("delLine");
		adminCommands.add("addStation");
		adminCommands.add("delStation");
		adminCommands.add("addUser");
		adminCommands.add("lineInfo");
		adminCommands.add("listLine");
		adminCommands.add("addTrain");
		adminCommands.add("delTrain");
		adminCommands.add("listTrain");
	}
	public static void main(String[] args) {
		String argStr;
		while (true) {
			argStr = in.nextLine();
			String arg[]=argStr.split(" ");
			cmd++;
			if(cmd<0){
				System.out.println(argStr+"fuck");
				System.exit(2);
			}
			if(status.equals("NormalUser")&&!normalCommands.contains(arg[0])){
				System.out.println("Command does not exist");
				continue;
			}
			else if(status.equals("Admin")&&!adminCommands.contains(arg[0])){
				System.out.println("Command does not exist");
				continue;
			}
			if(arg[0].equals("QUIT"))Test.exit();
			else if(arg[0].equals("addUser"))User.addUser(arg);
			else if(arg[0].equals("TunakTunakTun"))Test.changeStatus(arg);
			else if(arg[0].equals("NutKanutKanut"))Test.changeStatus(arg);
			else if(arg[0].equals("addLine"))Line.addLine(arg);
			else if(arg[0].equals("delLine"))Line.delLine(arg);
			else if(arg[0].equals("addStation"))Line.addStation(arg);
			else if(arg[0].equals("delStation"))Line.delStation(arg);
			else if(arg[0].equals("lineInfo"))Line.lineInfo(arg);
			else if(arg[0].equals("listLine"))Line.listLine(arg);
			else if(arg[0].equals("addTrain"))Train.addTrain(arg);
			else if(arg[0].equals("delTrain"))Train.delTrain(arg);
			else if(arg[0].equals("checkTicket"))Train.checkTicket(arg);
			else if(arg[0].equals("listTrain"))Train.listTrain(arg);
			else {
				System.out.println(argStr);
			}
		}
	}
	public static void exit() {
		System.out.println("----- Good Bye! -----");
		in.close();
		System.exit(0);
	}
	public static void changeStatus(String args[]){
		if(args.length!=1){
			System.out.println("Arguments illegal");
			return;
		}
		if((args[0].equals("TunakTunakTun")&&Test.status.equals("Admin"))||
			(args[0].equals("NutKanutKanut")&&Test.status.equals("NormalUser")))
			System.out.println("WanNiBa");
		else {
			if(args[0].equals("TunakTunakTun")){
				System.out.println("DuluDulu");
				Test.status="Admin";
			}
			else if(args[0].equals("NutKanutKanut")){
				System.out.println("DaDaDa");
				Test.status="NormalUser";
			}
			else{
				System.out.println("Status illegal");
			}
		}
	}
}

