interface Task {
    String getId();
    String getDescription();
    boolean isCompleted();
    void complete();
}