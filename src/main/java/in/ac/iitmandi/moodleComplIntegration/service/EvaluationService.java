package in.ac.iitmandi.moodleComplIntegration.service;

import in.ac.iitmandi.moodleComplIntegration.model.EvalResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface EvaluationService {
    String evaluateAssignment(String contextId, String dirPath, HttpServletResponse response);
}
