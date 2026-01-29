package sn.edu.ept.git.dic2.HelloSpringBoot.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sn.edu.ept.git.dic2.HelloSpringBoot.entities.LoggingRequest;
import sn.edu.ept.git.dic2.HelloSpringBoot.repositories.LoggingRequestRepository;

import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    private final LoggingRequestRepository loggingRequestRepository;

    public LoggingFilter(LoggingRequestRepository loggingRequestRepository) {
        this.loggingRequestRepository = loggingRequestRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        logger.debug("Journalisation");
        logger.info("URL: " + request.getRequestURL());

        LoggingRequest loggingRequest = new LoggingRequest();
        loggingRequest.setUrl(request.getRequestURI());
        loggingRequest.setMethod(request.getMethod());
        loggingRequest.setQueryParams(request.getQueryString());
        loggingRequest.setAdresseIP(request.getRemoteAddr());
        loggingRequestRepository.save(loggingRequest);

        filterChain.doFilter(request, response);

        loggingRequest.setStatus(response.getStatus());
        loggingRequestRepository.save(loggingRequest);
    }
}
