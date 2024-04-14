package in.ac.iitmandi.moodleComplIntegration.service;

import in.ac.iitmandi.moodleComplIntegration.client.ComplClient;
import in.ac.iitmandi.moodleComplIntegration.util.EvalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    private static final Logger LOG = LoggerFactory.getLogger(EvaluationServiceImpl.class);
    @Autowired
    ComplClient complClient;
    @Override
    public String evaluateAssignment(String contextId, String dirPath, HttpServletResponse response) {
        Map<String, Integer> asgMarksMap = new HashMap<>();
        try {
            try (Stream<Path> paths = Files.walk(Paths.get(dirPath))) {
                paths
                        .filter(Files::isRegularFile)
                        .forEach(path -> {
                            Integer marks = complClient.uploadFileAndEvaluate(path.toFile());
                            if(marks != null)
                                asgMarksMap.put(path.getFileName().toString().split("-")[0], marks);
                        });
            }
        } catch (Exception e) {
            LOG.error("Exception in evaluateAssignment : ", e);
        }

        if(!asgMarksMap.isEmpty()) {
            return generateAndDownloadResults(asgMarksMap, dirPath, response);
        }
        return null;
    }

    private String generateAndDownloadResults(Map<String, Integer> asgResult, String dirPath, HttpServletResponse response) {
        try {
            // File generation phase
            EvalUtil.writeDataToFile(asgResult, dirPath);
            LOG.info("Excel generation completed.");
        } catch (Exception e) {
            LOG.error("Error while writing assignment marks to excel.", e);
            return "Error while writing assignment marks to excel.";
        }
        // Downloading phase
        try {
            File file = new File(dirPath + "/AssignmentResult.xlsx");
            if (file.exists()) {

                //get the mimetype
                String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                if (mimeType == null) {
                    //unknown mimetype so set the mimetype to application/octet-stream
                    mimeType = "application/octet-stream";
                }

                response.setContentType(mimeType);

                response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

                response.setContentLength((int) file.length());

                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

                FileCopyUtils.copy(inputStream, response.getOutputStream());
                LOG.info("Download successful.");
                File folder = new File(dirPath);
                EvalUtil.deleteDirectory(folder);
                folder.delete();
                return "Success";
            }
        } catch (IOException e) {
            LOG.error("Error while uploading Assignment Marks excel.", e);
            return "Error while uploading Assignment Marks excel.";
        }
        return null;
    }
}
