package org.dev.iuh.client.services;

import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.dev.iuh.client.context.SocketClientContext;
import org.dev.iuh.common.dto.GeneralRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class RequestMaker<T> implements Serializable {
	
	private static final long serialVersionUID = 1900000L;
	private static final ObjectWriter WRITER =  new ObjectMapper().registerModule(new JavaTimeModule()).writer().withDefaultPrettyPrinter();
	private static final ObjectOutputStream TO_SERVER = SocketClientContext.getInstance().getSocketClient().getToServer();
	
	
	public static<T> void createRequest(String command, T payload) throws Exception {
		var req = new GeneralRequest<T>(command, payload);
		TO_SERVER.writeUTF(WRITER.writeValueAsString(req));
		TO_SERVER.flush();
		return;
	}

}
