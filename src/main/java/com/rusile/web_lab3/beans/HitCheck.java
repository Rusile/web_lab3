package com.rusile.web_lab3.beans;

import lombok.Data;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DoubleConverter;
import javax.faces.validator.ValidatorException;

@ManagedBean
@RequestScoped
@Data
public class AttemptBean {
    private double x;

    private double y;

    private double r;

    public void validateX(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
        String xStr = String.valueOf(value);
        double xValue;
        try {
            xValue = Double.parseDouble(xStr);
        } catch (NumberFormatException e) {
            FacesMessage message = new FacesMessage("X must be a number in range [-5;3]");
            throw new ValidatorException(message);
        }
        if (xValue < -5 || xValue > 3) {
            FacesMessage message = new FacesMessage("X must be in range [-5;3]");
            throw new ValidatorException(message);
        }
    }

    public void validateY(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
        String yStr = String.valueOf(value);
        double yValue;
        try {
            yValue = Double.parseDouble(yStr);
        } catch (NumberFormatException e) {
            FacesMessage message = new FacesMessage("Y must be a number in range [-3;3]");
            throw new ValidatorException(message);
        }
        if (yValue < -3 || yValue > 3) {
            FacesMessage message = new FacesMessage("Y must be in range [-3;3]");
            throw new ValidatorException(message);
        }

    }

    public void validateR(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
        String rStr = String.valueOf(value);
        double rValue;
        try {
            rValue = Double.parseDouble(rStr);
        } catch (NumberFormatException e) {
            FacesMessage message = new FacesMessage("R must be a number in range [2;5]");
            throw new ValidatorException(message);
        }
        if (rValue < 2 || rValue > 5) {
            FacesMessage message = new FacesMessage("R must be in range [2;5]");
            throw new ValidatorException(message);
        }
    }
}
