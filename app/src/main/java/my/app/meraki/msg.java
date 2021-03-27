package my.app.meraki;

public class msg {
    private String msg;
    private String sender;
    public msg(String msg, String sender){
        this.msg = msg;
        this.sender = sender;
    }

    public msg(String msg) {
        this.msg = msg;
        this.sender = "NotMe";
    }

    public msg(){
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
