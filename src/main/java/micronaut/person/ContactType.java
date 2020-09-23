package micronaut.person;

import io.micronaut.core.annotation.Introspected;

@Introspected
public enum ContactType {
    WORK_NUMBER {

        public String toString() {
            return "Work Number";
        }
    },

    HOME_NUMBER {

        public String toString() {
            return "Home Number";
        }
    },
    MOBILE_NUMBER {

        public String toString() {
            return "Mobile Number";
        }
    },
    EMAIL_ADDRESS {

        public String toString() {
            return "Email Address";
        }
    }
}