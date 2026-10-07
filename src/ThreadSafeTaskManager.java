import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

    public class ThreadSafeTaskManager implements TaskManager {
        private final Queue<Task> taskQueue = new LinkedList<>();
        private final AtomicInteger pendingTasksCount = new AtomicInteger(0);
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition hasTasks = lock.newCondition();

        @Override
        public void addTask(Task task) {
            lock.lock();
            try {
                taskQueue.offer(task);
                pendingTasksCount.incrementAndGet();
                hasTasks.signal();
            } finally {
                lock.unlock();
            }
        }
        @Override
        public Task getNextTask() {
            lock.lock();
            try {
                while (taskQueue.isEmpty()) {
                    hasTasks.await();
                }
                Task task = taskQueue.poll();
                if (task != null) {
                    pendingTasksCount.decrementAndGet();
                }
                return task;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } finally {
                lock.unlock();
            }
        }
        @Override
        public boolean completeTask(String id) {
            System.out.println("Задача " + id + " отмечена как выполненная");
            return true;
        }

        @Override
        public int getPendingTasksCount() {
            return pendingTasksCount.get();
        }

        @Override
        public boolean tryCompleteTask(String id, long timeout) {
            try {
                if (lock.tryLock(timeout, TimeUnit.MILLISECONDS)) {
                    try {
                        if (!taskQueue.isEmpty()) {
                            Task task = taskQueue.poll();
                            if (task != null) {
                                task.complete();
                                pendingTasksCount.decrementAndGet();
                                return true;
                            }
                        }
                        return false;
                    } finally {
                        lock.unlock();
                    }
                }
                return false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

    }