package client;

import java.util.List;

import protocol.GetAttributeMessage;
import protocol.ProtocolException;

import core.Attribute;
import database.DBAbstractionException;

/**
 * TODO-nf
 * @author nfulton
 *
 */
public class ClientAttribute extends Attribute {

	public ClientAttribute(int attributeId)
	{
		constructorHelper(attributeId);
	}
	
	@Override
	protected void constructorHelper(int attributeId) {
		this.setAttributeId(attributeId);
		GetAttributeMessage m = new GetAttributeMessage(this);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void constructorHelper(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Attribute> getAllAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void commit() throws DBAbstractionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() throws DBAbstractionException {
		// TODO Auto-generated method stub
		
	}

}
