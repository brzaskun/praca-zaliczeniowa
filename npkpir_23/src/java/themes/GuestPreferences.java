/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package themes;

/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import dao.UzDAO;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import view.WpisView;
@ManagedBean
@RequestScoped
public class GuestPreferences implements Serializable {

        private String theme = "redmond"; //default
        @Inject private UzDAO uzDAO;
        @ManagedProperty(value="#{WpisView}")
        private WpisView wpisView;
        
        
        public String getTheme() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            Principal principal = request.getUserPrincipal();
            String kto = principal.getName();
            Uz ktoUz = uzDAO.findUzByLogin(kto);
            theme = ktoUz.getTheme();
        } catch (Exception e) {
            E.e(e);
        }
        return theme;
    }

        public void setTheme(String theme) {
                this.theme = theme;
                Uz tmp = wpisView.getUzer();
                tmp.setTheme(theme);
                uzDAO.edit(tmp);
        }

    public UzDAO getUzDAO() {
        return uzDAO;
    }

    public void setUzDAO(UzDAO uzDAO) {
        this.uzDAO = uzDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

   
    
        
}

