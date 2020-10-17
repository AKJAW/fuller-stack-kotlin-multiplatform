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
import styled.StyleSheet
import styled.css
import styled.styledDiv

private object AuthenticationWrapperClasses : StyleSheet("AuthenticationWrapper", isStatic = true) {
    val authContainer by css {
        textAlign = TextAlign.center
        marginTop = LinearDimension("150px")
    }
}

val authenticationWrapper = functionalComponent<RProps> { props ->

    val useAuth0 = UseAuth0()
    console.log(useAuth0)
    when {
        useAuth0.isLoading -> {
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
