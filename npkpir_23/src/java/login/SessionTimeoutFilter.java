/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */

public class SessionTimeoutFilter  {

//WebFilter("/*")
//public class SessionTimeoutFilter implements Filter {
    @Inject
    private UserController userController;

    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();

        // Pomijanie zasobów statycznych takich jak CSS, JS, obrazy
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg") || uri.endsWith(".jpeg") 
                || uri.endsWith(".png") || uri.endsWith(".gif") || uri.endsWith(".woff") || uri.endsWith(".woff2") 
                || uri.endsWith(".ttf") || uri.endsWith(".eot")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session != null && isSessionExpired(session)) {
            userController.logout(httpRequest);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isSessionExpired(HttpSession session) {
    // Określ czas ważności sesji w milisekundach (np. 30 minut)
    long SESSION_TIMEOUT = 60 * 60 * 1000; // 30 minut w milisekundach

    // Pobierz czas ostatniej aktywności z sesji
    Long lastAccessedTime = (Long) session.getLastAccessedTime();

    if (lastAccessedTime == null) {
        // Jeśli nie ma informacji o ostatniej aktywności, traktujemy sesję jako wygasłą
        return true;
    }

    // Sprawdź, czy czas ostatniej aktywności przekroczył dopuszczalny limit
    long currentTime = System.currentTimeMillis();
    if ((currentTime - lastAccessedTime) > SESSION_TIMEOUT) {
        return true; // Sesja wygasła
    }

    // Sesja jest nadal aktywna
    return false;
}

}

