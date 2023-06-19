package itmo.blps.mommy.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
@Component
public class RequestLogFilter extends OncePerRequestFilter {

    private BufferedReader reader;

    public void setReader(HttpServletRequest request) throws IOException {
        if (reader == null) {
            reader = request.getReader();
        }
    }

    private String extractPostRequestBody(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s;
            try {
                s = new Scanner(request.getInputStream(), StandardCharsets.UTF_8).useDelimiter("\\A");
                return s.hasNext() ? s.next() : "";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest wrappedRequest = new BufferedServletRequestWrapper(request);
        String body = extractPostRequestBody(wrappedRequest);
        log.info(wrappedRequest.getRequestURL().toString());
        log.info(body);
//        reader.close();
        filterChain.doFilter(wrappedRequest, response);
    }
}
