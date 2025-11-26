package com.jmeta.send;

public interface MessageSender {
    MessageResponse send(WhatsappMessage whatsappMessage);
}
