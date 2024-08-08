package zip.neofin.chest;

import java.util.*;

class DependencyGraph {
    private final Map<String, Set<String>> graph = new HashMap<>();

    public void addTask(Task task) {
        graph.putIfAbsent(task.getName(), new HashSet<>());
        for (String dep : task.getDependencies()) {
            graph.get(task.getName()).add(dep);
        }
    }

    public List<Task> getOrderedTasks(Task goal) {
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        dfs(goal.getName(), visited, stack);

        List<Task> orderedTasks = new ArrayList<>();
        while (!stack.isEmpty()) {
            String taskName = stack.pop();
            Task task = TaskRepository.getInstance().getTask(taskName);
            if (task != null) {
                orderedTasks.add(task);
            }
        }
        return orderedTasks;
    }

    private void dfs(String taskName, Set<String> visited, Stack<String> stack) {
        visited.add(taskName);

        Set<String> dependencies = graph.get(taskName);
        if (dependencies != null) {
            for (String dep : dependencies) {
                if (!visited.contains(dep)) {
                    dfs(dep, visited, stack);
                }
            }
        }

        stack.push(taskName);
    }
}