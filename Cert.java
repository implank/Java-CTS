import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Cert {
	static Map<String,Boolean>cert=new HashMap<String,Boolean>();
	public static HashMap<String, Boolean> read(String fileName) throws IOException {
		HashMap<String, Boolean> cert = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line;
		while ((line = br.readLine()) != null) {
				String[] lines = line.split(",");
				cert.put(lines[0], lines[1].equals("N"));
		}
		br.close();
		return cert;
	}
	static void importCert(String args[]){
		if(args.length!=2){
			System.out.println("Arguments illegal");
			return;
		}
		try{
			HashMap<String, Boolean> newCert=Cert.read(args[1]);
			for (String ad : newCert.keySet()) {
				cert.putIfAbsent(ad, newCert.get(ad));
				if(cert.get(ad)!=newCert.get(ad)){
					cert.replace(ad, newCert.get(ad));
				}
				User user=User.getByCardnum(ad);
				if(user!=null){
					user.cert=cert.get(ad);
				}
			}
			int p = 0;
			int n = 0;
			for(String key : cert.keySet()){
				if(cert.get(key)==false){
					p++;
				}
				if(cert.get(key)==true){
					n++;
				}
			}
			System.out.println(String.format("Import Success, Positive:%d Negative:%d", p, n));
			return;
		}
		catch (IOException e){

		}
	}
}
