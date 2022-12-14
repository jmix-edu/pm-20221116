package com.company.pm.screen.filter;

import io.jmix.core.common.event.Subscription;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

@UiController("FilterFragment")
@UiDescriptor("filter-fragment.xml")
public class FilterFragment extends ScreenFragment {
    @Autowired
    private TextField<String> filterText;

    public Subscription addFilterListener(Consumer<SearchEvent> listener) {
        return getEventHub().subscribe(SearchEvent.class, listener);
    }
    @Subscribe("searchBnt")
    public void onSearchBntClick(Button.ClickEvent event) {
        fireEvent(SearchEvent.class, new SearchEvent(FilterFragment.this, filterText.getValue()));
    }
}