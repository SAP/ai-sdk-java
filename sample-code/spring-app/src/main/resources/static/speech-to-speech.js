const SPEECH_TO_SPEECH_URL = 'ws://localhost:8080/speech-to-speech';
const SPEECH_TO_SPEECH_SAMPLE_RATE = 24000;

const wsStatusEl = document.getElementById('speech-to-speech-websocket-status')
const micStatusEl = document.getElementById('speech-to-speech-mic-status')
const speechBtn = document.getElementById('speech-to-speech-btn')

let sts_ws = null;
let sts_audioCtx = null;
let sts_mediaStream = null;
let sts_processor = null;
let sts_nextStartTime = 0;
let sts_started = false;

async function startSession() {
    sts_ws = new WebSocket(SPEECH_TO_SPEECH_URL);
    sts_ws.binaryType = 'arraybuffer';

    sts_ws.onopen = async () => {
        wsStatusEl.textContent = `connected to ${SPEECH_TO_SPEECH_URL}`;
        wsStatusEl.style.color = 'green';

        await startMicrophone();
        speechBtn.innerText = 'Stop';
        sts_started = true;
    }

    sts_ws.onmessage = (event) => {
        if (event.data instanceof ArrayBuffer) {
            sts_playPcmAudio(event.data)
        }
    }

    sts_ws.onclose = () => stopSession();
}

async function startMicrophone() {
    try {
        sts_mediaStream = await navigator.mediaDevices.getUserMedia({audio: true});
        console.log("mediaStream is: ", sts_mediaStream)
        micStatusEl.textContent = 'active';
        micStatusEl.style.color = 'green';

        sts_audioCtx = new (window.AudioContext || window.webkitAudioContext)({sampleRate: SPEECH_TO_SPEECH_SAMPLE_RATE});

        const source = sts_audioCtx.createMediaStreamSource(sts_mediaStream);
        sts_processor = sts_audioCtx.createScriptProcessor(2048, 1, 1);
        sts_processor.onaudioprocess = (e) => {
            if (sts_ws && sts_ws.readyState === WebSocket.OPEN) {
                const float32Input = e.inputBuffer.getChannelData(0);
                const int16Buffer = new Int16Array(float32Input.length);

                for (let i = 0; i < float32Input.length; i++) {
                    let s = Math.max(-1, Math.min(1, float32Input[i]));
                    int16Buffer[i] = s < 0 ? s * 0x8000 : s * 0x7FFF;
                }
                sts_ws.send(int16Buffer.buffer);
            }
        };

        const gainNode = sts_audioCtx.createGain();
        gainNode.gain.value = 0;

        source.connect(sts_processor);
        sts_processor.connect(gainNode);
        gainNode.connect(sts_audioCtx.destination)

    } catch (err) {
        console.error('failed to find or bind microphone: ', err);
        micStatusEl.innerText = 'mic binding error';
        micStatusEl.style.color = 'red';
    }
}

function sts_playPcmAudio(arrayBuffer) {
    if (!sts_audioCtx) return;

    const int16Data = new Int16Array(arrayBuffer);
    const float32Data = new Float32Array(int16Data.length);
    for (let i = 0; i < int16Data.length; i++) {
        float32Data[i] = int16Data[i] / 32768.0;
    }

    const audioBuffer = sts_audioCtx.createBuffer(1, float32Data.length, SPEECH_TO_SPEECH_SAMPLE_RATE);
    audioBuffer.copyToChannel(float32Data, 0);
    const source = sts_audioCtx.createBufferSource();
    source.buffer = audioBuffer;
    source.connect(sts_audioCtx.destination);

    if (sts_nextStartTime < sts_audioCtx.currentTime) {
        sts_nextStartTime = sts_audioCtx.currentTime;
    }

    source.start(sts_nextStartTime);
    sts_nextStartTime += audioBuffer.duration;
}

function stopSession() {
    if (sts_ws) sts_ws.close();
    if (sts_mediaStream) sts_mediaStream.getTracks().forEach(track => track.stop());
    if (sts_processor) sts_processor.disconnect();
    if (sts_audioCtx) {
        sts_audioCtx.close();
        sts_audioCtx = null;
    }

    wsStatusEl.textContent = 'disconnected';
    wsStatusEl.style.color = 'red';
    micStatusEl.textContent = 'disabled';
    micStatusEl.style.color = 'red';
    speechBtn.innerText = 'Start';
    sts_started = false;
}

speechBtn.addEventListener('click', () => {
    sts_started ? stopSession() : startSession();
})