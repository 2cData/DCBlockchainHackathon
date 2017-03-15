package dc.hackathon.talia;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Ehr {
	private String enrollId;
	private String enrollSecret;
}
