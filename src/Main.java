public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadSafeTaskManager manager = new ThreadSafeTaskManager();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                manager.addTask(new SimpleTask("task-" + i, "Task " + i));
                System.out.println("Отправлена задача" + i);
                try { Thread.sleep(100); } catch (InterruptedException e) { break; }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Task task = manager.getNextTask();
                if (task != null) {
                    task.complete();
                    System.out.println("Обработана задача " + (i+1));
                }
                try { Thread.sleep(300); } catch (InterruptedException e) { break; }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Оставшиеся задачи: " + manager.getPendingTasksCount());
    }
}
