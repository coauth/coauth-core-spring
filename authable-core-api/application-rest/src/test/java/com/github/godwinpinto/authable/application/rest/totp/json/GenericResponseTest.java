package com.github.godwinpinto.authable.application.rest.totp.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GenericResponseTest {
    /**
     * Method under test: {@link GenericResponse#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        assertFalse((new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"))
                .canEqual("Other"));
    }

    /**
     * Method under test: {@link GenericResponse#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        GenericResponse genericResponse = new GenericResponse("Status Code", "Status Description",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        assertTrue(genericResponse.canEqual(
                new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY")));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link GenericResponse#GenericResponse()}
     *   <li>{@link GenericResponse#setSecretKey(String)}
     *   <li>{@link GenericResponse#setStatusCode(String)}
     *   <li>{@link GenericResponse#setStatusDescription(String)}
     *   <li>{@link GenericResponse#toString()}
     *   <li>{@link GenericResponse#getSecretKey()}
     *   <li>{@link GenericResponse#getStatusCode()}
     *   <li>{@link GenericResponse#getStatusDescription()}
     * </ul>
     */
    @Test
    void testConstructor() {
        GenericResponse actualGenericResponse = new GenericResponse();
        actualGenericResponse.setSecretKey("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        actualGenericResponse.setStatusCode("Status Code");
        actualGenericResponse.setStatusDescription("Status Description");
        String actualToStringResult = actualGenericResponse.toString();
        assertEquals("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY", actualGenericResponse.getSecretKey());
        assertEquals("Status Code", actualGenericResponse.getStatusCode());
        assertEquals("Status Description", actualGenericResponse.getStatusDescription());
        assertEquals("GenericResponse(statusCode=Status Code, statusDescription=Status Description, secretKey=EXAMPLEKEYwj"
                + "alrXUtnFEMI/K7MDENG/bPxRfiCY)", actualToStringResult);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link GenericResponse#GenericResponse(String, String, String)}
     *   <li>{@link GenericResponse#setSecretKey(String)}
     *   <li>{@link GenericResponse#setStatusCode(String)}
     *   <li>{@link GenericResponse#setStatusDescription(String)}
     *   <li>{@link GenericResponse#toString()}
     *   <li>{@link GenericResponse#getSecretKey()}
     *   <li>{@link GenericResponse#getStatusCode()}
     *   <li>{@link GenericResponse#getStatusDescription()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        GenericResponse actualGenericResponse = new GenericResponse("Status Code", "Status Description",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        actualGenericResponse.setSecretKey("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        actualGenericResponse.setStatusCode("Status Code");
        actualGenericResponse.setStatusDescription("Status Description");
        String actualToStringResult = actualGenericResponse.toString();
        assertEquals("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY", actualGenericResponse.getSecretKey());
        assertEquals("Status Code", actualGenericResponse.getStatusCode());
        assertEquals("Status Description", actualGenericResponse.getStatusDescription());
        assertEquals(
                "GenericResponse(statusCode=Status Code, statusDescription=Status Description, secretKey=EXAMPLEKEYwj"
                        + "alrXUtnFEMI/K7MDENG/bPxRfiCY)",
                actualToStringResult);
    }

    /**
     * Method under test: {@link GenericResponse#equals(Object)}
     */
    @Test
    void testEquals() {
        assertNotEquals(
                new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"), null);
        assertNotEquals(
                new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"),
                "Different type to GenericResponse");
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link GenericResponse#equals(Object)}
     *   <li>{@link GenericResponse#hashCode()}
     * </ul>
     */
    @Test
    void testEquals2() {
        GenericResponse genericResponse = new GenericResponse("Status Code", "Status Description",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        assertEquals(genericResponse, genericResponse);
        int expectedHashCodeResult = genericResponse.hashCode();
        assertEquals(expectedHashCodeResult, genericResponse.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link GenericResponse#equals(Object)}
     *   <li>{@link GenericResponse#hashCode()}
     * </ul>
     */
    @Test
    void testEquals3() {
        GenericResponse genericResponse = new GenericResponse("Status Code", "Status Description",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        GenericResponse genericResponse2 = new GenericResponse("Status Code", "Status Description",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");

        assertEquals(genericResponse, genericResponse2);
        int expectedHashCodeResult = genericResponse.hashCode();
        assertEquals(expectedHashCodeResult, genericResponse2.hashCode());
    }

    /**
     * Method under test: {@link GenericResponse#equals(Object)}
     */
    @Test
    void testEquals4() {
        GenericResponse genericResponse = new GenericResponse("Status Description", "Status Description",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        assertNotEquals(genericResponse,
                new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
    }

    /**
     * Method under test: {@link GenericResponse#equals(Object)}
     */
    @Test
    void testEquals5() {
        GenericResponse genericResponse = new GenericResponse(null, "Status Description",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        assertNotEquals(genericResponse,
                new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
    }

    /**
     * Method under test: {@link GenericResponse#equals(Object)}
     */
    @Test
    void testEquals6() {
        GenericResponse genericResponse = new GenericResponse("Status Code", "Status Code",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        assertNotEquals(genericResponse,
                new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
    }

    /**
     * Method under test: {@link GenericResponse#equals(Object)}
     */
    @Test
    void testEquals7() {
        GenericResponse genericResponse = new GenericResponse("Status Code", null,
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        assertNotEquals(genericResponse,
                new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
    }

    /**
     * Method under test: {@link GenericResponse#equals(Object)}
     */
    @Test
    void testEquals8() {
        GenericResponse genericResponse = new GenericResponse("Status Code", "Status Description", "Status Code");
        assertNotEquals(genericResponse,
                new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
    }

    /**
     * Method under test: {@link GenericResponse#equals(Object)}
     */
    @Test
    void testEquals9() {
        GenericResponse genericResponse = new GenericResponse("Status Code", "Status Description", null);
        assertNotEquals(genericResponse,
                new GenericResponse("Status Code", "Status Description", "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link GenericResponse#equals(Object)}
     *   <li>{@link GenericResponse#hashCode()}
     * </ul>
     */
    @Test
    void testEquals10() {
        GenericResponse genericResponse = new GenericResponse(null, "Status Description",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        GenericResponse genericResponse2 = new GenericResponse(null, "Status Description",
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");

        assertEquals(genericResponse, genericResponse2);
        int expectedHashCodeResult = genericResponse.hashCode();
        assertEquals(expectedHashCodeResult, genericResponse2.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link GenericResponse#equals(Object)}
     *   <li>{@link GenericResponse#hashCode()}
     * </ul>
     */
    @Test
    void testEquals11() {
        GenericResponse genericResponse = new GenericResponse("Status Code", null,
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
        GenericResponse genericResponse2 = new GenericResponse("Status Code", null,
                "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");

        assertEquals(genericResponse, genericResponse2);
        int expectedHashCodeResult = genericResponse.hashCode();
        assertEquals(expectedHashCodeResult, genericResponse2.hashCode());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link GenericResponse#equals(Object)}
     *   <li>{@link GenericResponse#hashCode()}
     * </ul>
     */
    @Test
    void testEquals12() {
        GenericResponse genericResponse = new GenericResponse("Status Code", "Status Description", null);
        GenericResponse genericResponse2 = new GenericResponse("Status Code", "Status Description", null);

        assertEquals(genericResponse, genericResponse2);
        int expectedHashCodeResult = genericResponse.hashCode();
        assertEquals(expectedHashCodeResult, genericResponse2.hashCode());
    }
}
