package com.manacher.rtc.webrtc.interfaces;

import com.manacher.rtc.webrtc.utils.IceCandidateServer;
import com.manacher.rtc.webrtc.utils.Offer;

import org.webrtc.DataChannel;

public interface RTCObserver {
    void onIceCandidate(IceCandidateServer iceCandidateServer);
    void iceCandidateStatus(int status);
    void onDataChannel(DataChannel dataChannel);
    void onInvitation(Offer offer, int type);
}
