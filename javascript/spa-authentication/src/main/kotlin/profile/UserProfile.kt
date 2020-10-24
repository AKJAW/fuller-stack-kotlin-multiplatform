package profile

import LogoutOptions
import com.ccfraser.muirwik.components.MGridAlignItems
import com.ccfraser.muirwik.components.MGridJustify
import com.ccfraser.muirwik.components.MGridSize
import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.mAvatar
import com.ccfraser.muirwik.components.mGridContainer
import com.ccfraser.muirwik.components.mGridItem
import kotlinx.css.Color
import kotlinx.css.LinearDimension
import kotlinx.css.TextAlign
import kotlinx.css.borderColor
import kotlinx.css.color
import kotlinx.css.fontSize
import kotlinx.css.height
import kotlinx.css.margin
import kotlinx.css.marginBottom
import kotlinx.css.marginTop
import kotlinx.css.textAlign
import kotlinx.css.width
import react.RProps
import react.child
import react.functionalComponent
import styled.StyleSheet
import styled.css
import useAuth0
import kotlin.browser.window

@Suppress("MagicNumber")
private object UserProfileClasses : StyleSheet("UserProfile", isStatic = true) {
    val root by css {
        marginTop = LinearDimension("50px")
        textAlign = TextAlign.center
    }
    val avatarContainer by css {
        specific {
            marginBottom = LinearDimension("50px")
            children {
                width = LinearDimension("100px")
                height = LinearDimension("100px")
                margin(LinearDimension.auto)
            }
        }
    }
    val logOutButton by css {
        specific {
            marginTop = LinearDimension("50px")
            fontSize = LinearDimension("20px")
            borderColor = Color("#ff4c4c")
            color = Color("#ff4c4c")
        }
    }
}

val userProfile = functionalComponent<RProps> {
    val useAuth0 = useAuth0()
    val user = useAuth0.user

    val logOutOptions = object : LogoutOptions {
        override var returnTo: String? = window.location.origin
    }

    mGridContainer(justify = MGridJustify.center, alignItems = MGridAlignItems.center) {
        css(UserProfileClasses.root)
        mGridItem(xs = MGridSize.cells12) {
            css(UserProfileClasses.avatarContainer)
            mAvatar(src = user?.picture)
        }
        if (user?.name != user?.email) {
            mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
                child(profileText) {
                    attrs.label = "Name"
                    attrs.text = user?.name
                }
            }
            mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
                child(profileText) {
                    attrs.label = "Email"
                    attrs.text = user?.email
                }
            }
        } else {
            mGridItem(xs = MGridSize.cells12) {
                child(profileText) {
                    attrs.label = "Email"
                    attrs.text = user?.email
                }
            }
        }
        mGridItem(xs = MGridSize.cells12) {
            mButton(
                caption = "Log out",
                variant = MButtonVariant.outlined,
                onClick = { useAuth0.logout(logOutOptions) }
            ) {
                css(UserProfileClasses.logOutButton)
            }
        }
    }
}
