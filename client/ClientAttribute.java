package client;

import java.util.ArrayList;
import java.util.List;

import protocol.CreateAttributeMessage;
import protocol.GetAllAttributesMessage;
import protocol.GetAttributeMessage;
import protocol.ProtocolException;
import protocol.RenameAttributeMessage;

import core.Attribute;
import database.DBAbstractionException;

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
		List<Attribute> retVal = new ArrayList<Attribute>();
		GetAllAttributesMessage m = new GetAllAttributesMessage(retVal);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}

	@Override
	public void commit() throws DBAbstractionException {
		RenameAttributeMessage m = new RenameAttributeMessage(this);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() throws DBAbstractionException {
		throw new DBAbstractionException("unimplemented.");
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
