package tests.jbehave.googlemail;

import java.util.List;

import org.jbehave.core.steps.InjectableStepsFactory;
import tests.jbehave.googlemail.steps.GoogleMailTestSteps;

import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InstanceStepsFactory;

/**
 * Created by Gadzilla on 25.10.2015.
 */
public class GoogleMailTestStories extends JUnitStories {

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration().useStoryLoader(
                new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder().withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                                .withFormats(Format.CONSOLE, Format.TXT, Format.HTML));
    }
    /*
    @Override
    public List<CandidateSteps> candidateSteps () {
        return new InstanceStepsFactory(configuration(), new GoogleMailTestSteps()).createCandidateSteps();
    }
    */
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new GoogleMailTestSteps());
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder()
                .findPaths(CodeLocations.codeLocationFromClass(this.getClass()).getFile(), "**/*.story", null);
    }

    @org.testng.annotations.Test
    public void run() throws Throwable {
        super.run();
    }
}
