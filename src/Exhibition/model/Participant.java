package Exhibition.model;

public class Participant {
    private String registrationId;
    private String name;
    private String faculty;
    private String projectTitle;
    private String contact;
    private String email;
    private String imagePath;

    public Participant(String registrationId, String name, String faculty, 
                      String projectTitle, String contact, String email, 
                      String imagePath) {
        this.registrationId = registrationId;
        this.name = name;
        this.faculty = faculty;
        this.projectTitle = projectTitle;
        this.contact = contact;
        this.email = email;
        this.imagePath = imagePath;
    }

    // Registration ID getter/setter
    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    // Name getter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Faculty getter/setter
    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    // Project Title getter/setter
    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    // Contact getter/setter
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // Email getter/setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Image Path getter/setter
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Optional: toString() method for debugging
    @Override
    public String toString() {
        return "Participant{" +
                "registrationId='" + registrationId + '\'' +
                ", name='" + name + '\'' +
                ", faculty='" + faculty + '\'' +
                ", projectTitle='" + projectTitle + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}