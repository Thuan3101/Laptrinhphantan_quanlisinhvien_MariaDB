package org.dev.iuh.client.context;

import org.dev.iuh.client.dto.SocketClient;

/**
 * SocketClientContext: lưu trữ các đối tượng socket client để giao tiếp với server. Vì các đối tượng này chỉ nên là duy nhất
 * trong suốt quá trình chạy app, dùng singleton pattern
 */
public class SocketClientContext {
	
	private static final SocketClientContext INSTANCE = new SocketClientContext();
	private SocketClient socketClient;
	
	private SocketClientContext() {
        System.out.println("Created Socket client context ");
        socketClient = new SocketClient();
    }

    public static SocketClientContext getInstance() {
        return INSTANCE;
    }
    
    public SocketClient getSocketClient() {
    	if (INSTANCE != null)
    		return INSTANCE.socketClient;
    	else return null;
    }
    
    public void setSocketClient(SocketClient client) {
    	if (INSTANCE != null)
    		INSTANCE.socketClient = client;
    }
}
