package ibm.java.academy.services.eduardocortes.entity.enums;

import ibm.java.academy.services.eduardocortes.constant.BusinessErrorMessages;
import ibm.java.academy.services.eduardocortes.exception.ResourceNotFoundException;
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
