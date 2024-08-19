package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiClient.JACKSON;

import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;

@Slf4j
@RequiredArgsConstructor
class OpenAiStreamingHandler {

  public static OpenAiChatCompletionStream handleResponse(ClassicHttpResponse response)
      throws IOException {
    return getContent(response.getEntity().getContent());
  }

  /**
   * @param inputStream
   * @return
   * @author stippi
   */
  public static OpenAiChatCompletionStream getContent(InputStream inputStream) {

    OpenAiChatCompletionOutput total = null;
    BufferedReader br =
        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

    Stream<OpenAiChatCompletionOutput> delta =
        br.lines()
            .filter(
                responseLine ->
                    !responseLine.isEmpty() && !"data: [DONE]".equals(responseLine.trim()))
            .map(
                responseLine -> {
                  // TODO: handle errors
                  //       {
                  //         "error": {
                  //           "code": "429",
                  //           "message": "exceeded token rate limit"
                  //         }
                  //       }
                  String data = responseLine.substring(5).replace("delta", "message");
                  try {
                    return JACKSON.readValue(data, OpenAiChatCompletionOutput.class);
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                });
    var outputStream = new OpenAiChatCompletionStream();
    outputStream.setDelta(delta);
    return outputStream;
  }
}
