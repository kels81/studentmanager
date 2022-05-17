package ibm.java.academy.services.entity.enums;

import ibm.java.academy.services.constant.BusinessErrorMessages;
import ibm.java.academy.services.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Eduardo Cortés
 */

@AllArgsConstructor
@Getter
public enum GenderEnum {

    FEMALE('F'), MALE('M');

    private final char key;

    public static char getClaveType(char key) {
        for (GenderEnum type : values()) {
            if (type.getKey() == key) {
                return type.getKey();
            }
        }
        throw new ResourceNotFoundException(BusinessErrorMessages.GENDER_KEY_INVALID + key);
    }

}
