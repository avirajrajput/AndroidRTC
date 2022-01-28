package com.manacher.rtc.interfaces;

import com.manacher.rtc.utils.IceCandidateServer;
import com.manacher.rtc.utils.Offer;


public interface RTCObserver {
    void onIceCandidate(IceCandidateServer iceCandidateServer);

    void onInvitation(Offer offer, int type);
}
