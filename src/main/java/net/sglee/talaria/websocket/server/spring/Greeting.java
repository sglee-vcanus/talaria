package net.sglee.talaria.websocket.server.spring;

public class Greeting {
	private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}