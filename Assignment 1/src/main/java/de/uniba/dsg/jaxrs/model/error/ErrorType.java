package de.uniba.dsg.jaxrs.model.error;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum(String.class)
public enum ErrorType {
    INVALID_PARAMETER,ORDER_ALREADY_PROCESSED,ORDER_PROCESSING_FAILED
}
