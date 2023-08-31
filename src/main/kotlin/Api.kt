import io.javalin.Javalin

import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.security.RouteRole
internal enum class Roles : RouteRole {
    ANYONE, USER, ADMIN
}

class Api {

    private val users = listOf(
        User("u_1", "juan", "juan", false),
        User("u_2", "a", "a", true),
        User("u_3", "b", "b", true),
    )

    private val tokenController = TokenController(users)
    private val userController = UserController(users, tokenController)


    fun start() {
        val app = Javalin.create {
        it.accessManager(tokenController::validate)
        }.start(7070)
        app.routes {
            path("/login") {
                post(userController::login, Roles.ANYONE)
            }
            path("/user") {
                get(userController::getLoginUser, Roles.USER)
                path("{id}") {
                    get(userController::getUser, Roles.ANYONE)
                    delete(userController::removeUser, Roles.ADMIN)
                }
            }
        }
    }
}

fun main() {
    Api().start()
}
