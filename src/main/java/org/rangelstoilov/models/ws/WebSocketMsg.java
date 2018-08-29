package org.rangelstoilov.models.ws;

public class WebSocketMsg {
    private String type;

    private Integer val;

    private String msg;

    private String opponentName;

    public WebSocketMsg(String type, Integer val, String msg, String opponentName) {
        this.type = type;
        this.val = val;
        this.msg = msg;
        this.opponentName = opponentName;
    }

    public Integer getVal() {
        return this.val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOpponentName() {
        return this.opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
