package com.manacher.rtc.webrtc.utils;

public class SessionDescriptionData {

    private boolean connected;
    private Offer answer;
    private Offer offer;
    private String otherId;

    public SessionDescriptionData() {
    }

    public SessionDescriptionData(Offer answer, Offer offer) {
        this.answer = answer;
        this.offer = offer;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }

    public Offer getAnswer() {
        return answer;
    }

    public void setAnswer(Offer answer) {
        this.answer = answer;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
