/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.MultiuserSettings;
import entity.Uz;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class MultiuserSettingsDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;

    public MultiuserSettingsDAO() {
        super(MultiuserSettings.class);
    }
    
    public List<MultiuserSettings> findByUser(Uz user) {
        return sessionFacade.findMutliuserSettingsByUz(user);
    }
}
