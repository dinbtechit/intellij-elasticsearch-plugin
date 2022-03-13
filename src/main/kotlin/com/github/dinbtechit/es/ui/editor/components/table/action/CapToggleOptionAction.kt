
import com.intellij.openapi.actionSystem.ToggleOptionAction
import com.intellij.openapi.project.DumbAware

open class CapToggleOptionAction(val option: Option): ToggleOptionAction(option), DumbAware {
}