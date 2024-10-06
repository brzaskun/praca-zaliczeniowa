/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */

public class GlobalSessionFilter  {
//@WebFilter("/*")
//public class GlobalSessionFilter implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();

        // Pomijanie zasobów statycznych takich jak CSS, JS, obrazy
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg") || uri.endsWith(".jpeg")
                || uri.endsWith(".png") || uri.endsWith(".gif") || uri.endsWith(".woff") || uri.endsWith(".woff2")
                || uri.endsWith(".ttf") || uri.endsWith(".eot")) {
            chain.doFilter(request, response);
            return;
        }

        // Logika filtrowania dla pozostałych zasobów
        HttpSession session = httpRequest.getSession(false);
        chain.doFilter(request, response);
//        if (session == null) {
//            //trzeba dac cos innego bo teraz to petla przekierowan
////            HttpServletResponse httpResponse = (HttpServletResponse) response;
////            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
//        } else {
//            chain.doFilter(request, response);
//        }
    }
}
