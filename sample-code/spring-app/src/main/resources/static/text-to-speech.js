const textToSpeechStatusElement = document.getElementById('text-to-speech-status');
const textToSpeechInput = document.getElementById('text-to-speech-input');
const textToSpeechSendButton = document.getElementById('text-to-speech-send-btn');
let textToSpeechAudioContext, textToSpeechWebSocket, textToSpeechState = {nextStartTime: 0};

setStatus(textToSpeechStatusElement, 'ready', false);

textToSpeechSendButton.onclick = () => {
    const text = textToSpeechInput.value.trim();
    if (!text) return;
    const data = new TextEncoder().encode(text);
    if (textToSpeechWebSocket?.readyState === WebSocket.OPEN) {
        textToSpeechWebSocket.send(data);
        textToSpeechInput.value = '';
        return;
    }
    textToSpeechWebSocket = new WebSocket('ws://localhost:8080/text-to-speech');
    textToSpeechWebSocket.binaryType = 'arraybuffer';
    textToSpeechWebSocket.onopen = () => {
        setStatus(textToSpeechStatusElement, 'connected', true);
        textToSpeechAudioContext ??= createAudioContext();
        textToSpeechWebSocket.send(data);
        textToSpeechInput.value = '';
    };
    textToSpeechWebSocket.onclose = () => {
        setStatus(textToSpeechStatusElement, 'ready', false);
        textToSpeechWebSocket = null;
    };
    textToSpeechWebSocket.onerror = () => {
        setStatus(textToSpeechStatusElement, 'error', false);
        textToSpeechWebSocket = null;
    };
    textToSpeechWebSocket.onmessage = (event) => {
        if (event.data instanceof ArrayBuffer) playPcmAudio(textToSpeechAudioContext, event.data, textToSpeechState);
    };
};
