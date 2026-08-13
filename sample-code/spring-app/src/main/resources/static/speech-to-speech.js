const speechWebSocketStatus = document.getElementById('speech-to-speech-websocket-status');
const speechMicrophoneStatus = document.getElementById('speech-to-speech-mic-status');
const speechButton = document.getElementById('speech-to-speech-btn');
let speechWebSocket, speechAudioContext, speechMediaStream, speechWorkletNode,
    speechStarted = false, speechState = {nextStartTime: 0};

const WORKLET_CODE = `class P extends AudioWorkletProcessor{process(inputs){const channel=inputs[0]?.[0];if(channel){const buffer=new Int16Array(channel.length);for(let i=0;i<channel.length;i++){const sample=Math.max(-1,Math.min(1,channel[i]));buffer[i]=sample<0?sample*0x8000:sample*0x7FFF}this.port.postMessage(buffer.buffer,[buffer.buffer])}return true}}registerProcessor('mic-processor',P)`;

async function startSpeechSession() {
    speechWebSocket = new WebSocket('ws://localhost:8080/speech-to-speech');
    speechWebSocket.binaryType = 'arraybuffer';
    speechWebSocket.onopen = async () => {
        setStatus(speechWebSocketStatus, 'socket: connected', true);
        try {
            speechMediaStream = await navigator.mediaDevices.getUserMedia({audio: true});
            setStatus(speechMicrophoneStatus, 'microphone: active', true);
            speechAudioContext = createAudioContext();
            const workletUrl = URL.createObjectURL(new Blob([WORKLET_CODE], {type: 'application/javascript'}));
            await speechAudioContext.audioWorklet.addModule(workletUrl);
            URL.revokeObjectURL(workletUrl);
            speechWorkletNode = new AudioWorkletNode(speechAudioContext, 'mic-processor');
            speechWorkletNode.port.onmessage = (event) => {
                if (speechWebSocket?.readyState === WebSocket.OPEN) speechWebSocket.send(event.data);
            };
            speechAudioContext.createMediaStreamSource(speechMediaStream).connect(speechWorkletNode);
        } catch (error) {
            console.error('Microphone error:', error);
            setStatus(speechMicrophoneStatus, 'mic error', false);
        }
        speechButton.textContent = 'Stop';
        speechStarted = true;
    };
    speechWebSocket.onmessage = (event) => {
        if (event.data instanceof ArrayBuffer) playPcmAudio(speechAudioContext, event.data, speechState);
    };
    speechWebSocket.onclose = () => stopSpeechSession();
}

function stopSpeechSession() {
    speechWebSocket?.close();
    speechMediaStream?.getTracks().forEach(track => track.stop());
    speechWorkletNode?.disconnect();
    if (speechAudioContext) {
        speechAudioContext.close();
        speechAudioContext = null;
    }
    setStatus(speechWebSocketStatus, 'socket: disconnected', false);
    setStatus(speechMicrophoneStatus, 'microphone: disabled', false);
    speechButton.textContent = 'Start';
    speechStarted = false;
}

speechButton.onclick = () => speechStarted ? stopSpeechSession() : startSpeechSession();
