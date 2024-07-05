package studybackend.refrigeratorcleaner.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.entity.Report;
import studybackend.refrigeratorcleaner.repository.DeleteBoard;
import studybackend.refrigeratorcleaner.repository.DeleteReport;
import studybackend.refrigeratorcleaner.repository.ReportRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final DeleteBoard deleteBoard;
    private final DeleteReport deleteReport;
    public  int checkDupleReport(String email,Long postId) {
        return reportRepository.checkDupleReport(email,postId).size();
    }
    public int getReportCount(Long postId){
        return  reportRepository.getReportCount(postId).size();
    }
    public void delBoard(Long postId){
        deleteBoard.deleteById(postId);
        deleteReport.deleteByPostId(postId);
    }
    public void reportBoard(String email,Long postId){
        Report report = Report.builder().
                email(email).
                postId(postId).
                build();

        reportRepository.reportBoard(report);
    }
}
