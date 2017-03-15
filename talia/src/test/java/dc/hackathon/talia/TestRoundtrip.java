package dc.hackathon.talia;

import java.util.List;

public class TestRoundtrip {

	private static final String ENROLL_ID = "user_type1_2";
	private static final String ENROLL_SECRET = "54c5466fc1";
	
	public static void main(String[] args) {
		EhrFabric fabric = new EhrFabric();
		Ehr ehr = new Ehr(ENROLL_ID, ENROLL_SECRET, "");
		fabric.deploy(ehr);
		
		EhrRepository repository = new EhrRepository();
		List<Ehr> ehrs =repository.findAll();
		
		for (Ehr item : ehrs) {
			System.out.println(item.getEnrollId() + " " + item.getEnrollSecret() + item.getMessage());
		}
		
	}
}
