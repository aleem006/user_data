package com.aleem.usersapp.controllers;

import com.aleem.usersapp.entities.Unit;
import com.aleem.usersapp.entities.User;
import com.aleem.usersapp.repositories.UnitReporsitory;
import com.aleem.usersapp.repositories.UserReporsitory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {

    private UnitReporsitory reporsitory;

    public UnitController(UnitReporsitory reporsitory) {
        this.reporsitory = reporsitory;
    }

    /*
    * Return all units
    * */
    @GetMapping
    public List<Unit> findAll(){
        return reporsitory.findAll();
    }
}
