class UserLoginDTO(val username: String, val password: String)

class UserDTO(user: User) {
    val id = user.id
    val username = user.username
}