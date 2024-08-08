package zip.neofin.chest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Chest {
    private final TaskRepository taskRepository;
    private final DependencyGraph dependencyGraph;
    private final ExecutorService executor;

    public Chest() {
        this.taskRepository = new TaskRepository();
        this.dependencyGraph = new DependencyGraph();
        this.executor = Executors.newCachedThreadPool();
    }

    public void defineTask(String taskName, List<String> dependencies, TaskDefinition definition) {
        Task task = new Task(taskName, dependencies, definition);
        taskRepository.addTask(task);
        dependencyGraph.addTask(task);
    }

    public CompletableFuture<Object> requestGoal(String goalName) {
        Task goal = taskRepository.getTask(goalName);
        if (goal == null) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("Goal not found: " + goalName));
        }

        return CompletableFuture.supplyAsync(() -> {
            List<Task> orderedTasks = dependencyGraph.getOrderedTasks(goal);
            return executeTasksInOrder(orderedTasks);
        }, executor);
    }

    private Object executeTasksInOrder(List<Task> tasks) {
        Object result = null;
        for (Task task : tasks) {
            result = executeTask(task);
        }
        return result;
    }

    private Object executeTask(Task task) {
        if (task.isComplex()) {
            List<Task> subtasks = task.breakdown();
            return executeTasksInOrder(subtasks);
        } else {
            return task.execute();
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}