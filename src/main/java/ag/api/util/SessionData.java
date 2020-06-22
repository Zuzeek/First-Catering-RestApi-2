package ag.api.util;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class SessionData {
	
	// by making it static it is a single value, so only one user can use it at the time
	private static SessionData INSTANCE;
	private Date createdAt; 
    private String cardNumber = "";
    
    private SessionData() {}
     
    public static SessionData getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SessionData();
        }
         
        return INSTANCE;
    }
    
    public String getCardNumber() {
        return cardNumber;   
    }
    
    public void setCardNumber(String value) {
    	this.createdAt = new Date(); 
    	cardNumber = value;
    }

	public Date getCreateAt() {
		return createdAt;
	}
    
    
}
