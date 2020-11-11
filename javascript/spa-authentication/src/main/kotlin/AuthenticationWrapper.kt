import com.ccfraser.muirwik.components.MColor
import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.mCircularProgress
import kotlinx.css.LinearDimension
import kotlinx.css.TextAlign
import kotlinx.css.marginTop
import kotlinx.css.textAlign
import react.RProps
import react.functionalComponent
import react.useEffect
import react.useState
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface AuthenticationWrapperProps: RProps {
    var tokenProvider: TokenProvider
}

private object AuthenticationWrapperClasses : StyleSheet("AuthenticationWrapper", isStatic = true) {
    val authContainer by css {
        textAlign = TextAlign.center
        marginTop = LinearDimension("150px")
    }
}

val authenticationWrapper = functionalComponent<AuthenticationWrapperProps> { props ->

    val (isTokenSet, setIsTokenSet) = useState(false)
    val useAuth0 = useAuth0()
    val tokenProvider = props.tokenProvider

    useEffect(listOf(useAuth0.isAuthenticated)) {
        console.log("isAuth changed", useAuth0.isAuthenticated)
        if (useAuth0.isAuthenticated) {
            useAuth0.getAccessTokenSilently(EMPTY_GET_TOKEN_SILENTLY_OPTIONS).then { token ->
                tokenProvider.initializeToken(token)
                setIsTokenSet(true)
            }
        } else {
            tokenProvider.revokeToken()
            setIsTokenSet(false)
        }
    }

    console.log(useAuth0)
    when {
        useAuth0.isLoading || (useAuth0.isAuthenticated && isTokenSet.not()) -> {
            styledDiv {
                css(AuthenticationWrapperClasses.authContainer)
                mCircularProgress()
            }
        }
        useAuth0.isAuthenticated -> {
            props.children()
        }
        else -> {
            styledDiv {
                css(AuthenticationWrapperClasses.authContainer)
                mButton(
                    caption = "Sign in",
                    color = MColor.primary,
                    variant = MButtonVariant.outlined,
                    onClick = { useAuth0.loginWithRedirect(null) }
                )
            }
        }
    }
}
