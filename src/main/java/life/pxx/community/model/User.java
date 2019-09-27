package life.pxx.community.model;

/**
 * @author pxx
 * Date 2019/9/27 16:43
 * @Description
 */
public class User {
	private int id;
	private String name;
	private String accountId;
	private String token;
	private Long gmtCreate;
	
	@Override
	public String toString() {
		return "User{" +
					   "id=" + id +
					   ", name='" + name + '\'' +
					   ", accountId='" + accountId + '\'' +
					   ", token='" + token + '\'' +
					   ", gmtCreate=" + gmtCreate +
					   ", gmtModified=" + gmtModified +
					   '}';
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public Long getGmtCreate() {
		return gmtCreate;
	}
	
	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	public Long getGmtModified() {
		return gmtModified;
	}
	
	public void setGmtModified(Long gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	private Long gmtModified;
}
