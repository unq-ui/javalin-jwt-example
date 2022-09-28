import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

class UserController(val users: List<User>, val tokenController: TokenController) {

    fun login(ctx: Context) {
        val userLogin = ctx.bodyValidator<UserLoginDTO>()
            .check({ it.username.isNotBlank() }, "Username cannot be empty")
            .check({ it.password.isNotBlank()}, "Password cannot be empty")
            .getOrThrow { throw BadRequestResponse("??") }
        val user = users.find { it.username == userLogin.username && it.password == userLogin.password } ?: throw NotFoundResponse("Username or password wrong")
        ctx.header(tokenController.header, tokenController.userToToken(user))
        ctx.json(UserDTO(user))
    }

    fun getLoginUser(ctx: Context) {
        val user = ctx.attribute<User>("user")
        ctx.json(UserDTO(user!!))
    }

    fun getUser(ctx: Context) {
        val userId = ctx.pathParam("id")
        val user = users.find { it.id == userId } ?: throw NotFoundResponse("User not found")
        ctx.json(UserDTO(user))
    }

    fun removeUser(ctx: Context) {
        ctx.json("ya se borro ;D")
    }
}