package com.cherkashyn.vitali.example.restfileserver.listener;

import com.cherkashyn.vitali.example.restfileserver.event.ReceiveFileEvent;
import com.cherkashyn.vitali.example.restfileserver.service.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class StorageSaver {
    @Autowired
    Storage storage;

    @EventListener
    public void saveFile(ReceiveFileEvent event){
        storage.store(event.getSource());
    }
}
