package com.rusile.web_lab3.beans;

import com.rusile.web_lab3.repo.HitCheckRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean(eager = true)
@SessionScoped
@Getter
@Setter
@NoArgsConstructor
public class Table {

    private Double currentR = 2D;

    private List<HitCheck> results = new ArrayList<>();

    @ManagedProperty("#{hitCheckRepoImpl}")
    private HitCheckRepo repo;

    public void clear() {
        results.clear();
        repo.deleteAll(gitSessionId());
    }

    public List<HitCheck> getAllResults() {
        results = repo.getAllResults(gitSessionId());
        return results;
    }

    public List<HitCheck> getAllByR() {
        return results.stream().filter(p -> Double.compare(p.getR(), currentR) == 0).collect(Collectors.toList());
    }

    public void addToTable(HitCheck hitCheck) {
        currentR = hitCheck.getR();
        results.add(hitCheck);
        repo.save(gitSessionId(), hitCheck);
    }

    private String gitSessionId() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        return  session.getId();
    }
}
