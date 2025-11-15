package az.ingress.client.decoder;

import az.ingress.exception.CustomFeignException;
import az.ingress.exception.ErrorMessage;
import az.ingress.logger.ApplicationLogger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static az.ingress.client.decoder.JsonNodeFieldName.MESSAGE;
import static az.ingress.exception.ErrorMessage.CLIENT_ERROR;

@Component
@RequiredArgsConstructor
public class CustomErrorDecoder implements ErrorDecoder {

    private final ApplicationLogger log = ApplicationLogger.getLogger(CustomErrorDecoder.class);
    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String s, Response response) {
        var errorMessage = CLIENT_ERROR.getValue();
        var statusCode = response.status();

        log.error("ActionLog.decode.error from url {} with statusCode {} ", response.request().url(), statusCode);

        try(var body = response.body().asInputStream()){
            var jsonNode = objectMapper.readValue(body, JsonNode.class);

            if (jsonNode.has(MESSAGE.getValue()))
                errorMessage = jsonNode.get(MESSAGE.getValue()).asText();

            throw new CustomFeignException(errorMessage, statusCode);
        }catch (Exception ex){
            log.error("ActionLog.decoder.error ", ex);
            throw new CustomFeignException(errorMessage, statusCode);
        }
    }
}
