package com.manacher.rtc.utils;

import org.webrtc.IceCandidate;

public class Util {
    public IceCandidateServer getIceCandidateServer(IceCandidate iceCandidate){
        return  new IceCandidateServer(iceCandidate.adapterType, iceCandidate.sdp, iceCandidate.sdpMLineIndex, iceCandidate.sdpMid, iceCandidate.serverUrl);
    }
}
