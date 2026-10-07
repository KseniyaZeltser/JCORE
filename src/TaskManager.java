interface TaskManager {
    void addTask(Task task); // ReentrantLock

    Task getNextTask(); // ReentrantLock + Condition

    boolean completeTask(String id); // Atomic

    int getPendingTasksCount(); // AtomicInteger

    boolean tryCompleteTask(String id, long timeout); // tryLock
}