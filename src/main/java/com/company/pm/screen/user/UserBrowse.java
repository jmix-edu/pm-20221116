package com.company.pm.screen.user;

import com.company.pm.entity.User;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.app.inputdialog.DialogActions;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.app.inputdialog.InputParameter;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.InputDialogFacet;
import io.jmix.ui.component.ProgressBar;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.executor.BackgroundTask;
import io.jmix.ui.executor.BackgroundTaskHandler;
import io.jmix.ui.executor.BackgroundWorker;
import io.jmix.ui.executor.TaskLifeCycle;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@UiController("User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
@Route("users")
public class UserBrowse extends StandardLookup<User> {
    private static final Logger log = LoggerFactory.getLogger(UserBrowse.class);
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private GroupTable<User> usersTable;
    @Autowired
    private Notifications notifications;
    @Autowired
    private BackgroundWorker backgroundWorker;
    @Autowired
    private ProgressBar emailSendingBar;
    @Autowired
    private InputDialogFacet sendEmailDialog;

    @Subscribe("usersTable.sendEmail")
    public void onUsersTableSendEmail(Action.ActionPerformedEvent event) {
        sendEmailDialog.show();
    }

    private void doSendEmail(String title, String body, Collection<User> users) {
        BackgroundTask<Integer, Void> task = new EmailTask(title, body, users);

        BackgroundTaskHandler<Void> handler = backgroundWorker.handle(task);
        handler.execute();

        emailSendingBar.setVisible(true);

//        dialogs.createBackgroundWorkDialog(this, task)
//                .withCaption("Sending emails")
//                .withMessage("Please wait...")
//                .withTotal(users.size())
//                .withShowProgressInPercentage(true)
//                .withCancelAllowed(true)
//                .show();
    }

    @Subscribe("sendEmailDialog")
    public void onSendEmailDialogInputDialogClose(InputDialog.InputDialogCloseEvent event) {
        if (event.closedWith(DialogOutcome.OK)) {
            doSendEmail(event.getValue("title"),
                    event.getValue("body"),
                    usersTable.getSelected());
        }
    }

    private class EmailTask extends BackgroundTask<Integer, Void> {

        private final String title;
        private final String body;
        private final Collection<User> users;

        public EmailTask(String title, String body, Collection<User> users) {
            super(10, TimeUnit.MINUTES, UserBrowse.this);
            this.title = title;
            this.body = body;
            this.users = users;
        }

        @Override
        public void progress(List<Integer> changes) {
            double change = changes.get(changes.size() - 1)+0.0;
            log.warn(change+" "+users.size());
            emailSendingBar.setValue(change / users.size());
        }

        @Override
        public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            int i = 0;
            for (User u : users) {
                if (taskLifeCycle.isCancelled()) {
                    break;
                }
                TimeUnit.SECONDS.sleep(2);
                i++;
                taskLifeCycle.publish(i);
            }
            return null;
        }

        @Override
        public void done(Void result) {
            emailSendingBar.setVisible(false);
            notifications.create()
                    .withCaption("Emails has been sent")
                    .withType(Notifications.NotificationType.TRAY)
                    .show();
        }


    }

}

