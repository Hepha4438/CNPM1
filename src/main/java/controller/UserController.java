package controller;

import model.User;
import service.UserService;

import java.util.List;
import java.util.Optional;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users") // Định nghĩa endpoint "/users"
@Produces(MediaType.APPLICATION_JSON) // Trả về dữ liệu JSON
@Consumes(MediaType.APPLICATION_JSON) // Nhận dữ liệu JSON
public class UserController {
    private final UserService userService;

    // Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Lấy danh sách người dùng
    @GET
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Lấy thông tin người dùng theo ID
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> Response.ok(value).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // Thêm người dùng mới
    @POST
    public Response registeredUser(User user) {
        User registeredUser = userService.registerUser(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
        return Response.status(Response.Status.CREATED).entity(registeredUser).build();

    }

    // Cập nhật người dùng
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return Response.ok(updatedUser).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    // Xóa người dùng
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        userService.deleteUser(id);
        return Response.noContent().build();
    }
}
