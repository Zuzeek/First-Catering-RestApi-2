package ag.api.util;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class SessionData {
	
	// by making it static it is a single value, so only one user can use it at the time
	private static SessionData INSTANCE;
	private Date createAt; 
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
    	this.createAt = new Date(); 
    	cardNumber = value;
    }

	public Date getCreateAt() {
		return createAt;
	}
    
    
}
