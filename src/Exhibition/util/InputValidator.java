package Exhibition.util;

import Exhibition.model.Participant;

public class InputValidator {
    public static boolean validateParticipant(Participant participant) {
        if (participant.getRegistrationId() == null || participant.getRegistrationId().trim().isEmpty() ||
            participant.getName() == null || participant.getName().trim().isEmpty() ||
            participant.getFaculty() == null || participant.getFaculty().trim().isEmpty() ||
            participant.getProjectTitle() == null || participant.getProjectTitle().trim().isEmpty() ||
            participant.getContact() == null || participant.getContact().trim().isEmpty() ||
            participant.getEmail() == null || participant.getEmail().trim().isEmpty()) {
            return false;
        }
        
        if (!participant.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return false;
        }
        
        if (!participant.getContact().matches("^[0-9+\\- ]+$")) {
            return false;
        }
        
        return true;
    }
}
