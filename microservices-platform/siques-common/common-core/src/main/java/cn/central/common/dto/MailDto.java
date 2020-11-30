package cn.central.common.dto;

import java.io.Serializable;

public class MailDto implements Serializable {
    private static final long serialVersionUID = 392365881428311040L;
    private String to;

    @Override
    public String toString() {
        return "MailDto{" +
                "to='" + to + '\'' +
                '}';
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
