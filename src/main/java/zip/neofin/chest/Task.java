package zip.neofin.chest;

import java.util.List;

class Task {
    private final String name;
    private final List<String> dependencies;
    private final TaskDefinition definition;

    public Task(String name, List<String> dependencies, TaskDefinition definition) {
        this.name = name;
        this.dependencies = dependencies;
        this.definition = definition;
    }

    public String getName() {
        return name;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public boolean isComplex() {
        return definition.isComplex();
    }

    public List<Task> breakdown() {
        return definition.breakdown();
    }

    public Object execute() {
        return definition.execute();
    }
}