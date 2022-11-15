package com.rusile.web_lab3.beans;

import com.rusile.web_lab3.repo.UserRepo;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
@Getter
@Setter
public class SecurityBean {

    private String message;

    @ManagedProperty("#{userRepoImpl}")
    private UserRepo userRepo;

    private String login;

    private String pwd;

    public void validateLogin(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
        String login = String.valueOf(value);
        if (login.length() < 5 || login.length() > 20) {
            FacesMessage message = new FacesMessage("Login length must be in range [5;20]");
            throw new ValidatorException(message);
        }
    }

    public void validatePassword(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
        String pwd = String.valueOf(value);
        if (pwd.length() < 8 || pwd.length() > 20) {
            FacesMessage message = new FacesMessage("Login length must be in range [8;20]");
            throw new ValidatorException(message);
        }
    }

    public String onload() {
        if (isRegistered()) {
            return "";
        } else
            return "login.xhtml";
    }

    public String logout() {
        login = null;
        pwd = null;
        return "login.xhtml?faces-redirect=true";
    }

    public String register() {
        if (!userRepo.addUser(login, pwd)) {
            message = "Login exists!";
            return "";
        } else {
            return "index.xhtml";
        }
    }

    public String login() {
        if (!isRegistered()) {
            message = "Login or password is wrong!";
            return "";
        } else {
            return "index.xhtml";
        }
    }

    private boolean isRegistered() {
        if (login == null || pwd == null)
            return false;
        else
            return userRepo.isUserRegistered(login, pwd);
    }

}
