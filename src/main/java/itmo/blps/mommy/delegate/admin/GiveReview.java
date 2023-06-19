//package itmo.blps.mommy.delegate.admin;
//
//import black.orange.rutube.entity.Video;
//import black.orange.rutube.service.AdminService;
//import black.orange.rutube.service.DelegateAuthChecker;
//import lombok.RequiredArgsConstructor;
//import org.camunda.bpm.engine.delegate.BpmnError;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.delegate.JavaDelegate;
//import org.springframework.stereotype.Component;
//
//import javax.inject.Named;
//
//import static org.camunda.spin.Spin.JSON;
//
//@Component
//@Named
//@RequiredArgsConstructor
//public class GiveReview implements JavaDelegate {
//    private final DelegateAuthChecker delegateAuthChecker;
//    private final AdminService adminService;
//
//    @Override
//    public void execute(DelegateExecution delegateExecution) {
//        try {
//            delegateAuthChecker.checkAdminAuth(delegateExecution);
//
//            long videoId = Long.parseLong(String.valueOf(delegateExecution.getVariable("videoId")));
//            boolean isApproved = Boolean.parseBoolean(String.valueOf(delegateExecution.getVariable("isApproved")));
//
//            Video video = adminService.giveReview(videoId, isApproved);
//            delegateExecution.setVariable("result", JSON(video).toString());
//        } catch (Throwable throwable) {
//            delegateExecution.setVariable("error", throwable.getMessage());
//            throw new BpmnError("giveReview_error", throwable.getMessage());
//        }
//    }
//}
