package dc.hackathon.talia;

import org.springframework.stereotype.Service;

// http://web.media.mit.edu/~guyzys/data/ZNP15.pdf
@Service
public class EnigmaService {

	public String[] createCompountIdentity(EnigmaRole user, EnigmaRole service) {
		return null;
	}
	
	public EnigmaRole checkPolicy(String pk, String x) {
		return null;
	}
	
	public Boolean handleAccessTX(String pk, String m) {
		// if pk == pk u,s sig then true
		return false;
	}
	
	public Boolean handleDataTX(String pk, String m) {
		return false;
	}
}

