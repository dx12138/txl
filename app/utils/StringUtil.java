package utils;

public class StringUtil {
	
	private StringBuffer stringBufferDescription;
	
	public String saveDescription(String userModifyDescription) {
		if (null == stringBufferDescription) {
			stringBufferDescription = new StringBuffer();
			stringBufferDescription.append(userModifyDescription);
		}
		else {
			stringBufferDescription.append(userModifyDescription);
		}
		
		return stringBufferDescription.toString();
	}
}
