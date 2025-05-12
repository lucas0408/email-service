package com.loja.email_service.DTO;

import java.util.UUID;

public record EmailDTO(UUID userId,
                       String emailTo,
                       String subject,
                       String text) {
}
