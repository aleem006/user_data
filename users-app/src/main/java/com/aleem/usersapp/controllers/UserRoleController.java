package com.aleem.usersapp.controllers;

import com.aleem.usersapp.entities.UserRole;
import com.aleem.usersapp.entities.ui.UserRoleUpdateUI;
import com.aleem.usersapp.repositories.UserRoleReporsitory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-roles")
public class UserRoleController {

    private UserRoleReporsitory reporsitory;

    public UserRoleController(UserRoleReporsitory reporsitory) {
        this.reporsitory = reporsitory;
    }

    /*
     * Return all users
     * */
    @GetMapping
    public List<UserRole> findAll() {
        return reporsitory.findAll();
    }

    @GetMapping("{userId}/{unitId}")
    public List<UserRole> findAllByUnitAndUser(@PathVariable Long userId, @PathVariable Long unitId) {
        return reporsitory.findAllByUser_IdAndUnit_Id(userId, unitId);
    }

    @GetMapping("{userId}/{unitId}/{timestamp}")
    public List<UserRole> findAllUserRolesByUserAndUnitAndTimestamp(@PathVariable Long userId, @PathVariable Long unitId, @PathVariable String timestamp) {
        LocalDateTime dateTime = LocalDateTime.parse(timestamp);
        List<UserRole> roles = new ArrayList<>();
        reporsitory.findAllByUser_IdAndUnit_Id(userId, unitId).forEach(userRole -> {
            if (userRole.isValidWithDateTime(dateTime)) {
                roles.add(userRole);
            }
        });
        return roles;
    }

    @PutMapping("{id}/{version}")
    public ResponseEntity update(@PathVariable Long id,@PathVariable Long version, @RequestBody UserRoleUpdateUI userRoleUpdateUI){
        UserRole role = reporsitory.getOne(id);
        if(role != null){ // if found role
            if(role.getVersion().equals(version)){
                if(userRoleUpdateUI.getValidFrom() == null){
                    return new ResponseEntity<>("Valid from timestamps must be provided", HttpStatus.BAD_REQUEST);
                }
                role.setValidFrom(userRoleUpdateUI.getValidFrom());
                role.setValidTo(userRoleUpdateUI.getValidTo());

                if(!role.isValid()){
                    return new ResponseEntity<>("Invalid timestamps", HttpStatus.BAD_REQUEST);
                }

                return new ResponseEntity<>(reporsitory.save(role), HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Version mismatch", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("User role not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UserRole userRole) {

        userRole.setVersion(1L);

        if (userRole.getValidFrom() == null) {
            userRole.setValidFrom(LocalDateTime.now());
        }
        if (userRole.getValidTo() != null && userRole.getValidFrom().isAfter(userRole.getValidTo())) {
            return new ResponseEntity<>("Invalid Timestamps provided", HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(reporsitory.save(userRole), HttpStatus.OK);
    }

    @DeleteMapping("{id}/{version}")
    public ResponseEntity delete(@PathVariable Long id, @PathVariable Long version) {
        UserRole role = reporsitory.getOne(id);
        if (role != null) {
            if (role.getVersion().equals(version)) {
                reporsitory.delete(role);
                return new ResponseEntity<>("User role deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Version mismatched", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("User role not found", HttpStatus.NOT_FOUND);
        }

    }


}
