package com.company.pm.bdd;

import com.company.pm.bdd.uimodel.*;
import com.thoughtworks.gauge.Step;
import io.jmix.masquerade.Selectors;
import io.jmix.masquerade.component.HasActions;

import static com.codeborne.selenide.Selenide.open;
import static io.jmix.masquerade.Conditions.ENABLED;
import static io.jmix.masquerade.Conditions.VISIBLE;
import static io.jmix.masquerade.Selectors.$j;

//Run the application before executing this test
//To start the test from the IDE you need to install Gauge plugin
public class TaskBddTestSpec {

    @Step("Open application in the browser")
    public void openApplicationUrl() {
        open("/");
    }

    @Step("Log in as user with <username> username and <password> password")
    public void login(String username, String password) {
        LoginScreen loginScreen = $j(LoginScreen.class);

        loginScreen.getUsernameField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue(username);
        loginScreen.getPasswordField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue(password);

        loginScreen.getLoginButton()
                .shouldBe(VISIBLE)
                .click();
    }

    @Step("Open the task browser")
    public void openTaskBrowser() {
        $j(MainScreen.class).openTaskBrowse();
    }

    @Step("Open the task editor")
    public void openTaskEditor() {
        $j(TaskBrowse.class).create();
    }

    @Step("Fill form fields with following values: name is <name>, start date is <startDate>, " +
            "estimated efforts are <estimatedEfforts>, project is <project>")
    public void fillFormFields(String name, String startDate, String estimatedEfforts, String project) {
        TaskEdit taskEdit = $j(TaskEdit.class);
        taskEdit.getNameField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue(name);
        taskEdit.getStartDateField()
                .shouldBe(VISIBLE, ENABLED)
                .setDateValue(startDate);
        taskEdit.getHoursPlannedField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue(estimatedEfforts);
        ProjectBrowse projectBrowse = taskEdit.getProjectField()
                .shouldBe(VISIBLE, ENABLED)
                .triggerAction(ProjectBrowse.class, new HasActions.Action("entityLookup"));
        projectBrowse.selectProject(project);
    }

    @Step("Save new task")
    public void saveNewTask() {
        $j(TaskEdit.class).commitAndClose();
    }

    @Step("Make sure the new task with <name> name is added to tasks table")
    public void checkTaskCreation(String name) {
        $j(TaskBrowse.class).getTasksTable()
                .shouldBe(VISIBLE, ENABLED)
                .getRow(Selectors.byText(name))
                .shouldBe(VISIBLE);
    }
}
