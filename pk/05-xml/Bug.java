package serializacja;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlType( propOrder = { "name", "message", "errorCode"})
@XmlRootElement( name = "Bug")
public class Bug {
	
	private String name;
	private String message;
	private long errorCode;
	
	public Bug() {};
	
	public Bug(String name, String message, long errorCode) {
		this.name = name;
		this.message = message;
		this.errorCode = errorCode;
	}
	
	public String getName() {
		return name;
	}
	
	@XmlElement(value = "Name")
	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}
	
	@XmlElement(value = "Message")
	public void setMessage(String message) {
		this.message = message;
	}
	
	public long getErrorCode() {
		return errorCode;
	}
	
	@XmlElement(value = "ErrorCode")
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "Bug [name=" + name + ", message=" + message + ", errorCode=" + errorCode + "]";
	}
	
}
