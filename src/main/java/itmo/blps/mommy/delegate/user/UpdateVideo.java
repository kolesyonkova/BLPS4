//package itmo.blps.mommy.delegate.user;
//
//import black.orange.rutube.converter.VideoConverter;
//import black.orange.rutube.entity.Video;
//import black.orange.rutube.service.DelegateAuthChecker;
//import black.orange.rutube.service.VideoService;
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
//public class UpdateVideo implements JavaDelegate {
//    private final DelegateAuthChecker delegateAuthChecker;
//    private final VideoConverter videoConverter;
//    private final VideoService videoService;
//
//    @Override
//    public void execute(DelegateExecution delegateExecution) {
//        try {
//            delegateAuthChecker.checkUserAuth(delegateExecution);
//            String name = String.valueOf(delegateExecution.getVariable("name"));
//            String link = String.valueOf(delegateExecution.getVariable("link"));
//            Video video = videoService.updateVideo(videoConverter.toDto(name, link));
//
//            delegateExecution.setVariable("result", JSON(video).toString());
//        } catch (Throwable throwable) {
//            delegateExecution.setVariable("error", throwable.getMessage());
//            throw new BpmnError("update_error", throwable.getMessage());
//        }
//    }
//}
