package com.manacher.rtc.services;


import android.content.Context;

import com.manacher.rtc.interfaces.RTCObserver;
import com.manacher.rtc.observers.CustomPeerConnectionObserver;
import com.manacher.rtc.observers.CustomSdpObserver;
import com.manacher.rtc.utils.Constants;
import com.manacher.rtc.utils.IceCandidateServer;
import com.manacher.rtc.utils.Offer;
import com.manacher.rtc.utils.SessionDescriptionData;
import com.manacher.rtc.utils.Util;

import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;
import org.webrtc.SessionDescription;
import org.webrtc.voiceengine.WebRtcAudioUtils;

import java.util.ArrayList;
import java.util.List;

public class ManacherService {
    private Context activity;
    private PeerConnection peerConnection;
    private DataChannel localDataChannel;
    private PeerConnectionFactory pcFactory;
    private PeerConnection.RTCConfiguration rtcConfig;

    private MediaStream localStream;
    private MediaStream remoteStream;

    private AudioTrack localAudioTrack;
    private AudioTrack remoteAudioTrack;

    private Util util;
    private RTCObserver listener;

    private static ManacherService instance;

    public ManacherService(Context activity){
        this.activity = activity;
        this.initialized();
    }

    public static ManacherService initialized(Context context){
        if(instance == null){
            instance = new ManacherService(context);
        }

        return instance;
    }

    private void initialized(){
        util = new Util();
        listener = (RTCObserver) activity;

        PeerConnectionFactory.InitializationOptions initializationOptions = PeerConnectionFactory.InitializationOptions.builder(activity).createInitializationOptions();
        PeerConnectionFactory.initialize(initializationOptions);
        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        pcFactory = PeerConnectionFactory.builder().setOptions(options).createPeerConnectionFactory();

        localStream = pcFactory.createLocalMediaStream(Constants.MEDIA_STREAM_LABEL_ONE);
        remoteStream = pcFactory.createLocalMediaStream(Constants.MEDIA_STREAM_LABEL_SECOND);

        List<PeerConnection.IceServer> iceServers = new ArrayList<>();
        iceServers.add(PeerConnection.IceServer.builder(Constants.STUN_SERVER).createIceServer());
        iceServers.add(PeerConnection.IceServer.builder(Constants.TUN_SERVER).setUsername(Constants.TUN_USERNAME).setPassword(Constants.TUN_PASSWORD).createIceServer());

        rtcConfig = new PeerConnection.RTCConfiguration(iceServers);

        rtcConfig.enableRtpDataChannel = false;
        rtcConfig.enableDtlsSrtp = true;

    }
    //
    // Step 1
    public void createEvent(){
        peerConnection = pcFactory.createPeerConnection(rtcConfig,
                new CustomPeerConnectionObserver() {

                    @Override
                    public void onIceCandidate(IceCandidate iceCandidate) {
                        super.onIceCandidate(iceCandidate);


                        IceCandidateServer iceCandidateServer = util.getIceCandidateServer(iceCandidate);

                        listener.onIceCandidate(iceCandidateServer);

                    }

                    @Override
                    public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {
                        super.onAddTrack(rtpReceiver, mediaStreams);

                    }

                    @Override
                    public void onAddStream(MediaStream mediaStream) {
                        super.onAddStream(mediaStream);

                        if(mediaStream.audioTracks.size() > 0) {
                            remoteAudioTrack = mediaStream.audioTracks.get(0);
                            remoteStream.addTrack(remoteAudioTrack);
                        }
                    }


                    @Override
                    public void onDataChannel(DataChannel dataChannel) {
                        super.onDataChannel(dataChannel);
                        localDataChannel = dataChannel;
                        localDataChannel.registerObserver((DataChannel.Observer)activity);

                    }

                    @Override
                    public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                        super.onIceConnectionChange(iceConnectionState);
                        if(iceConnectionState != null){
                            if(iceConnectionState == PeerConnection.IceConnectionState.CONNECTED){

                            }

                            if(iceConnectionState == PeerConnection.IceConnectionState.CLOSED){

                            }

                            if(iceConnectionState == PeerConnection.IceConnectionState.FAILED){

                            }

                        }
                    }
                });

        DataChannel.Init dcInit = new DataChannel.Init();

        dcInit.ordered = false;
        dcInit.negotiated = false;

        if (peerConnection != null) {
            localDataChannel = peerConnection.createDataChannel("1", dcInit);
            localDataChannel.registerObserver((DataChannel.Observer)activity);
            peerConnection.addStream(createStream());
        }

    }

    // Step 2
    public void createOffer(){
        peerConnection.createOffer(new CustomSdpObserver() {

            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
                super.onCreateSuccess(sessionDescription);

                Offer offer = new Offer(sessionDescription.description, sessionDescription.type);
                listener.onInvitation(offer, SessionDescription.Type.OFFER);

                peerConnection.setLocalDescription(new CustomSdpObserver(), sessionDescription);
                // sessionDescription.description is string which needs to the shared across network
            }

            @Override
            public void onCreateFailure(String s) {

                super.onCreateFailure(s);
                // Offer creation failed
            }
        }, new MediaConstraints());
    }

    public void createAnswer(SessionDescriptionData sessionDescriptionData){

        if (sessionDescriptionData != null && sessionDescriptionData.getOffer() != null) {
            SessionDescription sessionDescription = new SessionDescription(sessionDescriptionData.getOffer().getType(), sessionDescriptionData.getOffer().getSdp());
            peerConnection.setRemoteDescription(new CustomSdpObserver(), sessionDescription);


            peerConnection.createAnswer(new CustomSdpObserver() {

                @Override
                public void onCreateSuccess(SessionDescription sessionDescription) {
                    super.onCreateSuccess(sessionDescription);

                    SessionDescription sessionDescription_ = new SessionDescription(SessionDescription.Type.ANSWER, sessionDescription.description);
                    Offer answer = new Offer(sessionDescription_.description, sessionDescription_.type);

                    listener.onInvitation(answer, SessionDescription.Type.ANSWER);

                    peerConnection.setLocalDescription(new CustomSdpObserver(), sessionDescription_);
                    // sessionDescription.description is string which needs to the shared across network
                }

                @Override
                public void onCreateFailure(String s) {
                    super.onCreateFailure(s);

                }
            }, new MediaConstraints());
        }
    }

    private MediaStream createStream() {
        if (WebRtcAudioUtils.isNoiseSuppressorSupported()){
            WebRtcAudioUtils.setWebRtcBasedNoiseSuppressor(true);
        }

        if (WebRtcAudioUtils.isAcousticEchoCancelerSupported()){
            WebRtcAudioUtils.setWebRtcBasedAcousticEchoCanceler(true);
        }

        MediaConstraints audioConstraints = new MediaConstraints();
        AudioSource audioSource = pcFactory.createAudioSource(audioConstraints);
        localAudioTrack = pcFactory.createAudioTrack("ARDAMSa0", audioSource);
        localStream.addTrack(localAudioTrack);
        return localStream;
    }


}
