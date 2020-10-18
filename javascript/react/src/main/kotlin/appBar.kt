
import com.ccfraser.muirwik.components.MAppBarPosition
import com.ccfraser.muirwik.components.MTypographyVariant
import com.ccfraser.muirwik.components.mAppBar
import com.ccfraser.muirwik.components.mAvatar
import com.ccfraser.muirwik.components.mContainer
import com.ccfraser.muirwik.components.mToolbar
import com.ccfraser.muirwik.components.mTypography
import com.ccfraser.muirwik.components.styles.Breakpoint
import kotlinx.css.Color
import kotlinx.css.Cursor
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.css.LinearDimension
import kotlinx.css.a
import kotlinx.css.backgroundColor
import kotlinx.css.color
import kotlinx.css.cursor
import kotlinx.css.display
import kotlinx.css.justifyContent
import kotlinx.css.marginBottom
import kotlinx.css.properties.TextDecoration
import kotlinx.css.textDecoration
import react.RProps
import react.functionalComponent
import react.router.dom.navLink
import styled.StyleSheet
import styled.css

@Suppress("MagicNumber")
private object AppBarClasses : StyleSheet("AppBar", isStatic = true) {
    val root by css {
        marginBottom = LinearDimension("15px")
        specific {
            backgroundColor = Color("#b0bcff")
            color = Color.black
        }
        a {
            textDecoration = TextDecoration.none
            color = Color.inherit
        }
    }
    val toolbarRoot by css {
        display = Display.flex
        justifyContent = JustifyContent.spaceBetween
    }
    val avatar by css {
        cursor = Cursor.pointer
    }
}

val appBar = functionalComponent<RProps> {
    val user = UseAuth0().user
    mAppBar(position = MAppBarPosition.static) {
        css(AppBarClasses.root)
        mContainer(maxWidth = Breakpoint.lg) {
            mToolbar {
                css(AppBarClasses.toolbarRoot)
                navLink<RProps>("/") {
                    mTypography("Fuller stack", variant = MTypographyVariant.h6)
                }
                navLink<RProps>("/profile") {
                    mAvatar(src = user?.picture) {
                        css(AppBarClasses.avatar)
                    }
                }
            }
        }
    }
}
