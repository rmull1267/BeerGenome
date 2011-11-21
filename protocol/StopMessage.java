package protocol;

import server.PrefixParser;

public class StopMessage implements ProtocolMessage {

	public static final String PREFIX = "stop";
	@Override
	public String generateMessage() {
		return PREFIX + PrefixParser.DELIMITER + "STOP";
	}

	@Override
	public String generateResponse(String message) {
		return "OK";
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		
	}

}
