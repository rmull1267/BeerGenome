package client;

import java.util.List;

import protocol.CreateAttributeMessage;
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
	
	public ClientAttribute(String name)
	{
		constructorHelper(name);
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
		setNameWithoutCommit(name);
		CreateAttributeMessage m = new CreateAttributeMessage(this);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
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
	
	
	/**
	 * public so that CreateAttributeMessage can set the ID of a newly created attribute.
	 * @param attributeId
	 */
	public void setAttributeIdPublic(int attributeId)
	{
		this.setAttributeId(attributeId);
	}

}
