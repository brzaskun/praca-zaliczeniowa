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


import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
public class GuestPreferences implements Serializable {

        private String theme = "glass-x"; //default

        public String getTheme() {
                Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                if(params.containsKey("theme")) {
                        theme = params.get("theme");
                }
                
                return theme;
        }

        public void setTheme(String theme) {
                this.theme = theme;
        }
}
