package com.aleem.usersapp.controllers;

import com.aleem.usersapp.entities.User;
import com.aleem.usersapp.repositories.UserReporsitory;
import com.aleem.usersapp.repositories.UserRoleReporsitory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private UserReporsitory reporsitory;
    private UserRoleReporsitory userRoleReporsitory;

    public UserController(UserReporsitory reporsitory, UserRoleReporsitory userRoleReporsitory) {
        this.reporsitory = reporsitory;
        this.userRoleReporsitory = userRoleReporsitory;
    }

    /*
    * Return all users
    * */
    @GetMapping
    public List<User> findAll(){
        return reporsitory.findAll();
    }

    @GetMapping("{unitId}/{timestamp}")
    public ResponseEntity findByUnitAndRole(@PathVariable Long unitId, @PathVariable String timestamp){
        List<User> users = new ArrayList<>();
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(timestamp);
        }catch (Exception ex){
            return new ResponseEntity<>("Invalid timestamp provided", HttpStatus.BAD_REQUEST);
        }
        userRoleReporsitory.findByUnit_Id(unitId).forEach(userRole -> {
            if(userRole.isValidWithDateTime(dateTime)){
                users.add(userRole.getUser());
            }
        });
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public User save(@RequestBody User user){
        return reporsitory.save(user);
    }

    @PutMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody User user){
        return reporsitory.findById(id).map(user1 -> {
            user1.setName(user.getName());
            user1.setVersion(user.getVersion());
            return reporsitory.save(user1);
        }).orElse(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        User user = reporsitory.getOne(id);
        if(user != null){
            if(userRoleReporsitory.existsByUser(user)){ // if user has role
                return new ResponseEntity<>("Cannot delete user. It has registered user roles", HttpStatus.BAD_REQUEST);
            }else{
                reporsitory.delete(user);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
    }
}
