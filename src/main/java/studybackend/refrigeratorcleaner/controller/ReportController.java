package studybackend.refrigeratorcleaner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import studybackend.refrigeratorcleaner.service.ReportService;

import java.util.Map;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    @PostMapping("/board/report")
    public ResponseEntity<String> WE(@RequestBody Map<String,String> formData){

        String email = formData.get("email");
        Long postId = Long.valueOf(formData.get("postId"));
        if(reportService.checkDupleReport(email,postId)==1){

            return new ResponseEntity<>("no", HttpStatus.OK);
        }
        if (reportService.getReportCount(postId)>=2){
            reportService.delBoard(postId);
        }else{
            reportService.reportBoard(email,postId);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
