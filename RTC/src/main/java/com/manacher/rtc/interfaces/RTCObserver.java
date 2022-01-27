package com.manacher.rtc.interfaces;


import com.manacher.rtc.utils.IceCandidateServer;
import com.manacher.rtc.utils.Offer;

import org.webrtc.SessionDescription;

public interface RTCObserver {
    void onIceCandidate(IceCandidateServer iceCandidateServer);

    void onInvitation(Offer offer, SessionDescription.Type type);
}
