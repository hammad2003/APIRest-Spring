package com.example.servingwebcontent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    public List<UserDTO> readAll(){
        List<UserDTO> userDTOS;
        userDTOS = userService.readAllusers().stream().map(UserDTO::new).toList();
        return userDTOS;
    }

    public UserDTO getUserById(Integer id) {
        return new UserDTO(userService.getUserById(id));
    }

    @PostMapping
    public UserDTO addUser(@RequestBody User user) {
        return new UserDTO(userService.addUser(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        return new UserDTO(userService.updateUser(user));
    }

    @PatchMapping("/{id}")
    public UserDTO modifyUser(@PathVariable Integer id, @RequestBody JsonPatch jsonPatch) {
        User existingUser = userService.getUserById(id);
        User patchedUser = applyPatch(jsonPatch, existingUser);
        return new UserDTO(userService.modifyUser(patchedUser));
    }

    private User applyPatch(JsonPatch jsonPatch, User targetUser) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = jsonPatch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
            return objectMapper.treeToValue(patched, User.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException("Failed to apply patch", e);
        }
    }
}