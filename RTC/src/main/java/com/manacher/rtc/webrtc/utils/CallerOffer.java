package com.manacher.rtc.webrtc.utils;

public class CallerOffer {

    private Offer offer;

    public CallerOffer() {
    }

    public CallerOffer(Offer offer) {
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
