package com.zziri.todo.service;

import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.User;
import com.zziri.todo.exception.custom.UserNotFoundException;
import com.zziri.todo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Response<User> findById(Long id) {
        User user = userRepo.findById(id).orElseThrow(UserNotFoundException::new);

        return Response.<User>builder()
                .data(user).build();
    }

    public Response<List<User>> findAllUser() {
        return Response.<List<User>>builder()
                .data(userRepo.findAll()).build();
    }

    public Response<User> post(String account, String name) {
        User user = User.builder()
                .account(account)
                .name(name).build();
        userRepo.save(user);

        return Response.<User>builder()
                .data(user).build();
    }

    public Response<User> modify(long id, String account, String name) {
        User user = userRepo.findById(id).orElseThrow(UserNotFoundException::new);
        user.setAccount(account);
        user.setName(name);

        return Response.<User>builder()
                .data(userRepo.save(user)).build();
    }

    public Response<User> delete(long id) {
        User user = userRepo.findById(id).orElseThrow(UserNotFoundException::new);
        userRepo.delete(user);

        return Response.<User>builder()
                .data(user).build();
    }
}