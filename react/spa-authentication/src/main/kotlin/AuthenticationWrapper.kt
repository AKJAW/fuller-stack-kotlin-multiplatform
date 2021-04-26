import com.ccfraser.muirwik.components.MColor
import com.ccfraser.muirwik.components.MTypographyAlign
import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.mCircularProgress
import com.ccfraser.muirwik.components.mTypography
import kotlinx.css.LinearDimension
import kotlinx.css.TextAlign
import kotlinx.css.fontSize
import kotlinx.css.margin
import kotlinx.css.marginTop
import kotlinx.css.textAlign
import kotlinx.css.width
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
    val text by css {
        width = LinearDimension("50%")
        margin = "0 auto 20px"
    }
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
                styledDiv {
                    css(AuthenticationWrapperClasses.text)
                    mTypography(
                        text = "To use Fuller Stack you have to be authenticated",
                        align = MTypographyAlign.center
                    ) {
                        css {
                            fontSize = LinearDimension("20px")
                        }
                    }
                }
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
