package com.aleem.usersapp.controllers;

import com.aleem.usersapp.entities.Role;
import com.aleem.usersapp.entities.Unit;
import com.aleem.usersapp.repositories.RoleReporsitory;
import com.aleem.usersapp.repositories.UnitReporsitory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleReporsitory reporsitory;

    public RoleController(RoleReporsitory reporsitory) {
        this.reporsitory = reporsitory;
    }

    /*
    * Return all roles
    * */
    @GetMapping
    public List<Role> findAll(){
        return reporsitory.findAll();
    }


    @PostMapping
    public Role save(@RequestBody Role role){
        return reporsitory.save(role);
    }
}
