class SimpleTask implements Task {
    private final String id;
    private final String description;
    private volatile boolean completed;

    public SimpleTask(String id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getDescription() { return description; }

    @Override
    public boolean isCompleted() { return completed; }

    @Override
    public void complete() { completed = true; }

}
