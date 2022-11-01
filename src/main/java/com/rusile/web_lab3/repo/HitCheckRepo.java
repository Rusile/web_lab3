package com.rusile.web_lab3.repo;

import com.rusile.web_lab3.beans.HitCheck;

import java.util.List;

public interface HitCheckRepo {

    void deleteAll(String id);

    void save(String id, HitCheck hitCheck);

    List<HitCheck> getAllResults(String id);
}
