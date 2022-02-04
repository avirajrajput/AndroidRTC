package com.manacher.rtc.webrtc.utils;

import org.webrtc.IceCandidate;

public class Util {
    public IceCandidateServer getIceCandidateServer(IceCandidate iceCandidate){
        return  new IceCandidateServer(iceCandidate.adapterType, iceCandidate.sdp, iceCandidate.sdpMLineIndex, iceCandidate.sdpMid, iceCandidate.serverUrl);
    }

    public IceCandidate getIceCandidate(IceCandidateServer iceCandidateServer){
        return  new IceCandidate(iceCandidateServer.getSdpMid(), iceCandidateServer.getSdpMLineIndex(), iceCandidateServer.getSdp());
    }
}
