package cn.xiaocool.android_etong.bean.Mine;

public class PersonChat {
	/**
	 * id
	 */
	private int id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 聊天内容
	 */
	private String chatMessage;
	/**
	 * 
	 * @return 是否为本人发送
	 */
	private String chatTime;
	private String photo;
	private String chatuid;
	private String anotherPhoto;
    private boolean isMeSend;
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
	public String getChatMessage() {
		return chatMessage;
	}
	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}
	public void setChatTime(String chatTime){
		this.chatTime = chatTime;
	}
	public String getChatTime(){
		return chatTime;
	}
	public void setPhoto(String photo){
		this.photo = photo;
	}
	public String getPhoto(){
		return photo;
	}
	public void setchatuid(String chatuid){
		this.chatuid = chatuid;
	}
	public String getchatuid(){
		return chatuid;
	}
	public void setAnotherPhoto(String anotherPhoto){
		this.anotherPhoto = anotherPhoto;
	}
	public String getAnotherPhoto(){
		return anotherPhoto;
	}
	public boolean isMeSend() {
		return isMeSend;
	}
	public void setMeSend(boolean isMeSend) {
		this.isMeSend = isMeSend;
	}
	public PersonChat(int id, String name, String chatMessage, boolean isMeSend) {
		super();
		this.id = id;
		this.name = name;
		this.chatMessage = chatMessage;
		this.isMeSend = isMeSend;
	}
	public PersonChat() {
		super();
	}
	

}
