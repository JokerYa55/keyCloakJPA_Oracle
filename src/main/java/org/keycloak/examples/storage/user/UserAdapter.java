package org.keycloak.examples.storage.user;

import java.util.Iterator;
import org.jboss.logging.Logger;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.keycloak.models.GroupModel;

/**
 * ����� ��� ������������� ������������ ������ Keycloak
 *
 * @version 1
 * @author Vasiliy Andritsov
 *
 */
public class UserAdapter extends AbstractUserAdapterFederatedStorage {

    private static final Logger log = Logger.getLogger(UserAdapter.class);
    protected UserEntity entity;
    protected String keycloakId;

    /**
     *
     * @param session
     * @param realm
     * @param model
     * @param entity
     */
    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserEntity entity) {
        super(session, realm, model);
        log.debug("UserAdapter CONSTRUCTOR");
        this.entity = entity;
        // ���������� ID
        keycloakId = StorageId.keycloakId(model, entity.getId().toString());
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return entity.getPassword();
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        entity.setPassword(password);
    }

    /**
     *
     * @return
     */
    @Override
    public String getUsername() {
        return entity.getUsername();
    }

    /**
     *
     * @param username
     */
    @Override
    public void setUsername(String username) {
        entity.setUsername(username);

    }

    /**
     *
     * @param email
     */
    @Override
    public void setEmail(String email) {
        entity.setEmail(email);
    }

    /**
     *
     * @return
     */
    @Override
    public String getEmail() {
        return entity.getEmail();
    }

    /**
     *
     * @return
     */
    @Override
    public String getId() {
        return keycloakId;
    }

    /**
     *
     * @param name
     * @param value
     */
    @Override
    public void setSingleAttribute(String name, String value) {
        log.debug("setSingleAttribute");
        if (name.equals("phone")) {
            entity.setPhone(value);
        } else {
            super.setSingleAttribute(name, value);
        }
    }

    @Override
    public void removeAttribute(String name) {
        log.debug("removeAttribute");
        if (name.equals("phone")) {
            entity.setPhone(null);
        } else {
            super.removeAttribute(name);
        }
    }

    /**
     * ��������� ��������� �� ���������� Keycloak
     *
     * @param name ��� ���������
     * @param values �������� ��������� (������� �� ��������� ���� + �� �������
     * ���� � ������ ���������)
     */
    @Override
    public void setAttribute(String name, List<String> values) {
        log.debug("setAttribute");
        if (name.equals("phone")) {
            entity.setPhone(values.get(0));
        }
        if (name.equals("address")) {
            entity.setAddress(values.get(0));
        } else {
            super.setAttribute(name, values);
        }
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public String getFirstAttribute(String name) {
        log.debug("getFirstAttribute");
        if (name.equals("phone")) {
            return entity.getPhone();
        } else {
            return super.getFirstAttribute(name);
        }
    }

    /**
     * ����� ��������� ��������� ��������� �� ������� ���� � ��������� Keycloak
     *
     * @return ������ ���������� ������������
     */
    @Override
    public Map<String, List<String>> getAttributes() {
        log.debug("getAttributes");
        Map<String, List<String>> attrs = super.getAttributes();

        Iterator it = attrs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
            //log.info("key = "+ pair.getKey() + " val = " + pair.getValue());
            log.info("pair = " + pair.toString());
        }

        MultivaluedHashMap<String, String> all = new MultivaluedHashMap<>();
        all.putAll(attrs);
        // ��������� ���. ��������� � Keycloak
        all.add("phone", entity.getPhone());
        all.add("address", entity.getAddress());
        return all;
    }

    /**
     *
     * @param name ��� ���������
     * @return
     */
    @Override
    public List<String> getAttribute(String name) {
        log.debug("getAttribute");
        if (name.equals("phone")) {
            List<String> phone = new LinkedList<>();
            phone.add(entity.getPhone());
            return phone;
        } else {
            return super.getAttribute(name);
        }
    }

    /**
     * ����� ���������� ������ ������������. ��������� ��������� ������.
     *
     * @return ���������� ������ ����� ������������
     */
    @Override
    public Set<GroupModel> getGroups() {
        log.info("getGroups()");
        return super.getGroups(); //To change body of generated methods, choose Tools | Templates.
    }

}
