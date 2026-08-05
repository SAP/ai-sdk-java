const PCM_SAMPLE_RATE = 24000;

function createAudioContext() {
    return new AudioContext({sampleRate: PCM_SAMPLE_RATE});
}

function setStatus(element, text, active) {
    element.textContent = text;
    element.style.color = active ? '#198754' : '#6c757d';
}

function playPcmAudio(audioContext, arrayBuffer, state) {
    if (!audioContext) return;
    const intData = new Int16Array(arrayBuffer);
    const floatData = new Float32Array(intData.length);
    for (let i = 0; i < intData.length; i++) floatData[i] = intData[i] / 32768;
    const buffer = audioContext.createBuffer(1, floatData.length, PCM_SAMPLE_RATE);
    buffer.copyToChannel(floatData, 0);
    const source = audioContext.createBufferSource();
    source.buffer = buffer;
    source.connect(audioContext.destination);
    if (state.nextStartTime < audioContext.currentTime) state.nextStartTime = audioContext.currentTime;
    source.start(state.nextStartTime);
    state.nextStartTime += buffer.duration;
}
