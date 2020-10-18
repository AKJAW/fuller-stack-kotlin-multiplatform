package profile

import com.ccfraser.muirwik.components.MTypographyVariant
import com.ccfraser.muirwik.components.mTypography
import kotlinx.css.Color
import kotlinx.css.color
import react.RProps
import react.functionalComponent
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface ProfileTextProps: RProps {
    var label: String
    var text: String?
}

@Suppress("MagicNumber")
private object ProfileTextClasses : StyleSheet("UserProfile", isStatic = true) {
    val label by css {
        color = Color("#8e8e8e")
    }
    val text by css {

    }
}

val profileText = functionalComponent<ProfileTextProps> { props ->
    styledDiv {
        mTypography(text = "${props.label} ", variant = MTypographyVariant.h4, component = "span") {
            css(ProfileTextClasses.label)
        }
        val text = props.text ?: "Unavailable"
        mTypography(text = text, variant = MTypographyVariant.h4, component = "span") {
            css(ProfileTextClasses.text)
        }
    }
}
