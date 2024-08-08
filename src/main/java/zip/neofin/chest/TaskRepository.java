package zip.neofin.chest;

import java.util.HashMap;
import java.util.Map;

class TaskRepository {
    private static final TaskRepository instance = new TaskRepository();
    private final Map<String, Task> tasks = new HashMap<>();

    TaskRepository() {}

    public static TaskRepository getInstance() {
        return instance;
    }

    public void addTask(Task task) {
        tasks.put(task.getName(), task);
    }

    public Task getTask(String taskName) {
        return tasks.get(taskName);
    }
}