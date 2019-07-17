package com.cherkashyn.vitali.example.restfileserver.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

public class ReceiveFileEvent extends ApplicationEvent{
    public ReceiveFileEvent(MultipartFile source) {
        super(source);
    }

    @Override
    public MultipartFile getSource() {
        return (MultipartFile)super.getSource();
    }
}
