package com.tomkato.issuetracker;

public class Ticket implements java.io.Serializable {
    private String id;
    private String name;
    private String project;
    private String description;
    private Status status;
    
    public Ticket() {}
    
    public Ticket(String id, String name, String project, String description, Status status) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.description = description;
        this.status = status;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getProject() {
        return project;
    }
    
    public void setProject(String project) {
        this.project = project;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String Description) {
        this.description = description;
    }
    
    public String getStatus() {
        return status.toString();
    }
    
    public void setStatus(String status) {
        this.status = Status.fromString(status);
    }
    
    enum Status {
        OPEN("open"),
        CLOSED("closed");
        
        private String text;
        
        Status(String text) {
            this.text=text;
        }
        
        public static Status fromString(String input) {
            for (Status x : Status.values()) {
                if(x.text.equalsIgnoreCase(input)) {
                    return x;
                }
            }
            return null;
        }
    }
}