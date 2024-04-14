package in.ac.iitmandi.moodleComplIntegration.controller;

import in.ac.iitmandi.moodleComplIntegration.model.EvalResponse;
import in.ac.iitmandi.moodleComplIntegration.service.EvaluationService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("eval")
public class EvaluationController {
    private static final Logger LOG = LoggerFactory.getLogger(EvaluationController.class);

    @Autowired
    EvaluationService evaluationService;

    @ApiOperation(value = "API endpoint for evaluate assignment from moodle using compl validator", notes = "This API downloads assignment from moodle and evaluates assignments using compl validator and upload grades of students on moodle.")
    @CrossOrigin(origins = {"*"})
    @RequestMapping(value = "/assignment", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<String> evaluateAssignment(@RequestParam(value = "context", required = false) String contextId,
                                                                                                  @RequestParam(value = "directoryPath") String directoryPath,
                                                                                                          HttpServletResponse response) {
        LOG.info("/assignment called.");
        String res = null;
        try {
            res = evaluationService.evaluateAssignment(contextId, directoryPath, response);
        } catch (Exception e) {
            LOG.error("Exception in evaluateAssignment : ", e);
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"*"})
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public String test() {
        LOG.info("/test called.");
        return "Test Success.";
    }
}
