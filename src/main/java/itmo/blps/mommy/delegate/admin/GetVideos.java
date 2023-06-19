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
//import java.util.List;
//
//import static org.camunda.spin.Spin.JSON;
//
//@Component
//@Named
//@RequiredArgsConstructor
//public class GetVideos implements JavaDelegate {
//    private final DelegateAuthChecker delegateAuthChecker;
//    private final AdminService adminService;
//
//    @Override
//    public void execute(DelegateExecution delegateExecution) {
//        try {
//            delegateAuthChecker.checkAdminAuth(delegateExecution);
//            List<Video> videos = adminService.getVideos();
//            if (videos.isEmpty()) {
//                delegateExecution.setVariable("result", "Пока список видео на оценку пуст!");
//            } else {
//                delegateExecution.setVariable("result", JSON(videos).toString());
//            }
//        } catch (Throwable throwable) {
//            delegateExecution.setVariable("error", throwable.getMessage());
//            throw new BpmnError("getVideos_error", throwable.getMessage());
//        }
//    }
//}
