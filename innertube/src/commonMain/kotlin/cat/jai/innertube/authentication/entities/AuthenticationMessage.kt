package cat.jai.innertube.authentication.entities

class AuthenticationMessage(val type: AuthenticationMessageType, val message: String? = null) {
    companion object {
        fun pending(): AuthenticationMessage {
            return AuthenticationMessage(AuthenticationMessageType.PENDING)
        }

        fun success(): AuthenticationMessage {
            return AuthenticationMessage(AuthenticationMessageType.SUCCESS)
        }

        fun failure(message: String): AuthenticationMessage {
            return AuthenticationMessage(AuthenticationMessageType.FAILURE, message)
        }

        fun postCode(code: String): AuthenticationMessage {
            return AuthenticationMessage(AuthenticationMessageType.POST_CODE, code)
        }

    }
}