const WS_URL = 'ws://localhost:8080/text-to-speech';
const SAMPLE_RATE = 24000;

const statusEl = document.getElementById('text-to-speech-status')
const textInput = document.getElementById('text-to-speech-input')
const sendBtn = document.getElementById('text-to-speech-send-btn')

let audioCtx;
let nextStartTime = 0;

let ws = new WebSocket(WS_URL);
ws.binaryType = 'arraybuffer';

ws.onopen = () => {
    statusEl.textContent = `connected to ${WS_URL}`;
    statusEl.style.color = 'green';
    textInput.disabled = false;
    sendBtn.disabled = false;
};

ws.onclose = () => {
    statusEl.textContent = 'disconnected';
    statusEl.style.color = 'red';
    textInput.disabled = true;
    sendBtn.disabled = true;
}

ws.onerror = (error) => {
    console.error('text-to-speech WebSocket error:', error);
    statusEl.textContent = 'connection error'
    statusEl.style.color = 'red';
}

ws.onmessage = (event) => {
    if (event.data instanceof ArrayBuffer) {
        playPcmAudio(event.data);
    } else {
        console.log('text data received:', event.data)
    }
}

sendBtn.addEventListener('click', () => {
    const text = textInput.value.trim();
    if (text && ws.readyState === WebSocket.OPEN) {
        if (!audioCtx) {
            audioCtx = new (window.AudioContext || window.webkitAudioContext)({ sampleRate: SAMPLE_RATE});
        }

        const encoder = new TextEncoder();
        const binaryData = encoder.encode(text);
        ws.send(binaryData);
        textInput.value = '';
    }
})

function playPcmAudio(arrayBuffer) {
    if (!audioCtx) return;

    const int16Data = new Int16Array(arrayBuffer);
    const float32Data = new Float32Array(int16Data.length);
    for (let i = 0; i < int16Data.length; i++) {
        float32Data[i] = int16Data[i] / 32768.0;
    }

    const audioBuffer = audioCtx.createBuffer(1, float32Data.length, SAMPLE_RATE);
    audioBuffer.copyToChannel(float32Data, 0);
    const source = audioCtx.createBufferSource();
    source.buffer = audioBuffer;
    source.connect(audioCtx.destination);

    if (nextStartTime < audioCtx.currentTime) {
        nextStartTime = audioCtx.currentTime;
    }

    source.start(nextStartTime);
    nextStartTime += audioBuffer.duration;
}