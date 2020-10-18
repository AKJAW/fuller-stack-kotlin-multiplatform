package features.profile

import profile.userProfile
import react.RProps
import react.child
import react.functionalComponent

val profilePage = functionalComponent<RProps> {
    child(userProfile)
}
