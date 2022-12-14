package ${packageName};

<%if (controllerName != "Screen") {
%>import io.jmix.ui.screen.Screen;<%
    superClass = "Screen"
} else {
    superClass = "io.jmix.ui.screen.Screen"}
%>
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import com.company.pm.screen.filter.SearchEvent;
import io.jmix.ui.Notifications;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;


<%if (classComment) {%>
${classComment}
<%}%>@UiController("${id}")
@UiDescriptor("${descriptorName}.xml")
public class ${controllerName} extends ${superClass} {

    @Autowired
    private Notifications notifications;

    @Subscribe(id = "filterFragment", target = Target.CONTROLLER)
    protected void onFilter(SearchEvent event) {
        notifications.create().withCaption(event.getSearchText()).show();
    }

}