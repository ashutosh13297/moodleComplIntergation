package in.ac.iitmandi.moodleComplIntegration.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
@Service
public class ComplClient {
    private static final Logger LOG = LoggerFactory.getLogger(ComplClient.class);
    @Autowired
    RestTemplate restTemplateForCompl;

    private final static String serverUrl = "http://localhost:3011/eval";

    public Integer uploadFileAndEvaluate(File file) {
        if(file != null) {
            try {
                FileSystemResource fs = new FileSystemResource(file);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("AssnFile", fs);
                body.add("Course", "CS502");
                body.add("Assignment", "Assignment3");
                body.add("Secret", "Excadrill");

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

                ResponseEntity<String> response = restTemplateForCompl.postForEntity(serverUrl, requestEntity, String.class);

                String resBody = response.getBody();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode actualObj = mapper.readTree(resBody);
                Integer marks = 0;
                if(actualObj.get("out") != null) {
                    String out = actualObj.get("out").asText();
                    String[] res = out.split("\n");
                    if(res.length > 1)
                        marks = Integer.parseInt(res[res.length - 1].split(":")[1].trim());
                }
                return marks;
            } catch (Exception e) {
                LOG.error("Exception in uploadFileAndEvaluate : ", e);
            }
        }
        return null;
    }
}
