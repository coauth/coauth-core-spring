package org.coauth.core.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.coauth.core.commons.constants.ApplicationConstants;
import org.junit.jupiter.api.Test;

class RecordStatusTest {

  @Test
  void recordStatusTest() {
    String inActiveValue = "N";
    String activeValue = "A";
    String disabledValue = "D";
    assertEquals(inActiveValue, ApplicationConstants.RecordStatus.INACTIVE.getValue());
    assertEquals(activeValue, ApplicationConstants.RecordStatus.ACTIVE.getValue());
    assertEquals(disabledValue, ApplicationConstants.RecordStatus.DISABLED.getValue());

    assertEquals("role", ApplicationConstants.JWT_ROLE_KEY);
    assertEquals("systemId", ApplicationConstants.JWT_SYSTEM_ID_KEY);
    assertEquals("-", ApplicationConstants.DB_PAD_CHAR);
  }
}
