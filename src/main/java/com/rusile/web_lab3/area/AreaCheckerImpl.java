package com.rusile.web_lab3.area;

import com.rusile.web_lab3.beans.HitCheck;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class AreaCheckerImpl implements AreaChecker {
    @Override
    public boolean isHit(HitCheck hitCheck) {
        return isInCircle(hitCheck) || isInTriangle(hitCheck) || isInSquare(hitCheck);
    }

    private boolean isInCircle(HitCheck hitCheck) {
        return hitCheck.getX() <= 0 && hitCheck.getY() <= 0 &&
                Math.pow(hitCheck.getX(), 2) + Math.pow(hitCheck.getY(), 2) <= Math.pow(hitCheck.getR()/2, 2);
    }

    private boolean isInTriangle(HitCheck hitCheck) {
        return hitCheck.getX() >= 0 && hitCheck.getY() >= 0 &&
                hitCheck.getY() <= 0.5 * hitCheck.getX() + hitCheck.getR();
    }


    private boolean isInSquare(HitCheck hitCheck) {
        return hitCheck.getX() >= 0 && hitCheck.getY() <= 0 &&
                hitCheck.getX() <= hitCheck.getR() / 2 && hitCheck.getY() >= hitCheck.getR() * -1;
    }
}
